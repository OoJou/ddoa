//控制用户的主页操作
$(function () {

})

function get_user() {
    $.ajax({
        url:"/user/get_information.do",
        type:"POST",
        dataType:"json",
        success:function (result) {
            if(result.status==200){//如果存在当前用户，执行
                //保存返回的用户信息，填充到html
            }
        }
    })
}