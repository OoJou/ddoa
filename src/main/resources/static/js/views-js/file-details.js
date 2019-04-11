var currentUser;
$(function () {
    var id = getQueryVariable("id");
    get_user();
    get_file_details(id);
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
function get_file_details(id) {
    $.ajax({
        url: "/file/get_file_details.do",
        type: "POST",
        dataType: "json",
        data: {
            fileId: id
        },
        success: function (result) {
            if (result.status == 200) {
                set_file_details_html(result);
            } else {
                alert(result.msg);
            }
        }
    });
}

//填充页面内容
function set_file_details_html(result) {
    var item = result.data;
    $("#file-details").empty();
    var btn = null, img = null;
    //图片地址,
    var img_url = "http://localhost:8080/file/show_img.do?url=" + item.fileSource;//拼接
    var img1 = "<img src=\"" + img_url + "\" alt=\"图片加载失败····\" style='width: auto;height: auto;max-width: 100%;max-height: 100%'>";
    var img2 = "<img src=\"../../static/images/timg2.jpg\" alt=\"图片加载失败····\" style='width: auto;height: auto;max-width: 100%;max-height: 100%'>";
    var fileExtensionName = img_url.substring(img_url.lastIndexOf(".") + 1);//jpg等
    var pattern = "(bmp|jpg|png|tif|gif|pcx|tga|exif|fpx|svg|psd|cdr|pcd|dxf|ufo|eps|ai|raw|WMF|webp)";
    if (!fileExtensionName.match(pattern)) {//如果是图片则直接显示，不是则显示默认配置的图片
        img = img2;
    } else {
        img = img1;
    }

    //区别显示按钮,保存id
    var btn1 = "<input file-id=\"" + item.fileId + "\" type=\"button\" class=\"download open layui-btn layui-btn-normal my-upload-btn\" value=\"下载\">" +
        "      <input file-id=\"" + item.fileId + "\" type=\"button\" class=\"del open layui-btn layui-btn-normal my-upload-btn\" value=\"删除\">";
    var btn2 = "<input file-id=\"" + item.fileId + "\" type=\"button\" class=\"download open layui-btn layui-btn-normal download-btn\" value=\"下载\">";
    if (currentUser.userName == item.fileUploadUser) {//如果是上传者是当前用户，显示下载和删除的按钮
        btn = btn1;
    } else {
        btn = btn2;
    }
    var text = $("<div>" +
        "                <button id=\"back-btn\" class=\"layui-btn layui-btn-normal\"><i class=\"layui-icon\">&#xe603;</i>返回</button></div>" +
        "            <div class=\"layui-card-header\" style=\"text-align: center; font-size: large;\">"+item.fileName+"</div>" +
        "            <div>" +
        "                <p style=\"text-align: center; font-size: small;\">上传人："+item.fileUploadUser+"</p>" +
        "                <p style=\"text-align: center; font-size: small;\">"+renderTime(item.createTime)+"</p>"+
        "            </div>" +
        "            <div class=\"layui-card-body\">" +
        "                <div style=\"text-align: center; margin: 25px 0px\">" +
        img +
        "                </div>" +
        "                <div style=\"text-align: center\">" +
        btn +
        "                </div>" +
        "            </div>");
    $("#file-details").append(text);
}

//点击删除，保存id
$(document).on("click",".del",function () {
    var id=$(this).attr("file-id");
    $(".tips-del-yes").attr("file-id",id);
});
//确认删除
$(document).on("click",".tips-del-yes",function () {
    $.ajax({
        url:"/file/delete_file.do",
        type:"POST",
        dataType:"json",
        data:{
            fileId:$(".tips-del-yes").attr("file-id")
        },
        success:function (result) {
            alert(result.msg);
            $("#modal-del").hide();
            $(".mask").fadeOut();
            var html=getQueryVariable("html");
            if(html=="file"){
                window.location.href="file";
            }else {
                window.location.href="show";
            }
        }
    });
});
//取消删除
$(document).on("click",".tips-del-no",function () {
    $("#modal-del").hide();
    $(".mask").fadeOut();
});

//点击下载，保存id
$(document).on("click",".download",function () {
    var id=$(this).attr("file-id");
    $(".tips-download-yes").attr("file-id",id);
    $(".tips-download-yes").attr("href","http://localhost:8080/file/download_file.do?fileId="+id);
});
//确认下载
$(document).on("click",".tips-download-yes",function () {
    setTimeout(function () {
        $("#modal-download").hide();
        $(".mask").fadeOut();
    }, 500);//点击下载后0.5秒关闭弹框，保证点击事件是有效的
});
//取消下载
$(document).on("click",".tips-download-no",function () {
    $("#modal-download").hide();
    $(".mask").fadeOut();
});


//返回首页
$(document).on("click","#back-btn",function () {
    var html=getQueryVariable("html");
    if(html=="file"){
        window.location.href="file";
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