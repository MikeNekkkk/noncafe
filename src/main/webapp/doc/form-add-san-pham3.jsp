<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Thêm sản phẩm | Non Café Admin</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
	rel="stylesheet">
<style>
:root {
	--coffee-dark: #2C1810;
	--coffee-medium: #6B3A2A;
	--coffee-light: #C8956C;
	--cream: #FFF8F0;
	--success-green: #28a745;
	--ai-blue: #0d6efd;
}

body {
	background: var(--cream);
	font-family: 'Segoe UI', sans-serif;
}

.page-header {
	background: linear-gradient(135deg, var(--coffee-dark),
		var(--coffee-medium));
	color: white;
	padding: 20px 30px;
	border-radius: 12px;
	margin-bottom: 24px;
}

.section-card {
	background: white;
	border-radius: 12px;
	padding: 24px;
	margin-bottom: 20px;
	box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
	border-left: 4px solid var(--coffee-light);
}

.section-card.factual-section {
	border-left-color: var(--coffee-medium);
}

.section-card.ai-section {
	border-left-color: var(--ai-blue);
	display: none; /* Ẩn ban đầu */
}

.section-title {
	font-size: 16px;
	font-weight: 700;
	color: var(--coffee-dark);
	margin-bottom: 16px;
	display: flex;
	align-items: center;
	gap: 8px;
}

/* Flavor Tags */
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

.flavor-tag-chip:hover {
	border-color: var(--coffee-light);
	background: #fff3e0;
}

.flavor-tag-chip.selected {
	border-color: var(--coffee-medium);
	background: var(--coffee-medium);
	color: white;
}

/* Sliders */
.slider-group {
	margin-bottom: 16px;
}

.slider-label {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 6px;
}

.slider-label span.label-text {
	font-weight: 600;
	color: var(--coffee-dark);
	font-size: 14px;
}

.slider-value-badge {
	background: var(--coffee-medium);
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
	background: var(--coffee-light);
}

input[type="range"] {
	width: 100%;
	accent-color: var(--coffee-medium);
}

