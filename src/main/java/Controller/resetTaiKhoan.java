package Controller;

import Entity.User;
import Dao.Dao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/resetTK")
public class resetTaiKhoan extends HttpServlet {

	private Dao dao;

	@Override
	public void init() throws ServletException {
		dao = new Dao();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			String fullName = user.getFullName();
			request.setAttribute("nameht", user.getUsername());
			if (fullName != null && !fullName.trim().isEmpty()) {
				String[] nameParts = fullName.trim().split("\\s+", 2);
				request.setAttribute("firstName", nameParts[0]);
				if (nameParts.length > 1) {
					request.setAttribute("lastName", nameParts[1]);
				}
			} else {
				request.setAttribute("firstName", "");
				request.setAttribute("lastName", "");
			}
		}

		request.getRequestDispatcher("TKuser.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String Nameht = request.getParameter("nameht");
		String fullName = firstName + " " + lastName;
		String curentPassWord = request.getParameter("curentPassWord");
		String ChangePassword = request.getParameter("ChangePassword");
		String checkChangePassword = request.getParameter("checkChangePassword");
		if (ChangePassword == null) ChangePassword = "";
		if (checkChangePassword == null) checkChangePassword = "";
		if (curentPassWord == null) curentPassWord = "";

		User user = (User) session.getAttribute("user");
		if (user != null) {
			if (ChangePassword.equals(checkChangePassword)) {
				try {
					boolean isUpdated = dao.ResetPassword(user.getId(),Nameht, fullName, curentPassWord, ChangePassword);
					if (isUpdated) {
						request.setAttribute("successMessage", "Cập nhật thông tin thành công!");
						user.setFullName(fullName);
						user.setUsername(Nameht);
						session.setAttribute("user", user);
					} else {
						request.setAttribute("errorMessage", "Cập nhật thất bại. Hãy kiểm tra lại mật khẩu hiện tại!");
					}
					request.getRequestDispatcher("TKuser.jsp").forward(request, response);
					return; 

				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
					request.getRequestDispatcher("TKuser.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
				request.getRequestDispatcher("TKuser.jsp").forward(request, response);
				return;
			}
		} else {
			response.sendRedirect("TKuser.jsp");
		}
	}
}