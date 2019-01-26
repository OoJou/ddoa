$(function () {
    maxlength();//input长度限制
    equalpass();//input新密码和确认密码相等
});

function equalpass() {
    //在键盘键入后，检查确认密码与新密码是否相等
    $('input').keyup(function () {
        if($('#confirm-password').val()!=""){
            if($('#confirm-password').val()!=$('#new-password').val()){
                //每次添加前，先把之前的清除
                $('.alert').empty();
                $('.alert').append("<p>两次输入的密码不一致</p>");
                //密码不一致时候，设置跳转无效
                $('#reset').attr('href','javascript:void(0)');
            }else{
                $('.alert').empty();
                $('#reset').attr('href','login.html');
            }
        }
    })
}

function maxlength() {
    //在键盘键入后，检查密码长度
    $('input').keyup(function () {
        var i=$('#confirm-password').val();
        var j=$('#new-password').val();

        if(i.length>=12){
            //确认密码超出部分强制切除
            var subval=i.substring(0,12);
            $('#confirm-password').val(subval);
        }
        if(j.length>=12){
            //新密码超出部分强制切除
            var subval=j.substring(0,12);
            $('#new-password').val(subval);
        }
    })
}

// ready在加载完后会一直重复执行
// $(document).ready(function () {
//     $('input').keydown(function () {
//         if($('#confirm-password').val()!=""){
//             // alert("1");
//             if($('#confirm-password').val()!=$('#new-password').val()){
//                 //每次添加前，先把之前的清除
//                 $('.alert').empty();
//                 $('.alert').append("<p>两次输入的密码不一致</p>");
//                 //密码不一致时候，设置跳转无效
//                 $('#reset').attr('href','javascript:void(0)');
//             }else{
//                 $('.alert').empty();
//                 $('#reset').attr('href','login.html');
//             }
//         }
//     })
// })