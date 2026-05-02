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
<title>Quản lý Báo cáo | Quản trị Admin</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/admin-assets/ADcss/main.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
<link rel="stylesheet"
	href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
.app-sidebar {
	overflow-y: auto !important;
}
</style>
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
			<li><a class="app-menu__item" href="AdminHome"><i
					class='app-menu__icon bx bx-tachometer'></i><span
					class="app-menu__label">Bảng điều khiển</span></a></li>

			<li><a class="app-menu__item" href="HTU"><i
					class='app-menu__icon bx bx-user-voice'></i><span
					class="app-menu__label">Quản lý khách hàng</span></a></li>
					<li><a class="app-menu__item " href="HTDM"><i
					class='app-menu__icon bx bx-id-card'></i> <span
					class="app-menu__label">Quản lý Danh mục</span></a></li>
			<li><a class="app-menu__item" href=HTSP><i
					class='app-menu__icon bx bx-purchase-tag-alt'></i><span
					class="app-menu__label">Quản lý sản phẩm</span></a></li>
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
			<li><a class="app-menu__item haha" href="BaocaoThongke"><i
					class='app-menu__icon bx bx-pie-chart-alt-2'></i><span
					class="app-menu__label">Thống kê</span></a></li>
			
		</ul>
	</aside>
	<main class="app-content">
		<div class="row">
			<div class="col-md-12">
				<div class="app-title">
					<ul class="app-breadcrumb breadcrumb">
						<li class="breadcrumb-item"><a href="#"><b>Báo cáo
									doanh thu </b></a></li>
					</ul>
					<div id="clock"></div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="tile">
					<h3 class="tile-title">DỮ LIỆU HÀNG THÁNG</h3>
					<div class="embed-responsive embed-responsive-16by9">
						<canvas class="embed-responsive-item" id="lineChartDemo"></canvas>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="tile">
					<h3 class="tile-title">THỐNG KÊ DOANH SỐ</h3>
					<div class="embed-responsive embed-responsive-16by9">
						<canvas class="embed-responsive-item" id="barChartDemo"></canvas>
					</div>
				</div>
			</div>
		</div>

		<form method="get" action="BaocaoThongke" style="margin-bottom: 20px;">
			<label>Chọn kiểu:</label> <select name="type" id="typeSelect">
				<option value="day">Ngày</option>
				<option value="month">Tháng</option>
				<option value="year">Năm</option>
			</select> <label>Chọn thời gian:</label> <input type="date" name="date"
				id="dateInput" style="width: 155px;">

			<button type="submit" class="btn btn-primary">Lọc</button>
		</form>
		<div class="row">
			<div class="col-md-12">
				<div class="tile">
					<div>
						<h3 class="tile-title">Thống kê doanh thu</h3>
					</div>
					<div class="tile-body">
						<table class="table table-hover table-bordered" id="sampleTable">
							<thead>
								<tr>
									<th style="width: 105px;">ID đơn hàng</th>
									<th>Khách hàng</th>
									<th style="width: 345px;">Đơn hàng</th>
									<th>Ngày đặt</th>
									<th style="width: 85px;">Số lượng</th>
									<th>Tổng tiền</th>
								</tr>
							</thead>
							<tbody>
								<c:set var="total" value="0" />
								<c:forEach var="order" items="${getallorder}">
									<c:set var="total" value="${total + order.totalAmount}"></c:set>
									<tr>
										<td class="text-center">${order.id}</td>
										<td>${order.lastName} ${order.firstName}</td>
										<td>${order.allProductNames}</td>
										<td><fmt:formatDate value="${order.createdAt}"
												pattern="HH:mm  dd/MM/yyyy" /></td>
										<td class="text-center">${order.quantity}</td>
										<td class="text-center"><fmt:formatNumber
												value="${order.totalAmount}" type="number" pattern="#,##0" />đ</td>

									</tr>
								</c:forEach>
								<tr style="background-color: #f8f9fa; font-weight: bold;">
									<td colspan="5" class="text-end" style="padding-right: 20px;">Tổng
										doanh thu:</td>

									<td class="text-center" style="color: #d9534f;"><fmt:formatNumber
											value="${total}" type="number" pattern="#,##0" />đ</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<form method="get" action="BaocaoThongke" style="margin-bottom: 20px;">
			<label>Chọn kiểu:</label> <select name="typeLN" id="typeSelectLN">
				<option value="day">Ngày</option>
				<option value="month">Tháng</option>
				<option value="year">Năm</option>
			</select> <label>Chọn thời gian:</label> <input type="date" name="dateLN"
				id="dateInputLN" style="width: 155px;">

			<button type="submit" class="btn btn-primary">Lọc</button>
		</form>
		<div class="row">
			<div class="col-md-12">
				<div class="tile">
					<div>
						<h3 class="tile-title">Thống kê lợi nhuận</h3>
					</div>
					<div class="tile-body">
						<table class="table table-hover table-bordered" id="sampleTable">
							<thead>
								<tr>
									<th style="width: 105px;">ID đơn hàng</th>
									<th>Khách hàng</th>
									<th style="width: 345px;">Đơn hàng</th>
									<th style="width: 85px;">Số lượng</th>
									<th>Tổng tiền</th>
									<th>Lợi nhuận</th>
								</tr>
							</thead>
							<tbody>
								<c:set var="Profit" value="0" />
								<c:forEach var="order2" items="${getallorder2}">
									<c:set var="Profit" value="${Profit + order2.totalProfit}"></c:set>
									<tr>
										<td class="text-center">${order2.id}</td>
										<td>${order2.lastName} ${order2.firstName}</td>
										<td>${order2.allProductNames}</td>

										<td class="text-center">${order2.quantity}</td>
										<td class="text-center"><fmt:formatNumber
												value="${order2.totalAmount}" type="number" pattern="#,##0" />đ</td>
										<td class="text-center"><fmt:formatNumber
												value="${order2.totalProfit}" type="number" pattern="#,##0" />đ</td>

									</tr>
								</c:forEach>
								<tr style="background-color: #f8f9fa; font-weight: bold;">
									<td colspan="5" class="text-end" style="padding-right: 20px;">Tổng
										lợi nhuận:</td>

									<td class="text-center" style="color: #d9534f;"><fmt:formatNumber
											value="${Profit}" type="number" pattern="#,##0" />đ</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

	</main>
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
	<script>
    var revenueData = [
        <c:forEach var="r" items="${revenueByMonth}" varStatus="loop">
            ${r}<c:if test="${!loop.last}">,</c:if>
        </c:forEach>
    ];
