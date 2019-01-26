$(function () {
    maxlength_first();
    maxlength_reset();
    equalpass();
    forget_first_html();
})
var save_username;
var save_question;
var save_token;
//忘记密码分为三步，用ajax写页面的不同展示内容
function forget_first_html(result) {
    var first = $(" <li class=\"item\"><p>第一步:请输入你的账号名</p></li>" +
        "                <li class=\"item\">" +
        "                    <input type=\"text\" value placeholder=\"你的账号名\" id=\"forget-username\">" +
        "                    <div class=\"text clearfix\"></div>" +
        "                </li>" +
        "                <li class=\"item\">" +
        "                    <a class=\"btn-big btn-next\" id=\"first-next\">下一步</a>" +
        "                    <div class=\"text clearfix\"></div>" +
        "                </li>" +
        "                <li class=\"item\">" +
        "                    <a class=\"tologin clearfix\" href=\"login\">返回登录注册</a>" +
        "                </li>");
    $('#forget').empty();
    $('#forget').append(first);
}

function forget_answer_html(result) {
    var title= $(" <li class=\"item\"><p>第二步:请输入问题的答案</p></li>");
    var question=$("<li class='question'></li>").append("<p>"+result.data+"</p>");
    var question2=$("<li class='question'><p></p></li>").append(result.data);//错1，append只会接在li下(最外层)
    var question3=$("<li class='question'><p th:text=${result.data}></p></li>")//错2，th中${}只取attribute的值
    var answer = $(
    "                <li class=\"item\">" +
    "                    <input type=\"text\" value placeholder=\"你的答案\" id=\"forget-answer\">" +
    "                    <div class=\"text clearfix\"></div>" +
    "                </li>" +
    "                <li class=\"item\">" +
    "                    <a class=\"btn-big btn-next\" id=\"answer-next\">下一步</a>" +
    "                    <div class=\"text clearfix\"></div>" +
    "                </li>" +
    "                <li class=\"item\">" +
    "                    <a class=\"toprev\" id=\"answer-prev\">上一步</a>" +
    "                    <a class=\"tologin clearfix\" href=\"login.html\">返回登录注册</a>" +
    "                </li>");
    $('#forget').empty();
    $('#forget').append(title).append(question).append(answer);
}

function forget_reset_html(result) {
    var reset = $("<li class=\"item\"><p>第三步:重置密码</p></li>" +
        "                <li class=\"item\">" +
        "                    <input type=\"text\" value placeholder=\"输入新密码\" id=\"new-password\">" +
        "                    <div class=\"text clearfix\"></div>" +
        "                </li>" +
        "                <li class=\"item\">" +
        "                    <input type=\"text\" value placeholder=\"再次确认密码\" id=\"confirm-password\">" +
        "                    <div class=\"text clearfix alert\"></div>" +
        "                </li>" +
        "                <li class=\"item\">" +
        "                    <a class=\"btn-big btn-next\" id=\"reset\">确认重置</a>" +
        "                    <div class=\"text clearfix\"></div>" +
        "                </li>" +
        "                <li class=\"item\">" +
        "                    <a class=\"toprev\" id=\"reset-prev\">上一步</a>" +
        "                    <a class=\"tologin clearfix\" href=\"login\">返回登录注册</a>" +
        "                </li>")
    $('#forget').empty();
    $('#forget').append(reset);
}
//附加一个修改成功页面
function reset_success_html(result) {
    var success = $("<li class=\"success-tip\">\n" +
        "                    <p>成功重置密码!</p>\n" +
        "                    <div class=\"text clearfix\"></div>\n" +
        "                    <a href=\"login\">返回登录</a>\n" +
        "                </li>");
    $('#forget').empty();
    $('#forget').append(success);
}


//点击下一步，1.返回状态码为200，到下一个页面 2.返回状态码为201，无变化
//点击上一步，1.返回上一个页面 2.再次点击下一步需要判断当前填写内容是否通过判断(状态码)
$(document).on('click','#first-next',function () {//第一步，点击下一步
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
          if(result.status==200){
              save_username=$('#forget-username').val();//保存第一步输入的用户名
              save_question=result.data;//json返回200 描述 问题。在此保存返回的问题
              forget_answer_html(result);//成功则而进入下一步
          }else{
              alert(result.msg);
          }
      },
      error:function () {
          alert("无此用户");
          forget_answer_html();
      }
  })
})

$(document).on('click','#answer-next',function () {//第二步，点击下一步
  alert("1");
  $.ajax({
      url:"/user/forget_check_answer.do",
      type:"POST",
      dataType:"json",
      data:{
          username:save_username,
          question:save_question,
          answer:$('#forget-answer').val()
      },
      success:function (result) {
          console.log(result);
          if(result.status==200){
              save_token=result.data;//成功后保存返回的token
              forget_reset_html(result);
          }else{
              alert(result.msg);
          }
      },
      error:function () {
          alert("无此用户");
          forget_reset_html();
      }
  })
})

$(document).on('click','#answer-prev',function () {//第二步，点击上一步
  alert("1");
  forget_first_html();
})

$(document).on('click','#reset',function () {//第三部，点击确认重置
  $.ajax({
      url:"/user/forget_reset_password.do",
      type:"POST",
      dataType:"json",
      data:{
          username:save_username,
          passwordNew:$('#new-password').val(),
          forgetToken:save_token
      },
      success:function (result) {
          console.log(result);
          if(result.status==200){
              reset_success_html(result);
          }else{
              alert(result.msg);
          }
      },
      error:function () {
          alert("无此用户");
          reset_success_html();
      }
  })
})

$(document).on('click','#reset-prev',function () {//第三步，点击上一步
  alert("1");
  forget_answer_html();
})

function maxlength_first() {
    //在键盘键入后，检查输入长度。超出部分强制切除
    $('input').keyup(function () {
        var i=$('#forget-username').val();

        if(i.length>=10){
            var subval=i.substring(0,10);
            $('#forget-username').val(subval);
        }
    })
}

function maxlength_reset() {
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
function equalpass() {
    //在键盘键入后，检查确认密码与新密码是否相等
    $('#confirm-password').keyup(function () {
        if($('#confirm-password').val()!=""){
            if($('#confirm-password').val()!=$('#new-password').val()){
                //每次添加前，先把之前的清除
                $('.alert').empty();
                $('.alert').append("<p>两次输入的密码不一致</p>");
            }else{
                $('.alert').empty();
            }
        }
    })
}