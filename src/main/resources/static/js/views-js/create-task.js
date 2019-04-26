$(function () {
    get_all_responder();
});
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
                    responderSelect.append(responder);
                });

                console.log(result);
            }
        }
    });
}

//返回
$(document).on("click","#back-btn",function () {
    var html=getQueryVariable("html");
    if(html=="task_deal"){
        window.location.href="task_deal";
    }
    if(html=="manage_task"){
        window.location.href="manage_task";
    }
    if(html=="task_request"){
        window.location.href="task_request";
    }
    if(!html) {
        window.location.href="show";
    }
    //填充首页内容区
    // back_show_html();
});

//发布任务页 保存
$(document).on("click","#task-save-btn",function () {
    //保存各类信息，统一上传
    var taskTitle=$("#task-title").val();
    var taskDetails=$("#task-body").val();
    var taskResponder=$("#task-responder-select").find("option:selected").text();
    var taskType=$("#task-type").find("option:selected").text();
    $.ajax({
        url:"/task/create_task.do",
        type:"POST",
        dataType:"json",
        data:{
            taskTitle:taskTitle,
            taskDetails:taskDetails,
            taskResponder:taskResponder,
            taskType:taskType
        },
        success:function (result) {
            if (result.status == 200) {
                var html=getQueryVariable("html");
                if(html=="task_deal"){
                    window.location.href = "task_deal";
                }
                if(html=="manage_task"){
                    window.location.href="manage_task";
                }
                if(html=="task_request"){
                    window.location.href="task_request";
                }
                if((!html)) {
                    window.location.href = "show";
                }
            }
        }
    })
});

//发布任务页 重置
$(document).on("click","#task-reset-btn",function () {
    //重新用id请求一次详情页
    var taskId=$("#task-title").attr("task-id");
    var html=getQueryVariable("html");
    if(html=="task_deal"){
        window.location.href = "create_task?html=task_deal";
    }
    if(html=="manage_task"){
        window.location.href="create_task?html=manage_task";
    }
    if(html=="task_request"){
        window.location.href="create_task?html=task_request";
    }
    if(!html){
        window.location.href = "create_task";
    }
});

// /获取?id=，的值
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}