var currentUser;
$(function () {
    var id = getQueryVariable("id");
    get_user();
    get_notice_details(id);
});

function get_user() {//后台用拦截器拦截，前端就不必进来一次请求一次
    $.ajax({
        url: "/user/get_user_info.do",
        type: "POST",
        dataType: "json",
        success: function (result) {
            if (result.status == 200) {
                currentUser = result.data;
            }
        }
    });
}

//请求全部文件数据
function get_notice_details(id) {
    $.ajax({
        url: "/notice/get_notice_details.do",
        type: "POST",
        dataType: "json",
        data: {
            noticeId: id
        },
        success: function (result) {
            if (result.status == 200) {
                set_notice_details_html(result);
            } else {
                alert(result.msg);
            }
        }
    });
}

//填充页面内容
function set_notice_details_html(result) {
    var item = result.data;
    $("#notice-details").empty();
    var details=item.noticeDetails;
    var text = $("<div>" +
        "                <button id='back-btn' class=\"layui-btn layui-btn-normal\"><i class=\"layui-icon\">&#xe603;</i>返回</button></div>" +
        "            <div class=\"layui-card-header\" style=\"text-align: center; font-size: 28px;font-weight: 550;\">"+item.noticeTitle+"</div>" +
        "            <div>" +
        "                <p style=\"text-align: center; font-size: small;\">发布者："+item.noticePubilsher+"</p>" +
        "                <p style=\"text-align: center; font-size: small;\">"+renderTime(item.createTime)+"</p>" +
        "            </div>" +
        "            <div class=\"layui-card-body\">" +
        details +
        "            </div>");
    $("#notice-details").html(text);
}

//返回首页
$(document).on("click","#back-btn",function () {
    var html=getQueryVariable("html");
    if(html=="manage_notice"){
        window.location.href="manage_notice";
    }else {
        window.location.href="show";
    }
});

//获取?id=，的值
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return (false);
}

//前端时间转换2019-01-13T05:22:01.000+0000 --> 2019-01-13 13:22:01
function renderTime(date) {
    var dateFormat = new Date(date);
    var times = dateFormat.getFullYear() + '年'
        + (dateFormat.getMonth() + 1 < 10 ? "0" + (dateFormat.getMonth() + 1) : dateFormat.getMonth() + 1)
        + '月' + (dateFormat.getDate() < 10 ? "0" + dateFormat.getDate() : dateFormat.getDate())
        + '日  ' + (dateFormat.getHours() < 10 ? "0" + dateFormat.getHours() : dateFormat.getHours())
        + ':' + (dateFormat.getMinutes() < 10 ? "0" + dateFormat.getMinutes() : dateFormat.getMinutes())
        + ':' + (dateFormat.getSeconds() < 10 ? "0" + dateFormat.getSeconds() : dateFormat.getSeconds());
    return times;
}