//控制用户的主页操作
$(function () {
     //填充首页内容区
     get_notice_list();
     get_file_list();
     get_task_deal_list();
     get_task_complete_list();
     get_user();
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
            }
        }
    })
}

// function back_show_html() {
//     var back_show=$("    <div class=\"layui-row layui-col-space20\">" +
//         "" +
//         "    <div class=\"layui-col-md6\">" +
//         "        <div class=\"layui-card\">" +
//         "            <div class=\"layui-card-header card-notice\">" +
//         "                <p class=\"card-title\">公告</p>" +
//         "                <a class=\"card-more\">更多>></a>" +
//         "            </div>" +
//         "            <div class=\"layui-card-body\">" +
//         "                <ul id=\"show-notice\">" +
//         "                </ul>" +
//         "            </div>" +
//         "        </div>" +
//         "    </div>" +
//         "" +
//         "    <div class=\"layui-col-md6\">" +
//         "        <div class=\"layui-card\">" +
//         "            <div class=\"layui-card-header card-notice\">" +
//         "                <p class=\"card-title\">共享文件</p>" +
//         "                <a class=\"card-more\">更多>></a>" +
//         "            </div>" +
//         "            <div class=\"layui-card-body\">" +
//         "                <ul id=\"show-file\">" +
//         "                </ul>" +
//         "            </div>" +
//         "        </div>" +
//         "    </div>" +
//         "" +
//         "    <div class=\"layui-col-md12\">" +
//         "        <div class=\"layui-card\">" +
//         "            <div class=\"layui-tab layui-tab-brief\">" +
//         "                <ul class=\"layui-tab-title\">" +
//         "                    <li class=\"layui-this\">待办任务</li>" +
//         "                    <li>已完成任务</li>" +
//         "                </ul>" +
//         "                <div class=\"layui-tab-content clearfix\">" +
//         "                    <div class=\"layui-tab-item layui-show\">" +
//         "                        <table class=\"layui-table\" lay-size=\"sm\">" +
//         "                            <colgroup>" +
//         "                                <col width=\"\">" +
//         "                                <col width=\"150\">" +
//         "                                <col width=\"250\">" +
//         "                            </colgroup>" +
//         "                            <thead>" +
//         "                            <tr>" +
//         "                                <th>任务</th>" +
//         "                                <th>指派人</th>" +
//         "                                <th>指派时间</th>" +
//         "                            </tr>" +
//         "                            </thead>" +
//         "                            <tbody id=\"show-task-deal\">" +
//         "                            </tbody>" +
//         "                        </table>" +
//         "                    </div>" +
//         "                    <div class=\"layui-tab-item\">" +
//         "                        <table class=\"layui-table\" lay-size=\"sm\">" +
//         "                            <colgroup>" +
//         "                                <col width=\"\">" +
//         "                                <col width=\"150\">" +
//         "                                <col width=\"250\">" +
//         "                            </colgroup>" +
//         "                            <thead>" +
//         "                            <tr>" +
//         "                                <th>任务</th>" +
//         "                                <th>指派人</th>" +
//         "                                <th>完成时间</th>" +
//         "                            </tr>" +
//         "                            </thead>" +
//         "                            <tbody id=\"show-task-complete\">" +
//         "                            </tbody>" +
//         "                        </table>" +
//         "                    </div>" +
//         "                    <div>" +
//         "                        <a class=\"card-more\" href=\"#\">更多>></a>" +
//         "                    </div>" +
//         "                </div>" +
//         "            </div>" +
//         "        </div>" +
//         "    </div>" +
//         "</div>");
//     //返回，即
//     $("#show").empty();
//     $("#show").append(back_show);
//     //返回后重新回填页面
//     get_notice_list();
//     get_file_list();
//     get_task_deal_list();
//     get_task_complete_list();
// }

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
        var file_top_p=$("<p></p>").append(item.createTime);
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
        var notice_top_p=$("<p></p>").append(item.createTime);
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
        var task_deal_top_time=$("<td></td>").append("<p>"+item.createTime+"</p>");
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
        var task_complete_top_time=$("<td></td>").append("<p>"+item.createTime+"</p>");
        var task_complete=$("<tr></tr>").append(task_complete_top_a).append(task_complete_top_name).append(task_complete_top_time);
        $("#show-task-complete").append(task_complete);
    });
}

function file_details_html(result) {
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
        "                    <img src=\"../static/images/img-me.jpg\" alt=\"图片加载失败····\" id='file-image'>" +
        "                </div>" +
        "                <div style=\"text-align: center\">" +
        "                    <input type=\"button\" class=\"layui-btn layui-btn-normal download-btn\" value=\"下载\" id='file-download-btn'>" +
        "                </div>" +
        "            </div>" +
        "        </div>" +
        "    </div>");
    $("#show").empty();
    $("#show").append(file_details);

    $("#file-name").text(result.data.fileName);
    $("#file-upload-user").text("上传人："+fileUploadUser);
    $("#file-image").attr("src",result.data.fileImage);
}
function notice_details_html(result) {
    var notice_details=$("<div class=\"layui-col-sm12 layui-col-md12\">" +
        "        <div class=\"layui-card\">" +
        "            <div>" +
        "                <button class=\"layui-btn layui-btn-normal\" id='back-btn'><i class=\"layui-icon\">&#xe603;</i>返回</button></div>" +
        "            <div class=\"layui-card-header\" style=\"text-align: center; font-size: large;\" id='notice-title'>卡片面板</div>" +
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
    $("#show").append(notice_details);

    $("#notice-title").text(result.data.noticeTitle);
    $("#notice-publichser").text(result.data.noticePubilsher);
    $("#notice-body").text(result.data.noticeDetails);
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
$(document).on("click",".notice-click",function () {
    var id=$(this).attr("notice-id");
    get_notice_details(id);//传入id，查询后，详情页进行填充
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
    get_file_details(id);
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

//填充待处理任务
function get_task_deal_list() {
    $.ajax({
        url:"/task/get_task_of_user.do",
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
        url:"/task/get_task_of_user_close.do",
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

//返回首页
$(document).on("click","#back-btn",function () {
    window.location.href="show";
    //填充首页内容区
    // back_show_html();
})
