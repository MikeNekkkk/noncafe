<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="Entity.CartItem"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="${pageContext.request.contextPath}/img/blog/da_logo9.jpg" type="image/x-icon"/>
<title>Đơn hàng - Nõn Cà phê</title>

<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="vendors/linearicons/style.css" rel="stylesheet">
<link href="vendors/flat-icon/flaticon.css" rel="stylesheet">
<link href="vendors/stroke-icon/style.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="vendors/animate-css/animate.css" rel="stylesheet">
<link href="vendors/owl-carousel/owl.carousel.min.css" rel="stylesheet">
<link href="vendors/magnifc-popup/magnific-popup.css" rel="stylesheet">
<link href="vendors/jquery-ui/jquery-ui.min.css" rel="stylesheet">
<link href="vendors/nice-select/css/nice-select.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="css/responsive.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<style>
/* ── user menu (giữ nguyên) ───────────────────────────────────── */
.user-menu{position:relative;display:inline-block;margin-left:10px}
.user-menu-toggle{background:none;border:none;color:#fff;cursor:pointer;padding:8px 12px;font-size:13px;transition:all .3s;border-radius:4px}
.user-menu-toggle:hover{background:rgba(255,255,255,.1);color:rgb(128,64,0)}
.user-dropdown{display:none;position:absolute;right:0;top:calc(100% + 10px);background:#fff;min-width:220px;box-shadow:0 4px 20px rgba(0,0,0,.15);border-radius:8px;z-index:9999;overflow:hidden}
.user-dropdown.active{display:block;animation:fadeInDown .3s ease}
@keyframes fadeInDown{from{opacity:0;transform:translateY(-10px)}to{opacity:1;transform:translateY(0)}}
.user-dropdown::before{content:'';position:absolute;top:-8px;right:20px;width:0;height:0;border-left:8px solid transparent;border-right:8px solid transparent;border-bottom:8px solid #fff}
.user-info{padding:15px 20px;border-bottom:2px solid #f0f0f0;font-weight:600;color:#2c3e50;background:#f8f9fa}
.user-info i{margin-right:8px;color:rgb(128,64,0)}
.user-dropdown a{display:block;padding:12px 20px;color:#555;text-decoration:none;transition:all .3s;border-bottom:1px solid #f5f5f5}
.user-dropdown a:hover{background:#f8f9fa;color:rgb(128,64,0);padding-left:25px}
.user-dropdown a i{margin-right:10px;width:18px;text-align:center}
.auth-links{display:inline-block;margin-left:10px}
.auth-links a{color:#fff!important;padding:8px 15px;margin-left:5px;border-radius:4px;transition:all .3s;font-size:13px;text-decoration:none;display:inline-block}
.auth-links a:hover{background:rgba(255,255,255,.1);color:rgb(128,64,0)!important}

/* ── Vertical Timeline ──────────────────────────────────────────── */
.tracking-section {
    margin-top: 30px;
    margin-bottom: 20px;
}
.tracking-section h4 {
    font-size: 18px;
    font-weight: 700;
    color: #2c1810;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 2px solid #f0e6d3;
    display: flex;
    align-items: center;
    gap: 8px;
}
.timeline {
    position: relative;
    padding: 0;
    margin: 0;
    list-style: none;
}
/* Đường kẻ dọc */
.timeline::before {
    content: '';
    position: absolute;
    left: 20px;
    top: 0;
    bottom: 0;
    width: 2px;
    background: linear-gradient(to bottom, #c8a97e, #e0d0b8);
}
.timeline-item {
    position: relative;
    padding: 0 0 24px 54px;
}
.timeline-item:last-child { padding-bottom: 0; }

/* Chấm tròn trên timeline */
.timeline-dot {
    position: absolute;
    left: 10px;
    top: 4px;
    width: 22px;
    height: 22px;
    border-radius: 50%;
    background: #c8a97e;
    border: 3px solid #fff;
    box-shadow: 0 0 0 2px #c8a97e;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1;
}
.timeline-dot i { font-size: 10px; color: #fff; }

/* Mốc đầu tiên (mới nhất) – nổi bật hơn */
.timeline-item.active .timeline-dot {
    background: rgb(128,64,0);
    box-shadow: 0 0 0 3px rgba(128,64,0,.25);
    width: 26px;
    height: 26px;
    left: 8px;
    top: 2px;
}
.timeline-item.active .timeline-dot i { font-size: 12px; }

.timeline-content {
    background: #fff;
    border: 1px solid #e8ddd0;
    border-radius: 8px;
    padding: 12px 16px;
    transition: box-shadow .2s;
}
.timeline-item.active .timeline-content {
    border-color: rgb(128,64,0);
    box-shadow: 0 2px 12px rgba(128,64,0,.12);
}
.timeline-content .tl-time {
    font-size: 12px;
    color: #999;
    margin-bottom: 3px;
}
.timeline-content .tl-status {
    font-weight: 700;
    font-size: 15px;
    color: #2c1810;
    margin-bottom: 4px;
}
.timeline-item.active .timeline-content .tl-status {
    color: rgb(128,64,0);
}
.timeline-content .tl-location {
    font-size: 13px;
    color: #666;
    margin-bottom: 3px;
}
.timeline-content .tl-location i { margin-right: 4px; color: #c8a97e; }
.timeline-content .tl-note {
    font-size: 12px;
    color: #aaa;
    font-style: italic;
}

/* Badge trạng thái đơn hàng (header) */
.order-status-badge {
    display: inline-block;
    padding: 4px 14px;
    border-radius: 20px;
    font-size: 13px;
    font-weight: 600;
    margin-left: 8px;
}
.status-PENDING    { background:#fff3cd; color:#856404; }
.status-PROCESSING { background:#cff4fc; color:#0c5460; }
.status-DELIVERING { background:#d1ecf1; color:#0c5460; }
.status-COMPLETED  { background:#d4edda; color:#155724; }
.status-CANCELLED  { background:#f8d7da; color:#721c24; }

/* Tracking empty state */
.tracking-empty {
    text-align: center;
    padding: 30px 20px;
    color: #aaa;
}
.tracking-empty i { font-size: 36px; display: block; margin-bottom: 10px; }
</style>
</head>

<body>
<header class="main_header_area">
    <div class="top_header_area row m0">
        <div class="container">
            <div class="float-left">
                <a href="tel:+0901957365"><i class="fa fa-phone" aria-hidden="true"></i> + 0915 493 322</a>
                <a href="mailto:info@noncafe.com"><i class="fa fa-envelope-o" aria-hidden="true"></i> NONCAFE.com</a>
            </div>
            <div class="float-right">
                <ul class="h_social list_style">
                    <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                    <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                </ul>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <div class="user-menu">
                            <button class="user-menu-toggle" onclick="toggleUserMenu()">
                                <i class="fa fa-user-circle"></i> ${sessionScope.user.fullName}
                                <i class="fa fa-angle-down"></i>
                            </button>
                            <div class="user-dropdown" id="userDropdown">
                                <div class="user-info"><i class="fa fa-user"></i> ${sessionScope.user.fullName}</div>
                                <c:if test="${sessionScope.user.isAdmin()}">
                                    <a href="${pageContext.request.contextPath}/AdminHome"><i class="fa fa-dashboard"></i> Quản trị</a>
                                </c:if>
                                <c:if test="${!sessionScope.user.isAdmin()}">
                                    <a href="${pageContext.request.contextPath}/listuserOrders"><i class="fa fa-shopping-bag"></i> Đơn hàng của tôi</a>
                                    <a href="${pageContext.request.contextPath}/resetTK"><i class="fa fa-user"></i> Tài khoản</a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out"></i> Đăng xuất</a>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="auth-links">
                            <a href="${pageContext.request.contextPath}/login"><i class="fa fa-sign-in"></i> Đăng nhập</a>
                            <a href="${pageContext.request.contextPath}/register"><i class="fa fa-sign-in"></i> Đăng ký</a>
                        </div>
                    </c:otherwise>
                </c:choose>
                <ul class="h_search list_style">
                    <li class="shop_cart">
                        <% List<Entity.CartItem> cart = (List<Entity.CartItem>) session.getAttribute("cart");
                           int cartCount = (cart != null) ? cart.size() : 0; %>
                        <a href="${pageContext.request.contextPath}/cart.jsp" data-cart-count="<%= cartCount %>">
                            <i class="lnr lnr-cart"></i>
                        </a>
                    </li>
                    <li><a class="popup-with-zoom-anim" href="#test-search"><i class="fa fa-search"></i></a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="main_menu_area">
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/home">
                    <img src="${pageContext.request.contextPath}/img/blog/den_logo9.jpg" alt="">
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent">
                    <span class="my_toggle_menu"><span></span><span></span><span></span></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
                        <li><a href="${pageContext.request.contextPath}/CakeControl">Sản phẩm</a></li>
                        <li><a href="${pageContext.request.contextPath}/about-us.jsp">Về chúng tôi</a></li>
                    </ul>
                    <ul class="navbar-nav justify-content-end">
                        <li><a href="${pageContext.request.contextPath}/blogList">Bài Viết</a></li>
                        <li class="dropdown submenu active"><a href="${pageContext.request.contextPath}/ProductCategories">Mua Sắm</a></li>
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
            <h3>Chi Tiết Đơn Hàng Của Bạn</h3>
            <ul>
                <li><a href="home">Trang Chủ</a></li>
                <li><a href="cart.jsp">Chi tiết Đơn Hàng</a></li>
            </ul>
        </div>
    </div>
</section>

<!-- ── Flash messages ─────────────────────────────────────────── -->
<div class="container" style="margin-top:30px;min-height:80px;height:auto;">
    <c:if test="${not empty sessionScope.messageSuccess}">
        <div class="alert alert-success alert-dismissible fade show" role="alert"
             style="border-left:5px solid #28a745;background-color:#d4edda;color:#155724;padding:19px 40px 20px 20px;font-size:16px;box-shadow:0 2px 10px rgba(0,0,0,.1);">
            <i class="fa fa-check-circle" style="font-size:18px;margin-right:10px;"></i>
            ${sessionScope.messageSuccess}
            <button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>
        </div>
        <% session.removeAttribute("messageSuccess"); %>
    </c:if>
    <c:if test="${not empty sessionScope.messageError}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert"
             style="border-left:5px solid #dc3545;padding:20px 40px 20px 20px;font-size:16px;box-shadow:0 2px 10px rgba(0,0,0,.1);">
            <i class="fa fa-exclamation-triangle" style="margin-right:10px;"></i>
            ${sessionScope.messageError}
            <button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>
        </div>
        <% session.removeAttribute("messageError"); %>
    </c:if>
</div>

<!-- ── Nội dung chính ─────────────────────────────────────────── -->
<section class="discover_menu_area">
    <div class="discover_menu_inner" style="padding-top:0px;">
        <div class="container">
            <div class="main_title">
                <h2>Chi tiết đơn hàng</h2>
            </div>

            <!-- Thông tin khách hàng -->
            <div class="order-policy-box" style="margin-top:20px;border:1px solid #e5e5e5;border-radius:8px;overflow:hidden;margin-bottom:24px;">
                <div style="background:#f8f9fa;padding:10px 15px;border-bottom:1px solid #e5e5e5;">
                    <strong style="color:#2c1810;">Thông tin Khách hàng</strong>
                    <span class="order-status-badge status-${order.status}">
                        <c:choose>
                            <c:when test="${order.status == 'PENDING'}"><i class="fa fa-clock-o"></i> Chờ xác nhận</c:when>
                            <c:when test="${order.status == 'PROCESSING'}"><i class="fa fa-cog fa-spin"></i> Đang chuẩn bị</c:when>
                            <c:when test="${order.status == 'DELIVERING'}"><i class="fa fa-truck"></i> Đang vận chuyển</c:when>
                            <c:when test="${order.status == 'COMPLETED'}"><i class="fa fa-check-circle"></i> Hoàn thành</c:when>
                            <c:when test="${order.status == 'CANCELLED'}"><i class="fa fa-ban"></i> Đã hủy</c:when>
                            <c:otherwise>${order.status}</c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div style="padding:15px;">
                    <h5>
                        Đơn hàng #${order.id} đã được đặt vào lúc
                        <span style="color:rgb(254,209,56);font-weight:bold;">
                            <fmt:formatDate value="${order.createdAt}" pattern="HH:mm 'ngày' dd/MM/yyyy"/>
                        </span><br>
                        <strong>Người nhận:</strong> ${order.lastName} ${order.firstName}<br>
                        <strong>Số điện thoại:</strong> ${order.phone}<br>
                        <strong>Địa chỉ nhận hàng:</strong> ${order.address}
                    </h5>
                </div>
            </div>

            <!-- Layout 2 cột -->
            <div class="row">
                <!-- ── CỘT TRÁI: Sản phẩm + Chính sách ── -->
                <div class="col-lg-6">
                    <div class="discover_item_inner">
                        <!-- Danh sách sản phẩm -->
                        <div class="discover_item">
                            <div class="coco">
                                <h4>Sản phẩm</h4>
                                <h4>Tổng</h4>
                                <c:set var="totalAmount" value="0"/>
                            </div>
                            <c:forEach var="item" items="${DTorders}">
                                <c:set var="totalAmount" value="${totalAmount + item.subtotal}"/>
                                <p>${item.productName} x${item.quantity}
                                    <span><fmt:formatNumber value="${item.subtotal}" pattern="#,##0"/>₫</span>
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
                                <h4><fmt:formatNumber value="${totalAmount}" pattern="#,##0"/>₫</h4>
                            </div>
                        </div>
                        <div class="discover_item">
                            <div class="coco">
                                <h4>Phí Vận Chuyển</h4>
                                <h4><fmt:formatNumber value="${order.totalAmount - totalAmount}" pattern="#,##0"/>₫</h4>
                            </div>
                        </div>
                        <div class="discover_item">
                            <div class="coco">
                                <h4>Tổng Cộng</h4>
                                <h4><fmt:formatNumber value="${order.totalAmount}" pattern="#,##0"/>₫</h4>
                            </div>
                        </div>

                        <!-- Chính sách hủy / trạng thái -->
                        <div class="order-policy-box"
                             style="margin-top:20px;border:1px solid #e5e5e5;border-radius:8px;overflow:hidden;">
                            <div style="background:#f8f9fa;padding:10px 15px;border-bottom:1px solid #e5e5e5;">
                                <strong style="color:#2c1810;"><i class="fa fa-shield"></i> Chính sách đồng kiểm &amp; Hủy đơn</strong>
                            </div>
                            <div style="padding:15px;">
                                <c:choose>
                                    <c:when test="${order.status == 'DELIVERING'}">
                                        <div style="color:#28a745;font-weight:500;font-size:18px;">
                                            <i class="fa fa-truck"></i> <b>Đơn hàng đang trên đường đến bạn.</b>
                                        </div>
                                        <p style="font-size:16px;color:#666;margin-top:8px;">
                                            <strong>Ghi chú đồng kiểm:</strong> Khi nhận hàng, quý khách được quyền kiểm tra sản phẩm.
                                            Nếu không hài lòng và muốn trả hàng, vui lòng <strong>thanh toán phí vận chuyển cho Shipper</strong>
                                            để đơn hàng được hoàn về Shop.
                                        </p>
                                    </c:when>
                                    <c:when test="${order.status == 'COMPLETED'}">
                                        <div style="color:#28a745;font-weight:600;font-size:18px;">
                                            <i class="fa fa-check-circle"></i> <b>Đơn hàng đã hoàn thành thành công!</b>
                                        </div>
                                        <p style="font-size:16px;color:#444;margin-top:10px;line-height:1.6;">
                                            <strong>NÕN CAFÉ</strong> chân thành cảm ơn quý khách đã tin tưởng và ủng hộ sản phẩm của chúng tôi.
                                        </p>
                                        <div style="background:#f9f6f2;padding:10px;border-radius:5px;border-left:4px solid #2c1810;margin-top:10px;">
                                            <p style="font-size:16px;color:#555;margin-bottom:0;">
                                                <i class="fa fa-star"></i> Nếu có bất kỳ vấn đề gì về sản phẩm, vui lòng liên hệ hotline để được hỗ trợ ngay.
                                            </p>
                                        </div>
                                    </c:when>
                                    <c:when test="${order.status == 'CANCELLED'}">
                                        <div class="text-muted">
                                            <i class="fa fa-ban"></i> <b>Đơn hàng này đã được hủy thành công.</b>
                                        </div>
                                    </c:when>
                                    <c:when test="${isRequested == true}">
                                        <div style="background:#fff3cd;color:#856404;padding:15px;border-radius:8px;border-left:5px solid #ffeeba;">
                                            <i class="fa fa-info-circle"></i>
                                            <b>Bạn đã gửi yêu cầu hủy đơn hàng này trước đó.</b><br>
                                            Vui lòng chờ Admin phê duyệt hoặc liên hệ Hotline <b>0901.957.965</b> để được xử lý nhanh nhất.
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="font-size:16px;color:#333;">
                                            <b>Quý khách có thể yêu cầu hủy đơn ngay bây giờ nếu Shop chưa bàn giao cho đơn vị vận chuyển.</b>
                                        </p>
                                        <button type="button" class="btn btn-danger"
                                                onclick="requestCancelOrder('${order.id}')"
                                                style="background:#d9534f;color:white;border:none;padding:8px 20px;border-radius:4px;cursor:pointer;">
                                            Yêu cầu hủy đơn hàng
                                        </button>
                                        <p style="margin-top:12px;font-size:15px;color:#666;line-height:1.5;font-style:italic;">
                                            <i class="fa fa-exclamation-triangle" style="color:#ff9800;"></i>
                                            <strong>Lưu ý:</strong> Phí ship hoàn sẽ do quý khách thanh toán nếu từ chối khi hàng đã đi.
                                        </p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div><!-- /policy-box -->
                    </div>
                </div><!-- /col-lg-6 trái -->

                <!-- ── CỘT PHẢI: TRACKING TIMELINE ── -->
                <div class="col-lg-6">
                    <div class="tracking-section">
                        <h4>
                            <i class="fa fa-map-marker" style="color:rgb(128,64,0);"></i>
                            Hành trình đơn hàng
                            <c:if test="${not empty order.ghnOrderCode}">
                                <small style="font-size:12px;color:#999;font-weight:400;margin-left:8px;">
                                    Mã GHN: <b>${order.ghnOrderCode}</b>
                                </small>
                            </c:if>
                        </h4>

                        <c:choose>
                            <c:when test="${empty trackingLogs}">
                                <!-- Chưa có log → hiển thị trạng thái hiện tại đơn giản -->
                                <ul class="timeline">
                                    <li class="timeline-item active">
                                        <div class="timeline-dot"><i class="fa fa-circle"></i></div>
                                        <div class="timeline-content">
                                            <div class="tl-time">
                                                <fmt:formatDate value="${order.createdAt}" pattern="HH:mm dd/MM/yyyy"/>
                                            </div>
                                            <div class="tl-status">
                                                <c:choose>
                                                    <c:when test="${order.status == 'PENDING'}">Đơn hàng mới – chờ xác nhận</c:when>
                                                    <c:when test="${order.status == 'PROCESSING'}">Shop đang chuẩn bị hàng</c:when>
                                                    <c:when test="${order.status == 'DELIVERING'}">Đã bàn giao cho đơn vị vận chuyển</c:when>
                                                    <c:when test="${order.status == 'COMPLETED'}">Giao hàng thành công</c:when>
                                                    <c:when test="${order.status == 'CANCELLED'}">Đơn hàng đã hủy</c:when>
                                                    <c:otherwise>${order.status}</c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="tl-location">
                                                <i class="fa fa-map-marker"></i> Kho Nõn Cà Phê – Đà Nẵng
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <ul class="timeline">
                                    <c:forEach var="log" items="${trackingLogs}" varStatus="loop">
                                        <li class="timeline-item ${loop.first ? 'active' : ''}">
                                            <div class="timeline-dot">
                                                <c:choose>
                                                    <c:when test="${log.statusCode == 'COMPLETED' || log.statusCode == 'delivered'}">
                                                        <i class="fa fa-check"></i>
                                                    </c:when>
                                                    <c:when test="${log.statusCode == 'CANCELLED' || log.statusCode == 'cancel'}">
                                                        <i class="fa fa-times"></i>
                                                    </c:when>
                                                    <c:when test="${log.statusCode == 'DELIVERING' || log.statusCode == 'delivering' || log.statusCode == 'transporting'}">
                                                        <i class="fa fa-truck"></i>
                                                    </c:when>
                                                    <c:when test="${log.statusCode == 'storing' || log.statusCode == 'sorting'}">
                                                        <i class="fa fa-archive"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="fa fa-circle"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="timeline-content">
                                                <!-- Thời gian -->
                                                <div class="tl-time">
                                                    <c:choose>
                                                        <c:when test="${not empty log.createdAt}">
                                                            <fmt:formatDate value="${log.createdAt}" pattern="HH:mm dd/MM/yyyy"/>
                                                        </c:when>
                                                        <c:otherwise>--</c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <!-- Trạng thái -->
                                                <div class="tl-status">${log.statusName}</div>
                                                <!-- Vị trí kho -->
                                                <c:if test="${not empty log.location}">
                                                    <div class="tl-location">
                                                        <i class="fa fa-map-marker"></i> ${log.location}
                                                    </div>
                                                </c:if>
                                                <!-- Ghi chú -->
                                                <c:if test="${not empty log.note}">
                                                    <div class="tl-note">${log.note}</div>
                                                </c:if>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:otherwise>
                        </c:choose>

                        <!-- Nút refresh tracking (khi đang DELIVERING) -->
                        <c:if test="${order.status == 'DELIVERING'}">
                            <div style="text-align:center;margin-top:16px;">
                                <button onclick="window.location.reload()" class="btn btn-sm"
                                        style="background:#f0e6d3;color:#2c1810;border:1px solid #c8a97e;border-radius:20px;padding:6px 18px;font-size:13px;">
                                    <i class="fa fa-refresh"></i> Cập nhật hành trình
                                </button>
                            </div>
                        </c:if>
                    </div>
                </div><!-- /col-lg-6 phải -->
            </div><!-- /row -->
        </div>
    </div>
</section>

<jsp:include page="footer.jsp" />

<script>
function toggleUserMenu() {
    var dd = document.getElementById('userDropdown');
    if (dd) dd.classList.toggle('active');
}

function requestCancelOrder(orderId) {
    Swal.fire({
        title: 'Xác nhận hủy đơn hàng #' + orderId,
        html: '<div style="text-align:left;font-size:14px;">' +
              '<p>Bạn đang yêu cầu hủy đơn hàng <strong>#' + orderId + '</strong>.</p>' +
              '<p>• Nếu Shop <b>chưa gửi hàng</b>: Hủy miễn phí.</p>' +
              '<p>• Nếu Shop <b>đã gửi hàng</b>: Bạn không thể hủy trên web. Khi Shipper gọi, bạn có thể từ chối nhận và thanh toán phí ship.</p>' +
              '</div>',
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#aaa',
        confirmButtonText: 'Tôi muốn hủy',
        cancelButtonText: 'Đóng'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "${pageContext.request.contextPath}/cancelOrderRequest?id=" + orderId;
        }
    });
}

document.addEventListener('click', function (e) {
    var um = document.querySelector('.user-menu');
    var dd = document.getElementById('userDropdown');
    if (um && dd && !um.contains(e.target)) dd.classList.remove('active');
});
</script>

<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="vendors/magnifc-popup/jquery.magnific-popup.min.js"></script>
<script src="vendors/isotope/imagesloaded.pkgd.min.js"></script>
<script src="vendors/isotope/isotope.pkgd.min.js"></script>
<script src="vendors/nice-select/js/jquery.nice-select.min.js"></script>
<script src="vendors/jquery-ui/jquery-ui.min.js"></script>
<script src="js/theme.js"></script>
</body>
</html>
