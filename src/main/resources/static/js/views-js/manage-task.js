$(function () {
    get_all_task(1);
});
//请求全部任务数据
function get_all_task(pageNum) {
    var currentPageSize=8;
    var currentPageNum;
    if(pageNum!="" && pageNum!=null){
        currentPageNum=pageNum;
    }else {
        currentPageNum=1;
    }
    $.ajax({
        url:"/task/get_all_task.do",
        type:"POST",
        dataType:"json",
        data:{
            pageNum:currentPageNum,
            pageSize:currentPageSize,
            sortType:$("#sort-type").val(),
            taskTitle:$("#sort-title").val(),
            taskRequester:$("#sort-requester").val(),
            taskResponder:$("#sort-responder").val()
        },
        success:function (result) {
            if(result.status==200){
                set_all_task_html(result);
            }else {
                alert(result.msg);
            }
        }
    });
}
//填充页面内容
function set_all_task_html(result) {
    var allTaskList=result.data.list;
    $("#task-list").empty();
    $.each(allTaskList,function (index, item) {
        var text= $("<tr>" +
            // "                        <td><input type=\"checkbox\" name=\"userCheckBox\"></td>" +
            "                        <td>"+item.taskId+"</td>" +
            "                        <td>"+item.taskTitle+"</td>" +
            "                        <td>"+check_task_status(item.taskStatus)+"</td>" +
            "                        <td>"+item.taskRequester+"</td>" +
            "                        <td>"+item.taskResponder+"</td>" +
            "                        <td>"+renderTime(item.createTime)+"</td>" +
            "                        <td>"+renderTime(item.updateTime)+"</td>" +
            "                        <td>" +
            "                            <input class=\"edit layui-btn layui-btn-normal layui-btn-xs\" style=\"width: 69px\"" +
            "                                   type=\"button\" value=\"查看/修改\">" +
            "                            <input class=\"del open layui-btn layui-btn-normal layui-btn-xs\" style=\"width: 35px\"" +
            "                                   type=\"button\" value=\"删除\">" +
            "                        </td>" +
            "</tr>");
        $("#task-list").append(text);
    });

    set_pager(result.data);
}
//20004 20003 20002 20001
//待处理 处理中 已处理 已关闭
function check_task_status(taskStatus) {
    switch(taskStatus){
        case 20004:return "待处理";break;
        case 20003:return "处理中";break;
        case 20002:return "已处理";break;
        case 20001:return "已关闭";break;

    }
}
//分页
function set_pager(pageList) {
    //分页，填充分页统计
    $(".count").text("共 "+pageList.pages+" 页/ "+pageList.total+" 个 ，");
    //首页，上一页
    $(".pages").empty();
    if(pageList.hasPreviousPage==true){
        var firstPageLi =$("<li class=\"page-item first\">" +
            "                            <button class=\"nav-btn\">首页</button>" +
            "              </li>");
        firstPageLi.click(function () {
            get_all_task(1);
        });

        var prePageLi = $("<li class=\"page-item prev\">" +
            "                            <button class=\"nav-btn\">上一页</button>" +
            "              </li>");
        prePageLi.click(function () {
            get_all_task(pageList.prePage);
        });

        $(".pages").append(firstPageLi).append(prePageLi);
    }
    //中间页1,2,3
    var pageLi;
    for(var i=pageList.pageNum;i<=pageList.pages;i++){
        if(pageList.lastPage!=0 && pageList.lastPage!=1){//有第2页才循环
            pageLi = $("<li class=\"page-item\">" +
                "                            <button class=\"pagination-btn num-btn\">"+i+"</button>" +
                "                        </li>");
            if(i==pageList.pageNum){
                pageLi.addClass("active");
            }
            if(i==pageList.pageNum+5){
                break;
            }
        }
        $(".pages").append(pageLi);
    }


    //下一页
    if(pageList.hasNextPage==true) {
        var lastPageLi =$("<li class=\"page-item last\">" +
            "                            <button class=\"nav-btn\">尾页</button>" +
            "              </li>");
        lastPageLi.click(function () {
            get_all_task(pageList.lastPage+1);
        });

        var nextPageLi = $("<li class=\"page-item next\">" +
            "                            <button class=\"nav-btn\">下一页</button>" +
            "               </li>");
        nextPageLi.click(function () {
            get_all_task(pageList.nextPage);
        });
        $(".pages").append(nextPageLi).append(lastPageLi);
    }
}
// 点击分页1 2 3
$(document).on("click",".pagination-btn",function () {
    get_all_task($(this).text());
});
//点击跳转
$(document).on("click",".jump-pager-button",function () {
    get_all_task($(".jump-pager-input").val());
});

//搜索
$(document).on("click",".search",function () {
    get_all_task();
});

//点击新增跳转到任务发布界面
$(document).on("click",".insert",function () {
    window.location.href="manage_task_request_details";
});

//点击查看/修改跳转到task-deal-detail界面,传值?taskId=x
$(document).on("click",".edit",function () {
    var id=$(this).parent().parent().find("td:eq(0)").text();
    window.location.href="task_deal_details?id="+id+"&html=manage_task";
});

//点击删除，保存id
$(document).on("click",".del",function () {
    var id=$(this).parent().parent().find("td:eq(0)").text();
    $(".tips-del-yes").attr("task-id",id);
});
//确认删除
$(document).on("click",".tips-del-yes",function () {
    $.ajax({
        url:"/task/delete_task.do",
        type:"POST",
        dataType:"json",
        data:{
            taskId:$(".tips-del-yes").attr("task-id")
        },
        success:function (result) {
            alert(result.msg);
            $("#modal-del").hide();
            $(".mask").fadeOut();
            get_all_task();
        }
    });
});
//取消删除
$(document).on("click",".tips-del-no",function () {
    $("#modal-del").hide();
    $(".mask").fadeOut();
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