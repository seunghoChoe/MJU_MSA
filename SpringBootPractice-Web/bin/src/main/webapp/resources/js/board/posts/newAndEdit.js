$(function () {
    var ckeditor_config = {
        resize_enabled: true,
        height: 500,
        enterMode: CKEDITOR.ENTER_BR,
        shiftEnterMode: CKEDITOR.ENTER_P,
        filebrowserUploadUrl: "/image-upload"
    };

    CKEDITOR.replace('post_content', ckeditor_config);
});

// 게시글 제목 글자수 검사
function checkTitle() {
    var titleLength = document.getElementById("post_title").value.length;
    $('#titleLengthCounter').html(" (" + titleLength + " / 100자)");

    if (titleLength >= 100) {
        alert("제목은 100자 이내로 작성해주세요.");
    }
}

// 게시글 내용 글자수 검사 (동작 안함)
function checkContent() {
    var contentLength = CKEDITOR.instances.post_content.getData().length;
    $('#contentLengthCounter').html(" (" + contentLength + " / 1000자)");

    if (contentLength >= 1000) {
        alert("내용은 1000자 이내로 작성해주세요.");
    }
}