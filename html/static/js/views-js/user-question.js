$(function () {
    get_user();
    maxlength();
});
function get_user() {
    $.ajax({
        url:"/user/get_user_info.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){//如果存在当前用户，执行
                //保存返回的用户信息，填充到html
                currentUser=result.data;
                set_user_info_html(currentUser);
            }
        }
    });
}

function set_user_info_html(currentUser) {
    if (currentUser.userQuestion!=null && currentUser.userQuestion!=""){
        $("#old-question").text(currentUser.userQuestion);
    }else {
        $("#old-question").text(" ");
    }
}

//保存
$(document).on("click","#save-btn",function () {
    $.ajax({
        url:"/user/update_information.do",
        type:"POST",
        dataType:"json",
        data:{
            userQuestion:$("#new-question").val(),
            userAnswer:$("#new-answer").val()
        },
        success:function (result) {
            if(result.status==200){
                set_user_info_html(result.data);
                alert(result.msg);
            }else {
                alert(result.msg);
            }
        }
    })
})

//重置
$(document).on("click","#reset-btn",function () {
    window.location.reload();
})

function maxlength() {
    //在键盘键入后，检查密码长度
    $('input').keyup(function () {
        var i=$('#old-question').val();
        var j=$('#new-question').val();
        var k=$('#new-answer').val();

        if(i.length>=250){
            //确认密码超出部分强制切除
            var subval=i.substring(0,250);
            $('#old-question').val(subval);
        }
        if(j.length>=250){
            //新密码超出部分强制切除
            var subval=j.substring(0,250);
            $('#new-question').val(subval);
        }
        if(k.length>=250){
            //新密码超出部分强制切除
            var subval=k.substring(0,250);
            $('#new-answer').val(subval);
        }
    })
}