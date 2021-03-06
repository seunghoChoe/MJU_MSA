// 식당 수정 폼
function editRestaurant(res_index) {
    window.location.href='/restaurants/' + res_index + '/edit';
}

// 식당 삭제
function deleteRestaurant(res_index) {
    console.log(res_index);

    var message = confirm("식당을 삭제하시겠습니까?");
    if (message) {
        $.ajax({
            url: '/restaurants/' + res_index,
            type: 'delete',

            success: function (result) {
                if (result === 1) {
                    alert(res_index + '번 식당 삭제 성공!');
                    location.href = '/restaurants'
                } else {
                    alert(res_index + '번 식당 삭제 실패!');
                    location.href = '/restaurants/' + res_index;
                }
            },
            error: function () {
                alert("오류가 발생하였습니다.");
            }
        });
    }
}