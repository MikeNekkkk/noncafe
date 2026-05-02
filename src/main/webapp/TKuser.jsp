<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="img/fav-icon.png" type="image/x-icon" />
<title>Tài Khoản - NÕN CAFÉ</title>

<!-- CSS Links -->
<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="vendors/linearicons/style.css" rel="stylesheet">
<link href="vendors/flat-icon/flaticon.css" rel="stylesheet">
<link href="vendors/stroke-icon/style.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="vendors/revolution/css/settings.css" rel="stylesheet">
<link href="vendors/revolution/css/layers.css" rel="stylesheet">
<link href="vendors/revolution/css/navigation.css" rel="stylesheet">
<link href="vendors/animate-css/animate.css" rel="stylesheet">
<link href="vendors/owl-carousel/owl.carousel.min.css" rel="stylesheet">
<link href="vendors/magnifc-popup/magnific-popup.css" rel="stylesheet">
<link href="vendors/jquery-ui/jquery-ui.min.css" rel="stylesheet">
<link href="vendors/nice-select/css/nice-select.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="css/responsive.css" rel="stylesheet">

<style>
/* User Menu Styles */
.user-menu {
	position: relative;
	display: inline-block;
	margin-left: 10px;
}

.user-menu-toggle {
	background: none;
	border: none;
	color: #fff;
	cursor: pointer;
	padding: 8px 12px;
	font-size: 13px;
	transition: all 0.3s;
	border-radius: 4px;
}

.user-menu-toggle:hover {
	background: rgba(255, 255, 255, 0.1);
	color: rgb(128, 64, 0);
}

.user-menu-toggle i.fa-user-circle {
	font-size: 16px;
	margin-right: 5px;
}

.user-dropdown {
	display: none;
	position: absolute;
	right: 0;
	top: calc(100% + 10px);
	background: #fff;
	min-width: 220px;
	box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
	border-radius: 8px;
	z-index: 9999;
	overflow: hidden;
}

.user-dropdown.active {
	display: block;
	animation: fadeInDown 0.3s ease;
}

@
keyframes fadeInDown {from { opacity:0;
	transform: translateY(-10px);
}

to {
	opacity: 1;
	transform: translateY(0);
}

}
.user-dropdown::before {
	content: '';
	position: absolute;
	top: -8px;
	right: 20px;
	width: 0;
	height: 0;
	border-left: 8px solid transparent;
	border-right: 8px solid transparent;
	border-bottom: 8px solid #fff;
}

.user-info {
	padding: 15px 20px;
	border-bottom: 2px solid #f0f0f0;
	font-weight: 600;
	color: #2c3e50;
	background: #f8f9fa;
}

.user-info i {
	margin-right: 8px;
	color: rgb(128, 64, 0);
}

.user-dropdown a {
	display: block;
	padding: 12px 20px;
	color: #555;
	text-decoration: none;
	transition: all 0.3s;
	border-bottom: 1px solid #f5f5f5;
}

.user-dropdown a:last-child {
	border-bottom: none;
}

.user-dropdown a:hover {
	background: #f8f9fa;
	color: rgb(128, 64, 0);
	padding-left: 25px;
}

.user-dropdown a i {
	margin-right: 10px;
	width: 18px;
	text-align: center;
}

/* Auth Links Styles */
.auth-links {
	display: inline-block;
	margin-left: 10px;
}

.auth-links a {
	color: #fff !important;
	padding: 8px 15px;
	margin-left: 5px;
	border-radius: 4px;
	transition: all 0.3s;
	font-size: 13px;
	text-decoration: none;
	display: inline-block;
}

.auth-links a:hover {
	background: rgba(255, 255, 255, 0.1);
	color: rgb(128, 64, 0) !important;
}

.auth-links a i {
	margin-right: 5px;
}

.auth-links a.btn-register {
	background: #rgb(128, 64, 0);
	color: #fff !important;
}

.auth-links a.btn-register:hover {
	background: #e67e9f;
}

