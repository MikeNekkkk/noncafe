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
<title>Liên Hệ - NÕN CAFÉ</title>

<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="vendors/linearicons/style.css" rel="stylesheet">
<link href="vendors/flat-icon/flaticon.css" rel="stylesheet">
<link href="vendors/stroke-icon/style.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="vendors/revolution/css/settings.css" rel="stylesheet">
<link href="vendors/revolution/css/layers.css" rel="stylesheet">
<link href="vendors/revolution/css/navigation.css" rel="stylesheet">
<link href="vendors/animate-css/animate.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<%-- ✅ FIX: jQuery phải load TRƯỚC các script dùng $ --%>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link href="vendors/owl-carousel/owl.carousel.min.css" rel="stylesheet">
<link href="vendors/magnifc-popup/magnific-popup.css" rel="stylesheet">

<link href="css/style.css" rel="stylesheet">
<link href="css/responsive.css" rel="stylesheet">

<style>
.login-prompt {
	background: #fff3cd;
	border: 1px solid #ffc107;
	padding: 15px 20px;
	border-radius: 5px;
	margin-bottom: 20px;
	text-align: center;
}

.login-prompt p {
	margin: 0 0 8px 0;
	color: #856404;
	font-size: 14px;
}

.login-prompt a {
	color: rgb(254, 209, 56);
	font-weight: 600;
	text-decoration: none;
	font-size: 14px;
}

.login-prompt a:hover {
	text-decoration: underline;
}

.user-info-box {
	background: #e8f5e9;
	border: 1px solid #4caf50;
	padding: 15px 20px;
	border-radius: 5px;
	margin-bottom: 20px;
}

.user-info-box h5 {
	margin: 0 0 5px 0;
	color: #2e7d32;
	font-size: 16px;
}

.user-info-box p {
	margin: 0;
	font-size: 13px;
	color: #555;
}

.success-message {
	background: #d4edda;
	border: 1px solid #c3e6cb;
	color: #155724;
	padding: 15px 20px;
	border-radius: 5px;
	margin-bottom: 20px;
	text-align: center;
}

.error-message {
	background: #f8d7da;
	border: 1px solid #f5c6cb;
	color: #721c24;
	padding: 15px 20px;
	border-radius: 5px;
	margin-bottom: 20px;
	text-align: center;
}
</style>

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

.col-lg-4 {
	-webkit-box-flex: 0;
	-ms-flex: 0 0 33.3333333333%;
	flex: 0 0 33.3333333333%;
	max-width: 33.3333333333%;
}
/* CSS tách riêng cho khung thông tin khách hàng */
.tile {
	background: #ffffff;
	border-radius: 8px;
	padding: 20px;
	margin-bottom: 30px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	border: 1px solid #eee;
}

.tile-title {
	font-size: 20px;
	font-weight: 700;
	color: #2c1810; /* Màu đà đặc trưng của NÕN CAFÉ */
	margin-top: 0;
	margin-bottom: 20px;
	padding-bottom: 10px;
	border-bottom: 2px solid rgb(254, 209, 56); /* Màu vàng thương hiệu */
}

.info-label {
	font-weight: 600;
	color: #666;
	font-size: 13px;
	text-transform: uppercase;
}

