package Controller;

import Dao.Dao;
import Service.EmailService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet("/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {

    private Dao dao;

    @Override
    public void init() { dao = new Dao(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, javax.servlet.ServletException {
        req.getRequestDispatcher("forgot-password.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, javax.servlet.ServletException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");

        try {
            if (!dao.isEmailExists(email)) {
                req.setAttribute("error", "Email này chưa được đăng ký!");
                req.getRequestDispatcher("forgot-password.jsp").forward(req, res);
                return;
            }

            String token = UUID.randomUUID().toString().replace("-", "");

            Timestamp expiry = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);

            dao.saveResetToken(email, token, expiry);
            String baseUrl = req.getScheme() + "://" + req.getServerName()
                           + ":" + req.getServerPort()
                           + req.getContextPath();
            String resetLink = baseUrl + "/resetPassword?token=" + token;

            String fullName = dao.getFullNameByEmail(email);

            final String finalEmail = email;
            final String finalName  = fullName;
            final String finalLink  = resetLink;
            new Thread(() ->
                EmailService.sendResetPasswordEmail(finalEmail, finalName, finalLink)
            ).start();

            req.setAttribute("success",
                "Chúng tôi đã gửi link đặt lại mật khẩu đến " + email +
                ". Vui lòng kiểm tra hộp thư (kể cả thư mục Spam).");
            req.getRequestDispatcher("forgot-password.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại!");
            req.getRequestDispatcher("forgot-password.jsp").forward(req, res);
        }
    }
}