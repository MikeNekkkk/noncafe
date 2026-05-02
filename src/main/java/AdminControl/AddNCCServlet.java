package AdminControl;

import Dao.Dao;
import Entity.NCC;
import Entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/addNCC")
public class AddNCCServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Dao dao;

    @Override
    public void init() throws ServletException {
        dao = new Dao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	if (!isAdmin(request)) {
    		response.sendRedirect(request.getContextPath()+ "/login?error=accessDenied");
			return;
		}
        response.setContentType("application/json; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
          
            if (username == null || username.trim().isEmpty()) {
                response.getWriter().write(
                    "{\"success\": false, \"message\": \"Tên Kho không được để trống!\"}"
                );
                return;
            }

          
            if (fullName == null || fullName.trim().isEmpty()) {
                response.getWriter().write(
                    "{\"success\": false, \"message\": \"Họ tên người quản không được để trống!\"}"
                );
                return;
            }

            if (dao.isNCCnameExists(username.trim())) {
                response.getWriter().write(
                    "{\"success\": false, \"message\": \"Tên Kho đã tồn tại!\"}"
                );
                return;
            }

           
            NCC NCC = new NCC();
            NCC.setNameKho(username.trim());
            NCC.setNameQuanKho(fullName.trim());
            NCC.setPhone(phone != null ? phone.trim() : "");
            NCC.setAddress(address != null ? address.trim() : "");

            boolean success = dao.addNCC(NCC);

            if (success) {
                response.getWriter().write(
                    "{\"success\": true, \"message\": \"Thêm nhà cung cấp thành công!\"}"
                );
            } else {
                response.getWriter().write(
                    "{\"success\": false, \"message\": \"Không thể thêm Nhà cung cấp!\"}"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage().replace("\"", "\\\"");
            response.getWriter().write(
                "{\"success\": false, \"message\": \"Lỗi: " + msg + "\"}"
            );
        }
    }
    private boolean isAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}

		User user = (User) session.getAttribute("user");
		return user != null && "ADMIN".equals(user.getRole());
	}
}