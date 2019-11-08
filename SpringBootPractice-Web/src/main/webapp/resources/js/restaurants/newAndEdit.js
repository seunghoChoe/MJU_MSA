$(function () {
    var ckeditor_config = {
        resize_enabled: true,
        height: 500,
        enterMode: CKEDITOR.ENTER_BR,
        shiftEnterMode: CKEDITOR.ENTER_P,
        filebrowserUploadUrl: "/image-upload"
    };

    CKEDITOR.replace('res_content', ckeditor_config);
});

$('addRowButton').click(function () {

});