package Controller;

import Dao.Dao;
import Entity.User;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {

    private Dao dao;

    @Override
    public void init() { dao = new Dao(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, javax.servlet.ServletException {
        String token = req.getParameter("token");

        try {
            User user = dao.getUserByResetToken(token);

            if (user == null) {
                req.setAttribute("error",
                    "Link đặt lại mật khẩu không hợp lệ hoặc đã hết hạn. " +
                    "Vui lòng thực hiện lại.");
                req.getRequestDispatcher("forgot-password.jsp").forward(req, res);
                return;
            }

            req.setAttribute("token", token);
            req.getRequestDispatcher("reset-password.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("forgot-password.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, javax.servlet.ServletException {
        req.setCharacterEncoding("UTF-8");
        String token       = req.getParameter("token");
        String newPassword = req.getParameter("newPassword");
        String confirmPass = req.getParameter("confirmPassword");

        try {
            if (!newPassword.equals(confirmPass)) {
                req.setAttribute("token", token);
                req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
                req.getRequestDispatcher("reset-password.jsp").forward(req, res);
                return;
            }

            if (newPassword.length() < 6) {
                req.setAttribute("token", token);
                req.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự!");
                req.getRequestDispatcher("reset-password.jsp").forward(req, res);
                return;
            }

            User user = dao.getUserByResetToken(token);
            if (user == null) {
                req.setAttribute("error", "Link đã hết hạn. Vui lòng thực hiện lại.");
                req.getRequestDispatcher("forgot-password.jsp").forward(req, res);
                return;
            }

            dao.updatePasswordAndClearToken(user.getId(), newPassword);

            req.getSession().setAttribute("successMessage",
                "Đặt lại mật khẩu thành công! Vui lòng đăng nhập.");
            res.sendRedirect("login");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại!");
            req.getRequestDispatcher("reset-password.jsp").forward(req, res);
        }
    }
}