.info-value {
	color: #333;
	font-size: 15px;
	display: block;
	margin-bottom: 10px;
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
						aria-hidden="true"></i> NONCAFE.com</a>
				</div>
				<div class="float-right">
					<ul class="h_social list_style">
						<li><a
							href="https://www.facebook.com/sharer.php?u=https://trungnguyenlegendcafe.net/ca-phe-dua-nang-luong-thanh-mat-tu-san-vat-nhiet-doi-2/"><i
								class="fa fa-facebook"></i></a></li>
						<li><a href=""
							https://twitter.com/share?url=https://trungnguyenlegendcafe.net/ca-phe-dua-nang-luong-thanh-mat-tu-san-vat-nhiet-doi-2/""><i
								class="fa fa-twitter"></i></a></li>
						<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
						<li><a
							href="mailto:?subject=C%C3%80%20PH%C3%8A%20D%E1%BB%AAA%20%7C%20N%C4%83ng%20l%C6%B0%E1%BB%A3ng%20thanh%20m%C3%A1t%20t%E1%BB%AB%20s%E1%BA%A3n%20v%E1%BA%ADt%20nhi%E1%BB%87t%20%C4%91%E1%BB%9Bi.&amp;body=Xem%20n%C3%A0y%3A%20https%3A%2F%2Ftrungnguyenlegendcafe.net%2Fca-phe-dua-nang-luong-thanh-mat-tu-san-vat-nhiet-doi-2%2F"><i
								class="fa fa-linkedin"></i></a></li>
					</ul>

					<!-- Menu User / Auth Links -->
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
						src="${pageContext.request.contextPath}/img/blog/den_logo9.jpg"
						alt=""> <img
						src="${pageContext.request.contextPath}/img/blog/den_logo9.jpg"
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

							<li><a href="${pageContext.request.contextPath}/home">Trang
									chủ</a></li>
							<li><a href="${pageContext.request.contextPath}/CakeControl">
									Sản phẩm</a></li>
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

							<li class="dropdown submenu active"><a
								href="${pageContext.request.contextPath}/contact.jsp">Liên
									hệ </a></li>
						</ul>
					</div>
				</nav>
			</div>
		</div>
	</header>
	<section class="banner_area">
		<div class="container">
			<div class="banner_text">
				<h3>Kết nối cùng Nõn cà phê</h3>
				<ul>
					<li><a href="home">Trang chủ</a></li>
					<li><a href="contact.jsp">Liên Hệ</a></li>
				</ul>
			</div>
		</div>
	</section>

	<section class="contact_form_area p_100">
		<div class="container">
			<div class="main_title">
				<h2>Kết nối cùng Nõn cà phê</h2>
				<h5>Bạn có điều gì muốn chia sẻ với chúng tôi không? Đừng ngần
					ngại kết nối ngay qua biểu mẫu liên hệ nhé.</h5>
			</div>

			<div class="row">
				<div class="col-lg-7">
					<c:if test="${not empty sessionScope.successMessage}">
						<div class="success-message">
							<i class="fa fa-check-circle"></i> ${sessionScope.successMessage}
						</div>
						<c:remove var="successMessage" scope="session" />
					</c:if>

					<c:if test="${not empty errorMessage}">
						<div class="error-message">
							<i class="fa fa-exclamation-triangle"></i> ${errorMessage}
						</div>
					</c:if>

					<c:if test="${empty sessionScope.user}">
						<div class="login-prompt">
							<p>
								<i class="fa fa-info-circle"></i> Bạn có thể gửi liên hệ mà
								không cần tài khoản
							</p>
							<p>
								Bạn đã có tài khoản? <a href="login?returnUrl=contact.jsp">Đăng
									nhập ngay</a> để được hỗ trợ tốt hơn
							</p>
						</div>
					</c:if>

					<c:if test="${not empty sessionScope.user}">
						<div class="user-info-box">
							<h5>
								<i class="fa fa-user-circle"></i> Xin chào,
								${sessionScope.user.fullName}!
							</h5>
							<p>Thông tin của bạn đã được tự động điền vào form. Vui lòng
								kiểm tra và bổ sung nếu cần.</p>
						</div>
					</c:if>

					<form class="row contact_us_form" action="submitContact"
						method="post" id="contactForm">
						<div class="form-group col-md-6">
							<input type="text" class="form-control" id="name" name="name"
								placeholder="Tên của bạn" value="${sessionScope.user.fullName}"
								required>
						</div>
						<div class="form-group col-md-6">
							<input type="email" class="form-control" id="email" name="email"
								placeholder="Địa chỉ Email" value="${sessionScope.user.email}"
								required>
						</div>
						<div class="form-group col-md-12">
							<input type="text" class="form-control" id="subject"
								name="subject" placeholder="Chủ đề" required>
						</div>
						<div class="form-group col-md-12">
							<textarea class="form-control" name="message" id="message"
								rows="5" placeholder="Nội dung" required></textarea>
						</div>
						<div class="form-group col-md-12">
							<button type="button" class="btn order_s_btn form-control"
								onclick="submitAddUser()">Gởi ngay</button>
						</div>
					</form>
				</div>

				<div class="col-lg-4 offset-md-1">
					<div class="contact_details">
						<div class="contact_d_item">
							<h3>Địa chỉ :</h3>
							<p>
								52 đường 2 tháng 9 <br /> Phường Chính Gián, Quận Sơn Trà, Đà
								Nẵng

							</p>
						</div>
						<div class="contact_d_item">
							<h5>
								Điện thoại : <a href="tel:0915493322">0915 493 322</a>
							</h5>
							<h5>
								Email : <a href="mailto:nonncafee@gmai.com">noncafe@gmai.com</a>
							</h5>
						</div>
						<div class="contact_d_item">
							<h3>Thời gian mở cửa :</h3>
							<p>8:00 AM – 8:00 PM</p>
							<p>Thứ 2 – Chủ Nhật</p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-7" style="margin-top: 35px;">
					<div class="main_title">
						<h2>Tra cứu đơn hàng</h2>
						<form action="SearchID" method="post"
							style="display: flex; align-items: center; gap: 10px; margin-bottom: -45px">
							<input type="text" name="idorder" placeholder="Nhập mã đơn hàng"
								required
								style="padding: 5px; border: 1px solid #ccc; border-radius: 4px;">

							<button type="submit" class="btn btn-primary"
								style="background: rgb(254, 209, 56); border: none; color: #fff; padding: 5px 15px;">
								Tra cứu</button>
						</form>
					</div>
					<c:if test="${empty DTorders}">

						<div id="orderResultContainer">
							<p>*Nhập mã đơn để xem chi tiết.</p>
						</div>
					</c:if>
					<c:if test="${not empty kdonMessage}">
						<div class="col-md-12 form-group">
							<div class="error-message">
								<i class="fa fa-exclamation-triangle" style="color: red;"></i>
								${kdonMessage}
							</div>
						</div>
					</c:if>
					<c:if test="${not empty sessionScope.messageSuccess}">
						<div class="col-md-12 form-group" style="margin-top: 10px;">
							<div class="success-message">
								<i class="fa fa-check-circle"></i> <span>${sessionScope.messageSuccess}</span>
							</div>
						</div>
						<%
						session.removeAttribute("messageSuccess");
						%>
					</c:if>
					<c:if test="${not empty sessionScope.messageError}">
						<div class="col-md-12 form-group" style="margin-top: 10px;">
							<div class="error-message">
								<i class="fa fa-exclamation-triangle"></i>
								${sessionScope.messageError}
							</div>
						</div>
						<%
						session.removeAttribute("messageError");
						%>
					</c:if>
				</div>
				<c:if test="${not empty DTorders}">

					<div class="row" style="margin-top: 15px; margin-left: 2px;">
						<div class="col-lg-6" style="max-width: 700px;">
							<div class="discover_item_inner">
								<div class="discover_item">
									<div class="coco">
										<h4>Sản phẩm</h4>
										<h4>Tổng</h4>
										<c:set var="totalAmount" value="0" />
									</div>
									<c:forEach var="item" items="${DTorders}">

										<c:set var="totalAmount"
											value="${totalAmount + item.subtotal}" />
										<p>
											${item.productName} x${item.quantity}<span><fmt:formatNumber
													value="${item.subtotal}" pattern="#,##0" />₫ </span>
										</p>
									</c:forEach>
								</div>
								<div class="discover_item">
									<div class="coco">
										<h4>Phương Thức Thanh Toán</h4>
										<h4>${order.paymentMethod}</h4>
									</div>
								</div>
								<div class="discover_item">
									<div class="coco">
										<h4>Tổng Số Phụ</h4>
										<h4>
											<fmt:formatNumber value="${totalAmount}" pattern="#,##0" />
											₫
										</h4>
									</div>
								</div>
								<div class="discover_item">
									<div class="coco">
										<h4>Vận Chuyển</h4>
										<h4>
											<fmt:formatNumber value="${order.totalAmount-totalAmount}"
												pattern="#,##0" />
											₫
										</h4>
									</div>
								</div>

								<div class="discover_item">
									<div class="coco">
										<h4>Tổng Cộng</h4>
										<h4>
											<fmt:formatNumber value="${order.totalAmount}"
												pattern="#,##0" />
											₫
										</h4>
									</div>
								</div>
								<div class="order-policy-box"
									style="margin-top: 20px; border: 1px solid #e5e5e5; border-radius: 8px; overflow: hidden; width: 670px;">
									<div
										style="background: #f8f9fa; padding: 10px 15px; border-bottom: 1px solid #e5e5e5;">
										<strong style="color: #2c1810;"><i
											class="fa fa-shield"></i> Chính sách đồng kiểm & Hủy đơn</strong>
									</div>

									<div style="padding: 15px;">
										<c:choose>
											<c:when test="${order.status == 'DELIVERING'}">
												<div
													style="color: #28a745; font-weight: 500; font-size: 18px;">
													<i class="fa fa-truck"></i> <b>Đơn hàng đang trên đường
														đến bạn .</b>
												</div>
												<p style="font-size: 18px; color: #666; margin-top: 8px;">
													<strong>Ghi chú đồng kiểm:</strong> Khi nhận hàng, quý
													khách được quyền kiểm tra sản phẩm. Nếu quý khách không hài
													lòng và muốn trả hàng, vui lòng <strong>thanh toán
														phí vận chuyển cho Shipper</strong> để đơn hàng được hoàn về Shop.
												</p>
											</c:when>

											<c:when test="${order.status == 'COMPLETED'}">
												<div
													style="color: #28a745; font-weight: 600; font-size: 18px;">
													<i class="fa fa-check-circle"></i> <b>Đơn hàng đã hoàn
														thành thành công!</b>
												</div>

												<p
													style="font-size: 18px; color: #444; margin-top: 10px; line-height: 1.6;">
													<strong>NÕN CAFÉ</strong> chân thành cảm ơn quý khách đã
													tin tưởng và ủng hộ sản phẩm của chúng tôi. Hy vọng hương
													vị cà phê của Shop sẽ mang lại cho bạn những giây phút
													tuyệt vời!
												</p>

												<div
													style="background: #f9f6f2; padding: 10px; border-radius: 5px; border-left: 4px solid #2c1810; margin-top: 10px;">
													<p style="font-size: 18px; color: #555; margin-bottom: 0;">
														<i class="fa fa-star"></i>Nếu có bất kỳ vấn đề gì về sản
														phẩm, vui lòng liên hệ hotline để được hỗ trợ ngay lập
														tức.
													</p>
												</div>
											</c:when>
											<c:when test="${order.status == 'CANCELLED'}">
												<div class="text-muted">
													<i class="fa fa-ban"></i> <b>Đơn hàng này đã được hủy
														thành công.</b>
												</div>
											</c:when>

											<c:when test="${isRequested == true}">
												<div
													style="background: #fff3cd; color: #856404; padding: 15px; border-radius: 8px; border-left: 5px solid #ffeeba;">
													<i class="fa fa-info-circle"></i> <b>Bạn đã gửi yêu cầu
														hủy đơn hàng này trước đó.</b> <br>Vui lòng chờ Admin phê
													duyệt hoặc liên hệ Hotline <b>0901.957.965</b> để được xử
													lý nhanh nhất.
												</div>
											</c:when>

											<c:otherwise>
												<p style="font-size: 18px; color: #333; line-height: 1.6;">
													<b>Đơn hàng của bạn đang trong <Strong style="color:green;">quá trình xử lý</Strong>.</b><br>
													Nếu muốn hủy đơn, vui lòng liên hệ hotline <a
														href="tel:0901957965"
														style="color: #d32f2f; font-weight: bold; text-decoration: underline;">
														0901 957 965 </a> để chúng tôi hỗ trợ xử lý nhanh nhất.
												</p>

												<p
													style="margin-top: 15px; font-size: 16px; color: #666; line-height: 1.5; padding: 10px; background-color: #fff3e0; border-left: 4px solid #ff9800;">
													<i class="fa fa-exclamation-circle"
														style="color: #ff9800; margin-right: 8px;"></i> <strong>Lưu
														ý:</strong> Khi đơn hàng đã được bàn giao cho đơn vị vận chuyển,
													quý khách sẽ <span
														style="color: #d32f2f; font-weight: 600;">không thể
														hủy đơn</span> được nữa.<br> Khi Shipper gọi, bạn có thể từ chối nhận và <span
														style="color: #d32f2f; font-weight: 600;">thanh toán phí ship</span> cho Shipper để đơn hàng được hoàn trả về shop đúng quy định.
												</p>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-4 offset-md-1">
						<div class="col-md-4" style="max-width: 370px;">
							<div class="tile">
								<h3 class="tile-title">Thông tin khách hàng</h3>
								<div class="tile-body">
									<p>
										<span class="info-label">Họ tên:</span><br> <span
											class="info-value">${order.firstName}
											${order.lastName}</span>
									</p>
									<p>
										<span class="info-label">Email:</span><br> <span
											class="info-value">${order.email}</span>
									</p>
									<p>
										<span class="info-label">Điện thoại:</span><br> <span
											class="info-value">${order.phone}</span>
									</p>

									<p>
										<span class="info-label">Địa chỉ:</span><br> <span
											class="info-value">${order.address}</span>
									</p>

								</div>
							</div>
						</div>

					</div>
				</c:if>
			</div>
		</div>

	</section>


	<jsp:include page="footer.jsp" />
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
	 function submitAddUser() {
	      var formData = $('#contactForm').serialize();
	      
	      $.ajax({
	        url: 'submitContact',
	        type: 'POST',
	        data: formData,
	        dataType: 'json',
	        success: function(response) {
	          if (response.success) {
	              location.reload();
	          } else {
	        	  Swal.fire("Lỗi!", response.message, "error");
	          }
	        },
	        error: function() {
	        	Swal.fire("Lỗi!", "Không thể kết nối đến server!", "error");
	        }
	      });
	    }
	 
</script>

	<script>
function requestCancelOrder(orderId) {
    Swal.fire({
        title: 'Xác nhận hủy đơn hàng ID: ' + orderId,
        html: '<div style="text-align: left; font-size: 14px;">' +
              '<p>Bạn đang yêu cầu hủy đơn hàng <strong>#' + orderId + '</strong>.</p>' +
              '<p>• Nếu Shop <b>chưa gửi hàng</b>: Hủy miễn phí.</p>' +
              '<p>• Nếu Shop <b>đã gửi hàng</b>: Bạn không thể hủy trên web. Khi Shipper gọi, bạn có thể từ chối nhận và thanh toán phí ship cho Shipper.</p>' +
              '</div>',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#aaa',
        confirmButtonText: 'Tôi muốn hủy',
        cancelButtonText: 'Đóng'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "${pageContext.request.contextPath}/cancelOrderRequestKHVL?id=" + orderId;
        }
    });
}
</script>

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
	<script src="vendors/owl-carousel/owl.carousel.min.js"></script>
	<script src="vendors/magnifc-popup/jquery.magnific-popup.min.js"></script>
	<script src="vendors/datetime-picker/js/moment.min.js"></script>
	<script
		src="vendors/datetime-picker/js/bootstrap-datetimepicker.min.js"></script>
	<script src="vendors/nice-select/js/jquery.nice-select.min.js"></script>
	<script src="vendors/jquery-ui/jquery-ui.min.js"></script>
	<script src="vendors/lightbox/simpleLightbox.min.js"></script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjCGmQ0Uq4exrzdcL6rvxywDDOvfAu6eE"></script>
	<script src="js/gmaps.min.js"></script>
	<script src="js/map-active.js"></script>

	<script src="js/theme.js"></script>

</body>
</html>