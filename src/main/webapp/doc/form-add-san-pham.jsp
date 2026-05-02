<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Thêm sản phẩm | Quản trị Admin</title>
<link rel="icon" href="img/blog/da_logo9.jpg" type="image/x-icon" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/admin-assets/ADcss/main.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>
:root {
	--coffee-dark: #2C1810;
	--coffee-medium: #6B3A2A;
	--coffee-light: #C8956C;
	--cream: #FFF8F0;
	--ai-blue: #0d6efd;
	--success-green: #28a745;
}

.app-sidebar {
	overflow-y: auto !important;
}

.flavor-tag-chip {
	display: inline-block;
	padding: 6px 14px;
	border-radius: 20px;
	border: 2px solid #dee2e6;
	background: white;
	color: #495057;
	cursor: pointer;
	margin: 4px;
	font-size: 13px;
	transition: all 0.2s ease;
	user-select: none;
}

.app-sidebar {
	overflow-y: auto !important;
}

.flavor-tag-chip:hover {
	border-color: #532d20;
	background: #fff3e0;
}

.flavor-tag-chip.selected {
	border-color: #532d20;
	background: rgb(255, 212, 59);
	color: white;
}

.slider-group {
	margin-bottom: 16px;
}

.slider-label {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 6px;
}

.slider-label .label-text {
	font-weight: 600;
	color: var(--coffee-dark);
	font-size: 14px;
}

.slider-value-badge {
	background: #532d20;
	color: white;
	padding: 2px 10px;
	border-radius: 12px;
	font-size: 13px;
	font-weight: 700;
	min-width: 40px;
	text-align: center;
}

.slider-stars {
	display: flex;
	gap: 4px;
	margin-top: 4px;
}

.star {
	width: 16px;
	height: 16px;
	border-radius: 50%;
	background: #dee2e6;
	transition: background 0.2s;
}

.star.filled {
	background: rgb(255, 212, 59);
}

input[type="range"] {
	width: 100%;
	accent-color: #532d20;
}

.admin-correction-indicator {
	display: none;
	font-size: 12px;
	color: #fd7e14;
	font-weight: 600;
	margin-left: 8px;
}

.brew-method-tag {
	display: inline-block;
	background: #e3f2fd;
	color: #1565c0;
	padding: 4px 12px;
	border-radius: 12px;
	font-size: 12px;
	margin: 2px;
	font-weight: 500;
}

