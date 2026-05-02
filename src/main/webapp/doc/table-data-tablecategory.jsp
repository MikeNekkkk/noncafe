<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<link rel="icon" href="img/blog/da_logo9.jpg" type="image/x-icon" />
<title>Danh sách danh mục sản phẩm | Quản trị Admin</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/admin-assets/ADcss/main.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
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
					class='bx bx-log-out bx-rotate-180'></i></a></li>
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
			<li><a class="app-menu__item " href="HTU"><i
					class='app-menu__icon bx bx-user-voice'></i><span
					class="app-menu__label">Quản lý khách hàng</span></a></li>
			<li><a class="app-menu__item haha" href="HTDM"><i
					class='app-menu__icon bx bx-id-card'></i> <span
					class="app-menu__label">Quản lý Danh mục</span></a></li>
			<li><a class="app-menu__item" href="HTSP"><i
					class='app-menu__icon bx bx-purchase-tag-alt'></i><span
					class="app-menu__label">Quản lý sản phẩm</span></a></li>
			<li><a class="app-menu__item" href="listOrders"><i
					class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Quản
						lý đơn hàng</span></a></li>
			<li><a class="app-menu__item" href="HTNCC"><i
					class='app-menu__icon bx bx-purchase-tag-alt'></i><span
					class="app-menu__label">Quản lý NCC</span></a></li>
			<li><a class="app-menu__item" href="stock-management"> <i
					class="fa-solid fa-cart-flatbed "
					style="color:; margin-right: 15px;"></i><span
					class="app-menu__label">Quản lý Nhập Xuất Kho</span>
			</a></li>
			<li><a class="app-menu__item" href="blog"><i
					class='app-menu__icon bx bx-run'></i><span class="app-menu__label">Quản
						lý Bài viết</span></a></li>
			<li><a class="app-menu__item" href="listContacts"><i
					class='app-menu__icon bx bx-envelope'></i><span
					class="app-menu__label">Quản lý liên hệ</span></a></li>
			<li><a class="app-menu__item" href="BaocaoThongke"><i
					class='app-menu__icon bx bx-pie-chart-alt-2'></i><span
					class="app-menu__label">Thống kê</span></a></li>
		</ul>
	</aside>

	<main class="app-content">
		<div class="app-title">
			<ul class="app-breadcrumb breadcrumb side">
				<li class="breadcrumb-item active"><a href="#"><b>Danh
							mục sản phẩm</b></a></li>
			</ul>
			<div id="clock"></div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<div class="tile">
					<div class="tile-body">
						<div class="row element-button">
							<div class="col-sm-2">
								<a class="btn btn-add btn-sm" href="javascript:void(0)"
									onclick="openAddUserModal()" title="Thêm"> <i
									class="fas fa-plus"></i> Thêm mới Danh mục sản phẩm
								</a>
							</div>
						
						</div>

						<table class="table table-hover table-bordered js-copytextarea"
							cellpadding="0" cellspacing="0" border="0" id="sampleTable"
							Style="max-width:70%;margin:auto;">
							<thead>
								<tr>
									<th width="25">ID</th>
									<th width="550px">Tên danh mục</th>
									<th width="197px">Trạng thái</th>
									<th width="100">Tính năng</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="category" items="${listP}">
									<tr>

										<td>${category.id}</td>
										<td>${category.name}</td>
										<td>${category.status}</td>
										<td class="table-td-center">
											<button class="btn btn-primary btn-sm trash" type="button"
												title="Xóa" onclick="deletecategory(${category.id})">
												<i class="fas fa-trash-alt"></i>
											</button>
											<button class="btn btn-primary btn-sm edit" type="button"
												title="Sửa" onclick="editcategory(this)"
												data-id="${category.id}" data-username="${category.name}"
												data-status="${category.status}">

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

	<div class="modal fade" id="ModalAddUser" tabindex="-1" role="dialog"
		aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<div class="row">
						<div class="form-group col-md-12">
							<span class="thong-tin-thanh-toan"><h5>Thêm Danh mục
									sản phẩm</h5></span>
						</div>
					</div>
					<form id="addUserForm">
						<div class="row">
							<div class="form-group col-md-6">
								<label class="control-label">Tên danh mục <span
									style="color: red">*</span></label> <input class="form-control"
									type="text" name="username" required>
							</div>

						</div>
					</form>
					<BR>
					<button class="btn btn-save" type="button" onclick="submitAdd()">Lưu
						lại</button>
					<a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
					<BR>
				</div>
			</div>
		</div>
	</div>

	<!-- MODAL SỬA USER -->
	<div class="modal fade" id="ModalEditUser" tabindex="-1" role="dialog"
		aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<div class="row">
						<div class="form-group col-md-12">
							<span class="thong-tin-thanh-toan"><h5>Chỉnh sửa
									thông tin khách hàng</h5></span>
						</div>
					</div>
					<form id="editUserForm">
						<input type="hidden" name="userId" id="editUserId">
						<div class="row">
							<div class="form-group col-md-6">
								<label class="control-label">ID</label> <input
									class="form-control" type="text" id="editUserIdDisplay"
									disabled>
							</div>
							<div class="form-group col-md-6">
								<label for="orderStatus"> Trạng thái <span
									class="text-danger">*</span></label> <select class="form-control"
									id="KHStatus" name="status" required>
									<option value="ACTIVE">Còn hoạt động</option>
									<option value="INACTIVE">Ngưng hoạt động</option>
								</select>
							</div>
							<div class="form-group col-md-12">
								<label class="control-label">Tên Danh mục sản phẩm <span
									style="color: red">*</span></label> <input class="form-control"
									type="text" name="username" id="editUsername" required>
							</div>


						</div>
					</form>
					<BR>
					<button class="btn btn-save" type="button"
						onclick="submitEditcategory()">Lưu lại</button>
					<a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
					<BR>
				</div>
			</div>
		</div>
	</div>

	<!-- Scripts -->
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/jquery-3.2.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/main.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/plugins/pace.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/plugins/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin-assets/ADjs/plugins/dataTables.bootstrap.min.js"></script>

	<script>
    $('#sampleTable').DataTable();

    $('#all').click(function (e) {
      $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
      e.stopImmediatePropagation();
    });

    function openAddUserModal() {
      $('#addUserForm')[0].reset();
      $('#ModalAddUser').modal('show');
    }

    function submitAdd() {
      var formData = $('#addUserForm').serialize();
      
      $.ajax({
        url: 'addcategory',
        type: 'POST',
        data: formData,
        dataType: 'json',
        success: function(response) {
          if (response.success) {
            swal("Thành công!", response.message, "success").then(() => {
              location.reload();
            });
          } else {
            swal("Lỗi!", response.message, "error");
          }
        },
        error: function() {
          swal("Lỗi!", "Không thể kết nối đến server!", "error");
        }
      });
    }

    function editcategory(btn) {
        const id = $(btn).data('id');

        $('#editUserId').val(id);
        $('#editUserIdDisplay').val(id);
    
        $('#KHStatus').val($(btn).data('status'));

        $('#editUsername').val($(btn).data('username'));
        $('#ModalEditUser').modal('show');
    }


    function submitEditcategory() {
      var formData = $('#editUserForm').serialize();
      
      $.ajax({
        url: 'updatecategory',
        type: 'POST',
        data: formData,
        dataType: 'json',
        success: function(response) {
          if (response.success) {
            swal("Thành công!", response.message, "success").then(() => {
              location.reload();
            });
          } else {
            swal("Lỗi!", response.message, "error");
          }
        },
        error: function() {
          swal("Lỗi!", "Không thể kết nối đến server!", "error");
        }
      });
    }

    function deletecategory(userId) {
      swal({
        title: "Cảnh báo",
        text: "Bạn có chắc chắn muốn xóa danh mục này?",
        icon: "warning",
        buttons: ["Hủy bỏ", "Đồng ý"],
        dangerMode: true,
      }).then((willDelete) => {
        if (willDelete) {
          $.ajax({
            url: 'deletecategory?id=' + userId,
            type: 'GET',
            dataType: 'json',
            success: function(response) {
              if (response.success) {
                swal("Thành công!", response.message, "success").then(() => {
                  location.reload();
                });
              } else {
                swal("Lỗi!", response.message, "error");
              }
            },
            error: function() {
              swal("Lỗi!", "Không thể xóa danh mục này!", "error");
            }
          });
        }
      });
    }

  
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
      nowTime = h + " giờ " + m + " phút " + s + " giây";
      if (dd < 10) dd = '0' + dd;
      if (mm < 10) mm = '0' + mm;
      today = day + ', ' + dd + '/' + mm + '/' + yyyy;
      document.getElementById("clock").innerHTML = '<span class="date"> ' + today + ' - ' + nowTime + '</span>';
      setTimeout("time()", "1000");
    }

    function checkTime(i) {
      if (i < 10) i = "0" + i;
      return i;
    }
    </script>
</body>
</html>