</script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script type="text/javascript">
	var data = {
		    labels: ["T1","T2","T3","T4","T5","T6","T7","T8","T9","T10","T11","T12"],
		    datasets: [{
		        label: "Doanh thu",
		        fillColor: "rgba(255,255,255,0.2)",
		        strokeColor: "black",
		        pointColor: "red",
		        data: revenueData
		    }]
		};

	var ctxl = document.getElementById("lineChartDemo").getContext("2d");

	new Chart(ctxl, {
	    type: 'line',
	    data: {
	        labels: ["T1","T2","T3","T4","T5","T6","T7","T8","T9","T10","T11","T12"],
	        datasets: [{
	            label: "Doanh thu",
	            data: revenueData,
	            borderColor: "blue",
	            backgroundColor: "rgba(0,0,255,0.1)",
	            fill: true
	        }]
	    }
	});
	var ctxb = document.getElementById("barChartDemo").getContext("2d");

	new Chart(ctxb, {
	    type: 'bar',
	    data: {
	        labels: ["T1","T2","T3","T4","T5","T6","T7","T8","T9","T10","T11","T12"],
	        datasets: [{
	            label: "Doanh số",
	            data: revenueData,
	            backgroundColor: "rgba(255, 99, 132, 0.5)",
	            borderColor: "red",
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});
	</script>
	<script>
	document.getElementById("typeSelect").addEventListener("change", function () {
	    const input = document.getElementById("dateInput");
	    const type = this.value;

	    input.value = "";

	    if (type === "day") {
	        input.type = "date";
	    } else if (type === "month") {
	        input.type = "month";
	        input.placeholder = "Chọn tháng (VD: tháng 3)";
	    } else if (type === "year") {
	        input.type = "number";
	        input.placeholder = "Nhập năm (VD: 2026)";
	        input.min = "2000";
	        input.max = "2100";
	        style="width: 155px;";
	    }
	});
	</script>
	<script>
	document.getElementById("typeSelectLN").addEventListener("change", function () {
	    const input = document.getElementById("dateInputLN");
	    const typeLN = this.value;

	    input.value = "";

	    if (typeLN === "day") {
	        input.type = "date";
	    } else if (typeLN === "month") {
	        input.type = "month";
	        input.placeholder = "Chọn tháng (VD: tháng 3)";
	    } else if (typeLN === "year") {
	        input.type = "number";
	        input.placeholder = "Nhập năm (VD: 2026)";
	        input.min = "2000";
	        input.max = "2100";
	    }
	});
	</script>
	<script type="text/javascript">
		if (document.location.hostname == 'pratikborsadiya.in') {
			(function(i, s, o, g, r, a, m) {
				i['GoogleAnalyticsObject'] = r;
				i[r] = i[r] || function() {
					(i[r].q = i[r].q || []).push(arguments)
				}, i[r].l = 1 * new Date();
				a = s.createElement(o), m = s.getElementsByTagName(o)[0];
				a.async = 1;
				a.src = g;
				m.parentNode.insertBefore(a, m)
			})(window, document, 'script',
					'//www.google-analytics.com/analytics.js', 'ga');
			ga('create', 'UA-72504830-1', 'auto');
			ga('send', 'pageview');
		}
	</script>
</body>

</html>