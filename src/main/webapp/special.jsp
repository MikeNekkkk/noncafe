<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="vi">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" href="img/blog/da_logo9.jpg" type="image/x-icon" />
<title>NÕN CAFÉ</title>

<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="vendors/linearicons/style.css" rel="stylesheet">
<link href="vendors/flat-icon/flaticon.css" rel="stylesheet">
<link href="vendors/stroke-icon/style.css" rel="stylesheet">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Rev slider css -->
<link href="vendors/revolution/css/settings.css" rel="stylesheet">
<link href="vendors/revolution/css/layers.css" rel="stylesheet">
<link href="vendors/revolution/css/navigation.css" rel="stylesheet">
<link href="vendors/animate-css/animate.css" rel="stylesheet">

<!-- Extra plugin css -->
<link href="vendors/owl-carousel/owl.carousel.min.css" rel="stylesheet">
<link href="vendors/magnifc-popup/magnific-popup.css" rel="stylesheet">

<link href="css/style.css" rel="stylesheet">
<link href="css/responsive.css" rel="stylesheet">

<style>
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

	<header class="main_header_area">
		<div class="top_header_area row m0">
			<div class="container">
				<div class="float-left">
					<a href="tel:+0901957365"><i class="fa fa-phone"
						aria-hidden="true"></i> + 0915 493 322</a> <a
						href="mailto:info@cakebakery.com"><i class="fa fa-envelope-o"
						aria-hidden="true"></i> noncafe@gmail.com</a>
				</div>
				<div class="float-right">
					<ul class="h_social list_style">
						<li><a href="#"><i class="fa fa-facebook"></i></a></li>
						<li><a href="#"><i class="fa fa-twitter"></i></a></li>
						<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
						<li><a href="#"><i class="fa fa-linkedin"></i></a></li>
					</ul>
					<c:choose>
						<c:when test="${not empty sessionScope.user}">
							<div class="user-menu">
								<button class="user-menu-toggle" onclick="toggleUserMenu()">
									<i class="fa fa-user-circle"></i> ${sessionScope.user.fullName}
									<i class="fa fa-angle-down"></i>
								</button>
								<div class="user-dropdown" id="userDropdown">
									<div class="user-info">
										<i class="fa fa-user"></i> ${sessionScope.user.fullName}
									</div>
									<c:if test="${sessionScope.user.isAdmin()}">
										<a href="${pageContext.request.contextPath}/AdminHome"> <i
											class="fa fa-dashboard"></i> Quản trị
										</a>
									</c:if>
									<c:if test="${!sessionScope.user.isAdmin()}">
										<a href="${pageContext.request.contextPath}/listuserOrders">
											<i class="fa fa-shopping-bag"></i> Đơn hàng của tôi
										</a>
										<a href="${pageContext.request.contextPath}/resetTK"> <i
											class="fa fa-user"></i> Tài khoản
										</a>
									</c:if>
									<a href="${pageContext.request.contextPath}/logout"> <i
										class="fa fa-sign-out"></i> Đăng xuất
									</a>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="auth-links">
								<a href="${pageContext.request.contextPath}/login"> <i
									class="fa fa-sign-in"></i> Đăng nhập
								</a> <a href="${pageContext.request.contextPath}/register"> <i
									class="fa fa-sign-in"></i> Đăng ký
								</a>

							</div>
						</c:otherwise>
					</c:choose>
					<ul class="h_search list_style">
						<li class="shop_cart">
							<%
							List<Entity.CartItem> cart = (List<Entity.CartItem>) session.getAttribute("cart");
							int cartCount = (cart != null) ? cart.size() : 0;
							%> <a href="${pageContext.request.contextPath}/cart.jsp"
							title="Xem giỏ hàng" data-cart-count="<%= cartCount %>"> <i
								class="lnr lnr-cart"></i>
						</a>
						</li>

						<li><a class="popup-with-zoom-anim" href="#test-search">
								<i class="fa fa-search"></i>
						</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="main_menu_area">
			<div class="container">
				<nav class="navbar navbar-expand-lg navbar-light bg-light">
					<a class="navbar-brand"
						href="${pageContext.request.contextPath}/home"> <img
						src="${pageContext.request.contextPath}/img/blog/den_logo9.jpg" alt="">
						<img src="${pageContext.request.contextPath}/img/blog/den_logo9.jpg"
						alt="">
					</a>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="my_toggle_menu"> <span></span> <span></span> <span></span>
						</span>
					</button>
					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="navbar-nav mr-auto">

							<li class="dropdown submenu active"><a
								href="${pageContext.request.contextPath}/home">Trang
									chủ</a></li>
							<li><a
								href="${pageContext.request.contextPath}/CakeControl"> Sản
									phẩm</a></li>
							<li><a
								href="${pageContext.request.contextPath}/about-us.jsp">Về
									chúng tôi</a></li>
						</ul>
						<ul class="navbar-nav justify-content-end">



							<li><a href="${pageContext.request.contextPath}/blogList">Bài
									Viết</a></li>


							<li><a
								href="${pageContext.request.contextPath}/ProductCategories">Mua
									Sắm</a></li>

							<li><a href="${pageContext.request.contextPath}/contact.jsp">Liên hệ</a></li>
						</ul>
					</div>
				</nav>
			</div>
		</div>
	</header>
	<section class="banner_area">
		<div class="container">
			<div class="banner_text">
				<h3>Hướng dẫn pha chế</h3>
				<ul>
					<li><a href="index.html">Trang chủ</a></li>
					<li><a href="special.html">Hướng dẫn pha chế</a></li>
				</ul>
			</div>
		</div>
	</section>
	<section class="special_area p_100">
		<div class="container">
			<div class="" style="padding-botton: opx">
				<h2>Hướng Dẫn Pha Chế CÀ PHÊ DỪA</h2>
				<h5>
					<br>Cà Phê Dừa không chỉ mang đến bạn nguồn năng lượng sáng
					tạo dồi dào mà còn cung cấp thêm nhiều dưỡng chất tốt cho sức khỏe,
					hỗ trợ làm đẹp cho mái tóc và làn da.

				</h5>
			</div>
			<div class="special_item_inner">
				<div class="specail_item">
					<div class="row">
						<div class="col-lg-4">
							<div class="s_left_img">
								<img class=""
									style="position: relative; width: 65%; padding-bottom: 0%; left: 76px;"
									src="img/recipe/recipe-2.png" alt="">
							</div>
						</div>
						<div class="col-lg-8">
							<div class="special_item_text">
								<h4>CÀ PHÊ DỪA</h4>
								<p>Là sự kết hợp tuyệt hảo giữa hương vị của cà phê pha phin
									hảo hạng đậm chất truyền thống cùng sự béo bùi của Nước Cốt
									Dừa, hòa quyện với hương Lá Nếp nồng ấm.</p>
								<!-- <a class="pink_btn" href="#">Purchase now</a> -->
							</div>
						</div>
					</div>
				</div>
				<div class="specail_item">
					<div class="row">
						<div class="col-lg-6">
							<div class="s_item_left">
								<div class="main_title">
									<h2>Các nguyên liệu cần chuẩn bị:</h2>
								</div>
								<ul class="list_style">
									<li><a href="#">Cà Phê Sáng Tạo hoặc Chế Phin (lượng
											vừa phải theo sở thích)</a></li>
									<li><a href="#">50ml nước cốt dừa</a></li>
									<li><a href="#">40ml sữa đặc có đường Brothers</a></li>
									<li><a href="#">20ml kem béo Rich</a></li>
									<li><a href="#">10ml syrup lá nếp (lá dứa)</a></li>
									<li><a href="#">1 ít dừa sấy khô</a></li>
									<li><a href="#">Nước sôi và đá viên</a></li>
								</ul>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="s_right_img">
								<img class="img-fluid" src="img/special/special-2.jpg" alt="">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="our_bakery_area making_area">
		<div class="container">
			<div class="main_title">
				<h2>Hãy cùng xem video hướng dẫn và thử ngay bạn nhé!</h2>

			</div>

			<div style="position: relative; width: 100%; padding-bottom: 56.25%;">
				<iframe src="https://www.youtube.com/embed/3ihwfspniO0"
					frameborder="0"
					allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
					allowfullscreen=""
					style="position: absolute; top: 0; left: 180px; width: 70%; height: 70%;">
				</iframe>
			</div>
		</div>
	</section>

	<jsp:include page="footer.jsp" />



	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="vendors/revolution/js/jquery.themepunch.tools.min.js"></script>
	<script src="vendors/revolution/js/jquery.themepunch.revolution.min.js"></script>
	<script
		src="vendors/revolution/js/extensions/revolution.extension.actions.min.js"></script>
	<script
		src="vendors/revolution/js/extensions/revolution.extension.video.min.js"></script>
	<script
		src="vendors/revolution/js/extensions/revolution.extension.slideanims.min.js"></script>
	<script
		src="vendors/revolution/js/extensions/revolution.extension.layeranimation.min.js"></script>
	<script
		src="vendors/revolution/js/extensions/revolution.extension.navigation.min.js"></script>
	<!-- Extra plugin js -->
	<script src="vendors/owl-carousel/owl.carousel.min.js"></script>
	<script src="vendors/magnifc-popup/jquery.magnific-popup.min.js"></script>
	<script src="vendors/isotope/imagesloaded.pkgd.min.js"></script>
	<script src="vendors/isotope/isotope.pkgd.min.js"></script>
	<script src="vendors/datetime-picker/js/moment.min.js"></script>
	<script
		src="vendors/datetime-picker/js/bootstrap-datetimepicker.min.js"></script>
	<script src="vendors/nice-select/js/jquery.nice-select.min.js"></script>
	<script src="vendors/jquery-ui/jquery-ui.min.js"></script>
	<script src="vendors/lightbox/simpleLightbox.min.js"></script>

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

	<script src="js/theme.js"></script>
</body>

</html>