// 게시글 수정 폼
function editPost(post_id) {
    window.location.href='/board/posts/' + post_id + '/edit';
}

// 게시글 삭제
function deletePost(post_id) {
    console.log(post_id);

    var message = confirm("게시글을 삭제하시겠습니까?");
    if (message) {
        $.ajax({
            url: '/board/posts/' + post_id,
            type: 'delete',

            success: function (result) {
                if (result === 1) {
                    alert(post_id + '번 게시글 삭제 성공!');
                    location.href = '/board/posts'
                } else {
                    alert(post_id + '번 게시글 삭제 실패!');
                    location.href = '/board/posts/' + post_id;
                }
            },
            error: function () {
                alert("오류가 발생하였습니다.");
            }
        });
    }
}