package AdminControl;

import Dao.Dao;
import Entity.Category;
import Entity.NCC;
import Entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/updatecategory")
public class UpdatecategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Dao dao;

	@Override
	public void init() throws ServletException {
		dao = new Dao();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		if (!isAdmin(request)) {
			response.sendRedirect(request.getContextPath() + "/login?error=accessDenied");
			return;
		}
		try {
			String userId = request.getParameter("userId");
			String state = request.getParameter("status");
			String namekho = request.getParameter("username");

			if (userId == null || userId.trim().isEmpty()) {
				response.getWriter().write("{\"success\": false, \"message\": \"ID Danh mục không hợp lệ!\"}");
				return;
			}

			if (namekho == null || namekho.trim().isEmpty()) {
				response.getWriter().write("{\"success\": false, \"message\": \"Tên Danh mục không được để trống!\"}");
				return;
			}


			Long id = Long.parseLong(userId);

			if (dao.iscategorynameExists(namekho.trim(),id)) {
				response.getWriter().write("{\"success\": false, \"message\": \"Tên Danh mục đã tồn tại!\"}");
				return;
			}
			 
			Category category  = new Category();
			category.setId(id);
			category.setName(namekho.trim());
			category.setStatus(state);

			boolean success = dao.updatecategory(category);

			if (success) {
				response.getWriter().write("{\"success\": true, \"message\": \"Cập nhật nhà cung cấp thành công!\"}");
			} else {
				response.getWriter().write("{\"success\": false, \"message\": \"Không thể cập nhật nhà cung cấp!\"}");
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().write("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ!\"}");
		} catch (Exception e) {
			e.printStackTrace();
			String msg = escapeJson(e.getMessage());
			response.getWriter().write("{\"success\": false, \"message\": \"Lỗi: " + msg + "\"}");
		}
	}

	private String escapeJson(String text) {
		if (text == null) {
			return "";
		}
		return text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t",
				"\\t");
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