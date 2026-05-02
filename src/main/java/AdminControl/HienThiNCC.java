package AdminControl;

import Dao.Dao;
import Entity.Category;
import Entity.NCC;
import Entity.Product;
import Entity.User;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/HTNCC")
public class HienThiNCC extends HttpServlet {
       
    private Dao dao; 
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init() throws ServletException {
        dao = new Dao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	if (!isAdmin(request)) {
    		response.sendRedirect(request.getContextPath()+ "/login?error=accessDenied");
			return;
		}
    	try {
            List<NCC> listNCC = dao.AllNCC();
            request.setAttribute("listNCC", listNCC);
            request.getRequestDispatcher("doc/table-data-NCC.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                               "Lỗi khi tải dữ liệu trang chủ.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
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