package Controller;

import Entity.Contact;
import Entity.Order;
import Entity.OrderItem;
import Entity.User;
import Dao.Dao;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SearchID")
public class SearchIdorder extends HttpServlet {

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
			request.setAttribute("userName", user.getFullName());
			request.setAttribute("userEmail", user.getEmail());
		}
		request.getRequestDispatcher("contact.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String idParam = request.getParameter("idorder");
		Long id = Long.parseLong(idParam);
		List<OrderItem> DTorders = null;
		try {
			DTorders = dao.getOrderItemsByOrderId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Order order = dao.getOrderById(id);
		if (order == null) {
            request.setAttribute("kdonMessage", "Không tìm thấy đơn hàng với mã đơn hàng #  " + id);
            request.getRequestDispatcher("contact.jsp").forward(request, response);
            return;
        }
		request.setAttribute("DTorders", DTorders);
		request.setAttribute("order", order);
		request.getRequestDispatcher("contact.jsp").forward(request, response);
	}
}