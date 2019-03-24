//控制用户的主页操作
var currentUser;
var currentResponder;//当前处理人
var answer_message;//当前回复
$(function () {
    var taskId=getQueryVariable("id");
    get_task_deal_details(taskId);
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
//填充信息----状态判断单独做方法
function task_deal_details_html(result) {

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
    switch (result.data.taskType){//设值状态
        case '请假':statusSelect.find("option[value='请假']").attr("selected",true);break;
        case '资金申请':statusSelect.find("option[value='资金申请']").attr("selected",true);break;
        case '活动申请':statusSelect.find("option[value='活动申请']").attr("selected",true);break;
        case '其他':statusSelect.find("option[value='其他']").attr("selected",true);break;
    }
    set_status_time(result.data.taskStatus);//设置时间线
    $("#task-requester").text(result.data.taskRequester);//接下来一一设置对应值
    $("#task-time").text(renderTime(result.data.createTime));

    $("#task-title").attr("task-id",result.data.taskId);
}
//请求详情页信息
function get_task_deal_details(id) {
    var taskDealId=id;
    $.ajax({
        url:"/task/get_task_details.do",
        type:"POST",
        dataType:"json",
        data:{
            taskId:taskDealId
        },
        success:function (result) {
            if(result.status==200){
                task_deal_details_html(result);
                set_reply(result);
                console.log(result);
            }else{
                alert(result.msg);
            }
        }
    })
}
//获取全部处理人
function get_all_responder() {
    $.ajax({
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
        var message_div=$("<div class=\"content-wrap layui-card-body\"></div>").append(message[j]);
        var name_div=$("<div class=\"answer-info\"></div>").append("回复人  --  "+name[j]);
        var model=$("<div class=\"layui-card\" style=\"margin-bottom: 4px;border: 1px solid #e7e7e7;\"></div>").append(message_div).append(name_div);
        $("#answer-body").append(model);
    }
}

//回复
$(document).on("click","#answer-btn",function () {
    answer_message=currentUser.userName+":"+$("#answer-message").val();
    if ($("#answer-message").val()==''||$("#answer-message").val()==null){
        alert("回复不能为空");
    }else {
        $("#task-save-btn").click();
    }
});

//返回任务管理界面
$(document).on("click","#back-btn",function () {
    window.location.href="manage_task";
    //填充首页内容区
    // back_show_html();
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
    var taskMessage=answer_message;
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
            taskMessage:taskMessage
        },
        success:function (result) {
            if (result.status == 200) {
                window.location.href = "manage_task_details?id=" + taskId;
            }
        }
    })
});

//任务详情页 重置
$(document).on("click","#task-reset-btn",function () {
    //重新用id请求一次详情页
    var taskId=$("#task-title").attr("task-id");
    window.location.href="manage_task_details?id="+taskId;
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