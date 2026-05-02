<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đặt lại mật khẩu – NÕN CAFÉ</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div class="container" style="max-width:480px;margin:80px auto">
    <h3 class="text-center mb-4">Đặt lại mật khẩu</h3>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form action="resetPassword" method="post">
        <input type="hidden" name="token" value="${token}">
        <div class="form-group">
            <label>Mật khẩu mới</label>
            <input type="password" name="newPassword" class="form-control"
                   placeholder="Tối thiểu 6 ký tự" required minlength="6">
        </div>
        <div class="form-group mt-2">
            <label>Xác nhận mật khẩu</label>
            <input type="password" name="confirmPassword" class="form-control"
                   placeholder="Nhập lại mật khẩu" required>
        </div>
        <button type="submit" class="btn btn-warning btn-block mt-3">
            Cập nhật mật khẩu
        </button>
    </form>
</div>
</body>
</html>