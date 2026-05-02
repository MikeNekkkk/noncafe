package Controller;

import Dao.Dao;
import Entity.Order;
import Entity.OrderItem;
import Entity.TrackingLog;
import Entity.User;
import Service.GHNService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DetaillistuserOrders")
public class DetailOrderOfUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Dao        dao;
    private GHNService ghn;

    @Override
    public void init() throws ServletException {
        dao = new Dao();
        ghn = new GHNService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User   user    = (User) session.getAttribute("user");
            String idParam = request.getParameter("id");
            Long   userId  = user.getId();
            Long   id      = Long.parseLong(idParam);

            List<OrderItem>  DTorders = dao.getOrderItemsByOrderId(id);
            Order            order    = dao.getOrderById(id);
            boolean          check    = dao.isOrderCancelRequested(userId, id);

            List<TrackingLog> trackingLogs = buildTrackingLogs(order);

            request.setAttribute("isRequested",  check);
            request.setAttribute("DTorders",     DTorders);
            request.setAttribute("order",        order);
            request.setAttribute("trackingLogs", trackingLogs);

            request.getRequestDispatcher("ChiTiet_DonHang.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải đơn hàng: " + e.getMessage());
            request.getRequestDispatcher("404.jsp").forward(request, response);
        }
    }

    private List<TrackingLog> buildTrackingLogs(Order order) {
        if (order == null) return new ArrayList<>();

        List<TrackingLog> logs = dao.getTrackingLogsByOrderId(order.getId());

        String ghnCode = dao.getGhnOrderCode(order.getId());
        if (ghnCode != null && !ghnCode.isEmpty()) {
            List<Map<String, Object>> ghnLogs = ghn.getTrackingLogs(ghnCode);
            for (Map<String, Object> g : ghnLogs) {
                String statusCode = (String) g.get("statusCode");
                boolean exists = logs.stream()
                        .anyMatch(l -> l.getStatusCode().equals(statusCode)
                                    && l.getUpdatedBy().equals("SYSTEM"));
                if (!exists) {
                    TrackingLog t = new TrackingLog();
                    t.setOrderId(order.getId());
                    t.setStatusCode(statusCode);
                    t.setStatusName((String) g.get("statusName"));
                    t.setLocation((String) g.get("location"));
                    t.setNote("Cập nhật từ GHN");
                    t.setUpdatedBy("SYSTEM");
                    logs.add(t);
                }
            }
            logs.sort((a, b) -> {
                if (a.getCreatedAt() == null && b.getCreatedAt() == null) return 0;
                if (a.getCreatedAt() == null) return 1;
                if (b.getCreatedAt() == null) return -1;
                return b.getCreatedAt().compareTo(a.getCreatedAt());
            });
        }

        return logs;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}