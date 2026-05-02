package Controller;

import Service.GoogleOAuthConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

@WebServlet("/auth/google/login")
public class GoogleLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String state = UUID.randomUUID().toString();
        req.getSession().setAttribute("oauth_state", state);

        String returnUrl = req.getParameter("returnUrl");
        if (returnUrl != null) {
            req.getSession().setAttribute("returnUrl", returnUrl);
        }

        String googleAuthUrl = GoogleOAuthConfig.AUTH_URL
            + "?client_id="     + URLEncoder.encode(GoogleOAuthConfig.CLIENT_ID, "UTF-8")
            + "&redirect_uri="  + URLEncoder.encode(GoogleOAuthConfig.REDIRECT_URI, "UTF-8")
            + "&response_type=code"
            + "&scope="         + URLEncoder.encode(GoogleOAuthConfig.SCOPE, "UTF-8")
            + "&state="         + state
            + "&access_type=online"
            + "&prompt=select_account"; 

        res.sendRedirect(googleAuthUrl);
    }
}