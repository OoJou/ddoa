$(function () {
    // alert("forget1");
    $('input').keyup(function () {
        if($('#confirm-password').val()!=""){
            // alert("1");
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
});

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
