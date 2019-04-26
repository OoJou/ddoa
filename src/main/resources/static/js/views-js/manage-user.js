$(function () {
    get_all_user();
});//html，js，css拉到项目下进行运行。
//进入用户管理界面，请求数据
function get_all_user(pageNum) {
    var currentPageSize=8;
    var currentPageNum;
    if(pageNum!="" && pageNum!=null){
        currentPageNum=pageNum;
    }else {
        currentPageNum=1;
    }
    $.ajax({
        url:"/user/get_all_user.do",
        type:"POST",
        dataType:"json",
        data:{
            pageNum:currentPageNum,
            pageSize:currentPageSize,
            sortType:$("#sort-type").val(),
            userName:$("#sort-username").val(),
            userPhone:$("#sort-userphone").val()
        },
        success:function (result) {
            if(result.status==200){
                set_all_user_html(result);
            }else {
                alert(result.msg);
            }
        }
    });
}
//将数据填充至页面
function set_all_user_html(result) {
    //填充列表
    var allUserList=result.data.list;
    $("#user-list").empty();
    $.each(allUserList,function (index, item) {
        var email=(item.userEmail==null?"":item.userEmail);
        var text= $("<tr>" +
            // "                        <td><input type=\"checkbox\" name=\"userCheckBox\"></td>" +
            "                        <td>"+item.userId+"</td>" +
            "                        <td>"+item.userName+"</td>" +
            "                        <td>"+check_user_level(item.userLevel)+"</td>" +
            "                        <td>"+email+"</td>" +
            "                        <td>"+item.userPhone+"</td>" +
            "                        <td>"+renderTime(item.createTime)+"</td>" +
            "                        <td>"+renderTime(item.updateTime)+"</td>" +
            "                        <td>" +
            "                            <input class=\"edit open layui-btn layui-btn-normal layui-btn-xs\" style=\"width: 35px\"" +
            "                                   type=\"button\" value=\"修改\">" +
            "                            <input class=\"del open layui-btn layui-btn-normal layui-btn-xs\" style=\"width: 35px\"" +
            "                                   type=\"button\" value=\"删除\">" +
            "                            <input class=\"reset open layui-btn layui-btn-normal layui-btn-xs\" style=\"width: 69px\"" +
            "                                   type=\"button\" value=\"初始化密码\">" +
            "                        </td>" +
            "</tr>");
        $("#user-list").append(text);
    });

    //填充分页
    set_pager(result.data);
}
//判断用户级别，传入10001,10002,10003,10004,10005
//对应 总经理，部门经理，行政人员，部门主管，职员
function check_user_level(userLevel) {
    switch (userLevel){
        case 10001:return "总经理";break;
        case 10002:return "部门经理";break;
        case 10003:return "行政人员";break;
        case 10004:return "部门主管";break;
        case 10005:return "员工";break;
    }
}
//分页
function set_pager(pageList){
    //分页，填充分页统计
    $(".count").text("共 "+pageList.pages+" 页/ "+pageList.total+" 个 ，");
    //首页，上一页
    $(".pages").empty();
    if(pageList.hasPreviousPage==true){
        var firstPageLi =$("<li class=\"page-item first\">" +
            "                            <button class=\"nav-btn\">首页</button>" +
            "              </li>");
        firstPageLi.click(function () {
            get_all_user(1);
        });

        var prePageLi = $("<li class=\"page-item prev\">" +
            "                            <button class=\"nav-btn\">上一页</button>" +
            "              </li>");
        prePageLi.click(function () {
            get_all_user(pageList.prePage);
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
            get_all_user(pageList.lastPage+1);
        });

        var nextPageLi = $("<li class=\"page-item next\">" +
            "                            <button class=\"nav-btn\">下一页</button>" +
            "               </li>");
        nextPageLi.click(function () {
            get_all_user(pageList.nextPage);
        });
        $(".pages").append(nextPageLi).append(lastPageLi);
    }
}


//搜索
$(document).on("click",".search",function () {
    get_all_user(1);
});



// 点击分页1 2 3
$(document).on("click",".pagination-btn",function () {
   get_all_user($(this).text());
});
//点击跳转
$(document).on("click",".jump-pager-button",function () {
    get_all_user($(".jump-pager-input").val());
});
//enter跳转
$(document).keydown(function(ev){
    if(ev.keyCode==13){
        $(".jump-pager-button").trigger("click");
    }
});