.btn-ai {
	background: linear-gradient(135deg, #667eea, #764ba2);
	color: white;
	border: none;
	border-radius: 8px;
	padding: 10px 24px;
	font-weight: 600;
	cursor: pointer;
	transition: all 0.3s ease;
	display: inline-flex;
	align-items: center;
	gap: 8px;
	font-size: 14px;
}

.btn-ai:hover {
	transform: translateY(-1px);
	box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
}

.btn-ai:disabled {
	opacity: 0.6;
	cursor: not-allowed;
	transform: none;
}

.ai-loading {
	display: none;
	text-align: center;
	padding: 30px;
	color: #667eea;
}

.spinner-border {
	display: inline-block;
	width: 2rem;
	height: 2rem;
	border: 0.25em solid currentColor;
	border-right-color: transparent;
	border-radius: 50%;
	animation: spinner-border .75s linear infinite;
}

@
keyframes spinner-border {to { transform:rotate(360deg);
	
}

}
.ai-badge {
	background: linear-gradient(135deg, #667eea, #764ba2);
	color: white;
	font-size: 11px;
	padding: 2px 8px;
	border-radius: 10px;
	font-weight: 600;
}

.correction-summary {
	background: #fff3cd;
	border: 1px solid #ffc107;
	border-radius: 8px;
	padding: 10px 14px;
	font-size: 13px;
	color: #856404;
	margin-top: 12px;
	display: none;
}

.hint-text {
	font-size: 12px;
	color: #6c757d;
	margin-top: 4px;
	font-style: italic;
}

.Choicefile {
	display: block;
	background: #14142B;
	border: 1px solid #fff;
	color: #fff;
	width: 150px;
	text-align: center;
	text-decoration: none;
	cursor: pointer;
	padding: 5px 0px;
	border-radius: 5px;
	font-weight: 500;
}

.Choicefile:hover {
	text-decoration: none;
	color: white;
}

.removeimg {
	display: none;
}

#thumbbox {
	position: relative;
	width: 100%;
	margin-bottom: 20px;
}

.removeimg {
	height: 25px;
	position: absolute;
	top: 5px;
	left: 5px;
	width: 25px;
	border-radius: 50%;
	background: red;
	cursor: pointer;
}
</style>
</head>

<body class="app sidebar-mini rtl">
	<header class="app-header">
		<a class="app-sidebar__toggle" href="#" data-toggle="sidebar"></a>
		<ul class="app-nav">
			<li><a class="app-nav__item" href="home"><i
					class='bx bx-log-out bx-rotate-180'></i></a></li>
		</ul>
	</header>

	<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
	<aside class="app-sidebar">
		<div class="app-sidebar__user">
			<img class="app-sidebar__user-avatar"
				src="${pageContext.request.contextPath}/img/blog/da_logo9.jpg"
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
			<li><a class="app-menu__item" href="AdminHome"><i
					class='app-menu__icon bx bx-tachometer'></i><span
					class="app-menu__label">Bảng điều khiển</span></a></li>
			<li><a class="app-menu__item" href="HTU"><i
					class='app-menu__icon bx bx-user-voice'></i><span
					class="app-menu__label">Quản lý khách hàng</span></a></li>
			<li><a class="app-menu__item haha" href="HTSP"><i
					class='app-menu__icon bx bx-purchase-tag-alt'></i><span
					class="app-menu__label">Quản lý sản phẩm</span></a></li>
			<li><a class="app-menu__item " href="HTDM"><i
					class='app-menu__icon bx bx-id-card'></i> <span
					class="app-menu__label">Quản lý Danh mục</span></a></li>
			<li><a class="app-menu__item" href="listOrders"><i
					class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Quản
						lý đơn hàng</span></a></li>
			<li><a class="app-menu__item" href="HTNCC"><i
					class='app-menu__icon bx bx-user-voice'></i><span
					class="app-menu__label">Quản lý NCC</span></a></li>
			<li><a class="app-menu__item" href="stock-management"><i
					class="fa-solid fa-cart-flatbed"
					style="color: rgb(255, 255, 255); margin-right: 15px;"></i><span
					class="app-menu__label">Quản lý Nhập Xuất Kho</span></a></li>
			<li><a class="app-menu__item" href="blog"><i
					class='app-menu__icon bx bx-run'></i><span class="app-menu__label">Quản
						lý Bài viết</span></a></li>
			<li><a class="app-menu__item" href="listContacts"><i
					class='app-menu__icon bx bx-envelope'></i><span
					class="app-menu__label">Quản lý liên hệ</span></a></li>
			<li><a class="app-menu__item " href="BaocaoThongke"><i
					class='app-menu__icon bx bx-pie-chart-alt-2'></i><span
					class="app-menu__label">Thống kê</span></a></li>
		</ul>
	</aside>

	<main class="app-content">
		<div class="app-title">
			<ul class="app-breadcrumb breadcrumb">
				<li class="breadcrumb-item"><a href="HTSP">Danh sách sản
						phẩm</a></li>
				<li class="breadcrumb-item">Thêm sản phẩm</li>
			</ul>
		</div>

		<form action="addProduct" method="POST" enctype="multipart/form-data"
			id="addProductForm">

			<div class="row">
				<div class="col-md-12">
					<div class="tile">
						<h3 class="tile-title">Thông tin cơ bản</h3>
						<div class="tile-body row">
							<div class="form-group col-md-4">
								<label class="control-label">Danh mục <span
									style="color: red">*</span></label> <select class="form-control"
									name="categoryId" required>
									<option value="">-- Chọn danh mục --</option>
									<c:forEach var="cat" items="${listC}">
										<option value="${cat.id}">${cat.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label">Tên sản phẩm <span
									style="color: red">*</span></label> <input class="form-control"
									type="text" name="productName" id="productName" required
									placeholder="VD: Trung Nguyên Legend Blend 1">
							</div>
							<div class="form-group col-md-4">
								<label class="control-label">Trọng lượng (gram) <span
									style="color: red">*</span></label> <input class="form-control"
									type="number" name="WeightGram" id="WeightGram" required min="1" max="30000" 
									placeholder="VD: 250">
							</div>
							<div class="form-group col-md-3">
								<label class="control-label">Giá nhập (VNĐ) <span
									style="color: red">*</span></label> <input class="form-control"
									type="number" name="pricein" required min="1"
									placeholder="VD: 150000">
							</div>
							<div class="form-group col-md-3">
								<label class="control-label">Giá bán (VNĐ) <span
									style="color: red">*</span></label> <input class="form-control"
									type="number" name="price" required min="1"
									placeholder="VD: 250">
							</div>

							<div class="form-group col-md-3">
								<label class="control-label">Số lượng tồn kho <span
									style="color: red">*</span></label> <input class="form-control"
									type="number" name="stockQuantity" required min="0"
									placeholder="VD: 100">
							</div>



							<div class="form-group col-md-3">
								<label class="control-label">Ảnh sản phẩm</label> <input
									type="file" class="form-control" id="uploadfile"
									name="productImage" accept="image/*" onchange="readURL(this)">
								<div id="thumbbox" style="margin-top: 8px;">
									<img id="thumbimage" src="" alt="Preview"
										style="display: none; max-height: 120px; border-radius: 8px;">
								</div>
							</div>

							<div class="form-group col-md-12">
								<label class="control-label">Mô tả sản phẩm</label>
								<textarea class="form-control" name="detailDescription" rows="3"
									placeholder="Mô tả chi tiết về sản phẩm..."></textarea>
							</div>

						</div>
					</div>
				</div>

				<div class="col-md-12">
					<div class="tile">
						<h3 class="tile-title">Thông tin chi tiết</h3>
						<div class="tile-body row">

							<div class="form-group col-md-4">
								<label class="control-label">Phương pháp sơ chế</label> <select
									class="form-control" id="processing_method"
									name="processing_method">
									<option value="">-- Chọn --</option>
									<option value="DRY">Khô (Natural/Dry)</option>
									<option value="WET">Ướt (Washed)</option>
									<option value="HONEY">Mật ong (Honey)</option>
								</select>
								<div class="hint-text" id="processingHint"></div>
							</div>

							<div class="form-group col-md-4">
								<label class="control-label">Mức độ rang</label> <select
									class="form-control" id="roast_level" name="roast_level">
									<option value="">-- Chọn --</option>
									<option value="LIGHT">Light Roast</option>
									<option value="MEDIUM">Medium Roast</option>
									<option value="DARK">Dark Roast</option>
								</select>
								<div class="hint-text" id="roastHint"></div>
							</div>

							<div class="form-group col-md-4">
								<label class="control-label">Mức độ xay</label> <select
									class="form-control" id="grind_size" name="grind_size">
									<option value="">-- Chọn --</option>
									<option value="FINE">Mịn (Espresso)</option>
									<option value="MEDIUM">Vừa (Phin / V60)</option>
									<option value="COARSE">Thô (Cold Brew)</option>
									<option value="WHOLE_BEAN">Nguyên hạt</option>
								</select>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label">Mức caffeine</label> <select
									class="form-control" id="caffeine_level" name="caffeine_level">
									<option value="">-- Chọn --</option>
									<option value="LOW">Thấp</option>
									<option value="MEDIUM">Trung bình</option>
									<option value="HIGH">Cao</option>
								</select>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label">Uống lúc nào</label> <select
									class="form-control" id="recommended_time"
									name="recommended_time">
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
							<div class="form-group col-md-6">
								<label class="control-label">Phương pháp pha</label>
								<div id="brewMethodsDisplay" style="margin-top: 6px;">
									<span class="text-muted" style="font-size: 13px;"></span>
								</div>

								<input class="form-control" type="text" name="brew_methods"
									id="brewMethodsInput" required
									placeholder="Phin,Cold brew, Espresso...">

							</div>
							<div class="form-group col-md-6">
								<label class="control-label">Phù hợp cho</label>
								<div id="suitable_forDisplay" style="margin-top: 6px;">
									<span class="text-muted" style="font-size: 13px;"></span>
								</div>

								<input class="form-control" type="text" name="suitable_for"
									id="suitable_for" required
									placeholder="Người mới, Người mất ngủ...">

							</div>
							<div class="col-md-12 mb-3">
								<label class="control-label">Hương vị đặc trưng (Flavor
									Tags)</label>
								<div id="flavorTagsContainer">
									<span class="flavor-tag-chip" data-value="Chocolate"
										onclick="toggleFlavorTag(this)">Chocolate</span> <span
										class="flavor-tag-chip" data-value="Caramel"
										onclick="toggleFlavorTag(this)">Caramel</span> <span
										class="flavor-tag-chip" data-value="Vanilla"
										onclick="toggleFlavorTag(this)">Vanilla</span> <span
										class="flavor-tag-chip" data-value="Nutty"
										onclick="toggleFlavorTag(this)">Nutty</span> <span
										class="flavor-tag-chip" data-value="Citrus"
										onclick="toggleFlavorTag(this)">Citrus</span> <span
										class="flavor-tag-chip" data-value="Floral"
										onclick="toggleFlavorTag(this)">Floral</span> <span
										class="flavor-tag-chip" data-value="Berry"
										onclick="toggleFlavorTag(this)">Berry</span> <span
										class="flavor-tag-chip" data-value="Honey"
										onclick="toggleFlavorTag(this)">Honey</span> <span
										class="flavor-tag-chip" data-value="Earthy"
										onclick="toggleFlavorTag(this)">Earthy</span> <span
										class="flavor-tag-chip" data-value="Smoky"
										onclick="toggleFlavorTag(this)">Smoky</span>
								</div>
								<small id="selectedTagsInfo" class="text-muted">Chưa
									chọn tag nào</small> <input type="hidden" name="flavor_tags"
									id="flavor_tags">
							</div>


							<div class="col-md-6">
								<div class="slider-group">
									<div class="slider-label">
										<span class="label-text">Độ đậm đà (Body)</span> <span
											class="slider-value-badge" id="bodyVal">3</span> <span
											class="admin-correction-indicator" id="bodyCorrection"></span>
									</div>
									<input type="range" name="flavor_intensity" id="bodySlider"
										min="1" max="5" value="3"
										oninput="updateSlider('body', this.value)">
									<div class="slider-stars" id="bodyStars"></div>
								</div>
							</div>

							<!-- Acidity -->
							<div class="col-md-6">
								<div class="slider-group">
									<div class="slider-label">
										<span class="label-text">Độ chua (Acidity)</span> <span
											class="slider-value-badge" id="acidityVal">3</span> <span
											class="admin-correction-indicator" id="acidityCorrection">✏️
											Đã chỉnh</span>
									</div>
									<input type="range" name="acidity_level" id="aciditySlider"
										min="1" max="5" value="3"
										oninput="updateSlider('acidity', this.value)">
									<div class="slider-stars" id="acidityStars"></div>
								</div>
							</div>

							<!-- Bitterness -->
							<div class="col-md-6">
								<div class="slider-group">
									<div class="slider-label">
										<span class="label-text">Độ đắng (Bitterness)</span> <span
											class="slider-value-badge" id="bitternessVal">3</span> <span
											class="admin-correction-indicator" id="bitternessCorrection">✏️
											Đã chỉnh</span>
									</div>
									<input type="range" name="bitterness_level"
										id="bitternessSlider" min="1" max="5" value="3"
										oninput="updateSlider('bitterness', this.value)">
									<div class="slider-stars" id="bitternessStars"></div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="slider-group">
									<div class="slider-label">
										<span class="label-text">Độ ngọt (Sweetness)</span> <span
											class="slider-value-badge" id="sweetnessVal">3</span> <span
											class="admin-correction-indicator" id="sweetnessCorrection">✏️
											Đã chỉnh</span>
									</div>
									<input type="range" name="sweetness_level" id="sweetnessSlider"
										min="1" max="5" value="3"
										oninput="updateSlider('sweetness', this.value)">
									<div class="slider-stars" id="sweetnessStars"></div>
								</div>
							</div>


						</div>
					</div>
				</div>

			</div>
			<div class="col-md-12 text-right mb-5">
				<hr>
				<button type="submit" class="btn btn-success btn-lg">
					<i class="fas fa-save"></i> Lưu sản phẩm
				</button>
				<a href="HTSP" class="btn btn-secondary btn-lg ml-2">Hủy bỏ</a>
			</div>

			</div>
		</form>
	</main>

	<script>
let aiPredictionValues = {};
function updateSlider(key, value) {
    const v = parseInt(value, 10);
    const cfg = SLIDER_MAP[key];
    if (!cfg) return;
    const badge = document.getElementById(cfg.badge);
    if (badge) badge.textContent = v;

    updateStars(key, v);
}

function updateStars(key, value) {
    const cfg = SLIDER_MAP[key];
    if (!cfg) return;
    const container = document.getElementById(cfg.stars);
    if (!container) return;

    container.innerHTML = '';
    for (let i = 1; i <= 5; i++) {
        const dot = document.createElement('div');
        dot.className = 'star' + (i <= value ? ' filled' : '');
        container.appendChild(dot);
    }
}
function readURL(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = e => {
            const img = document.getElementById('thumbimage');
            img.src = e.target.result;
            img.style.display = 'block';
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function removeImage() {
    document.getElementById('thumbimage').src = '';
    document.getElementById('thumbimage').style.display = 'none';
    document.getElementById('uploadfile').value = '';
    document.querySelector('.removeimg').style.display = 'none';
    document.querySelector('.filename').textContent = '';
}

function toggleFlavorTag(el) {
    el.classList.toggle('selected');
    updateFlavorTagsInput();
}

function updateFlavorTagsInput() {
    const selected = [...document.querySelectorAll('.flavor-tag-chip.selected')]
                        .map(t => t.dataset.value);
    document.getElementById('flavor_tags').value = selected.join(',');
    const info = document.getElementById('selectedTagsInfo');
    if (info) {
        info.textContent = selected.length > 0
            ? 'Đã chọn: ' + selected.join(', ')
            : 'Chưa chọn tag nào';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    ['body','acidity','bitterness','sweetness'].forEach(k => updateStars(k, 3));
});
</script>

	<c:if test="${not empty successMessage}">
		<script>swal("Thành công!", "${successMessage}", "success").then(() => { window.location.href = "HTSP"; });</script>
	</c:if>
	<c:if test="${not empty errorMessage}">
		<script>swal("Lỗi!", "${errorMessage}", "error");</script>
	</c:if>

	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/jquery-3.2.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/main.js"></script>
</body>
</html>
