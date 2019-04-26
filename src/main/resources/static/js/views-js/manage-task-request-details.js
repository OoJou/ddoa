$(function () {
    maxlength();
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

//上传数据
$(document).on("click","#save",function () {
   $.ajax({
       url:"/task/create_task.do",
       type:"POST",
       dataType:"json",
       data:{
           taskTitle:$("#task-title").val(),
           taskDetails:$("#task-details").val(),
           taskResponder:$("#task-responder-select").find("option:selected").text(),
           taskType:$("#task-type").val(),
       },
       success:function (result) {
           alert(result.msg);
       }
   });
});

//点击返回任务管理页
$(document).on("click","#back-btn",function () {
   window.location.href="manage_task";
});

//限制标题长度50，内容250字
function maxlength() {
    //在键盘键入后，检查输入长度
    $('input').keyup(function () {
        var i=$('#task-title').val();
        var j=$('#task-details').val();

        if(i.length>=50){
            var subval=i.substring(0,50);
            $('#task-title').val(subval);
        }
        if(j.length>=500){
            var subval=j.substring(0,500);
            $('#task-details').val(subval);
        }
    })
}