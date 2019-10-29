<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Sign up Form</title>
</head>
<body>
	<h2>회원가입 페이지</h2>

	<div class="container">
		<form action="/restSignUpProc" method="post">
			<div class="form-group">
				<label for="user_id">ID</label> <input type="text"
					class="form-control" id="user_id" name="user_id"
					placeholder="아이디를 입력하십시오." required>
			</div>

			<div class="form-group">
				<label for="user_password">비밀번호</label> <input type="password"
					class="form-control" id="user_password" name="user_password"
					placeholder="비밀번호를 입력하세요." required>
			</div>

			<div class="form-group">
				<label for=user_name">이름</label>
				<textarea class="form-control" id="user_name" name="user_name"
					rows="3" placeholder="이름(본명)을 입력하세요." required></textarea>
			</div>

			<div class="form-group">
				<label for=user_email">이메일</label>
				<textarea class="form-control" id="user_email" name="user_email" rows="3"
					placeholder="이메일을 입력하세요."></textarea>
			</div>
			<button type="submit" class="btn btn-primary">가입</button>
		</form>
	</div>



	<%@ include file="bootstrap.jsp"%>
</body>
</html>