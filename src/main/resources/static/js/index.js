//控制用户的主页操作
$(function () {
     //填充首页内容区
     get_notice_list();
     get_file_list();
     get_task_deal_list();
     get_task_complete_list();
     get_user();
    check_is_admin();
})
var currentUser;

function get_user() {//后台用拦截器拦截，前端就不必进来一次请求一次
    $.ajax({
        url:"/user/get_user_info.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){//如果存在当前用户，执行
                //保存返回的用户信息，填充到html
                currentUser=result.data;
                $("#nav-user").html(currentUser.userName+"<span class=\"layui-nav-more\"></span>");
            }
        }
    });
}
function check_is_admin() {
    $.ajax({
        url:"/user/check_is_admin.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){
                $("#Nav").append(" <li class=\"layui-nav-item\">" +
                    "                    <a href=\"\">" +
                    "                        <i class=\"layui-icon\">&#xe609;</i>" +
                    "                        <em>后台管理</em>" +
                    "<span class=\"layui-nav-more\"></span>"+
                    "                    </a>" +
                    "                    <dl class=\"layui-nav-child\">" +
                    "                        <dd><a href=\"views/manage_user\">账号信息管理</a></dd>" +
                    "                        <dd><a href=\"views/manage_task\">任务信息管理</a></dd>" +
                    "                        <dd><a href=\"views/manage_file\">文件信息管理</a></dd>" +
                    "                        <dd><a href=\"views/manage_notice\">公告信息管理</a></dd>" +
                    // "                        <dd><a href=\"views/manage_department\">部门信息管理</a></dd>" +
                    // "                        <dd><a href=\"views/manage_dictionary\">字典管理</a></dd>" +
                    "                    </dl>" +
                    "                </li>");
            }
        }
    })
}


function file_column(result) {
    $('#show-file').empty();
    var fileList=result.data.list;
    var flag=0;
    $.each(fileList,function (index,item) {
        flag=flag+1;
        if(flag>5){
            return;
        }
        var file_top_a=$("<a href=\"javascript:void(0);\" class='file-click'></a>").append(item.fileName);
        file_top_a.attr("file-id",item.fileId);
        var file_top_p=$("<p></p>").append(renderTime(item.createTime));
        var file=$("<li class=\"card-link\"></li>").append(file_top_a).append(file_top_p);
        $('#show-file').append(file);
    });
}
function notice_column(result){
    $('#show-notice').empty();
    var noticeList=result.data.list;
    var flag=0;
    $.each(noticeList,function (index,item) {
        flag=flag+1;
        if(flag>5){
            return;
        }
        var notice_top_a=$("<a href=\"javascript:void(0);\" class='notice-click' " +
            "data-method=\"offset\" data-type=\"auto\"></a>").append(item.noticeTitle);
        //自定义属性，存放id
        notice_top_a.attr("notice-id",item.noticeId);
        var notice_top_p=$("<p></p>").append(renderTime(item.createTime));
        var notice=$("<li class=\"card-link\"></li>").append(notice_top_a).append(notice_top_p);
        $('#show-notice').append(notice);
    });
}
function task_deal_column(result) {
    $("#show-task-deal").empty();
    var taskDealList=result.data.list;
    var flag=0;
    $.each(taskDealList,function (index, item) {
        flag=flag+1;
        if(flag>5){
            return;
        }
        var task_deal_top_a=$("<a class='task-deal-click'></a>").append(item.taskTitle);
        task_deal_top_a.attr("task-id",item.taskId);
        var task_deal_top_a=$("<td></td>").append(task_deal_top_a);
        var task_deal_top_name=$("<td></td>").append("<p>"+item.taskRequester+"</p>");
        var task_deal_top_time=$("<td></td>").append("<p>"+renderTime(item.createTime)+"</p>");
        var task_deal=$("<tr></tr>").append(task_deal_top_a).append(task_deal_top_name).append(task_deal_top_time);
        $("#show-task-deal").append(task_deal);
    });
}
function task_complete_column(result) {
    $("#show-task-complete").empty();
    var taskCompleteList=result.data.list;
    var flag=0;
    $.each(taskCompleteList,function (index, item) {
        flag=flag+1;
        if(flag>5){
            return;
        }
        var task_complete_top_a=$("<a class='task-complete-click'></a>").append(item.taskTitle);
        task_complete_top_a.attr("task-id",item.taskId)
        var task_complete_top_a=$("<td></td>").append(task_complete_top_a);
        var task_complete_top_name=$("<td></td>").append("<p>"+item.taskRequester+"</p>");
        var task_complete_top_time=$("<td></td>").append("<p>"+renderTime(item.createTime)+"</p>");
        var task_complete=$("<tr></tr>").append(task_complete_top_a).append(task_complete_top_name).append(task_complete_top_time);
        $("#show-task-complete").append(task_complete);
    });
}


