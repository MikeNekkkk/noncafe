package Controller;

import Entity.Contact;
import Entity.User;
import Dao.Dao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cancelOrderRequest")
public class cancelOrderRequest extends HttpServlet {

	private Dao dao;

	@Override
	public void init() throws ServletException {
		dao = new Dao();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String cid = request.getParameter("id");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		String name = user.getFullName();
		String email = user.getEmail();
		String subject = "Yêu cầu hủy đơn hàng #" + cid;
		String message = "Khách hàng " + name + " (ID: " + user.getId() + ") yêu cầu hủy đơn hàng với mã là #" + cid
				+ ". Lý do: Khách hàng nhấn nút yêu cầu hủy trên giao diện.";
		boolean success = false;
		try {

			Contact contact = new Contact();
			contact.setUserId(user.getId());
			contact.setName(name);
			contact.setEmail(email);
			contact.setSubject(subject);
			contact.setMessage(message);
			contact.setStatus("NEW");
			success = dao.createContact(contact);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Có lỗi xảy ra khi gửi liên hệ: " + e.getMessage());
			request.getRequestDispatcher("contact.jsp").forward(request, response);
		}

		if (success) {
			session.setAttribute("messageSuccess",
					"Yêu cầu hủy đơn hàng #" + cid + " đã được gửi thành công. "
							+ "Admin sẽ kiểm tra và xử lý sớm nhất! Nếu sau 24h đơn hàng vẫn chưa được hủy, "
							+ "vui lòng liên hệ Hotline: <b>0901.957.965</b> để chúng tôi giải quyết nhanh hơn.");
			response.sendRedirect("DetaillistuserOrders?id=" + cid);
		} else {
			session.setAttribute("messageError", "Không thể gửi yêu cầu hủy đơn. Vui lòng liên hệ hotline!");
			response.sendRedirect("DetaillistuserOrders?id=" + cid);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}