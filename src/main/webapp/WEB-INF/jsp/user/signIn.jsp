<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container">

	<!-- Outer Row -->
	<div class="row justify-content-center">

		<div class="col-xl-10 col-md-9">

			<div class="o-hidden border-0 shadow-lg my-5">
				<div class="p-0 d-flex justify-content-center">
					<!-- Nested Row within Card Body -->
					<div class="p-5 col-7">
						<div class="text-center">
							<h1 class="h4 text-gray-900 mb-4">로그인</h1>
						</div>
						<form id="loginForm" method="post" action="/user/sign_in">
							<div class="form-group">
								<input type="text" class="form-control" id="loginId"
									name="loginId" placeholder="Username">
							</div>
							<div class="form-group">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="Password">
							</div>

							<button type="submit" class="btn btn-primary btn-user btn-block">로그인</button>
							<a href="/user/sign_up_view"
								class="btn btn-secondary btn-user btn-block">회원가입</a>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		$("#loginForm").on('submit', function(e){
			e.preventDefault(); // 서브밋 기능 중단
			
			let loginId = $("#loginId").val().trim();
			let password = $("#password").val();

			if (!loginId) {
				alert("아이디를 입력하세요");
				return;
			}
			if (!password) {
				alert("비밀번호를 입력하세요");
				return;
			}

			let url = $("#loginForm").attr("action");
			console.log(url);
			let params = $("#loginForm").serialize();
			console.log(params);

			$.post(url, params)
			.done(function(data) {
				if (data.code == 1) {
					//로그인 성공 시 게시판 목록으로 이동
					location.href = "/post/post_list_view";
				} else {
					alert(data.errorMessage);
				}
			})

		});
	});
</script>
