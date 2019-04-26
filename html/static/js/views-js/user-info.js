$(function () {
    get_user();
    maxlength();
});

//进入我的信息界面，填充个人当前信息
function get_user() {
    $.ajax({
        url:"/user/get_user_info.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){//如果存在当前用户，执行
                //保存返回的用户信息，填充到html
                currentUser=result.data;
                set_user_info_html(currentUser);
            }
        }
    });
}


function set_user_info_html(currentUser) {
    $("#my-name").val(currentUser.userName);
    $("#my-name").attr("user-id",currentUser.userId);
    $("#my-phone").val(currentUser.userPhone);
    if(currentUser.userEmail!=null && currentUser.userEmail!=""){
        $("#my-email").val(currentUser.userEmail);
    }else {
        $("#my-email").attr("placeholder","未填写");
    }
    $("#my-department").text(currentUser.userDepartmentName);
    $("#my-createtime").text(renderTime(currentUser.createTime));
}

//保存
$(document).on("click","#save-btn",function () {
    $.ajax({
        url:"/user/update_information.do",
        type:"POST",
        dataType:"json",
        data:{
            userName:$("#my-name").val(),
            userPhone:$("#my-phone").val(),
            userEmail:$("#my-email").val()
        },
        success:function (result) {
            if(result.status==200){
                set_user_info_html(result.data);
                alert(result.msg);
            }else {
                alert(result.msg);
            }
        }
    })
})

//重置
$(document).on("click","#reset-btn",function () {
    window.location.reload();
})

//前端时间转换2019-01-13T05:22:01.000+0000 --> 2019-01-13 13:22:01
function renderTime(date) {
    var dateFormat = new Date(date);
    var times= dateFormat.getFullYear() + '年'
        + (dateFormat.getMonth() + 1 < 10 ? "0" + (dateFormat.getMonth() + 1) : dateFormat.getMonth() + 1)
        + '月' + (dateFormat.getDate() < 10 ? "0"+ dateFormat.getDate() : dateFormat.getDate())
        + '日  ' + (dateFormat.getHours() < 10 ? "0"+ dateFormat.getHours() : dateFormat.getHours())
        + ':' + (dateFormat.getMinutes() < 10 ? "0"+ dateFormat.getMinutes() : dateFormat.getMinutes())
        + ':' + (dateFormat.getSeconds() < 10 ? "0"+ dateFormat.getSeconds() : dateFormat.getSeconds());
    return times;
}

function maxlength() {
    //在键盘键入后，检查密码长度
    $('input').keyup(function () {
        var i=$('#my-name').val();
        var j=$('#my-phone').val();
        var k=$('#my-email').val();

        if(i.length>=10){
            //确认密码超出部分强制切除
            var subval=i.substring(0,10);
            $('#my-name').val(subval);
        }
        if(j.length>=20){
            //新密码超出部分强制切除
            var subval=j.substring(0,20);
            $('#my-phone').val(subval);
        }
        if(k.length>=50){
            //新密码超出部分强制切除
            var subval=k.substring(0,50);
            $('#my-email').val(subval);
        }
    })
}