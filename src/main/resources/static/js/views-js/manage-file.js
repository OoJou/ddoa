$(function () {
    get_all_file(1);
});

//请求全部文件数据
function get_all_file(pageNum) {
    var currentPageSize=8;
    var currentPageNum;
    if(pageNum!="" && pageNum!=null){
        currentPageNum=pageNum;
    }else {
        currentPageNum=1;
    }
    $.ajax({
        url:"/file/get_all_file.do",
        type:"POST",
        dataType:"json",
        data:{
            pageNum:currentPageNum,
            pageSize:currentPageSize,
            sortType:$("#sort-type").val(),
            fileUploadUser:$("#sort-upload-user").val(),
            fileName:$("#sort-filename").val()
        },
        success:function (result) {
            if(result.status==200){
                set_all_file_html(result);
            }else {
                alert(result.msg);
            }
        }
    });
}
//填充页面内容
function set_all_file_html(result) {
    var allFileList=result.data.list;
    $("#file-list").empty();
    $.each(allFileList,function (index, item) {
        var text= $("<tr>" +
            // "                        <td><input type=\"checkbox\" name=\"userCheckBox\"></td>" +
            "                        <td>"+item.fileId+"</td>" +
            "                        <td>"+item.fileName+"</td>" +
            "                        <td>"+item.fileUploadUser+"</td>" +
            "                        <td>"+item.fileSource+"</td>" +
            "                        <td>"+renderTime(item.createTime)+"</td>" +
            "                        <td>"+renderTime(item.updateTime)+"</td>" +
            "                        <td>" +
            "                            <input class=\"download open layui-btn layui-btn-normal layui-btn-xs\" style=\"width: 35px\"" +
            "                                   type=\"button\" value=\"下载\">" +
            "                            <input class=\"del open layui-btn layui-btn-normal layui-btn-xs\" style=\"width: 35px\"" +
            "                                   type=\"button\" value=\"删除\">" +
            "                        </td>" +
            "</tr>");
        $("#file-list").append(text);
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
            get_all_file(1);
        });

        var prePageLi = $("<li class=\"page-item prev\">" +
            "                            <button class=\"nav-btn\">上一页</button>" +
            "              </li>");
        prePageLi.click(function () {
            get_all_file(pageList.prePage);
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
            get_all_file(pageList.lastPage+1);
        });

        var nextPageLi = $("<li class=\"page-item next\">" +
            "                            <button class=\"nav-btn\">下一页</button>" +
            "               </li>");
        nextPageLi.click(function () {
            get_all_file(pageList.nextPage);
        });
        $(".pages").append(nextPageLi).append(lastPageLi);
    }
}
// 点击分页1 2 3
$(document).on("click",".pagination-btn",function () {
    get_all_file($(this).text());
});
//点击跳转
$(document).on("click",".jump-pager-button",function () {
    get_all_file($(".jump-pager-input").val());
});

//确认上传
$(document).on("click",".upload-yes",function () {
    var form = new FormData($("#uf")[0]);
    // form.append("file",$("#upload-file")[0].files[0]);
    $.ajax({
        url:"/file/upload_file.do",
        type:"POST",
        dataType:"json",
        data:form,
        processData:false,//因为data值是FormData对象，不需要对数据做序列化。
        contentType:false,//因为form已经设置enctype ="multipart/form-data"，不需要再设置类型
        cache:false,//上传文件不需要缓存
        success:function (result) {
            alert(result.msg);
            $("#modal-upload").hide();
            $(".mask").fadeOut();
            get_all_file();
        }
    });
});
//取消上传
$(document).on("click",".upload-no",function () {
    $("#modal-upload").hide();
    $(".mask").fadeOut();
});

//点击删除，保存id
$(document).on("click",".del",function () {
    var id=$(this).parent().parent().find("td:eq(0)").text();
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
            get_all_file();
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
    var id=$(this).parent().parent().find("td:eq(0)").text();
    $(".tips-download-yes").attr("file-id",id);
    $(".tips-download-yes").attr("href","http://localhost:8080/file/download_file.do?fileId="+id);
});
//确认下载
$(document).on("click",".tips-download-yes",function () {
    setTimeout(function () {
        $("#modal-download").hide();
        $(".mask").fadeOut();
    }, 2000);
    // $.ajax({
    //     url:"/file/download_file.do",
    //     type:"GET",
    //     data:{
    //         fileId:$(".tips-download-yes").attr("file-id")
    //     },
    //     success:function (result) {
    //         $("#modal-download").hide();
    //         $(".mask").fadeOut();
    //     }
    // });
});
//取消下载
$(document).on("click",".tips-download-no",function () {
    $("#modal-download").hide();
    $(".mask").fadeOut();
});


//搜索
$(document).on("click",".search",function () {
    get_all_file();
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