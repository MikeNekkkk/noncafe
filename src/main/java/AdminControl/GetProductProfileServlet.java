package AdminControl;

import Dao.Dao;
import Entity.User;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/getProductProfile")
public class GetProductProfileServlet extends HttpServlet {

    private Dao dao;

    @Override
    public void init() { dao = new Dao(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json; charset=UTF-8");

        if (!isAdmin(request)) {
            response.setStatus(403);
            response.getWriter().write("{\"error\":\"Không có quyền\"}");
            return;
        }

        String productId = request.getParameter("productId");
        if (productId == null || productId.isEmpty()) {
            response.getWriter().write("null");
            return;
        }

        try {
            Map<String, Object> profile = dao.getProductProfile(Long.parseLong(productId));
            if (profile == null) {
                response.getWriter().write("null");
            } else {
                profile.remove("embeddingVector");
                response.getWriter().write(new Gson().toJson(profile));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("null");
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        if (s == null) return false;
        User u = (User) s.getAttribute("user");
        return u != null && "ADMIN".equals(u.getRole());
    }
}