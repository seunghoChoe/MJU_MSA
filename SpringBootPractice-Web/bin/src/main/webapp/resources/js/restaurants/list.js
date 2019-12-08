function getRestaurant(res_index) {
    location.href = '/restaurants/' + res_index;
}

function addFavorite(res_index) {
    event.cancelBubble = true; // 중복 이벤트 처리 (https://m.blog.naver.com/PostView.nhn?blogId=ys38317&logNo=150075866640&proxyReferer=https%3A%2F%2Fwww.google.com%2F)
    location.href = '/favorites/new/'+ res_index;
}