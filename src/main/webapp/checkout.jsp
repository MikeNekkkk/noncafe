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
<title>Thanh toán - NÕN CAFÉ</title>

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

<style>
/* ── User menu (giữ nguyên từ bản gốc) ─────────────────────── */
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
	transition: all .3s;
	border-radius: 4px;
}

.user-menu-toggle:hover {
	background: rgba(255, 255, 255, .1);
	color: rgb(128, 64, 0);
}

.user-dropdown {
	display: none;
	position: absolute;
	right: 0;
	top: calc(100% + 10px);
	background: #fff;
	min-width: 220px;
	box-shadow: 0 4px 20px rgba(0, 0, 0, .15);
	border-radius: 8px;
	z-index: 9999;
	overflow: hidden;
}

.user-dropdown.active {
	display: block;
	animation: fadeInDown .3s ease;
}

@
keyframes fadeInDown {
	from {opacity: 0;
	transform: translateY(-10px)
}

to {
	opacity: 1;
	transform: translateY(0)
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
	transition: all .3s;
	border-bottom: 1px solid #f5f5f5;
}

.user-dropdown a:hover {
	background: #f8f9fa;
	color: rgb(128, 64, 0);
	padding-left: 25px;
}

.auth-links {
	display: inline-block;
	margin-left: 10px;
}

.auth-links a {
	color: #fff !important;
	padding: 8px 15px;
	margin-left: 5px;
	border-radius: 4px;
	transition: all .3s;
	font-size: 13px;
	text-decoration: none;
	display: inline-block;
}

.auth-links a:hover {
	background: rgba(255, 255, 255, .1);
	color: rgb(128, 64, 0) !important;
}

/* ── Shipping fee box ───────────────────────────────────────── */
#shippingFeeBox {
	background: #fffbf0;
	border: 1px solid #f0d080;
	border-radius: 6px;
	padding: 10px 14px;
	margin-top: 8px;
	font-size: 14px;
	display: none;
}

#shippingFeeBox .fee-label {
	color: #666;
}

#shippingFeeBox .fee-value {
	font-weight: 700;
	color: rgb(128, 64, 0);
	font-size: 15px;
}

#shippingFeeBox .fee-loading {
	color: #999;
	font-style: italic;
}

/* ── Loading spinner trên select ───────────────────────────── */
.select-loading {
	position: relative;
}

