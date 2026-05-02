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

@WebServlet("/updateNCC")
public class UpdateNCCServlet extends HttpServlet {
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
			String namequankho = request.getParameter("fullName");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");

			if (userId == null || userId.trim().isEmpty()) {
				response.getWriter().write("{\"success\": false, \"message\": \"ID Nhà cung cấp không hợp lệ!\"}");
				return;
			}

			if (namekho == null || namekho.trim().isEmpty()) {
				response.getWriter().write("{\"success\": false, \"message\": \"Tên Kho không được để trống!\"}");
				return;
			}

			if (namequankho == null || namequankho.trim().isEmpty()) {
				response.getWriter().write("{\"success\": false, \"message\": \"Họ tên người quản kho không được để trống!\"}");
				return;
			}

			Long id = Long.parseLong(userId);

			if (dao.isNCCnameExistsExcept(namekho.trim(), id)) {
				response.getWriter().write("{\"success\": false, \"message\": \"Tên kho đã tồn tại!\"}");
				return;
			}

	

			NCC NCC = new NCC();
			NCC.setId(id);
			NCC.setNameKho(namekho.trim());
			NCC.setNameQuanKho(namequankho.trim());
			NCC.setStatus(state != null ? state.trim() : "ACTIVE");
			NCC.setPhone(phone != null ? phone.trim() : "");
			NCC.setAddress(address != null ? address.trim() : "");

			boolean success = dao.updateNCC(NCC);

			if (success) {
				response.getWriter().write("{\"success\": true, \"message\": \"Cập nhật khách hàng thành công!\"}");
			} else {
				response.getWriter().write("{\"success\": false, \"message\": \"Không thể cập nhật khách hàng!\"}");
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