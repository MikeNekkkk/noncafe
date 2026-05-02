package AdminControl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.Dao;
import Entity.Order;
import Entity.Product;
import Entity.User;

@WebServlet("/BaocaoThongke")
public class BaocaoThongke extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Dao dao;

	@Override
	public void init() throws ServletException {
		dao = new Dao();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int totalCustomers = dao.getTotalCustomers();
			int totalProducts = dao.getTotalProducts();
			int totalOrders = dao.getTotalOrders();
			int lowStockProducts = dao.getLowStockProducts();
			int totalContacts = dao.getTotalContacts();
			int newContacts = dao.getNewContactsCount();
			String type = request.getParameter("type");
			String date = request.getParameter("date");
			String typeLN = request.getParameter("typeLN");
			String dateLN = request.getParameter("dateLN");
			List<Order> getAllOrdersLN;
			List<Order> getAllOrdersLN2;
			if (type != null && date != null && !date.isEmpty()) {
			    getAllOrdersLN = dao.getOrdersByDate(type, date);
			} else {
			    getAllOrdersLN = dao.getAllOrdersLN();
			}
			if (typeLN != null && dateLN != null && !dateLN.isEmpty()) {
			    getAllOrdersLN2 = dao.getOrdersByDate(typeLN, dateLN);
			} else {
			    getAllOrdersLN2 = dao.getAllOrdersLN();
			}
			List<Double> revenueByMonth = dao.getRevenueByMonth();
			request.setAttribute("revenueByMonth", revenueByMonth);

			List<User> recentCustomers = dao.getRecentCustomers(5);
			List<Product> ProductsSoldout = dao.ProductsSoldout();
			List<Order> recentOrders = dao.getRecentOrders(5);
			request.setAttribute("getallorder", getAllOrdersLN);
			request.setAttribute("getallorder2", getAllOrdersLN2);
			request.setAttribute("ProductsSoldout", ProductsSoldout);
			request.setAttribute("totalCustomers", totalCustomers);
			request.setAttribute("totalProducts", totalProducts);
			request.setAttribute("totalOrders", totalOrders);
			request.setAttribute("lowStockProducts", lowStockProducts);
			request.setAttribute("totalContacts", totalContacts);
			request.setAttribute("newContacts", newContacts);
			request.setAttribute("recentCustomers", recentCustomers);
			request.setAttribute("recentOrders", recentOrders);

			request.getRequestDispatcher("doc/quan-ly-bao-cao.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu trang chủ admin.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}