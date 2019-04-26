$(function () {
    get_user();
    maxlength();
    equalpass();
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
            }
        }
    });
}

function maxlength() {
    //在键盘键入后，检查密码长度
    $('input').keyup(function () {
        var i=$('#confirm-password').val();
        var j=$('#new-password').val();

        if(i.length>=8){
            //确认密码超出部分强制切除
            var subval=i.substring(0,8);
            $('#confirm-password').val(subval);
        }
        if(j.length>=8){
            //新密码超出部分强制切除
            var subval=j.substring(0,8);
            $('#new-password').val(subval);
        }
    })
}
function equalpass() {
    //在键盘键入后，检查确认密码与新密码是否相等
    $('#confirm-password').keyup(function () {
        if($('#confirm-password').val()!=""){
            if($('#confirm-password').val()!=$('#new-password').val()){
                //每次添加前，先把之前的清除
                $('.alert').empty();
                $('.alert').append("<p style=\"font-size: 10px;font-weight: 500;color: #c9302c;margin-left:130px;\">" +
                    "两次输入的密码不一致</p>");
            }else{
                $('.alert').empty();
            }
        }
    })
}

//保存
$(document).on("click","#save-btn",function () {
    $.ajax({
        url:"/user/reset_password.do",
        type:"POST",
        dataType:"json",
        data:{
            passwordOld:$("#old-password").val(),
            passwordNew:$("#new-password").val()
        },
        success:function (result) {
            alert(result.msg);
        }
    });
})

//重置
$(document).on("click","#reset-btn",function () {
    window.location.reload();
})