$(function () {
    get_all_notice(1);
});

//请求公告列表数据
function get_all_notice(pageNum) {
    var currentPageSize=8;
    var currentPageNum;
    if(pageNum!="" && pageNum!=null){
        currentPageNum=pageNum;
    }else {
        currentPageNum=1;
    }
    $.ajax({
        url:"/notice/get_all_notice.do",
        type:"POST",
        dataType:"json",
        data:{
            pageNum:currentPageNum,
            pageSize:currentPageSize
        },
        success:function (result) {
            if(result.status==200){
                set_all_notice_html(result);
            }else {
                alert(result.msg);
            }
        }
    });
}

//填充数据到页面
function set_all_notice_html(result) {
    var noticeList=result.data.list;

    $(".noticeList").empty();
    $.each(noticeList,function (index, item) {
        var a="<a href=\"noitce_details?="+item.noticeId+"\">";
        // var img="<img width=\"250\" height=\"153\" class=\"fl\" src=\"file:///"+item.noticeImage+"\">";
        var img="<img width=\"250\" height=\"153\" class=\"fl\" src=\"../../../static/images/img7.png\">";

        var text=$(" <li>" +
            a +
            img +
            "                    <div class=\"noticeList-box fl\">" +
            "                        <h3 class=\"cut\">"+item.noticeTitle+"</h3>" +
            "                        <p class=\"cut3\">"+item.noticeDetails+"</p>" +
            "                        <span><i class=\"time\"></i>"+renderTime(item.createTime)+"</span>" +
            "                    </div>" +
            "                </a>" +
            "            </li>");

        $(".noticeList").append(text);
    });
    set_pager(result.data);
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
            get_all_notice(1);
        });

        var prePageLi = $("<li class=\"page-item prev\">" +
            "                            <button class=\"nav-btn\">上一页</button>" +
            "              </li>");
        prePageLi.click(function () {
            get_all_notice(pageList.prePage);
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
            get_all_notice(pageList.lastPage+1);
        });

        var nextPageLi = $("<li class=\"page-item next\">" +
            "                            <button class=\"nav-btn\">下一页</button>" +
            "               </li>");
        nextPageLi.click(function () {
            get_all_notice(pageList.nextPage);
        });
        $(".pages").append(nextPageLi).append(lastPageLi);
    }
}
// 点击分页1 2 3
$(document).on("click",".pagination-btn",function () {
    get_all_notice($(this).text());
});
//点击跳转
$(document).on("click",".jump-pager-button",function () {
    get_all_notice($(".jump-pager-input").val());
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