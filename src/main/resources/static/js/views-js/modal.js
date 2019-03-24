$(function(){
});
var modalType;
$(document).on("click",".open",function () {
    modalType=checkmodalType($(this).val());
    showmodal(modalType);
    $(".tips").text("确定"+$(this).val()+"吗？");
});
function showmodal(modal){
    var wW=$(window).width();  //浏览器可视区域宽度和高度
    var wH=$(window).height();
    var oW=modal.innerWidth(); //获取类叫modal的宽度和高度
    var oH=modal.innerHeight();
    modal.show().css({"top":(wH-oH)/2+"px","left":(wW-oW)/2+"px"});
    $(".mask").fadeIn();
}
function checkmodalType(type) {
    switch(type) {
        case "修改":return $("#modal-edit");break;
        case "删除":return $("#modal-del");break;
        case "初始化密码":return $("#modal-reset");break;
        case "新增":return $("#modal-insert");break;
        case "下载":return $("#modal-download");break;
        case "上传文件":return $("#modal-upload");break;
    }
}
$(window).resize(function(){
    if(modalType.is(':visible')){ //弹出框必须可见后 才能调用showmodal()
        showmodal(modalType);
    }
});
$(document).on("click",".close",function () {
    modalType.hide();
    $(".mask").fadeOut();
});
$(document).keydown(function(ev){
    if(ev.keyCode==27){  //当按下键盘Esc时===》close关闭按钮
        $(".close").trigger("click");//trigger("事件名")  模拟事件
    }
});