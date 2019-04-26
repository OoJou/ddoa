$(function () {
    get_task_of_user(1);
});

//请求get_task_of_user接口数据
function get_task_of_user(pageNum) {
    var currentPageSize=8;
    var currentPageNum;
    if(pageNum!="" && pageNum!=null){
        currentPageNum=pageNum;
    }else {
        currentPageNum=1;
    }
    $.ajax({
        url:"/task/get_task_of_user.do",
        type:"POST",
        dataType:"json",
        data:{
            pageNum:currentPageNum,
            pageSize:currentPageSize
        },
        success:function (result) {
            if(result.status==200){
                set_task_html(result);
            }else {
                alert(result.msg);
            }
        }
    })
}

//填充到页面列表，绑定每个点击事件的跳转
function set_task_html(result) {
    //填充列表
    var taskList=result.data.list;
    $("#task-list").empty();
    $.each(taskList,function (index, item) {
        var taskTitleTd=$("<td></td>").append("<a class='content-wrap' >"+item.taskTitle+"</a>");
        taskTitleTd.attr("task-id",item.taskId);
        taskTitleTd.click(function () {
            window.location.href="task_complete_details?id="+item.taskId+"&html=task_completed";
        });
        var taskRequesterTd=$("<td></td>").append(item.taskRequester);
        var taskResponderTd=$("<td></td>").append(item.taskResponder);
        var taskStatusTd=$("<td></td>").append(check_task_status(item.taskStatus));
        var createTimeTd=$("<td></td>").append(renderTime(item.createTime));
        var taskTr=$("<tr></tr>").append(taskTitleTd)
            .append(taskRequesterTd)
            .append(taskResponderTd)
            .append(taskStatusTd)
            .append(createTimeTd);
        $("#task-list").append(taskTr);
    });

    //填充分页
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
            get_task_of_user(1);
        });

        var prePageLi = $("<li class=\"page-item prev\">" +
            "                            <button class=\"nav-btn\">上一页</button>" +
            "              </li>");
        prePageLi.click(function () {
            get_task_of_user(pageList.prePage);
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
            get_task_of_user(pageList.lastPage+1);
        });

        var nextPageLi = $("<li class=\"page-item next\">" +
            "                            <button class=\"nav-btn\">下一页</button>" +
            "               </li>");
        nextPageLi.click(function () {
            get_task_of_user(pageList.nextPage);
        });
        $(".pages").append(nextPageLi).append(lastPageLi);
    }
}
//点击分页1 2 3
$(document).on("click",".pagination-btn",function () {
    get_task_of_user($(this).text());
});
//点击跳转
$(document).on("click",".jump-pager-button",function () {
    get_task_of_user($(".jump-pager-input").val());
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