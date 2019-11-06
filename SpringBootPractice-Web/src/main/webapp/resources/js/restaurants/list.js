function getRestaurant(res_index) {
    location.href = '/restaurants/' + res_index;
}

function addFavorite(res_index) {
	location.href = '/favorites/new/' + res_index;
}