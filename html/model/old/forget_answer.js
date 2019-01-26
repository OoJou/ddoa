$(function () {
})

$(document).on('click','#second-next',function () {
    alert("1");
    $.ajax({
        url:"/user/forget_check_answer.do",
        type:"POST",
        dataType:"json",
        data:{
            username:"",
            question:"",
            answer:$('#forget-answer').val()
        },
        success:function (result) {
            console.log(result);
            alert(result.msg);
            window.location.href="forget_reset.html";
        },
        error:function () {
            alert("无此用户");
            window.location.href="forget_reset.html";
        }
    })
})