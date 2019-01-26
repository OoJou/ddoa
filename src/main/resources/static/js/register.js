$(function () {
    maxlength()//input长度限制
})

//对完注册，以及忘记密码三个页面的接口
$(document).on("click","#register",function () {
    if(!isEmpty()){
        alert("1");
        $.ajax({
            url:"/user/register.do",
            type:"POST",
            dataType:"json",
            data:{
                userName:$('#register-username').val(),
                userPassword:$('#register-password').val(),
                userPhone:$('#register-phone').val()
            },
            success:function (result) {
                console.log(result);
                if(result.status==200){
                    window.location.href="login";
                }else {
                    alert(result.msg);
                }
            },
            error:function () {
                alert("注册格式错误");
            }
        })
    }else {
        alert("注册信息不能为空或空格");
    }
})

function maxlength() {
    //在键盘键入后，检查输入长度
    $('input').keyup(function () {
        var i=$('#register-username').val();
        var j=$('#register-phone').val();
        var k=$('#register-password').val();

        if(i.length>=10){
            var subval=i.substring(0,10);
            $('#register-username').val(subval);
        }
        if(j.length>=20){
            var subval=j.substring(0,20);
            $('#register-phone').val(subval);
        }
        if(k.length>=12){
            var subval=k.substring(0,12);
            $('#register-password').val(subval);
        }
    })
}
//去除所有空格: str = str.replace(/\s+/g,"");
//判断有无空格：str.indexOf(" ") == -1则无空格
function isEmpty(){
 //为空或有空格时，返回true
 if($('#register-username').val().trim().length==0
     ||$('#register-phone').val().trim().length==0
     ||$('#register-password').val().trim().length==0
     ||$('#register-username').val().indexOf(" ")!=-1
     ||$('#register-phone').val().indexOf(" ")!=-1
     ||$('#register-password').val().indexOf(" ")!=-1){
     return true;
 }else{
     return false;
 }
}