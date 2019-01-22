//选择器可选 类 ‘.class’ ID ‘#id’ 标签 ‘body’
//标签+名 ‘input[name=..]’

var PATH;
//$代表jQuery代码，$(function(){})在加载页面时，就会被执行。
$(function () {
    PATH = getPATH();
    // alert(PATH);
    //加载时判断，"记住我"如果为true，则回填cookie中的username和password。使用prop设置复选框为选中状态
    //prop("属性"，值)。如果只有prop("属性")，则返回当前值
    var rem=$.cookie("remember");
    if(rem){
        $('#remember').prop("checked",true);
        $('#login-username').val($.cookie("username"));
        $('#login-password').val($.cookie("password"));
    }
});

//获取的是上下文路径
//如http:../user/login 的login前面的一段路径(不包括http、localhost)
function getPATH() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}

//由于$('.class').click在加载页面时候执行，不方便绑定。在此使用on代替
$(document).on('click','#login',function () {
    // alert("login2");
    if ($('#login-username').val()!="" && $('#login-password').val()!=""){
        save_cookies();
        alert("login3");
    }

    $.ajax({
        // url:"${PATH}/login.do",
        url:"/user/login.do",
        type:"POST",
        dataType:"json",
        data:{
            username:$('#login-username').val(),
            password:$('#login-password').val()
        },
        // data:$('#user').serialize(),
        success:function (result) {
        	console.log(result);
            alert("成功！"+result.status);
            // 主页面也是要ajax请求一次接口数据拿到用户信息的，所以现在是跳转页面+请求
            //            window.location.href = '/';
            
            window.location.href='/ddoa/index'
        },
        error:function () {
            alert("异常!");
        }
    })
});
//进入页面，默认选中“用户名”输入框
//回车登录

//记住我。使用$.cookie("键","值",{expires:7}有效期)
function save_cookies() {
    if($('#remember').prop("checked")){
        var username = $('#login-username').val();
        var password = $('#login-password').val();
        $.cookie("remember","true",{expires:7});
        $.cookie("username",username,{expires:7});
        $.cookie("password",password,{expires:7});
    }else {
        $.cookie("remember","false",{expires:-1});
        $.cookie("username","",{expires:-1});
        $.cookie("password","",{expires:-1});
    }
}