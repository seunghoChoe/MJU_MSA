// 식당 이름 글자수 검사
function checkName() {
    var nameLength = document.getElementById("res_name").value.length;
    $('#nameLengthCounter').html(" (" + nameLength + " / 50자)");

    if (nameLength >= 50) {
        alert("이름은 50자 이내로 작성해주세요.");
    }
}