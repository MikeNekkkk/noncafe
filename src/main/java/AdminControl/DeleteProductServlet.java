package AdminControl;

import Dao.Dao;
import Entity.User;

import java.io.IOException;
import java.net.http.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Dao dao;

    @Override
    public void init() throws ServletException {
        dao = new Dao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        if(!isAdmin(request)){
    		response.sendRedirect(request.getContextPath()+ "/login?error=accessDenied");
    		return;
    	}
        try {
        	
            String productId = request.getParameter("id");

            if (productId == null || productId.isEmpty()) {
                response.getWriter().write("{\"success\": false, \"message\": \"ID sản phẩm không hợp lệ!\"}");
                return;
            }
    		if (dao.isProductInAnyOrder(productId)) {
    			dao.softDeleteProduct(productId);
    			response.getWriter().write("{\"success\": true, \"message\": \"Sản phẩm còn tồn tại trong hóa đơn nên không thể xóa, Hệ thống sẽ chuyển sản phẩm sang trạng thái ngưng hoạt động!\"}");
                return;
    		}
            boolean success = dao.deleteProduct(productId);

            if (success) {
                response.getWriter().write("{\"success\": true, \"message\": \"Xóa sản phẩm thành công!\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Không thể xóa sản phẩm!\"}");
            }

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
    	if(session == null) {
    		return false;
    	}
    	User user = (User) session.getAttribute("user");
    	return user!=null && "ADMIN".equals(user.getRole());
    }
}