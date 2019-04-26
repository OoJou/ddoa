/*
  使用Layui的各类模块，统一放在这里进行预加载
*/
layui.use(['element','form','layedit','laydate','table','layer'], function(){
    var element = layui.element
        ,form = layui.form
        ,layedit = layui.layedit
        ,laydate = layui.laydate
        ,table = layui.table
        ,layer = layui.layer;

    //触发事件
    var active = {
        offset: function(othis){
            var type = othis.data('type')
                ,text = othis.text();

            layer.open({
                type: 1
                ,title:'<div style="text-align: center">'+ text +'</div>'
                ,offset: type //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,id: 'show-notice'+type //防止重复弹出
                ,content:'<div style="padding: 200px 1000px;">'+ text +'</div>'
                ,btn: '关闭全部'
                ,btnAlign: 'c' //按钮居中
                ,shade: 0 //不显示遮罩
                ,yes: function(){
                    layer.closeAll();
                }
            });
        }

}

    $('#show-notice li a').on('click', function(){
        var othis = $(this), method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });
    // form.on('submit(formDemo)', function(data){
    //     layer.msg(JSON.stringify(data.field));
    //     return false;
    // });
});

/**
 *  入口文件索引
 *  使用说明：将此文件引入到页面中，可在script标签上定义一个data-main=""属性，
 *  此属性指定页面入口文件。
 *
 **/
// (function () {
//
//     var entry,
//         // 配置所有应用的入口文件，程序将会按照data-main属性中设置的值进行索引查找
//         // 如果你在引入此脚本的script标签上没有设置data-main属性，程序将会默认访问home.js文件
//         app = {
//             home : '{/}home',
//             login : '{/}login'
//         };
//
//     (function(){
//
//         var dataMain, scripts = document.getElementsByTagName('script'),
//             eachScripts = function(el){
//                 dataMain = el.getAttribute('data-main');
//                 if(dataMain){
//                     entry = dataMain;
//                 }
//             };
//
//         [].slice.call(scripts).forEach(eachScripts);
//
//     })();
//
//     layui.config({
//         base: '../static/layui/lay/modules/'
//     }).extend(app).use( entry || 'home');
//
// })();