/* Responsive */
@media ( max-width : 768px) {
	.user-menu {
		margin-left: 0;
		margin-top: 10px;
	}
	.user-dropdown {
		right: auto;
		left: 0;
	}
	.user-dropdown::before {
		right: auto;
		left: 20px;
	}
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" />

	<section class="banner_area">
		<div class="container">
			<div class="banner_text">
				<h3>Tài khoản người dùng</h3>
				<ul>
					<li><a href="home">Trang chủ</a></li>
					<li><a href="resetTK">Tài khoản</a></li>
				</ul>
			</div>
		</div>
	</section>

	<section class="billing_details_area p_100">
		<div class="container">
			<div class="row">
				<div class="col-lg-7">

					<div class="main_title"></div>

					<div class="billing_form_area">

						<form class="billing_form row" action="resetTK" method="post"
							id="checkoutForm">
							<c:if test="${not empty sessionScope.user}">
								<div class="user-info-box">
									<h5>
										<i class="fa fa-user-circle"></i> Xin chào,
										${sessionScope.user.fullName}!
									</h5>

								</div>
							</c:if>
							<div class="row">

								<div class="col-md-6 form-group">
									<label>Họ *</label> <input type="text" class="form-control"
										name="firstName" value="${firstName}" placeholder="Họ"
										required>
								</div>
								<div class="col-md-6 form-group">
									<label>Tên *</label> <input type="text" class="form-control"
										name="lastName" value="${lastName}" placeholder="Tên" required>
								</div>

								<div class="col-md-12 form-group">
									<label>Tên hiển thị</label> <input type="text"
										class="form-control" name="nameht" value="${nameht}"
										placeholder="Tên hiển thị của bạn" required>
									<p> </p>

								</div>

								<div class="col-md-12 form-group">
									<h5>Thay đổi thông tin / mật khẩu</h5>

									<label>Mật khẩu hiện tại (Bắt buộc nếu muốn đổi mật
										khẩu mới)</label> <input type="password" class="form-control"
										name="curentPassWord" value=""
										placeholder="Mật khẩu hiện tại của bạn">
								</div>

								<div class="col-md-12 form-group">
									<label>Mật khẩu mới (bỏ trống nếu chỉ đổi Tên) </label> <input
										type="password" class="form-control" name="ChangePassword"
										value="" placeholder="Mật khẩu mới của bạn">
								</div>

								<div class="col-md-12 form-group">
									<label>Xác nhận mật khẩu mới</label> <input type="password"
										class="form-control" name="checkChangePassword" value=""
										placeholder="Xác nhận mật khẩu mới của bạn">
								</div>
								<c:if test="${not empty errorMessage}">
									<div class="col-md-12">
										<div class="alert alert-danger" role="alert">
											<i class="fa fa-exclamation-triangle"></i> ${errorMessage}
										</div>
									</div>
								</c:if>

								<c:if test="${not empty successMessage}">
									<div class="col-md-12">
										<div class="alert alert-success" role="alert">
											<i class="fa fa-check-circle"></i> ${successMessage}
										</div>
									</div>
								</c:if>
								<div class="col-md-12 form-group">
									<button type="submit" class="pest_btn"
										style="border: none; cursor: pointer;">Lưu thay đổi</button>
								</div>
							</div>
							</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="footer.jsp" />

	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="vendors/nice-select/js/jquery.nice-select.min.js"></script>
	<script src="js/theme.js"></script>
	<script>
		// --- 3. ẨN HIỆN PASSWORD ---
		function togglePassword() {
			var checkBox = document.getElementById("f-option2");
			var passBox = document.getElementById("passwordBox");
			var passInput = passBox.querySelector("input");

			if (checkBox.checked) {
				passBox.style.display = "block";
				passInput.required = true;
			} else {
				passBox.style.display = "none";
				passInput.required = false;
			}
		}

		function proceedToPayment() {
			var form = document.getElementById('checkoutForm');
			if (!form.checkValidity()) {
				form.reportValidity();
				return;
			}

			form.submit();
		}

		$(document).ready(function() {
			$('select').niceSelect();
		});
	</script>
	<script>
		function toggleUserMenu() {
			var dropdown = document.getElementById('userDropdown');
			if (dropdown) {
				dropdown.classList.toggle('active');
			}
		}

		document.addEventListener('click', function(event) {
			var userMenu = document.querySelector('.user-menu');
			var dropdown = document.getElementById('userDropdown');

			if (userMenu && dropdown && !userMenu.contains(event.target)) {
				dropdown.classList.remove('active');
			}
		});
	</script>
</body>
</html>