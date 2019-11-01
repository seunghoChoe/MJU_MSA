// ID 검사 (AJAX 호출 추가)
function checkUserId() {
    var userId = $('#user_id').val();
    var regexp = /^[a-z0-9][a-z0-9_\-]{4,16}$/;

    $.ajax({
        type: 'GET',
        url: 'http://52.78.148.181:8080/user-service/check-id/' + userId,
        data: {user_id: userId},

        success: function (result) {
            console.log(result);
            if (regexp.test(userId) && result === 0) {
                $('#userIdMessage').html("사용 가능한 ID 입니다.").css('color', '#0098F7');
            } else if (! regexp.test(userId)) {
                $('#userIdMessage').html("5~15자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.").css('color', '#FF3636');
            } else {
                $('#userIdMessage').html("사용할 수 없는 ID 입니다.").css('color', '#FF3636');
            }
            $('#userIdError').hide();
        },
        error: function () {
            alert("오류가 발생하였습니다.");
        }
    });
}

// 비밀번호 검사
function checkUserPassword() {
    var userPassword = $('#user_password').val();
    var regexp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;

    if (regexp.test(userPassword)) {
        $('#userPasswordMessage').html("비밀번호가 확인되었습니다.").css('color', '#0098F7');
    } else if (! regexp.test(userPassword)) {
        $('#userPasswordMessage').html("8~15자의 영문 대 소문자, 숫자, 특수문자를 최소 1개씩 사용하세요.").css('color', '#FF3636');
    } else {
        $('#userPasswordMessage').html("사용할 수 없는 비밀번호입니다.").css('color', '#FF3636');
    }
    $('#userPasswordError').hide();
}

// 이름 검사
function checkUserName() {
    var userName = $('#user_name').val();
    var regexp = /^[가-힣]{2,5}$/;

    if (regexp.test(userName)) {
        $('#userNameMessage').html("사용 가능한 이름입니다.").css('color', '#0098F7');
    } else {
        $('#userNameMessage').html("2~5자의 한글만 사용 가능합니다.").css('color', '#FF3636');
    }
    $('#userNameError').hide();
}

// 이메일 검사
function checkUserEmail() {
    var userEmail = $('#user_email').val();
    var regexp = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;

    if (regexp.test(userEmail)) {
        $('#userEmailMessage').html("사용 가능한 이메일입니다.").css('color', '#0098F7');
    } else if (! regexp.test(userEmail)) {
        $('#userEmailMessage').html("이메일 형식이 맞지 않습니다.").css('color', '#FF3636');
    } else {
        $('#userEmailMessage').html("사용할 수 없는 이메일입니다.").css('color', '#FF3636');
    }
    $('#userEmailError').hide();
}