//选择器可选 类 ‘.class’ ID ‘#id’ 标签 ‘body’
//标签+名 ‘input[name=..]’

var PATH;
//$代表jQuery代码，$(function(){})在加载页面时，就会被执行。
$(function () {
    PATH = getPATH();
    // alert(PATH);
    set_val();//"记住我"时，回填cookie中的username和password
    maxlength();//input长度限制
});


//由于$('.class').click在加载页面时候执行，不方便绑定。在此使用on代替
$(document).on('click','#login',function () {
    //存上一次账号密码
    if ($('#login-username').val()!="" && $('#login-password').val()!=""){
        save_cookies();
        alert("login3");
    }
    //异步提交
    $.ajax({
        url:"/user/login.do",
        type:"POST",
        dataType:"json",
        data:{
            username:$('#login-username').val(),
            password:$('#login-password').val()
        },
        success:function (result) {
            console.log(result);
            if(result.status==200){
                window.location.href="index.html";
            }else{
                alert(result.msg);
            }
        },
        error:function () {
            alert("异常!");
        }
    })
});
//回车登录

//记住我。使用$.cookie("键","值",{expires:7}有效期7天)
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

function set_val() {
    //"记住我"如果为true，则回填
    var rem=$.cookie("remember");//取key为remember的值
    if(rem){
        //prop("属性"，值),使用prop设置复选框为选中状态。如果只有prop("属性")，则返回当前值
        $('#remember').prop("checked",true);
        $('#login-username').val($.cookie("username"));
        $('#login-password').val($.cookie("password"));
    }
}

function maxlength() {
    //在键盘键入后，检查输入长度
    $('input').keyup(function () {
        var i=$('#login-username').val();
        var j=$('#login-password').val();

        if(i.length>=10){
            var subval=i.substring(0,10);
            $('#login-username').val(subval);
        }
        if(j.length>=12){
            var subval=j.substring(0,12);
            $('#login-password').val(subval);
        }
    })
}


//获取的是上下文路径
//如http:../user/login 的login前面的一段路径(不包括http、localhost)
function getPATH() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}