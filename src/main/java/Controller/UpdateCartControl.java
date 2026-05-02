package Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.CartItem;

@WebServlet("/updateCart")
public class UpdateCartControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id_raw = request.getParameter("productId");
		String quantity_raw = request.getParameter("quantity");

		try {
			long productId = Long.parseLong(id_raw);
			int newQuantity = Integer.parseInt(quantity_raw);

			HttpSession session = request.getSession();
			List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

			if (cart != null) {
				for (CartItem item : cart) {
					if (item.getProduct().getId() == productId) {
						if (newQuantity < 0) {
							String errorMsg = "Xin vui lòng nhập số lượng sản phẩm muốn mua lớn hơn 0";

							request.setAttribute("errorMessage", errorMsg);
							request.getRequestDispatcher("cart.jsp").forward(request, response);
							return;
						} else if(newQuantity == 0){
							cart.remove(item);
	                        break; 
						} else if (newQuantity > item.getProduct().getStockQuantity()) {
							int stock = item.getProduct().getStockQuantity();
							String productName = item.getProduct().getName();

							String errorMsg = "Rất tiếc, sản phẩm '" + productName + "' hiện chỉ còn " + stock
									+ " sản phẩm trong kho. " + "Vui lòng giảm số lượng đặt hàng.";

							request.setAttribute("errorMessage", errorMsg);
							request.getRequestDispatcher("cart.jsp").forward(request, response);
							return;
						} else if (newQuantity <= item.getProduct().getStockQuantity()) {
							item.setQuantity(newQuantity);
						}
					}
				}
				session.setAttribute("cart", cart);
			}

			response.sendRedirect("cart.jsp");

		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID hoặc Số lượng không hợp lệ.");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật giỏ hàng.");
		}
	}
}