.select-loading::after {
	content: '⏳';
	position: absolute;
	right: 38px;
	top: 50%;
	transform: translateY(-50%);
	font-size: 14px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" />

	<section class="banner_area">
		<div class="container">
			<div class="banner_text">
				<h3>Thanh toán</h3>
				<ul>
					<li><a href="home">Trang chủ</a></li>
					<li><a href="checkout.jsp">Thanh toán</a></li>
				</ul>
			</div>
		</div>
	</section>

	<section class="billing_details_area p_100">
		<div class="container">
			<div class="row">
				<!-- ═══════════════ CỘT TRÁI: FORM ═══════════════ -->
				<div class="col-lg-7">
					<c:if test="${empty sessionScope.user}">
						<div class="login-prompt">
							<p>
								<i class="fa fa-info-circle"></i> Bạn có thể mua hàng mà không
								cần tài khoản
							</p>
							<p>
								Bạn đã có tài khoản? <a href="login?returnUrl=checkout"
									style="color: rgb(254, 209, 56);">Đăng nhập ngay</a> để được hỗ
								trợ tốt hơn
							</p>
						</div>
					</c:if>
					<c:if test="${not empty sessionScope.user}">
						<div class="user-info-box">
							<h5>
								<i class="fa fa-user-circle"></i> Xin chào,
								${sessionScope.user.fullName}!
							</h5>
							<p>Thông tin của bạn đã được tự động điền. Vui lòng kiểm tra
								và bổ sung nếu cần.</p>
						</div>
					</c:if>

					<div class="main_title">
						<h2>Thông tin thanh toán</h2>
					</div>

					<div class="billing_form_area">
						<form class="billing_form row" action="checkout" method="post"
							id="checkoutForm">

							<div class="row">
								<c:if test="${not empty errorMessage}">
									<div class="col-md-12 form-group">
										<div class="error-message">
											<i class="fa fa-exclamation-triangle" style="color: red;"></i>
											${errorMessage}
										</div>
									</div>
								</c:if>
								<br />

								<div class="col-md-6 form-group">
									<label>Họ *</label> <input type="text" class="form-control"
										name="firstName" value="${firstName}" placeholder="Họ"
										required>
								</div>
								<div class="col-md-6 form-group">
									<label>Tên *</label> <input type="text" class="form-control"
										name="lastName" value="${lastName}" placeholder="Tên" required>
								</div>

								<div class="col-md-6 form-group">
									<label>Số điện thoại *</label> <input type="text"
										class="form-control" name="phone" value="${phone}"
										placeholder="Số điện thoại" required>
								</div>
								<div class="col-md-6 form-group">
									<label>Địa chỉ email *</label> <input type="email"
										class="form-control" name="email" value="${email}"
										placeholder="Email của bạn" required>
								</div>

								<!-- ── Sử dụng địa chỉ đơn cũ (chỉ hiện khi đã login) ── -->
								<c:if test="${not empty sessionScope.user}">
									<div class="col-md-12 form-group">
										<div
											style="display: flex; align-items: center; margin-bottom: 5px;">
											<input type="checkbox" id="useOldAddress"
												onclick="toggleOldAddress()"> <label
												for="useOldAddress" style="margin-left: 5px;">Sử
												dụng địa chỉ của đơn hàng trước</label>
										</div>
									</div>
								</c:if>

								<!-- ── TỈNH/THÀNH – lấy từ GHN API ── -->
								<div class="col-md-6 form-group">
									<label>Tỉnh / Thành phố *</label> <select class="form-control"
										id="provinceSelect" name="city" onchange="onProvinceChange()"
										required>
										<option value="">-- Chọn Tỉnh/Thành phố --</option>
									</select>
									<!-- hidden lưu ProvinceID -->
									<input type="hidden" id="provinceId" name="provinceId">
								</div>

								<!-- ── QUẬN/HUYỆN ── -->
								<div class="col-md-6 form-group">
									<label>Quận / Huyện *</label> <select class="form-control"
										id="districtSelect" name="district"
										onchange="onDistrictChange()" disabled required>
										<option value="">-- Chọn Quận/Huyện --</option>
									</select> <input type="hidden" id="districtId" name="districtId">
								</div>

								<!-- ── PHƯỜNG/XÃ ── -->
								<div class="col-md-12 form-group">
									<label>Phường / Xã *</label> <select class="form-control"
										id="wardSelect" name="ward" onchange="onWardChange()" disabled
										required>
										<option value="">-- Chọn Phường/Xã --</option>
									</select> <input type="hidden" id="wardCode" name="wardCode">
								</div>

								<!-- ── PHÍ SHIP HIỂN THỊ NGAY ── -->
								  <div class="col-md-12">
                                    <div id="shippingFeeBox">
                                        <span class="fee-label">Phí vận chuyển ước tính: </span>
                                        <span class="fee-value" id="shippingFeeInline">--</span>
                                        <span class="fee-loading" id="feeLoading" style="display:none"> Đang tính...</span>
                                    </div>
                                </div> 

								<input type="hidden" name="region" id="regionHidden" value="GHN">
								<input type="hidden" name="ship_fee">
								<div class="col-md-12 form-group" style="margin-top: 10px;">
									<label>Địa chỉ cụ thể *</label> <input type="text"
										class="form-control" name="address" value="${address}"
										placeholder="Ví dụ: Số 20, ngõ 90" required>
								</div>

								<c:if test="${empty sessionScope.user}">
									<div class="col-md-12 form-group">
										<div
											style="display: flex; align-items: center; margin-bottom: 5px;">
											<input type="checkbox" id="f-option2" name="createAccount"
												onchange="togglePassword()"
												style="margin: 0 8px 0 0; cursor: pointer;"> <label
												for="f-option2"
												style="white-space: nowrap; margin: 0; cursor: pointer;">
												Tạo tài khoản mới? </label>
										</div>
										<small
											style="color: #6c757d; font-style: italic; display: block;">
											*Lưu ý: Đăng ký giúp quản lí đơn hàng dễ hơn. </small>
									</div>
									<div class="col-md-12 form-group" id="passwordBox"
										style="display: none;">
										<label>Mật khẩu đăng nhập *</label> <input type="password"
											class="form-control" name="accountPassword"
											placeholder="Nhập mật khẩu">
									</div>
								</c:if>

								<!-- Hidden: grandTotal để submit -->
								<input type="hidden" name="grandTotalText" id="grandTotalValue">

								<div class="main_title">
									<h2>Thông tin bổ sung</h2>
								</div>
								<div class="col-md-12 form-group">
									<label>Ghi chú đơn hàng</label>
									<textarea class="form-control" name="notes" rows="4"
										placeholder="Ghi chú về đơn hàng, ví dụ: thời gian hay địa điểm giao hàng chi tiết."></textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- /col-lg-7 -->

				<!-- ═══════════════ CỘT PHẢI: ĐƠN HÀNG ═══════════════ -->
				<div class="col-lg-5">
					<div class="order_box_price">
						<div class="main_title">
							<h2>Đơn hàng của bạn</h2>
						</div>
						<div class="payment_list">
							<div class="price_single_cost">
								<h5>
									Sản phẩm <span>Tổng</span>
								</h5>

								<c:set var="cart" value="${sessionScope.cart}" />
								<c:set var="subTotal" value="${0}" />
								<c:forEach var="item" items="${cart}">
									<c:set var="p" value="${item.product}" />
									<c:set var="itemTotal" value="${p.price * item.quantity}" />
									<c:set var="subTotal" value="${subTotal + itemTotal}" />
									<h5>${p.name}
										x${item.quantity} <span><fmt:formatNumber
												value="${itemTotal}" pattern="#,##0" />₫</span>
									</h5>
								</c:forEach>

								<h4>
									Tạm tính <span><fmt:formatNumber value="${subTotal}"
											pattern="#,##0" />₫</span>
								</h4>
								<h5>
									Phí vận chuyển <span class="text_f" id="shippingFeeText">--
										VNĐ</span>
								</h5>
								<h3>
									Tổng cộng <span class="text_f" id="grandTotalText">--
										VNĐ</span>
								</h3>
							</div>

							<!-- Điều khoản -->
							<div class="mb-3 mt-4">
								<div
									style="max-height: 150px; overflow-y: auto; border: 1px solid #e5e5e5; padding: 15px; background: #fcfcfc; margin-bottom: 15px; border-radius: 4px;">
									<h6 style="font-weight: bold; margin-bottom: 10px;">Điều
										khoản và Điều kiện giao dịch</h6>
									<p style="font-size: 13px; color: #666; margin-bottom: 8px;">
										<strong>1. Quy định chung:</strong><br> Bằng việc đặt
										hàng, quý khách đồng ý mua các sản phẩm và tuân thủ quy định
										của NÕN CAFÉ.
									</p>
									<p style="font-size: 13px; color: #666; margin-bottom: 8px;">
										<strong>2. Giao hàng:</strong><br> Thời gian giao hàng dự
										kiến từ 2–5 ngày làm việc. Phí vận chuyển được tính tự động
										theo địa chỉ nhận hàng qua GHN.
									</p>
									<p style="font-size: 13px; color: #666; margin-bottom: 8px;">
										<strong>3. Chính sách hoàn hủy:</strong><br> Quý khách có
										thể yêu cầu hủy đơn trước khi Shop bàn giao cho đơn vị vận
										chuyển.
									</p>
								</div>
								<div style="display: flex; align-items: flex-start;">
									<input type="checkbox" id="agreeTerms" name="agreeTerms"
										required style="margin: 4px 10px 0 0; cursor: pointer;">
									<label for="agreeTerms"
										style="margin: 0; cursor: pointer; font-weight: normal; font-size: 14px;">
										Tôi đã đọc và đồng ý với <a href="#"
										style="color: rgb(128, 64, 0); text-decoration: underline;">điều
											khoản</a> của NÕN CAFÉ <span style="color: red;">*</span>
									</label>
								</div>
							</div>

							<button type="button" class="btn pest_btn"
								onclick="proceedToPayment()"
								style="width: 100%; margin-top: 20px;">Tiến hành thanh
								toán</button>
						</div>
					</div>
				</div>
				<!-- /col-lg-5 -->
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
// ════════════════════════════════════════════════════════════════
//  CẤU HÌNH
// ════════════════════════════════════════════════════════════════
const CTX        = '${pageContext.request.contextPath}';
const SUB_TOTAL  = ${subTotal.longValue()};

// Trọng lượng đơn hàng (gram) – tính từ giỏ hàng (mỗi gói ~250g)
// JSP tính tổng quantity rồi nhân 250
let totalWeight  = 0;
<c:forEach var="item" items="${cart}">
    totalWeight += ${item.quantity} * 250;
</c:forEach>
if (totalWeight < 200) totalWeight = 200;

// ════════════════════════════════════════════════════════════════
//  STATE
// ════════════════════════════════════════════════════════════════
let selectedDistrictId = 0;
let selectedWardCode   = '';
let currentShipFee     = 0;

// ════════════════════════════════════════════════════════════════
//  KHỞI ĐỘNG: Tải danh sách tỉnh/thành từ GHN
// ════════════════════════════════════════════════════════════════
$(document).ready(function () {
    loadProvinces();
    $('select').niceSelect();
    updateGrandTotal(0); // hiển thị -- khi chưa chọn địa chỉ
});

function loadProvinces() {
    $.getJSON(CTX + '/ghn-api?action=provinces', function (res) {
        if (!res.success) return;
        const sel = document.getElementById('provinceSelect');
        sel.innerHTML = '<option value="">-- Chọn Tỉnh/Thành phố --</option>';
        res.data.forEach(p => {
            const opt = document.createElement('option');
            opt.value          = p.name;
            opt.dataset.id     = p.id;
            opt.textContent    = p.name;
            sel.appendChild(opt);
        });
        $('select').niceSelect('update');

        // Khôi phục địa chỉ cũ nếu có (khi form load lại sau lỗi)
        const savedCity = '${lastOrder.city != null ? lastOrder.city : ""}';
        if (savedCity) {
            sel.value = savedCity;
            onProvinceChange(true);
        }
    });
}

// ════════════════════════════════════════════════════════════════
//  Tỉnh thay đổi → tải quận/huyện
// ════════════════════════════════════════════════════════════════
function onProvinceChange(silent) {
    const sel  = document.getElementById('provinceSelect');
    const opt  = sel.options[sel.selectedIndex];
    const pid  = opt ? parseInt(opt.dataset.id || 0) : 0;
    const name = sel.value;

    document.getElementById('provinceId').value = pid;

    // Reset huyện & xã
    resetSelect('districtSelect', '-- Chọn Quận/Huyện --', true);
    resetSelect('wardSelect',     '-- Chọn Phường/Xã --',  true);
    selectedDistrictId = 0;
    selectedWardCode   = '';
    hideFeeBox();

    if (!pid) return;

    // Loading state
    setSelectLoading('districtSelect', true);

    $.getJSON(CTX + '/ghn-api?action=districts&provinceId=' + pid, function (res) {
        setSelectLoading('districtSelect', false);
        if (!res.success) return;
        const dsel = document.getElementById('districtSelect');
        dsel.innerHTML = '<option value="">-- Chọn Quận/Huyện --</option>';
        dsel.disabled  = false;
        res.data.forEach(d => {
            const o = document.createElement('option');
            o.value        = d.name;
            o.dataset.id   = d.id;
            o.textContent  = d.name;
            dsel.appendChild(o);
        });
        $('select').niceSelect('update');

        // Khôi phục giá trị cũ
        const savedDistrict = '${lastOrder.district != null ? lastOrder.district : ""}';
        if (savedDistrict && silent) {
            dsel.value = savedDistrict;
            onDistrictChange(true);
        }
    });
}

// ════════════════════════════════════════════════════════════════
//  Quận thay đổi → tải phường/xã
// ════════════════════════════════════════════════════════════════
function onDistrictChange(silent) {
    const sel = document.getElementById('districtSelect');
    const opt = sel.options[sel.selectedIndex];
    const did = opt ? parseInt(opt.dataset.id || 0) : 0;

    document.getElementById('districtId').value = did;
    selectedDistrictId = did;
    selectedWardCode   = '';

    resetSelect('wardSelect', '-- Chọn Phường/Xã --', true);
    hideFeeBox();

    if (!did) return;

    setSelectLoading('wardSelect', true);

    $.getJSON(CTX + '/ghn-api?action=wards&districtId=' + did, function (res) {
        setSelectLoading('wardSelect', false);
        if (!res.success) return;
        const wsel = document.getElementById('wardSelect');
        wsel.innerHTML = '<option value="">-- Chọn Phường/Xã --</option>';
        wsel.disabled  = false;
        res.data.forEach(w => {
            const o = document.createElement('option');
            o.value       = w.name;
            o.dataset.code= w.code;
            o.textContent = w.name;
            wsel.appendChild(o);
        });
        $('select').niceSelect('update');

        const savedWard = '${lastOrder.ward != null ? lastOrder.ward : ""}';
        if (savedWard && silent) {
            wsel.value = savedWard;
            onWardChange();
        }
    });
}

// ════════════════════════════════════════════════════════════════
//  Phường thay đổi → tính phí ship
// ════════════════════════════════════════════════════════════════
function onWardChange() {
    const wsel = document.getElementById('wardSelect');
    const opt  = wsel.options[wsel.selectedIndex];
    const code = opt ? (opt.dataset.code || '') : '';

    document.getElementById('wardCode').value = code;
    selectedWardCode = code;

    if (!selectedDistrictId || !code) { hideFeeBox(); return; }

    // Hiện box phí với trạng thái loading
    document.getElementById('shippingFeeBox').style.display  = 'block';
    document.getElementById('shippingFeeInline').style.display = 'none';
    document.getElementById('feeLoading').style.display       = 'inline';

    const url = CTX + '/ghn-api?action=fee'
              + '&districtId=' + selectedDistrictId
              + '&wardCode='   + encodeURIComponent(code)
              + '&weight='     + totalWeight;

    $.getJSON(url, function (res) {
        document.getElementById('feeLoading').style.display       = 'none';
        document.getElementById('shippingFeeInline').style.display = 'inline';

        if (res.success) {
            currentShipFee = res.fee;
        } else {
            // Fallback nếu GHN lỗi
            currentShipFee = selectedDistrictId >= 1570 && selectedDistrictId <= 1585 ? 15000 : 30000;
        }
        updateGrandTotal(currentShipFee);
    }).fail(function () {
        document.getElementById('feeLoading').style.display       = 'none';
        document.getElementById('shippingFeeInline').style.display = 'inline';
        currentShipFee = 30000; // fallback
        updateGrandTotal(currentShipFee);
    });
}

// ════════════════════════════════════════════════════════════════
//  Cập nhật tổng tiền
// ════════════════════════════════════════════════════════════════
function updateGrandTotal(shipFee) {
    const grand = SUB_TOTAL + shipFee;
    document.getElementById('shippingFeeText').innerText  = formatCurrency(shipFee);
    document.getElementById('shippingFeeInline').innerText= formatCurrency(shipFee);
    document.getElementById('grandTotalText').innerText   = formatCurrency(grand);
    document.getElementById('grandTotalValue').value      = grand;
    document.querySelector('input[name="ship_fee"]').value = shipFee;
}

function formatCurrency(amount) {
    return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + ' VNĐ';
}

function hideFeeBox() {
    document.getElementById('shippingFeeBox').style.display = 'none';
    updateGrandTotal(0);
}

// ════════════════════════════════════════════════════════════════
//  Sử dụng địa chỉ đơn hàng cũ
// ════════════════════════════════════════════════════════════════
function toggleOldAddress() {
    const cb = document.getElementById('useOldAddress');
    if (!cb.checked) return;

    const oldCity     = '${lastOrder.city}';
    const oldDistrict = '${lastOrder.district}';
    const oldWard     = '${lastOrder.ward}';
    const oldAddress  = '${lastOrder.address}';

    if (!oldCity) {
        alert('Bạn chưa có đơn hàng trước đó!');
        cb.checked = false;
        return;
    }

    // Chọn tỉnh
    const psel = document.getElementById('provinceSelect');
    for (let i = 0; i < psel.options.length; i++) {
        if (psel.options[i].value === oldCity) {
            psel.selectedIndex = i;
            break;
        }
    }
    onProvinceChange(false);

    // Sau khi district load → chọn district → ward
    // Dùng polling vì AJAX bất đồng bộ
    waitAndSet('districtSelect', oldDistrict, function () {
        onDistrictChange(false);
        waitAndSet('wardSelect', oldWard, function () {
            onWardChange();
        });
    });

    // Điền địa chỉ cụ thể
    const shortAddr = oldAddress.split(', Phường')[0].split(',')[0];
    document.getElementsByName('address')[0].value = shortAddr;
}

function waitAndSet(selectId, value, callback) {
    let tries = 0;
    const iv = setInterval(function () {
        const sel = document.getElementById(selectId);
        if (!sel.disabled && sel.options.length > 1) {
            clearInterval(iv);
            for (let i = 0; i < sel.options.length; i++) {
                if (sel.options[i].value === value) {
                    sel.selectedIndex = i;
                    $('select').niceSelect('update');
                    break;
                }
            }
            if (callback) setTimeout(callback, 150);
        }
        if (++tries > 20) clearInterval(iv);
    }, 150);
}

// ════════════════════════════════════════════════════════════════
//  UI helpers
// ════════════════════════════════════════════════════════════════
function resetSelect(id, placeholder, disable) {
    const sel = document.getElementById(id);
    sel.innerHTML = '<option value="">' + placeholder + '</option>';
    sel.disabled  = !!disable;
    $('select').niceSelect('update');
}

function setSelectLoading(id, loading) {
    const wrap = document.getElementById(id).parentElement;
    if (loading) wrap.classList.add('select-loading');
    else         wrap.classList.remove('select-loading');
}

// ════════════════════════════════════════════════════════════════
//  Toggle password khi tạo tài khoản
// ════════════════════════════════════════════════════════════════
function togglePassword() {
    const cb   = document.getElementById('f-option2');
    const box  = document.getElementById('passwordBox');
    const inp  = box.querySelector('input');
    if (cb.checked) { box.style.display = 'block'; inp.required = true;  }
    else            { box.style.display = 'none';  inp.required = false; }
}

// ════════════════════════════════════════════════════════════════
//  Submit form
// ════════════════════════════════════════════════════════════════
function proceedToPayment() {
    // Kiểm tra phường đã chọn chưa (bắt buộc để tính phí ship)
    if (!selectedWardCode) {
        alert('Vui lòng chọn đầy đủ Tỉnh/Quận/Phường để tính phí vận chuyển!');
        return;
    }
    const form = document.getElementById('checkoutForm');
    if (!form.checkValidity()) { form.reportValidity(); return; }
    form.submit();
}

// ════════════════════════════════════════════════════════════════
//  User menu dropdown
// ════════════════════════════════════════════════════════════════
function toggleUserMenu() {
    var dd = document.getElementById('userDropdown');
    if (dd) dd.classList.toggle('active');
}
document.addEventListener('click', function (e) {
    var um = document.querySelector('.user-menu');
    var dd = document.getElementById('userDropdown');
    if (um && dd && !um.contains(e.target)) dd.classList.remove('active');
});
</script>
</body>
</html>
