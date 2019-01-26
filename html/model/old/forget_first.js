$(function () {
    maxlength()
})

$(document).on('click','#first-next',function () {
    alert("1");
    $.ajax({
        url:"/user/forget_get_question.do",
        type:"POST",
        dataType:"json",
        data:{
            username:$('#forget-username').val()
        },
        success:function (result) {
            console.log(result);
            alert(result.msg);
            window.location.href="forget_answer.html";
        },
        error:function () {
            alert("无此用户");
            window.location.href="forget_answer.html";
        }
    })
})

function maxlength() {
    //在键盘键入后，检查输入长度。超出部分强制切除
    $('input').keyup(function () {
        var i=$('#forget-username').val();

        if(i.length>=10){
            var subval=i.substring(0,10);
            $('#forget-username').val(subval);
        }
    })
}