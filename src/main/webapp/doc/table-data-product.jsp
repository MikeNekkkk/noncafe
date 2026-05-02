<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="vi">

<head>
<link rel="icon" href="img/blog/da_logo9.jpg" type="image/x-icon" />

<title>Danh sách Sản phẩm | Quản trị Admin</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Main CSS-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/admin-assets/ADcss/main.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
<!-- or -->
<link rel="stylesheet"
	href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
.modal-header-custom {
	background: #5B3427;
	color: #fff;
	border-radius: 4px 4px 0 0;
	padding: 14px 20px;
}

.modal-header-custom .close {
	color: #fff;
	opacity: 1;
}

.app-sidebar {
	overflow-y: auto !important;
}

.section-label {
	font-weight: 700;
	color: #5B3427;
	border-left: 4px solid #C8956C;
	padding-left: 10px;
	margin: 16px 0 12px;
	font-size: 14px;
}

.star-row {
	display: flex;
	gap: 1px;
	margin-top: 6px;
}

.star-dot {
	width: 20px;
	height: 20px;
	border-radius: 50%;
	background: #dee2e6;
	cursor: pointer;
	transition: background .15s;
}

.star-dot:hover {
	background: #ffc107;
}

.star-dot.on {
	background: #f5c518;
}

.star-val {
	font-size: 12px;
	color: #888;
	margin-top: 4px;
}

.flavor-chip {
	display: inline-block;
	padding: 4px 10px;
	border-radius: 16px;
	border: 2px solid #dee2e6;
	background: #fff;
	color: #495057;
	cursor: pointer;
	margin: 3px;
	font-size: 12px;
	transition: all .15s;
	user-select: none;
}

.flavor-chip.selected {
	border-color: #5B3427;
	background: #5B3427;
	color: #fff;
}

.flavor-chip:hover {
	border-color: #5B3427;
}
}
</style>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">

</head>

