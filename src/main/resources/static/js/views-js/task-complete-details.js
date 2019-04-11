//控制用户的主页操作
var currentResponder;//当前处理人
var currentUser;
$(function () {
    var taskId=getQueryVariable("id");
    get_task_complete_details(taskId);
    get_user();
});
function get_user() {//后台用拦截器拦截，前端就不必进来一次请求一次
    $.ajax({
        url:"/user/get_user_info.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){//如果存在当前用户，执行
                //保存返回的用户信息，填充到html
                currentUser=result.data;
            }
        }
    })
}
//填充信息，设置页面只可读
function task_complete_details_html(result) {
    currentResponder=result.data.taskResponder;
    get_all_responder();//此时全局变量responderList填充完毕

    $("#task-title").val(result.data.taskTitle);
    $("#task-body").val(result.data.taskDetails);

    var statusSelect=$("#task-status-select");
    statusSelect.empty();
    var val=-1;
    var arr=new Array("待处理","处理中","已处理","已关闭");//状态数组
    var arr2=new Array(20004,20003,20002,20001);
    $.each(arr,function (index,item) {
        val=val+1;
        var status=$("<option></option>").append(item);//循环产生各选项
        status.attr("value",arr2[val]);
        statusSelect.append(status);
    });
    switch (result.data.taskStatus){//设值状态
        case 20001:statusSelect.find("option[value='20001']").attr("selected",true);break;
        case 20002:statusSelect.find("option[value='20002']").attr("selected",true);break;
        case 20003:statusSelect.find("option[value='20003']").attr("selected",true);break;
        case 20004:statusSelect.find("option[value='20004']").attr("selected",true);break;
    }
    var typeSelect=$("#task-type");
    switch (result.data.taskType){//设值状态
        case '请假':typeSelect.find("option[value='11']").attr("selected",true);break;
        // case '资金申请':typeSelect.find("option[value='22']").attr("selected",true);break;
        case '活动申请':typeSelect.find("option[value='33']").attr("selected",true);break;
        case '其他':typeSelect.find("option[value='44']").attr("selected",true);break;
    }
    var passSelect=$("#task-pass-select");
    switch (result.data.taskPass){//设值状态
        case '不通过':passSelect.find("option[value='0']").attr("selected",true);break;
        case '通过':passSelect.find("option[value='1']").attr("selected",true);break;
        case '待定':passSelect.find("option[value='2']").attr("selected",true);break;
    }

    set_status_time(result.data.taskStatus);//设置时间线
    $("#task-requester").text(result.data.taskRequester);//接下来一一设置对应值
    $("#task-time").text(renderTime(result.data.createTime));
    if(result.data.taskRequester==currentUser.userName){//发起人不能编辑是否通过等
        $("#task-pass-select").attr('disabled', true);//设置页面只可读
        $("#task-result").parent().empty();
        $("#task-result").parent().append("<p>"+result.data.taskResult+"</p>");
    }else {
        $("#task-result").val(result.data.taskResult);
    }

    $("#task-title").attr("task-id",result.data.taskId);//自定义属性设置id

    if((!check_level(currentUser.userLevel)
        && result.data.taskRequester!=currentUser.userName)
        || result.data.taskStatus==20001){//判断级别,级别不够且不是发起人，或者任务状态为已关闭的不能修改
        $("input").attr('readonly', true);//设置页面只可读
        $("textarea").attr('readonly', true);
        $(':radio').attr('disabled', true);
        $(':checkbox').attr('disabled', true);
        $('a').removeAttr('onclick');
        $('select').attr('disabled', true);
    }
    else if((check_level(currentUser.userLevel)
            || result.data.taskRequester==currentUser.userName)
            && result.data.taskStatus!=20001){//如果级别够高增加修改按钮
        var btn=$("<div class=\"detail-item\">\n" +
            "                                <div class=\"layui-input-block\">\n" +
            "                                    <button id=\"task-save-btn\" class=\"layui-btn layui-btn-normal\">保存\n" +
            "                                    </button>\n" +
            "                                    <button id=\"task-reset-btn\" type=\"reset\" class=\"layui-btn layui-btn-primary\">重置\n" +
            "                                    </button>\n" +
            "                                </div>\n" +
            "                            </div>");
        $("#completed").append(btn);
    }
}
//请求详情页信息
function get_task_complete_details(id) {
    var taskCompleteId=id;
    $.ajax({
        url:"/task/get_task_details.do",
        type:"POST",
        dataType:"json",
        data:{
            taskId:taskCompleteId
        },
        success:function (result) {
            if(result.status==200){
                task_complete_details_html(result);
                set_reply(result);
                console.log(result);
            }
        },
        error:function () {
            task_complete_details_html(null);
        }
    })
}
//获取所有处理人
function get_all_responder() {
    $.ajax({//由于全局变量保存不了ajax的返回结果result.data.list。。。这里这能写一半的填充代码到这里
        url:"/task/get_all_responder.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){
                var responderList=result.data.list;
                var responderSelect=$("#task-responder-select");
                responderSelect.empty();
                var val=0;
                $.each(responderList,function (index, item) {
                    val=val+1;
                    var responder=$("<option></option>").append(item.userName);
                    responder.attr("value",val);//设置option值
                    if(item.userName==currentResponder){//循环用户等于当前任务的处理人
                        responder.attr("selected",true);//设置为已选中
                    }
                    responderSelect.append(responder);
                });
                console.log(result);
            }
        }
    });
}
//设置状态时间线显示
function set_status_time(status) {
    switch (status){//设值状态
        case 20001:;break;
        case 20002:$("#yiguanbi").remove();break;
        case 20003:$("#yiguanbi").remove();$("#yichuli").remove();break;
        case 20004:$("#yiguanbi").remove();$("#yichuli").remove();$("#chulizhong").remove();break;
    }
}
//设置回复信息
function set_reply(result) {
    var reply=result.data.taskMessage;//"#OoJou:已验收A#nico:已验收B#",
    var reply_split=reply.split('#');
    var reply_split_result=new Array();
    var flag=0;
    for (var i in reply_split){
        if (reply_split[i]!==""){
            reply_split_result[flag]=reply_split[i];
            flag=flag+1;
        }//最终reply_split_result保留1.OoJou:已验收A 2.nico:已验收B
    }

    //填充到页面
    var name_and_message;
    var name=new Array();
    var message=new Array();
    flag=0;
    var num=0;
    for (var l in reply_split_result){
        var k=0;
        name_and_message=reply_split_result[l].split(':');
        for (k in name_and_message){
            if(num==0){
                num=num+1;
                name[flag]=name_and_message[k];
            }else {
                num=num-1;
                message[flag]=name_and_message[k];
                flag=flag+1;
            }
        }
    }
    $("#answer-body").empty();
    for (var j in name){
        var message_div=$("<div class=\"layui-card-body\"></div>").append(message[j]);
        var name_div=$("<div class=\"answer-info\"></div>").append("回复人  --  "+name[j]);
        var model=$("<div class=\"layui-card\" style=\"margin-bottom: 4px;border: 1px solid #e7e7e7;\"></div>").append(message_div).append(name_div);
        $("#answer-body").append(model);
    }
}
//判断级别，确定是否能修改状态(这里只设置可写，其实后台有逻辑判断，级别不够这里改了也没用)
function check_level(level) {
    switch (level){
        case 10001:return true;break;
        case 10002:return true;break;
        case 10003:return true;break;
        case 10004:return false;break;
        case 10005:return false;break;
    }
}

