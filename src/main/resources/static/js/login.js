var PATH;
$(function () {
    PATH = getPATH();
    alert(PATH);
})

$(document).on('click','#login',function () {
    // alert("login2");
    $.ajax({
//      url:"${PATH}/user/login.do",
        url:"/user/login.do",
        type:"POST",
        dataType:"json",
        data:{
            username:$('#login-username').val(),
            password:$('#login-password').val()
        },
//         data:$('#user').serialize(),
        success:function (result) {
            console.log(result);
            alert("成功！");
        },
        error:function () {
            alert("异常!");
        }
    })
});

function getPATH() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}