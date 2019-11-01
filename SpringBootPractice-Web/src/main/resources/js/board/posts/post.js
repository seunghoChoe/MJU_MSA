// 게시글 삭제
function deletePost(post_id) {
    console.log(post_id);
    $.ajax({
        url: 'http://52.78.148.181:8080/posts/' + post_id,
        type: 'delete',
        success: function (result) {
            console.log('delete: ' + result);
            location.href='/board/posts';
        }
    })
}