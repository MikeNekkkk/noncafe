package AdminControl;

import Dao.Dao;
import Entity.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

@WebServlet("/admin/deleteOrder")
public class DeleteOrderServlet extends HttpServlet {
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
        if(!isAdmin(request)) {
    		response.sendRedirect(request.getContextPath()+ "/login?error=accessDenied");
        	return;
        }
        try {
            String orderIdStr = request.getParameter("id");

            if (orderIdStr == null || orderIdStr.isEmpty()) {
                response.getWriter().write("{\"success\": false, \"message\": \"ID đơn hàng không hợp lệ!\"}");
                return;
            }

            Long orderId = Long.parseLong(orderIdStr);
            
            boolean success = dao.deleteOrder(orderId);

            if (success) {
                response.getWriter().write("{\"success\": true, \"message\": \"Xóa đơn hàng thành công!\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Không thể xóa đơn hàng!\"}");
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