//填充广告
function get_notice_list() {
    $.ajax({
        url:"/notice/get_all_notice.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){
                notice_column(result);
            }
        },
        error:function () {
            notice_column(null);
        }
    })
}
//监听+获取广告详情
$(document).on("click",".notice-click",function ()  {
    var id=$(this).attr("notice-id");
    // get_notice_details(id);//作废
    window.location.href="notice_details?id="+id+"&html=show"
})
function get_notice_details(id) {
    var noticeId=id;
    $.ajax({
        url:"/notice/get_notice_details.do",
        type:"POST",
        dataType:"json",
        data:{
            noticeId:noticeId
        },
        success:function (result) {
            if(result.status==200){
                notice_details_html(result);
            }
        },
        error:function () {
            notice_details_html(null);
        }
    })
}
function notice_details_html(result) {
    var notice_details=$("<div class=\"layui-col-sm12 layui-col-md12\">" +
        "        <div class=\"layui-card\">" +
        "            <div>" +
        "                <button class=\"layui-btn layui-btn-normal\" id='back-btn'><i class=\"layui-icon\">&#xe603;</i>返回</button></div>" +
        "            <div class=\"layui-card-header\" style=\"text-align:center;font-size: 28px;font-weight:550;\" id='notice-title'>卡片面板</div>" +
        "            <div>" +
        "                <p style=\"text-align: center; font-size: small;\" id='notice-publisher'>发布者：</p>" +
        "            </div>" +
        "            <div class=\"layui-card-body\" id='notice-body'>" +
        "                卡片式面板面板通常用于非白色背景色的主体内<br>" +
        "                从而映衬出边框投影" +
        "            </div>" +
        "        </div>" +
        "    </div>");
    $("#show").empty();
    $("#show").html(notice_details);

    $("#notice-title").text(result.data.noticeTitle);
    $("#notice-publisher").text("发布者："+result.data.noticePubilsher);
    $("#notice-body").html(result.data.noticeDetails);
}

//填充文件
function get_file_list() {
    $.ajax({
        url:"/file/get_all_file.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){
                file_column(result);//填充到广告栏
            }
        },
        error:function () {
            alert("file_column");
        }
    })
}
$(document).on("click",".file-click",function () {
    var id=$(this).attr("file-id");
    // get_file_details(id);----该方法可以作废了
    window.location.href="file_details?id="+id+"&html=show"
})
function get_file_details(id) {
    var fileId=id;
    $.ajax({
        url:"/file/get_file_details.do",
        type:"POST",
        dataType:"json",
        data:{
            fileId:fileId
        },
        success:function (result) {
            if(result.status==200){
                file_details_html(result);
            }
        },
        error:function () {
            alert("详情页错误");
        }
    })
}
function file_details_html(result) {
    var item=result.data;
    var btn=null,img=null;
    //图片地址,
    var img_url ="http://localhost:8080/file/show_img.do?url="+item.fileSource;//拼接
    var img1 ="<img src=\""+img_url+"\" alt=\"图片加载失败····\" style='width: auto;height: auto;max-width: 100%;max-height: 100%'>";
    var img2 ="<img src=\"../../static/images/timg2.jpg\" alt=\"图片加载失败····\" style='width: auto;height: auto;max-width: 100%;max-height: 100%'>";
    var fileExtensionName = img_url.substring(img_url.lastIndexOf(".")+1);//jpg等
    var pattern = "(bmp|jpg|png|tif|gif|pcx|tga|exif|fpx|svg|psd|cdr|pcd|dxf|ufo|eps|ai|raw|WMF|webp)";
    if(!fileExtensionName.match(pattern)){//如果是图片则直接显示，不是则显示默认配置的图片
        img=img2;
    }else {
        img=img1;
    }

    //区别显示按钮,保存id
    var btn1= "<input file-id=\""+item.fileId+"\" type=\"button\" class=\"download open layui-btn layui-btn-normal my-upload-btn\" value=\"下载\">" +
        "      <input file-id=\""+item.fileId+"\" type=\"button\" class=\"del open layui-btn layui-btn-normal my-upload-btn\" value=\"删除\">";
    var btn2="<input file-id=\""+item.fileId+"\" type=\"button\" class=\"download open layui-btn layui-btn-normal download-btn\" value=\"下载\">";
    if(currentUser.userName==item.fileUploadUser){//如果是上传者是当前用户，显示下载和删除的按钮
        btn=btn1;
    }else {
        btn=btn2;
    }

    var file_details=$("<div class=\"layui-col-sm12 layui-col-md12\">" +
        "        <div class=\"layui-card\">" +
        "            <div>" +
        "                <button class=\"layui-btn layui-btn-normal\" id='back-btn'><i class=\"layui-icon\">&#xe603;</i>返回</button></div>" +
        "            <div class=\"layui-card-header\" style=\"text-align: center; font-size: large;\" id='file-name'>面板</div>" +
        "            <div>" +
        "                <p style=\"text-align: center; font-size: small;\" id='file-upload-user'>上传人：</p>" +
        "            </div>," +
        "            <div class=\"layui-card-body\">" +
        "                <div style=\"text-align: center; margin: 25px 0px\">" +
        img +
        "                </div>" +
        "                <div style=\"text-align: center\">" +
        btn +
        "                </div>" +
        "            </div>" +
        "        </div>" +
        "    </div>");
    $("#show").empty();
    $("#show").html(file_details);

    $("#file-name").text(result.data.fileName);
    $("#file-upload-user").text("上传人："+fileUploadUser);
}

//填充待处理任务
function get_task_deal_list() {
    $.ajax({
        url:"/task/get_task_of_user_now.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){
                console.log(result);
                task_deal_column(result);//填充到广告栏
            }
        },
        error:function () {
            task_deal_column(null);
        }
    })
}
$(document).on("click",".task-deal-click",function () {
    var id=$(this).attr("task-id");
    window.location.href="task_deal_details?id="+id;
})

//填充已关闭任务
function get_task_complete_list() {
    $.ajax({
        url:"/task/get_task_of_user.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){
                task_complete_column(result);//填充到广告栏
            }
        },
        error:function () {
            task_complete_column(null);
        }
    })
}
$(document).on("click",".task-complete-click",function () {
    var id=$(this).attr("task-id");
    window.location.href="task_complete_details?id="+id;
})

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
    // back_show_html();
});

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
