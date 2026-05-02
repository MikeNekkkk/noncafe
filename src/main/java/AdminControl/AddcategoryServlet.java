package AdminControl;

import Dao.Dao;
import Entity.Category;
import Entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/addcategory")
public class AddcategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Dao dao;

    @Override
    public void init() throws ServletException {
        dao = new Dao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	if (!isAdmin(request)) {
    		response.sendRedirect(request.getContextPath()+ "/login?error=accessDenied");
			return;
		}
        response.setContentType("application/json; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            String username = request.getParameter("username");
          
            if (username == null || username.trim().isEmpty()) {
                response.getWriter().write(
                    "{\"success\": false, \"message\": \"Tên Danh mục không được để trống!\"}"
                );
                return;
            }


            if (dao.iscategorynameExists2(username.trim())) {
                response.getWriter().write(
                    "{\"success\": false, \"message\": \"Tên Danh mục đã tồn tại!\"}"
                );
                return;
            }
            
    
           
            Category category = new Category();
            category.setName(username.trim());
          

            boolean success = dao.addcategory(category);

            if (success) {
                response.getWriter().write(
                    "{\"success\": true, \"message\": \"Thêm nhà cung cấp thành công!\"}"
                );
            } else {
                response.getWriter().write(
                    "{\"success\": false, \"message\": \"Không thể thêm Nhà cung cấp!\"}"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage().replace("\"", "\\\"");
            response.getWriter().write(
                "{\"success\": false, \"message\": \"Lỗi: " + msg + "\"}"
            );
        }
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