<body onload="time()" class="app sidebar-mini rtl">
	<header class="app-header">
		<a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
			aria-label="Hide Sidebar"></a>
		<ul class="app-nav">
			<li><a class="app-nav__item" href="home"><i
					class='bx bx-log-out bx-rotate-180'></i> </a></li>
		</ul>
	</header>
	<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
	<aside class="app-sidebar">
		<div class="app-sidebar__user">
			<img class="app-sidebar__user-avatar" src="img/blog/da_logo9.jpg"
				width="50px" alt="User Image">
			<div>
				<div class="f_title">
					<h4>Mai Huyền</h4>
				</div>
				<div class="f_title">
					<h5>Chào mừng bạn trở lại</h5>
				</div>
			</div>
		</div>
		<hr>
		<ul class="app-menu">
			<li><a class="app-menu__item " href="AdminHome"><i
					class='app-menu__icon bx bx-tachometer'></i><span
					class="app-menu__label">Bảng điều khiển</span></a></li>

			<li><a class="app-menu__item" href="HTU"><i
					class='app-menu__icon bx bx-user-voice'></i><span
					class="app-menu__label">Quản lý khách hàng</span></a></li>
			<li><a class="app-menu__item " href="HTDM"><i
					class='app-menu__icon bx bx-id-card'></i> <span
					class="app-menu__label">Quản lý Danh mục</span></a></li>
			<li><a class="app-menu__item haha" href=HTSP><i
					class='app-menu__icon bx bx-purchase-tag-alt'></i><span
					class="app-menu__label ">Quản lý sản phẩm</span></a></li>
			<li><a class="app-menu__item" href="listOrders"><i
					class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Quản
						lý đơn hàng</span></a></li>
			<li><a class="app-menu__item" href="HTNCC"><i
					class='app-menu__icon bx bx-user-voice'></i><span
					class="app-menu__label">Quản lý NCC</span></a></li>
			<li><a class="app-menu__item" href="stock-management"> <i
					class="fa-solid fa-cart-flatbed "
					style="color:; margin-right: 15px;"></i><span
					class="app-menu__label">Quản lý Nhập Xuất Kho</span>
			</a></li>
			<li><a class="app-menu__item" href="blog"><i
					class='app-menu__icon bx bx-run'></i><span class="app-menu__label">Quản
						lý Bài viết </span></a></li>
			<li><a class="app-menu__item" href="listContacts"><i
					class='app-menu__icon bx bx-envelope'></i><span
					class="app-menu__label">Quản lý liên hệ</span></a></li>

			<li><a class="app-menu__item" href="doc/quan-ly-bao-cao.jsp"><i
					class='app-menu__icon bx bx-pie-chart-alt-2'></i><span
					class="app-menu__label">Báo cáo doanh thu</span></a></li>
			<li><a class="app-menu__item " href="BaocaoThongke"><i
					class='app-menu__icon bx bx-pie-chart-alt-2'></i><span
					class="app-menu__label">Thống kê</span></a></li>
		</ul>
	</aside>
	<main class="app-content">
		<div class="app-title">
			<ul class="app-breadcrumb breadcrumb side">
				<li class="breadcrumb-item active"><a href="#"><b>Danh
							sách sản phẩm</b></a></li>
			</ul>
			<div id="clock"></div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="tile">
					<div class="tile-body">
						<div class="row element-button">
							<div class="col-sm-2">

								<a class="btn btn-add btn-sm" href="addProduct" title="Thêm"><i
									class="fas fa-plus"></i> Tạo mới sản phẩm</a>
							</div>

							<div class="col-sm-2">
								<a class="btn btn-delete btn-sm pdf-file" type="button"
									title="In" onclick="myFunction(this)"><i
									class="fas fa-file-pdf"></i> Xóa</a>
							</div>

						</div>
						<table class="table table-hover table-bordered" id="sampleTable">
							<thead>
								<tr>
									<th style="width: 50px;">Mã</th>
									<th style="width: 200px;">Tên sản phẩm</th>
									<th style="width: 140px;">Ảnh</th>
									<th style="width: 90px;">Số lượng</th>
									<th>Tình trạng</th>
									<th>Giá nhập</th>
									<th>Giá bán</th>
									<th>Danh mục</th>
									<th style="width: 109px;">Chức năng</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="p" items="${listP}">
									<tr>

										<td>${p.id}</td>
										<td>${p.name}</td>
										<td><img
											src="${pageContext.request.contextPath}/${p.imageUrl}"
											alt="${p.name}" width="100px;"></td>
										<td>${p.stockQuantity}</td>
										<td><c:choose>
												<c:when test="${p.status == 'ACTIVE'}">
													<span class="badge bg-success">Còn bán</span>
												</c:when>
												<c:otherwise>
													<span class="badge bg-secondary">Ngưng bán</span>
												</c:otherwise>
											</c:choose></td>
										<td><fmt:formatNumber value="${p.pricein}" type="number"
												pattern="#,##0" />đ</td>
										<td><fmt:formatNumber value="${p.price}" type="number"
												pattern="#,##0" />đ</td>
										<td>${p.categoryName}</td>
										<td>
											<button class="btn btn-primary btn-sm trash" type="button"
												title="Xóa" onclick="deleteProduct(${p.id})">
												<i class="fas fa-trash-alt"></i>
											</button>
											<button class="btn btn-primary btn-sm edit"
												onclick="editProduct(this)" data-id="${p.id}"
												data-name="${p.name}" data-price="${p.price}"
												data-pricein="${p.pricein}" data-stock="${p.stockQuantity}"
												data-category="${p.categoryId}"
												data-weightgram="${p.weightGram}" data-status="${p.status}"
												data-image="${pageContext.request.contextPath}/${p.imageUrl}">

												<i class="fas fa-edit"></i>
											</button>
										</td>
									</tr>
								</c:forEach>


							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</main>

	<div class="modal fade" id="ModalUP" tabindex="-1" role="dialog"
		aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<div class="row">
						<div class="form-group col-md-12">
							<span class="thong-tin-thanh-toan">
								<h5>Chỉnh sửa thông tin sản phẩm</h5>
							</span>
						</div>
					</div>

					<form id="updateProductForm" enctype="multipart/form-data">
						<input type="hidden" id="productId" name="productId"> <input
							type="hidden" id="oldImageUrl" name="oldImageUrl">
						<div class="section-label">Thông tin cơ bản</div>

						<div class="row">
							<div class="form-group col-md-6">
								<label class="control-label">Mã sản phẩm</label> <input
									class="form-control" type="text" id="productCode" readonly>
							</div>
							<div class="form-group col-md-6">
								<label class="control-label">Danh mục</label> <select
									class="form-control" id="categoryId" name="categoryId" required>
									<c:forEach var="cat" items="${listC}">
										<option value="${cat.id}">${cat.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group col-md-6">
								<label class="control-label">Trạng thái</label> <select
									class="form-control" id="status" name="status" required>
									<option value="ACTIVE">Còn bán</option>
									<option value="INACTIVE">Ngưng bán</option>
								</select>
							</div>
							<div class="form-group col-md-6">
								<label class="control-label">Tên sản phẩm</label> <input
									class="form-control" type="text" id="productName"
									name="productName" required>
							</div>

							<div class="form-group col-md-6">
								<label class="control-label">Số lượng</label> <input
									class="form-control" type="number" id="stockQuantity"
									name="stockQuantity" required min="0" readonly>
							</div>

							<div class="form-group col-md-6">
								<label class="control-label">Giá nhập</label> <input
									class="form-control" type="number" id="pricein" name="pricein"
									required min="0" step="1000">
							</div>

							<div class="form-group col-md-6">
								<label class="control-label">Giá bán</label> <input
									class="form-control" type="number" id="price" name="price"
									required min="0" step="1000">
							</div>

							<div class="form-group col-md-6">
								<label class="control-label">Khối lượng(g)</label> <input
									class="form-control" type="number" id="WeightGram"
									name="WeightGram" required min="0">
							</div>

							<div class="form-group col-md-12">
								<label class="control-label">Ảnh sản phẩm</label>

								<div id="currentImageContainer" style="margin-bottom: 15px;">
									<label
										style="font-weight: 600; color: #333; margin-bottom: 8px; display: block;">Ảnh
										hiện tại:</label> <img id="currentImage" src="" alt="Current Image"
										style="max-width: 200px; max-height: 200px; border: 2px solid #ddd; padding: 5px; border-radius: 5px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
								</div>

								<input class="form-control" type="file" id="productImage"
									name="productImage" accept="image/*"> <small
									class="form-text text-muted"> Để trống nếu không muốn
									thay đổi ảnh. Chấp nhận: JPG, PNG, GIF (tối đa 10MB) </small>

								<div id="newImagePreview"
									style="margin-top: 15px; display: none;">
									<label
										style="font-weight: 600; color: #007bff; margin-bottom: 8px; display: block;">
										<i class="fas fa-sync-alt"></i> Ảnh thay đổi:
									</label> <img id="previewImage" src="" alt="Preview"
										style="max-width: 200px; max-height: 200px; border: 2px solid #007bff; padding: 5px; border-radius: 5px; box-shadow: 0 2px 4px rgba(0, 123, 255, 0.2);">
								</div>
							</div>
						</div>

						<div class="section-label">Thông tin chi tiết</div>
						<div class="row">

							<div class="form-group col-md-3">

								<label class="control-label">Phương pháp sơ chế</label> <select
									class="form-control" id="up_processing"
									name="processing_method">
									<option value="">-- Chọn --</option>
									<option value="DRY">Khô (Natural/Dry)</option>
									<option value="WET">Ướt (Washed)</option>
									<option value="HONEY">Mật ong (Honey)</option>
								</select>
							</div>

							<div class="form-group col-md-3">
								<label class="control-label" style="height: 42px;">Mức
									độ rang</label> <select class="form-control" id="up_roast"
									name="roast_level">
									<option value="">-- Chọn --</option>
									<option value="LIGHT">Light Roast</option>
									<option value="MEDIUM">Medium Roast</option>
									<option value="DARK">Dark Roast</option>
								</select>
							</div>
							<div class="form-group col-md-3">
								<label class="control-label" style="height: 42px;">Kích
									cỡ xay</label> <select class="form-control" id="up_grind"
									name="grind_size">
									<option value="">-- Chọn --</option>
									<option value="WHOLE_BEAN">Nguyên hạt</option>
									<option value="COARSE">Thô (Cold Brew)</option>
									<option value="MEDIUM">Vừa (Phin / V60)</option>
									<option value="FINE">Mịn (Espresso)</option>
								</select>
							</div>
							<div class="form-group col-md-3">
								<label class="control-label" style="height: 42px;">Mức
									caffeine</label> <select class="form-control" id="up_caffeine"
									name="caffeine_level">
									<option value="">-- Chọn --</option>
									<option value="LOW">Thấp</option>
									<option value="MEDIUM">Trung bình</option>
									<option value="HIGH">Cao</option>
								</select>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label">Phù hợp cho</label> <input
									class="form-control" type="text" id="up_suitable"
									name="suitable_for"
									placeholder="VD: Người mới uống, người thích vị đậm">
							</div>
							<div class="form-group col-md-4">
								<label class="control-label">Uống lúc nào</label> <select
									class="form-control" id="up_time" name="recommended_time">
									<option value="">-- Chọn --</option>
									<option value="EarlyMorning">Sáng sớm</option>
									<option value="Morning">Buổi sáng</option>
									<option value="Morning,EarlyMorning">Sáng sớm &amp;
										buổi sáng</option>
									<option value="Afternoon">Buổi chiều</option>
									<option value="Morning,Afternoon">Sáng &amp; chiều</option>
									<option value="Evening">Buổi tối</option>
									<option value="Morning,Evening">Sáng &amp; tối</option>
									<option value="Afternoon,Evening">Chiều &amp; tối</option>
									<option value="Morning,Afternoon,Evening">Sáng, chiều
										&amp; tối</option>
									<option value="EVERYDAY">Bất cứ lúc nào</option>
								</select>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label">Phương pháp pha</label> <input
									class="form-control" type="text" id="up_brew"
									name="brew_methods" placeholder="VD: Phin, Cold Brew, Espresso">
							</div>
							<div class="col-md-12 mb-3">
								<label class="control-label" style="font-weight: 600">Hương
									vị đặc trưng</label>
								<div id="chipContainer">
									<span class="flavor-chip" data-val="Chocolate"
										onclick="toggleChip(this)"> Chocolate</span> <span
										class="flavor-chip" data-val="Caramel"
										onclick="toggleChip(this)"> Caramel</span> <span
										class="flavor-chip" data-val="Vanilla"
										onclick="toggleChip(this)"> Vanilla</span> <span
										class="flavor-chip" data-val="Nutty"
										onclick="toggleChip(this)"> Nutty</span> <span
										class="flavor-chip" data-val="Citrus"
										onclick="toggleChip(this)"> Citrus</span> <span
										class="flavor-chip" data-val="Floral"
										onclick="toggleChip(this)"> Floral</span> <span
										class="flavor-chip" data-val="Berry"
										onclick="toggleChip(this)"> Berry</span> <span
										class="flavor-chip" data-val="Honey"
										onclick="toggleChip(this)"> Honey</span> <span
										class="flavor-chip" data-val="Earthy"
										onclick="toggleChip(this)"> Earthy</span> <span
										class="flavor-chip" data-val="Smoky"
										onclick="toggleChip(this)"> Smoky</span>
								</div>
								<input type="hidden" name="flavor_tags" id="up_tags"> <small
									class="text-muted" id="chipHint">Chưa chọn hương vị nào</small>
							</div>
						</div>


						<div class="row">
							<div class="col-md-3 mb-3">
								<label style="font-size: 13px; font-weight: 600">Độ đậm
									đà (Body)</label>
								<div class="star-row" id="stars_flavor_intensity"></div>
								<div class="star-val">
									Giá trị: <b id="lbl_flavor">3</b>/5
								</div>
								<input type="hidden" name="flavor_intensity" id="up_flavor"
									value="3">
							</div>
							<div class="col-md-3 mb-3">
								<label style="font-size: 13px; font-weight: 600">Độ đắng
									(Bitterness)</label>
								<div class="star-row" id="stars_bitterness_level"></div>
								<div class="star-val">
									Giá trị: <b id="lbl_bitter">3</b>/5
								</div>
								<input type="hidden" name="bitterness_level" id="up_bitter"
									value="3">
							</div>
							<div class="col-md-3 mb-3">
								<label style="font-size: 13px; font-weight: 600">Độ chua
									(Acidity)</label>
								<div class="star-row" id="stars_acidity_level"></div>
								<div class="star-val">
									Giá trị: <b id="lbl_acid">3</b>/5
								</div>
								<input type="hidden" name="acidity_level" id="up_acid" value="3">
							</div>
							<div class="col-md-3 mb-3">
								<label style="font-size: 13px; font-weight: 600">Độ ngọt
									(Sweetness)</label>
								<div class="star-row" id="stars_sweetness_level"></div>
								<div class="star-val">
									Giá trị: <b id="lbl_sweet">3</b>/5
								</div>
								<input type="hidden" name="sweetness_level" id="up_sweet"
									value="3">
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-save" type="submit">Lưu lại</button>
							<button class="btn btn-cancel" type="button" data-dismiss="modal">Hủy
								bỏ</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/jquery-3.2.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/popper.min.js"></script>
	<script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/main.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/plugins/pace.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/admin-assets/ADjs/plugins/chart.js"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/admin-assets/ADjs/plugins/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/admin-assets/ADjs/plugins/dataTables.bootstrap.min.js"></script>


	<script>
	const STAR_FIELDS = [
	    { containerKey: 'flavor_intensity', inputId: 'up_flavor',  lblId: 'lbl_flavor' },
	    { containerKey: 'bitterness_level', inputId: 'up_bitter',  lblId: 'lbl_bitter' },
	    { containerKey: 'acidity_level',    inputId: 'up_acid',    lblId: 'lbl_acid'   },
	    { containerKey: 'sweetness_level',  inputId: 'up_sweet',   lblId: 'lbl_sweet'  },
	];

	function renderStars(containerKey, value) {
	    const container = document.getElementById('stars_' + containerKey);
	    if (!container) return;
	    container.innerHTML = '';
	    for (let i = 1; i <= 5; i++) {
	        const d = document.createElement('div');
	        d.className = 'star-dot' + (i <= value ? ' on' : '');
	        d.dataset.val = i;
	        d.onclick = function() { setStarValue(containerKey, parseInt(this.dataset.val)); };
	        container.appendChild(d);
	    }
	}

	function setStarValue(containerKey, value) {
	    const field = STAR_FIELDS.find(f => f.containerKey === containerKey);
	    if (!field) return;
	    document.getElementById(field.inputId).value = value;
	    document.getElementById(field.lblId).textContent = value;
	    renderStars(containerKey, value);
	}

	function initAllStars(val) {
	    STAR_FIELDS.forEach(f => renderStars(f.containerKey, val || 3));
	}
	function toggleChip(el) {
	    el.classList.toggle('selected');
	    syncChips();
	}

	function syncChips() {
	    const selected = [...document.querySelectorAll('.flavor-chip.selected')].map(c => c.dataset.val);
	    document.getElementById('up_tags').value = selected.join(',');
	    document.getElementById('chipHint').textContent =
	        selected.length ? 'Đã chọn: ' + selected.join(', ') : 'Chưa chọn hương vị nào';
	}

	function setChipsFromString(tagStr) {
	    document.querySelectorAll('.flavor-chip').forEach(c => c.classList.remove('selected'));
	    if (!tagStr) { syncChips(); return; }
	    const tags = tagStr.split(',').map(t => t.trim());
	    tags.forEach(tag => {
	        const chip = document.querySelector('.flavor-chip[data-val="' + tag + '"]');
	        if (chip) chip.classList.add('selected');
	    });
	    syncChips();
	}

	function deleteProduct(productId) {
	    Swal.fire({
	        title: 'Cảnh báo',
	        text: 'Bạn có chắc chắn muốn xóa sản phẩm này?',
	        icon: 'warning',
	        showCancelButton: true,
	        confirmButtonText: 'Đồng ý',
	        cancelButtonText: 'Hủy',
	        confirmButtonColor: '#d33'
	    }).then((result) => {
	        if (result.isConfirmed) {
	            Swal.fire({
	                title: 'Đang xóa...',
	                allowOutsideClick: false,
	                didOpen: () => Swal.showLoading()
	            });

	            $.ajax({
	                url: 'deleteProduct',
	                type: 'GET',
	                data: { id: productId },
	                dataType: 'json',
	                success: function (res) {
	                    if (res.success) {
	                        Swal.fire('Thành công!', res.message, 'success')
	                            .then(() => location.reload());
	                    } else {
	                        Swal.fire('Lỗi!', res.message, 'error');
	                    }
	                },
	                error: function () {
	                    Swal.fire('Lỗi!', 'Không thể kết nối server!', 'error');
	                }
	            });
	        }
	    });
	}

	function editProduct(btn) {
	    const id       = $(btn).data('id');
	    const priceRaw = $(btn).data('price');
	    const priceinRaw = $(btn).data('pricein');

	    $('#productId').val(id);
	    $('#productCode').val(id);
	    $('#productName').val($(btn).data('name'));
	    $('#price').val(parseInt(priceRaw));
	    $('#pricein').val(parseInt(priceinRaw));
	    $('#stockQuantity').val($(btn).data('stock')); 
	    $('#categoryId').val($(btn).data('category'));
	    $('#WeightGram').val($(btn).data('weightgram'));
	    $('#status').val($(btn).data('status'));
	    $('#currentImage').attr('src', $(btn).data('image'));
	    $('#newImagePreview').hide();
	    $('#oldImageUrl').val($(btn).data('image'));

	    initAllStars(3);
	    setChipsFromString('');
	    ['up_processing','up_roast','up_grind','up_caffeine','up_time'].forEach(id => {
	        const el = document.getElementById(id);
	        if (el) el.value = '';
	    });
	    document.getElementById('up_suitable').value = '';
	    document.getElementById('up_brew').value = '';

	    $('#ModalUP').modal('show');

	    $('#profileLoading').show();
	    fetch('getProductProfile?productId=' + id)
	        .then(r => r.json())
	        .then(p => {
	            $('#profileLoading').hide();
	            if (!p) return; 

	            // Dropdown kỹ thuật
	            setSelectVal('up_processing', p.processingMethod);
	            setSelectVal('up_roast',      p.roastLevel);
	            setSelectVal('up_grind',      p.grindSize);
	            setSelectVal('up_caffeine',   p.caffeineLevel);
	            setSelectVal('up_time',       p.recommendedTime);

	            // Input text
	            document.getElementById('up_suitable').value = p.suitableFor  || '';
	            document.getElementById('up_brew').value     = p.brewMethods  || '';

	            // Flavor chips
	            setChipsFromString(p.tasteNotes || '');

	            // Star rating
	            setStarValue('flavor_intensity', p.flavorIntensity  || 3);
	            setStarValue('bitterness_level', p.bitternessLevel  || 3);
	            setStarValue('acidity_level',    p.acidityLevel     || 3);
	            setStarValue('sweetness_level',  p.sweetnessLevel   || 3);
	        })
	        .catch(() => {
	            $('#profileLoading').hide();
	        });
	}

	function setSelectVal(id, val) {
	    const el = document.getElementById(id);
	    if (el && val) el.value = val;
	}
	$('#updateProductForm').submit(function(e) {
	    e.preventDefault();

	    Swal.fire({
	        title: 'Xác nhận',
	        text: 'Bạn có muốn cập nhật sản phẩm này?',
	        icon: 'question',
	        showCancelButton: true,
	        confirmButtonText: 'Cập nhật',
	        cancelButtonText: 'Hủy',
	        confirmButtonColor: '#5B3427'
	    }).then(result => {
	        if (!result.isConfirmed) return;

	        Swal.fire({ title: 'Đang cập nhật...', allowOutsideClick: false, didOpen: () => Swal.showLoading() });

	        $.ajax({
	            url: 'updateProduct',
	            type: 'POST',
	            data: new FormData(document.getElementById('updateProductForm')),
	            processData: false,
	            contentType: false,
	            success: function(res) {
	                if (res.success) {
	                    Swal.fire('Thành công!', res.message, 'success').then(() => location.reload());
	                } else {
	                    Swal.fire('Lỗi!', res.message, 'error');
	                }
	            },
	            error: function() {
	                Swal.fire('Lỗi!', 'Không thể kết nối server!', 'error');
	            }
	        });
	    });
	});

$('#ModalUP').on('hidden.bs.modal', function () {
    const form = document.getElementById('updateProductForm');
    if (form) {
        form.reset();
    }
    const preview = document.getElementById('newImagePreview');
    if (preview) preview.style.display = 'none';    let isSubmitting = false;
});

function time() {
    var today = new Date();
    var weekday = ["Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"];
    var day = weekday[today.getDay()];
    var dd = today.getDate();
    var mm = today.getMonth() + 1;
    var yyyy = today.getFullYear();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    if (dd < 10) dd = '0' + dd;
    if (mm < 10) mm = '0' + mm;
    document.getElementById("clock").innerHTML = '<span class="date">' + day + ', ' + dd + '/' + mm + '/' + yyyy + ' - ' + h + " giờ " + m + " phút " + s + " giây</span>";
    setTimeout("time()", 1000);
    
    function checkTime(i) {
        return i < 10 ? "0" + i : i;
    }
}

$('#all').click(function (e) {
    $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
    e.stopImmediatePropagation();
});

$('#productImage').change(function (event) {
    const input = event.target; 
    
    if (input.files && input.files[0]) {
        const reader = new FileReader(); 
        reader.onload = function (e) {
            $('#previewImage').attr('src', e.target.result);
            $('#previewImage').show(); 
            $('#newImagePreview').show(); 
        }

        reader.readAsDataURL(input.files[0]);
    } else {
        $('#newImagePreview').hide();
    }
});
</script>
</body>

</html>