package Controller;

import Dao.Dao;
import Entity.User;
import Service.GoogleOAuthConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

@WebServlet("/auth/google/callback")   
public class GoogleCallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Dao dao;

    @Override
    public void init() throws ServletException {
        dao = new Dao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String code  = req.getParameter("code");
        String state = req.getParameter("state");
        String error = req.getParameter("error");

        HttpSession session = req.getSession();

        if (error != null) {
            res.sendRedirect(req.getContextPath() + "/login?error=cancelled");
            return;
        }

        String savedState = (String) session.getAttribute("oauth_state");
        if (savedState == null || !savedState.equals(state)) {
            res.sendRedirect(req.getContextPath() + "/login?error=invalid_state");
            return;
        }
        session.removeAttribute("oauth_state");

        try {
            String accessToken = exchangeCodeForToken(code);
            if (accessToken == null) {
                req.setAttribute("errorMessage", "Không thể xác thực với Google. Vui lòng thử lại!");
                req.getRequestDispatcher("/login.jsp").forward(req, res);
                return;
            }

            JsonObject userInfo = getUserInfo(accessToken);
            if (userInfo == null || !userInfo.has("email")) {
                req.setAttribute("errorMessage", "Không lấy được thông tin từ Google!");
                req.getRequestDispatcher("/login.jsp").forward(req, res);
                return;
            }

            String googleId = userInfo.get("sub").getAsString();
            String email    = userInfo.has("email")   ? userInfo.get("email").getAsString()   : null;
            String fullName = userInfo.has("name")    ? userInfo.get("name").getAsString()    : "Người dùng";
            String avatar   = userInfo.has("picture") ? userInfo.get("picture").getAsString() : null;

            User user = dao.getUserByGoogleId(googleId);

            if (user == null) {
                User existingUser = dao.getUserByEmail(email);
                if (existingUser != null) {
                    dao.linkGoogleId(existingUser.getId(), googleId, avatar);
                    existingUser.setGoogleId(googleId);
                    existingUser.setAvatar(avatar);
                    user = existingUser;
                } else {
                    user = dao.createUserFromGoogle(googleId, email, fullName, avatar);
                }
            }

            if (user == null) {
                req.setAttribute("errorMessage", "Có lỗi khi tạo tài khoản. Vui lòng thử lại!");
                req.getRequestDispatcher("/login.jsp").forward(req, res);
                return;
            }

            session.setAttribute("user", user);

            String returnUrl = (String) session.getAttribute("returnUrl");
            session.removeAttribute("returnUrl");

            if (user.isAdmin()) {
                res.sendRedirect(req.getContextPath() + "/home");
            } else if (returnUrl != null && !returnUrl.isEmpty()) {
                res.sendRedirect(req.getContextPath() + "/" + returnUrl);
            } else {
                res.sendRedirect(req.getContextPath() + "/home");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Lỗi đăng nhập Google: " + e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, res);
        }
    }

    private String exchangeCodeForToken(String code) throws Exception {
        String params = "code="           + URLEncoder.encode(code, "UTF-8")
                      + "&client_id="     + URLEncoder.encode(GoogleOAuthConfig.CLIENT_ID, "UTF-8")
                      + "&client_secret=" + URLEncoder.encode(GoogleOAuthConfig.CLIENT_SECRET, "UTF-8")
                      + "&redirect_uri="  + URLEncoder.encode(GoogleOAuthConfig.REDIRECT_URI, "UTF-8")
                      + "&grant_type=authorization_code";

        HttpURLConnection conn = (HttpURLConnection) new URL(GoogleOAuthConfig.TOKEN_URL).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes(StandardCharsets.UTF_8));
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
        }

        JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();
        return json.has("access_token") ? json.get("access_token").getAsString() : null;
    }

    private JsonObject getUserInfo(String accessToken) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(GoogleOAuthConfig.USERINFO_URL).openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
        }

        return JsonParser.parseString(sb.toString()).getAsJsonObject();
    }
}