package AdminControl;

import Dao.Dao;
import Entity.User;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Dao dao;

	@Override
	public void init() throws ServletException {
		dao = new Dao();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!isAdmin(request)) {
    		response.sendRedirect(request.getContextPath()+ "/login?error=accessDenied");
			return;
		}
		response.setContentType("application/json; charset=UTF-8");

		try {
			String userId = request.getParameter("id");

			if (userId != null && !userId.isEmpty()) {
				boolean success = dao.deleteUser(Long.parseLong(userId));

				if (success) {
					response.getWriter().write("{\"success\": true, \"message\": \"Xóa khách hàng thành công!\"}");
				} else {
					response.getWriter().write("{\"success\": false, \"message\": \"Không thể xóa khách hàng!\"}");
				}
				return;
			}

			response.getWriter().write("{\"success\": false, \"message\": \"ID khách hàng không hợp lệ!\"}");

		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage().replace("\"", "\\\"");
			response.getWriter().write("{\"success\": false, \"message\": \"Lỗi: " + msg + "\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean isAdmin(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	if(session ==null) {
    		return false;
    	}
    	User user = (User) session.getAttribute("user");
    	return user!=null && "Admin".equals(user.getRole());
    }

}