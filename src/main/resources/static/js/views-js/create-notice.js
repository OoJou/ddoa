var html=getQueryVariable("html");
var currentUser;
$(function () {
    get_user();
});

function get_user() {
    $.ajax({
        url:"/user/get_user_info.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){//如果存在当前用户，执行
                //保存返回的用户信息，填充到html
                currentUser=result.data;
                if (html=="manage_notice_insert"){
                    $("#notice-pubilsher").text(currentUser.userName);//设置当前用户为
                }
                else if (html=="manage_notice_edit"){
                    var id=getQueryVariable("id");
                    $("#notice-title").attr("notice-id",id);
                    get_notice_details(id);
                }
            }else {
                alert(result.msg);
            }
        }
    });
}


//获取公告详情
function get_notice_details(id) {
    $.ajax({
        url:"/notice/get_notice_details.do",
        type:"POST",
        dataType:"json",
        data:{
            noticeId:id
        },
        success:function (result) {
            if(result.status==200){
                set_notice_details_html(result);
            }else {
                alert(result.msg);
            }
        }
    })
}
//填充页面
function set_notice_details_html(result){
    var item=result.data;
    $("#notice-title").val(item.noticeTitle);
    $("#details").val(item.noticeDetails);
    $("#notice-pubilsher").text(item.noticePubilsher);
}

//保存,根据html的不同进行操作
$(document).on("click","#notice-save-btn",function () {
    if (html=="manage_notice_insert"){
        create_notice();
    }
    else if (html=="manage_notice_edit"){
        var id=getQueryVariable("id");
        update_notice(id);
    }
});
//重置
$(document).on("click","#notice-reset-btn",function () {
    if (html=="manage_notice_insert"){
        window.location.href="create_notice?html="+html;
    }
    else if (html=="manage_notice_edit"){
        var noticeId=$("#notice-title").attr("notice-id");
        window.location.href="create_notice?id="+ noticeId +"&html="+html;
    }
});
//发布公告
function create_notice() {
    // 1、获取CKEditor被选中的内容
    // var mySelection = CKEDITOR.instances.WORK_INTRODUCTION.getSelection();
    // if (CKEDITOR.env.ie) {
    //     mySelection.unlock(true);
    //     data = mySelection.getNative().createRange().text;
    // } else {
    //     data = mySelection.getNative();
    // }
    // 2、获取CKEditor纯文本
    // var CText=CKEDITOR.instances.WORK_INTRODUCTION.document.getBody().getText(); //取得纯文本
    //
    // 3、获取CKEditor带HTML标签的文本
    // var CHtml= CKEDITOR.instances.WORK_INTRODUCTION.getData();
    //
    // 4、给CKEditor赋值
    //
    // CKEDITOR.instances.WORK_INTRODUCTION.setData("要显示的文字内容");
    $.ajax({
        url:"/notice/create_notice.do",
        type:"POST",
        dataType:"json",
        data:{
            noticeTitle:$("#notice-title").val(),
            noticeDetails:CKEDITOR.instances.details.getData()
        },
        success:function (result) {
            if(result.status==200){
                alert(result.msg);
                window.location.href = "manage_notice";
            }else {
                alert(result.msg);
            }
        }
    });
}
//修改公告
function update_notice(id) {
    $.ajax({
        url:"/notice/update_notice.do",
        type:"POST",
        dataType:"json",
        data:{
            noticeId:id,
            noticePubilsher:$("#notice-pubilsher").text(),
            noticeTitle:$("#notice-title").val(),
            noticeDetails:CKEDITOR.instances.details.getData()
        },
        success:function (result) {
            if(result.status==200){
                alert(result.msg);
                get_notice_details(id);
            }else {
                alert(result.msg);
            }
        }
    });
}

//返回
$(document).on("click","#back-btn",function () {
    window.location.href="manage_notice";
});


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
//获取?id=，的值
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}