//返回首页
$(document).on("click","#back-btn",function () {
    var html=getQueryVariable("html");
    if(html=="task_completed"){
        window.location.href="task_completed";
    }
    if(!html) {
        window.location.href="show";
    }
});

//任务详情页 保存
$(document).on("click","#task-save-btn",function () {
    //保存各类信息，统一上传
    var taskId=$("#task-title").attr("task-id");
    var taskTitle=$("#task-title").val();
    var taskDetails=$("#task-body").val();
    var taskResponder=$("#task-responder-select").find("option:selected").text();
    var taskStatus=$("#task-status-select").find("option:selected").val();
    var taskRequester=$("#task-requester").text();
    var taskType=$("#task-type").find("option:selected").text();
    var taskPass=$("#task-pass-select").find("option:selected").text();
    // $("#task-result").val()==null||$("#task-result").val()==""
    if($("#task-result").val()==null||$("#task-result").val()==""){
        var taskResult=$("#task-result").text()
    }else {
        var taskResult=$("#task-result").val();
    }
    $.ajax({
        url:"/task/handle_task.do",
        type:"POST",
        dataType:"json",
        data:{
            taskId:taskId,
            taskTitle:taskTitle,
            taskDetails:taskDetails,
            taskResponder:taskResponder,
            taskRequester:taskRequester,
            taskStatus:taskStatus,
            taskType:taskType,
            taskPass:taskPass,
            taskResult:taskResult
        },
        success:function (result) {
            if(result.status==200){
                console.log(result);
                var html=getQueryVariable("html");
                if(html=="task_completed"){
                    window.location.href="task_complete_details?id="+taskId + "&html=task_completed";
                }
                if(!html) {
                    window.location.href="task_complete_details?id="+taskId;
                }
            }
        }
    })
});

//任务详情页 重置
$(document).on("click","#task-reset-btn",function () {
    //重新用id请求一次详情页
    var taskId=$("#task-title").attr("task-id");
    window.location.href="task_complete_details?id="+taskId;
    var html=getQueryVariable("html");
    if(html=="task_completed"){
        window.location.href="task_complete_details?id="+taskId + "&html=task_completed";
    }
    if(!html) {
        window.location.href="task_complete_details?id="+taskId;
    }
});

//前端时间转换2019-01-13T05:22:01.000+0000 --> 2019-01-13 13:22:01
function renderTime(date) {
    var dateFormat = new Date(date);
    var times= dateFormat.getFullYear() + '年'
        + (dateFormat.getMonth() + 1 < 10 ? "0" + (dateFormat.getMonth() + 1) : dateFormat.getMonth() + 1)
        + '月' + (dateFormat.getDate() < 10 ? "0"+ dateFormat.getDate() : dateFormat.getDate())
        + '日  ' + (dateFormat.getHours() < 10 ? "0"+ dateFormat.getHours() : dateFormat.getHours())
        + ':' + (dateFormat.getMinutes() < 10 ? "0"+ dateFormat.getMinutes() : dateFormat.getMinutes())
        + ':' + (dateFormat.getSeconds() < 10 ? "0"+ dateFormat.getSeconds() : dateFormat.getSeconds());
    return times;
}
//获取?id=，的值
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}