/* AI Button */
.btn-ai {
	background: linear-gradient(135deg, #667eea, #764ba2);
	color: white;
	border: none;
	border-radius: 8px;
	padding: 10px 24px;
	font-weight: 600;
	cursor: pointer;
	transition: all 0.3s ease;
	display: flex;
	align-items: center;
	gap: 8px;
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

/* AI Loading */
.ai-loading {
	display: none;
	text-align: center;
	padding: 30px;
	color: #667eea;
}

.ai-loading .spinner-border {
	width: 40px;
	height: 40px;
}

/* AI Badge */
.ai-badge {
	background: linear-gradient(135deg, #667eea, #764ba2);
	color: white;
	font-size: 11px;
	padding: 2px 8px;
	border-radius: 10px;
	font-weight: 600;
}

.admin-correction-indicator {
	display: none;
	font-size: 12px;
	color: #fd7e14;
	font-weight: 600;
}

/* Accuracy Preview */
.accuracy-preview {
	background: #f8f9fa;
	border-radius: 8px;
	padding: 12px;
	margin-top: 16px;
	font-size: 13px;
}

/* Radar Chart Container */
.profile-radar {
	display: flex;
	justify-content: center;
	margin: 12px 0;
}

/* Brew Methods */
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

/* Alert */
.alert-ai-info {
	background: linear-gradient(135deg, #f3f0ff, #e8f4fd);
	border: 1px solid #c8b8f8;
	border-radius: 10px;
	padding: 14px 18px;
	font-size: 13px;
	color: #4a3f6b;
	margin-bottom: 16px;
}
</style>
</head>
<body>
	<div class="container-fluid py-4"
		style="max-width: 900px; margin: 0 auto;">

		<!-- Header -->
		<div class="page-header">
			<h4 class="mb-1">
				<i class="fas fa-plus-circle me-2"></i>Thêm sản phẩm mới
			</h4>
			<p class="mb-0 opacity-75">Nhập thông số kỹ thuật → AI phân tích
				hồ sơ cảm quan → Kiểm duyệt → Lưu</p>
		</div>

		<!-- Alert -->
		<c:if test="${not empty successMessage}">
			<div class="alert alert-success alert-dismissible fade show">
				<i class="fas fa-check-circle me-2"></i>${successMessage}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger alert-dismissible fade show">
				<i class="fas fa-exclamation-circle me-2"></i>${errorMessage}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>

		<form action="addProduct" method="post" enctype="multipart/form-data"
			id="productForm">
			<div class="section-card">
				<div class="section-title">
					<i class="fas fa-box-open" style="color: var(--coffee-light)"></i>
					Thông tin cơ bản
				</div>

				<div class="row g-3">
					<div class="col-md-8">
						<label class="form-label fw-semibold">Tên sản phẩm <span
							class="text-danger">*</span></label> <input type="text"
							name="productName" id="productName" class="form-control"
							placeholder="Ví dụ: G7 Gold Motherland - Hộp 14 sticks" required>
					</div>
					<div class="col-md-4">
						<label class="form-label fw-semibold">Danh mục <span
							class="text-danger">*</span></label> <select name="categoryId"
							class="form-select" required>
							<option value="">-- Chọn danh mục --</option>
							<c:forEach var="cat" items="${listC}">
								<option value="${cat.id}">${cat.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-4">
						<label class="form-label fw-semibold">Giá (VNĐ) <span
							class="text-danger">*</span></label> <input type="number" name="price"
							class="form-control" min="1000" placeholder="78000" required>
					</div>
					<div class="col-md-4">
						<label class="form-label fw-semibold">Số lượng tồn kho <span
							class="text-danger">*</span></label> <input type="number"
							name="stockQuantity" class="form-control" min="0"
							placeholder="500" required>
					</div>
					<div class="col-md-4">
						<label class="form-label fw-semibold">Ảnh sản phẩm</label> <input
							type="file" name="productImage" class="form-control"
							accept="image/*" onchange="previewImage(this)">
					</div>
					<div class="col-12" id="imagePreviewBox" style="display: none">
						<img id="imagePreview" src="" alt="Preview"
							style="max-height: 140px; border-radius: 8px; border: 1px solid #dee2e6">
					</div>
					<div class="col-12">
						<label class="form-label fw-semibold">Mô tả sản phẩm</label>
						<textarea name="detailDescription" class="form-control" rows="3"
							placeholder="Mô tả ngắn về sản phẩm..."></textarea>
					</div>
				</div>
			</div>

			<!-- ═══════════════════════════════════════════════════════════════
             PHẦN 2: THÔNG SỐ KỸ THUẬT (FACTUAL - Admin nhập)
        ════════════════════════════════════════════════════════════════ -->
			<div class="section-card factual-section">
				<div class="section-title">
					<i class="fas fa-flask" style="color: var(--coffee-medium)"></i>
					Thông số kỹ thuật <small class="text-muted fw-normal ms-2">(Sự
						thật khách quan — Admin nhập)</small>
				</div>

				<div class="alert-ai-info">
					<i class="fas fa-info-circle me-2"></i> Đây là các thông số <strong>không
						thể suy luận</strong> từ tên sản phẩm. Hãy điền đúng theo thực tế sản phẩm.
					AI sẽ dùng chính xác 4 thông số này để phân tích.
				</div>

				<div class="row g-3">
					<!-- Processing Method -->
					<div class="col-md-4">
						<label class="form-label fw-semibold"> <i
							class="fas fa-water me-1 text-primary"></i>Phương pháp sơ chế
						</label> <select name="processing_method" id="processing_method"
							class="form-select" onchange="onFactualChange()">
							<option value="">-- Chưa xác định --</option>
							<option value="WASHED">Chế biến ướt (Washed) — Vị sạch,
								chua thanh</option>
							<option value="NATURAL">Chế biến khô (Natural) — Ngọt,
								Body dày</option>
							<option value="HONEY">Mật ong (Honey) — Cân bằng, ngọt
								vừa</option>
						</select>
						<div class="form-text" id="processingHint"></div>
					</div>

					<!-- Roast Level -->
					<div class="col-md-4">
						<label class="form-label fw-semibold"> <i
							class="fas fa-fire me-1 text-danger"></i>Mức độ rang
						</label> <select name="roast_level" id="roast_level" class="form-select"
							onchange="onFactualChange()">
							<option value="">-- Chưa xác định --</option>
							<option value="LIGHT">Rang nhạt (Light) — Hương hoa quả,
								chua</option>
							<option value="MEDIUM">Rang vừa (Medium) — Cân bằng,
								caramel</option>
							<option value="DARK">Rang đậm (Dark) — Đắng, khói, body
								dày</option>
						</select>
						<div class="form-text" id="roastHint"></div>
					</div>

					<!-- Grind Size -->
					<div class="col-md-4">
						<label class="form-label fw-semibold"> <i
							class="fas fa-cog me-1 text-secondary"></i>Mức độ xay
						</label> <select name="grind_size" id="grind_size" class="form-select"
							onchange="onFactualChange()">
							<option value="">-- Chưa xác định --</option>
							<option value="WHOLE_BEAN">Nguyên hạt — Xay trước khi
								pha</option>
							<option value="COARSE">Xay thô — French Press, Cold Brew</option>
							<option value="MEDIUM">Xay vừa — Pour Over, Phin, Drip</option>
							<option value="FINE">Xay mịn — Espresso, Phin nhỏ, Moka</option>
						</select>
					</div>

					<!-- Flavor Tags -->
					<div class="col-12">
						<label class="form-label fw-semibold"> <i
							class="fas fa-tags me-1 text-success"></i>Ghi chú hương vị <small
							class="text-muted fw-normal">(Bấm để chọn nhiều tag)</small>
						</label>
						<div id="flavorTagsContainer">
							<%
							String[] tags = {"Chocolate", "Caramel", "Vanilla", "Nutty", "Hazelnut", "Citrus", "Berry", "Tropical Fruit",
									"Stone Fruit", "Floral", "Jasmine", "Rose", "Honey", "Brown Sugar", "Molasses", "Smoky", "Earthy", "Woody",
									"Spicy", "Ginger", "Butter", "Cream", "Wine-like", "Fermented"};
							for (String tag : tags) {
							%>
							<span class="flavor-tag-chip" data-value="<%=tag%>"
								onclick="toggleFlavorTag(this)"><%=tag%></span>
							<%
							}
							%>
						</div>
						<input type="hidden" name="flavor_tags" id="flavor_tags" value="">
						<div class="form-text" id="selectedTagsInfo">Chưa chọn tag
							nào</div>
					</div>
				</div>

				<!-- Nút phân tích AI -->
				<div class="mt-4 d-flex align-items-center gap-3">
					<button type="button" class="btn-ai" id="btnAnalyzeAI"
						onclick="callAIPreview()">
						<i class="fas fa-robot"></i> <span id="btnAIText">Phân tích
							AI</span>
					</button>
					<span class="text-muted small"> AI sẽ dự đoán hồ sơ cảm quan
						dựa trên 4 thông số trên </span>
				</div>
			</div>

			<!-- ═══════════════════════════════════════════════════════════════
             PHẦN 3: AI PREVIEW + KIỂM DUYỆT (Admin chỉnh nếu sai)
        ════════════════════════════════════════════════════════════════ -->
			<div class="section-card ai-section" id="aiPreviewSection">

				<!-- Loading state -->
				<div class="ai-loading" id="aiLoadingState">
					<div class="spinner-border" role="status" style="color: #667eea"></div>
					<p class="mt-3 fw-semibold" style="color: #667eea">AI đang phân
						tích theo nguyên lý Q-Grader...</p>
				</div>

				<!-- Content state -->
				<div id="aiPreviewContent" style="display: none">
					<div class="section-title">
						<i class="fas fa-robot" style="color: var(--ai-blue)"></i> Hồ sơ
						cảm quan <span class="ai-badge ms-1">AI dự đoán</span> <small
							class="text-muted fw-normal ms-2">— Kéo slider để chỉnh
							nếu AI đoán sai</small>
					</div>

					<!-- 4 sliders -->
					<div class="row g-3">
						<div class="col-md-6">
							<div class="slider-group">
								<div class="slider-label">
									<span class="label-text">☕ Độ đậm đà (Body)</span> <span
										class="slider-value-badge" id="bodyVal">3</span>
								</div>
								<input type="range" name="body" id="bodySlider" min="1" max="5"
									value="3" oninput="updateSlider('body', this.value)">
								<div class="slider-stars" id="bodyStars"></div>
								<span class="admin-correction-indicator" id="bodyCorrection">
									✏️ Đã chỉnh từ AI </span>
							</div>
						</div>

						<div class="col-md-6">
							<div class="slider-group">
								<div class="slider-label">
									<span class="label-text">🍋 Độ chua (Acidity)</span> <span
										class="slider-value-badge" id="acidityVal">3</span>
								</div>
								<input type="range" name="acidity" id="aciditySlider" min="1"
									max="5" value="3" oninput="updateSlider('acidity', this.value)">
								<div class="slider-stars" id="acidityStars"></div>
								<span class="admin-correction-indicator" id="acidityCorrection">
									✏️ Đã chỉnh từ AI </span>
							</div>
						</div>

						<div class="col-md-6">
							<div class="slider-group">
								<div class="slider-label">
									<span class="label-text">🫘 Độ đắng (Bitterness)</span> <span
										class="slider-value-badge" id="bitternessVal">3</span>
								</div>
								<input type="range" name="bitterness" id="bitternessSlider"
									min="1" max="5" value="3"
									oninput="updateSlider('bitterness', this.value)">
								<div class="slider-stars" id="bitternessStars"></div>
								<span class="admin-correction-indicator"
									id="bitternessCorrection"> ✏️ Đã chỉnh từ AI </span>
							</div>
						</div>

						<div class="col-md-6">
							<div class="slider-group">
								<div class="slider-label">
									<span class="label-text">🍯 Độ ngọt (Sweetness)</span> <span
										class="slider-value-badge" id="sweetnessVal">3</span>
								</div>
								<input type="range" name="sweetness" id="sweetnessSlider"
									min="1" max="5" value="3"
									oninput="updateSlider('sweetness', this.value)">
								<div class="slider-stars" id="sweetnessStars"></div>
								<span class="admin-correction-indicator"
									id="sweetnessCorrection"> ✏️ Đã chỉnh từ AI </span>
							</div>
						</div>
					</div>

					<!-- Brew Methods -->
					<div class="mt-3">
						<label class="form-label fw-semibold"> <i
							class="fas fa-mug-hot me-1" style="color: var(--coffee-light)"></i>
							Phương pháp pha gợi ý <span class="ai-badge ms-1">AI</span>
						</label>
						<div id="brewMethodsDisplay" class="mb-2"></div>
						<input type="text" name="brew_methods" id="brewMethodsInput"
							class="form-control"
							placeholder="Ví dụ: Pour Over, Phin, Cold Brew">
						<div class="form-text">AI đề xuất dựa theo độ xay. Có thể
							sửa.</div>
					</div>

					<!-- Description Text -->
					<div class="mt-3">
						<label class="form-label fw-semibold"> <i
							class="fas fa-comment-dots me-1"
							style="color: var(--coffee-light)"></i> Đoạn văn tư vấn chatbot <span
							class="ai-badge ms-1">AI viết</span>
						</label>
						<textarea name="description_text" id="descriptionTextArea"
							class="form-control" rows="3"
							placeholder="AI sẽ viết đoạn văn này..."></textarea>
						<div class="form-text">Chatbot dùng đoạn này để tư vấn khách
							hàng. Có thể chỉnh sửa.</div>
					</div>

					<!-- Accuracy preview -->
					<div class="accuracy-preview" id="accuracyPreview">
						<i class="fas fa-chart-bar me-2 text-primary"></i> <strong>Training
							Log:</strong> Khi lưu, hệ thống sẽ ghi lại <span id="correctionCount"
							class="text-warning fw-bold">0</span> điều chỉnh so với dự đoán
						ban đầu của AI để cải thiện độ chính xác về sau.
					</div>
				</div>
			</div>

			<input type="hidden" name="ai_prediction_json" id="aiPredictionJson"
				value="">
			<div class="d-flex gap-3 justify-content-end">
				<a href="HTSP" class="btn btn-outline-secondary"> <i
					class="fas fa-arrow-left me-1"></i>Quay lại
				</a>
				<button type="submit" class="btn btn-success px-4" id="btnSubmit">
					<i class="fas fa-save me-2"></i>Lưu sản phẩm
				</button>
			</div>

		</form>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>

// ─────────────────────────────────────────────────────────────────────────────
// State
// ─────────────────────────────────────────────────────────────────────────────
let aiPredictionValues = {}; // Lưu giá trị AI đoán ban đầu

// ─────────────────────────────────────────────────────────────────────────────
// Flavor Tags
// ─────────────────────────────────────────────────────────────────────────────
function toggleFlavorTag(el) {
    el.classList.toggle('selected');
    updateFlavorTagsInput();
}

function updateFlavorTagsInput() {
    const selected = [...document.querySelectorAll('.flavor-tag-chip.selected')]
                        .map(t => t.dataset.value);
    document.getElementById('flavor_tags').value = selected.join(',');
    document.getElementById('selectedTagsInfo').textContent =
        selected.length > 0 ? `Đã chọn: ${selected.join(', ')}` : 'Chưa chọn tag nào';
}

// ─────────────────────────────────────────────────────────────────────────────
// Image Preview
// ─────────────────────────────────────────────────────────────────────────────
function previewImage(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = e => {
            document.getElementById('imagePreview').src = e.target.result;
            document.getElementById('imagePreviewBox').style.display = 'block';
        };
        reader.readAsDataURL(input.files[0]);
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Factual hints
// ─────────────────────────────────────────────────────────────────────────────
function onFactualChange() {
    const hints = {
        processing: {
            'WASHED':  '→ Acidity cao, Body thấp, hương sạch thanh',
            'NATURAL': '→ Body dày, Sweetness cao, hương trái cây đậm',
            'HONEY':   '→ Cân bằng, ngọt vừa, Body trung bình'
        },
        roast: {
            'LIGHT':  '→ Acidity cao, Bitterness thấp, hương hoa quả',
            'MEDIUM': '→ Cân bằng, hương caramel nhẹ',
            'DARK':   '→ Bitterness cao, Body dày, hương khói'
        }
    };
    const proc  = document.getElementById('processing_method').value;
    const roast = document.getElementById('roast_level').value;
    document.getElementById('processingHint').textContent = hints.processing[proc] || '';
    document.getElementById('roastHint').textContent      = hints.roast[roast]     || '';
}

// ─────────────────────────────────────────────────────────────────────────────
// Gọi AI Preview (AJAX)
// ─────────────────────────────────────────────────────────────────────────────
function callAIPreview() {
    const processing = document.getElementById('processing_method').value;
    const roast      = document.getElementById('roast_level').value;
    const grind      = document.getElementById('grind_size').value;
    const flavors    = document.getElementById('flavor_tags').value;
    const name       = document.getElementById('productName').value;

    if (!processing || !roast || !grind) {
        alert('Vui lòng chọn đủ 3 thông số: Sơ chế, Rang, Xay trước khi phân tích.');
        return;
    }

    // Hiện section AI + loading
    const aiSection = document.getElementById('aiPreviewSection');
    aiSection.style.display = 'block';
    document.getElementById('aiLoadingState').style.display = 'block';
    document.getElementById('aiPreviewContent').style.display = 'none';

    // Disable button
    const btn = document.getElementById('btnAnalyzeAI');
    btn.disabled = true;
    document.getElementById('btnAIText').textContent = 'Đang phân tích...';

    // Scroll đến section AI
    aiSection.scrollIntoView({ behavior: 'smooth', block: 'start' });

    const params = new URLSearchParams({
        processing_method: processing,
        roast_level:       roast,
        grind_size:        grind,
        flavor_tags:       flavors,
        product_name:      name
    });

    fetch('ai-preview-product', {
        method:  'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body:    params.toString()
    })
    .then(r => r.json())
    .then(data => {
        if (data.error) throw new Error(data.error);
        displayAIResult(data);
    })
    .catch(err => {
        console.error('AI Error:', err);
        alert('AI tạm thời không khả dụng: ' + err.message + '\nVui lòng tự điền thủ công.');
        document.getElementById('aiLoadingState').style.display = 'none';
        document.getElementById('aiPreviewContent').style.display = 'block';
        // Hiện sliders trống để admin tự điền
        ['body','acidity','bitterness','sweetness'].forEach(k => updateSlider(k, 3));
    })
    .finally(() => {
        btn.disabled = false;
        document.getElementById('btnAIText').textContent = 'Phân tích lại';
    });
}

function displayAIResult(data) {
    // Lưu prediction ban đầu
    aiPredictionValues = {
        body:       data.body       || 3,
        acidity:    data.acidity    || 3,
        bitterness: data.bitterness || 3,
        sweetness:  data.sweetness  || 3
    };

    // Lưu vào hidden field
    document.getElementById('aiPredictionJson').value = JSON.stringify(aiPredictionValues);

    // Set sliders
    setSlider('body',       aiPredictionValues.body);
    setSlider('acidity',    aiPredictionValues.acidity);
    setSlider('bitterness', aiPredictionValues.bitterness);
    setSlider('sweetness',  aiPredictionValues.sweetness);

    // Brew methods
    if (data.brew_methods && data.brew_methods.length > 0) {
        const methods = Array.isArray(data.brew_methods) ? data.brew_methods : [data.brew_methods];
        document.getElementById('brewMethodsDisplay').innerHTML =
            methods.map(m => `<span class="brew-method-tag">${m}</span>`).join('');
        document.getElementById('brewMethodsInput').value = methods.join(', ');
    }

    // Description
    if (data.description_text) {
        document.getElementById('descriptionTextArea').value = data.description_text;
    }

    // Reset correction indicators
    ['body','acidity','bitterness','sweetness'].forEach(k => {
        document.getElementById(k + 'Correction').style.display = 'none';
    });
    updateCorrectionCount();

    // Hiện content
    document.getElementById('aiLoadingState').style.display  = 'none';
    document.getElementById('aiPreviewContent').style.display = 'block';
}

// ─────────────────────────────────────────────────────────────────────────────
// Slider management
// ─────────────────────────────────────────────────────────────────────────────
function setSlider(key, value) {
    document.getElementById(key + 'Slider').value = value;
    updateSlider(key, value);
}

function updateSlider(key, value) {
    value = parseInt(value);
    document.getElementById(key + 'Val').textContent = value;
    updateStars(key, value);

    // Hiện "đã chỉnh" nếu khác AI
    if (aiPredictionValues[key] !== undefined && aiPredictionValues[key] !== value) {
        document.getElementById(key + 'Correction').style.display = 'inline';
    } else {
        document.getElementById(key + 'Correction').style.display = 'none';
    }

    updateCorrectionCount();
}

function updateStars(key, value) {
    const container = document.getElementById(key + 'Stars');
    if (!container) return;
    container.innerHTML = '';
    for (let i = 1; i <= 5; i++) {
        const star = document.createElement('div');
        star.className = 'star' + (i <= value ? ' filled' : '');
        container.appendChild(star);
    }
}

function updateCorrectionCount() {
    let count = 0;
    ['body','acidity','bitterness','sweetness'].forEach(k => {
        const slider = document.getElementById(k + 'Slider');
        if (slider && aiPredictionValues[k] !== undefined
                && parseInt(slider.value) !== aiPredictionValues[k]) {
            count++;
        }
    });
    const el = document.getElementById('correctionCount');
    if (el) el.textContent = count;
}

// ─────────────────────────────────────────────────────────────────────────────
// Init stars on page load
// ─────────────────────────────────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
    ['body','acidity','bitterness','sweetness'].forEach(k => updateStars(k, 3));
});
</script>
</body>
</html>
