<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
	integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N"
	crossorigin="anonymous">
<!-- AJAX를 사용하려면 jquery 원본 필요 -->
<script src="https://code.jquery.com/jquery-3.6.4.js"
	integrity="sha256-a9jBBRygX1Bh5lt8GZjXDzyOB+bWve9EiO7tROUtj/E="
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
	crossorigin="anonymous"></script>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.min.css" rel="stylesheet">

</head>
<body class="bg-gradient-primary">

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
							<form method="post" action="/user/sign_in">
								<div class="form-group">
									<input type="text" class="form-control"
										id="userId" name="userId"
										placeholder="Username">
								</div>
								<div class="form-group">
									<input type="password" class="form-control"
										id="password" name="password" placeholder="Password">
								</div>

								<button type="button" class="btn btn-primary btn-user btn-block">로그인</button>
								<a href="#" class="btn btn-secondary btn-user btn-block">회원가입</a>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>