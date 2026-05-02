<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quên mật khẩu – NÕN CAFÉ</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="icon" href="img/blog/da_logo9.jpg" type="image/x-icon" />
    
</head>
<body>
<div class="container" style="max-width:480px;margin:80px auto">
    <h3 class="text-center mb-4">Quên mật khẩu</h3>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <c:if test="${empty success}">
        <form action="forgotPassword" method="post">
            <div class="form-group">
                <label>Email đã đăng ký</label>
                <input type="email" name="email" class="form-control"
                       placeholder="Nhập email của bạn" required>
            </div>
            <button type="submit" class="btn btn-warning btn-block mt-3">
                Gửi link đặt lại mật khẩu
            </button>
        </form>
    </c:if>

    <div class="text-center mt-3">
        <a href="login">← Quay lại đăng nhập</a>
    </div>
</div>
</body>
</html>