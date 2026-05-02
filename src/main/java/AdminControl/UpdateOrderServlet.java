package AdminControl;

import Dao.Dao;
import Entity.Order;
import Entity.User;
import Service.GHNService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin/updateOrder")
public class UpdateOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Dao        dao;
    private GHNService ghn;

    @Override
    public void init() throws ServletException {
        dao = new Dao();
        ghn = new GHNService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login?error=accessDenied");
            return;
        }

        try {
            String orderIdStr    = request.getParameter("orderId");
            String newStatus     = request.getParameter("status");
            String paymentMethod = request.getParameter("paymentMethod");

            if (orderIdStr == null || newStatus == null) {
                writeJson(response, false, "Thiếu thông tin orderId hoặc status!");
                return;
            }

            Long  orderId      = Long.parseLong(orderIdStr);
            Order currentOrder = dao.getOrderById(orderId);

            if (currentOrder == null) {
                writeJson(response, false, "Không tìm thấy đơn hàng #" + orderId);
                return;
            }

            String currentStatus = currentOrder.getStatus();

            if (isLocked(currentStatus)) {
                writeJson(response, false,
                    "Đơn hàng đang ở trạng thái \""
                    + GHNService.translateStatus(currentStatus)
                    + "\" – không thể thay đổi. Trạng thái do GHN cập nhật tự động.");
                return;
            }

            if (!isValidTransition(currentStatus, newStatus)) {
                writeJson(response, false,
                    "Không thể chuyển từ \""
                    + GHNService.translateStatus(currentStatus)
                    + "\" → \""
                    + GHNService.translateStatus(newStatus) + "\"");
                return;
            }

            String ghnCode = null;

            if ("DELIVERING".equals(newStatus)) {
                ghnCode = handleDelivering(orderId, currentOrder);

            } else if ("COMPLETED".equals(newStatus)) {
                dao.updateQualityproduct(orderId);
                dao.addTrackingLog(orderId, "COMPLETED",
                        GHNService.translateStatus("COMPLETED"),
                        "Kho Nõn Cà Phê", "Đơn hàng đã hoàn thành", "ADMIN");

            } else if ("CANCELLED".equals(newStatus)) {
                dao.updateQualityproducthuy(orderId);
                dao.addTrackingLog(orderId, "CANCELLED",
                        GHNService.translateStatus("CANCELLED"),
                        "Kho Nõn Cà Phê", "Đơn hàng bị hủy bởi Admin", "ADMIN");

            } else {
                dao.addTrackingLog(orderId, newStatus,
                        GHNService.translateStatus(newStatus),
                        "Kho Nõn Cà Phê – Đà Nẵng", null, "ADMIN");
            }

            boolean ok = dao.updateOrderStatus(orderId, newStatus, paymentMethod);
            if (ok) {
                if (ghnCode != null) {
                    writeJsonWithGhn(response, true,
                        "Cập nhật đơn hàng thành công! Mã vận đơn GHN: " + ghnCode, ghnCode);
                } else {
                    writeJson(response, true, "Cập nhật đơn hàng thành công!");
                }
            } else {
                writeJson(response, false, "Không thể cập nhật đơn hàng!");
            }

        } catch (RuntimeException e) {
            writeJson(response, false, e.getMessage() != null ? e.getMessage() : "Lỗi không xác định");
        } catch (Exception e) {
            e.printStackTrace();
            writeJson(response, false, "Lỗi hệ thống: " + e.getMessage());
        }
    }
  private String handleDelivering(Long orderId, Order order) {
        int    districtId = order.getGhnDistrictId() != null ? order.getGhnDistrictId() : 0;
        String wardCode   = order.getGhnWardCode()   != null ? order.getGhnWardCode()   : "";

        int weightGram = dao.getOrderTotalWeightGram(orderId);

        List<Entity.OrderItem> orderItems = new ArrayList<>();
        try {
            orderItems = dao.getOrderItemsByOrderId(orderId);
        } catch (Exception e) {
            System.err.println("không lấy được orderItems cho đơn #"
                    + orderId + " – sẽ dùng item fallback. " + e.getMessage());
        }

        String ghnCode = null;

        if (districtId > 0 && !wardCode.isEmpty()) {
            ghnCode = ghn.createShipOrder(
                order.getFirstName() + " " + order.getLastName(),
                order.getPhone(),
                order.getAddress(),
                districtId,
                wardCode,
                weightGram,
                orderId,
                order.getNotes() != null ? order.getNotes() : "",
                orderItems   
            );
        } else {
            System.err.println("districtId/wardCode rỗng cho đơn #" + orderId
                    + " – bỏ qua tạo vận đơn GHN.");
        }

        String note = (ghnCode != null)
                ? "Mã vận đơn GHN: " + ghnCode + " | Tổng KL: " + weightGram + "g"
                : "Đã bàn giao cho đơn vị vận chuyển (chưa có mã GHN – KL: " + weightGram + "g)";

        dao.addTrackingLog(orderId, "DELIVERING",
                GHNService.translateStatus("DELIVERING"),
                "Kho Nõn Cà Phê – Đà Nẵng", note, "ADMIN");

        if (ghnCode != null) {
            dao.updateGhnOrderCode(orderId, ghnCode);
        }

        return ghnCode;
    }
  private boolean isLocked(String status) {
        return "DELIVERING".equals(status)
            || "COMPLETED".equals(status)
            || "CANCELLED".equals(status);
    }

    private boolean isValidTransition(String current, String next) {
        if (current.equals(next)) return false;

        switch (current) {

            case "PENDING":
                return "PROCESSING".equals(next)
                    || "CANCELLED".equals(next);

            case "PROCESSING":
                return "DELIVERING".equals(next)
                    || "CANCELLED".equals(next);

            case "PAID":
                return "DELIVERING".equals(next)
                    || "CANCELLED".equals(next);

            default:
                return false;
        }
    }

    private void writeJson(HttpServletResponse resp, boolean success, String message)
            throws IOException {
        String safe = escapeJson(message);
        resp.getWriter().write("{\"success\":" + success + ",\"message\":\"" + safe + "\"}");
    }

    private void writeJsonWithGhn(HttpServletResponse resp, boolean success,
                                   String message, String ghnCode) throws IOException {
        String safeMsg  = escapeJson(message);
        String safeCode = escapeJson(ghnCode);
        resp.getWriter().write(
            "{\"success\":" + success
            + ",\"message\":\"" + safeMsg + "\""
            + ",\"ghnOrderCode\":\"" + safeCode + "\"}");
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        User user = (User) session.getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole());
    }
}