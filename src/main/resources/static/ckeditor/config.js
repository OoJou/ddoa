/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

// CKEDITOR.editorConfig = function( config ) {
// 	// Define changes to default configuration here. For example:
// 	// config.language = 'fr';
// 	// config.uiColor = '#AADC6E';
//
// };
CKEDITOR.editorConfig = function( config ) {
    config.toolbarGroups = [
        { name: 'styles', groups: [ 'styles' ] },
        { name: 'paragraph', groups: [ 'align', 'indent', 'list', 'blocks', 'bidi', 'paragraph' ] },
        { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
        '/',
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'forms', groups: [ 'forms' ] },
        { name: 'links', groups: [ 'links' ] },
        { name: 'insert', groups: [ 'insert' ] },
        { name: 'colors', groups: [ 'colors' ] },
        { name: 'tools', groups: [ 'tools' ] },
        { name: 'others', groups: [ 'others' ] },
        { name: 'about', groups: [ 'about' ] }
    ];

    config.removeButtons = 'About,ShowBlocks,BGColor,TextColor,Cut,Copy,Paste,PasteText,PasteFromWord,NewPage,Print,Templates,SelectAll,Scayt,HiddenField,ImageButton,Button,Select,Replace,Find,Language,BidiRtl,BidiLtr,Flash,Iframe,Maximize,RemoveFormat,CopyFormatting,Form,PageBreak,CreateDiv,Anchor,Textarea,TextField,Save,Preview';
    config.extraPlugins = 'image2';
    // 服务器端上传图片接口URL
    config.filebrowserImageUploadUrl='/file/upload_image.do';
};