//点击修改，请求详细信息
$(document).on("click",".edit",function () {
    var userId=$(this).parent().parent().find("td:eq(0)").text();
    $.ajax({
        url:"/user/get_user_details.do",
        type:"POST",
        dataType:"json",
        data:{
            userId:userId
        },
        success:function (result) {
            if(result.status==200){
                set_edit_user_html(result);
            }else {
                alert(result.msg);
            }
        }
    });
});
//修改弹框显示
function set_edit_user_html(result) {
    //清空之前的数据
    var userList=result.data;
    //填充详细数据
    $("#user-id").text(userList.userId);
    $("#user-name").val(userList.userName);
    $("#user-email").val(userList.userEmail);
    $("#user-phone").val(userList.userPhone);
    var statusSelect=$("#user-level-select");
    statusSelect.find("option:selected").attr("selected",false);
    switch (userList.userLevel){//设值状态
        case 10001:statusSelect.find("option[value='10001']").attr("selected",true);break;
        case 10002:statusSelect.find("option[value='10002']").attr("selected",true);break;
        case 10003:statusSelect.find("option[value='10003']").attr("selected",true);break;
        case 10004:statusSelect.find("option[value='10004']").attr("selected",true);break;
        case 10005:statusSelect.find("option[value='10005']").attr("selected",true);break;
    }
    var departmentSelect=$("#user-department-select");
    departmentSelect.find("option:selected").attr("selected",false);
    switch (userList.userDepartmentId){//设值状态
        case 30001:departmentSelect.find("option[value='30001']").attr("selected",true);break;
        case 30002:departmentSelect.find("option[value='30002']").attr("selected",true);break;
        case 30003:departmentSelect.find("option[value='30003']").attr("selected",true);break;
        case 30004:departmentSelect.find("option[value='30004']").attr("selected",true);break;
        case 30005:departmentSelect.find("option[value='30005']").attr("selected",true);break;
    }
    $("#createtime").text(renderTime(userList.createTime));
    $("#updatetime").text(renderTime(userList.updateTime));
}
//确定修改请求数据
$(document).on("click",".edit-yes",function () {
    $.ajax({
        url:"/user/update_user.do",
        type:"POST",
        dataType:"json",
        data:{
            userId:$("#user-id").text(),
            userName:$("#user-name").val(),
            userEmail:$("#user-email").val(),
            userPhone:$("#user-phone").val(),
            userLevel:$("#user-level-select").val(),
            userDepartmentId:$("#user-department-select").val(),
            userDepartmentName:$("#user-department-select").find("option:selected").text()
        },
        success:function (result) {
            alert(result.msg);
            $("#modal-edit").hide();
            $(".mask").fadeOut();
            get_all_user();//再次请求接口数据刷新页面
        },
    })
});
//取消修改
$(document).on("click",".edit-no",function () {
    $("#modal-edit").hide();
    $(".mask").fadeOut();
});



//点击删除，保存要删除人的id
$(document).on("click",".del",function () {
    var userId=$(this).parent().parent().find("td:eq(0)").text();
    $(".tips-del-yes").attr("user-id",userId);
});
//确认删除
$(document).on("click",".tips-del-yes",function () {
    $.ajax({
        url:"/user/delete_user.do",
        type:"POST",
        dataType:"json",
        data:{
            userId:$(this).attr("user-id")
        },
        success:function (result) {
            alert(result.msg);
            $("#modal-del").hide();
            $(".mask").fadeOut();
            get_all_user();
        }
    });
});
//取消删除
$(document).on("click",".tips-del-no",function () {
    $("#modal-del").hide();
    $(".mask").fadeOut();
});



//点击初始化密码
$(document).on("click",".reset",function () {
    var userId=$(this).parent().parent().find("td:eq(0)").text();
    $(".tips-reset-yes").attr("user-id",userId);
});
//确认初始化密码
$(document).on("click",".tips-reset-yes",function () {
    $.ajax({
        url:"/user/update_user.do",
        type:"POST",
        dataType:"json",
        data:{
            userId:$(this).attr("user-id"),
            userPassword:"123456"
        },
        success:function (result) {
            alert(result.msg);
            $("#modal-reset").hide();
            $(".mask").fadeOut();
        }
    });
});
//取消初始化密码
$(document).on("click",".tips-reset-no",function () {
    $("#modal-reset").hide();
    $(".mask").fadeOut();
});


//确认新增
$(document).on("click",".insert-yes",function () {
   $.ajax({
       url:"/user/create_user.do",
       type:"POST",
       dataType:"json",
       data:{
           userName:$("#insert-user-name").val(),
           userPhone:$("#insert-user-phone").val(),
           userPassword:$("#insert-user-password").text(),
           userLevel:$("#insert-user-level-select").val(),
           userDepartmentId:$("#insert-user-department-select").val(),
           userDepartmentName:$("#insert-user-department-select").find("option:selected").text()
       },
       success:function (result) {
           alert(result.msg);
           $("#modal-insert").hide();
           $(".mask").fadeOut();
           get_all_user();
       }
   })
});
//取消新增
$(document).on("click",".insert-no",function () {
    $("#modal-insert").hide();
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