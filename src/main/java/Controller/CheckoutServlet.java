package Controller;

import Entity.CartItem;
import Entity.Order;
import Entity.OrderItem;
import Entity.Product;
import Entity.User;
import Dao.Dao;
import Service.GHNService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

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

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user != null) {
            String fullName = user.getFullName();
            if (fullName != null && !fullName.trim().isEmpty()) {
                String[] parts = fullName.trim().split("\\s+", 2);
                request.setAttribute("firstName", parts[0]);
                if (parts.length > 1) request.setAttribute("lastName", parts[1]);
            } else {
                request.setAttribute("firstName", "");
                request.setAttribute("lastName",  "");
            }
            Order lastOrder = dao.getLatestOrderByUserId(user.getId());
            if (lastOrder != null) request.setAttribute("lastOrder", lastOrder);
            request.setAttribute("email",   user.getEmail());
            request.setAttribute("phone",   user.getPhone());
            request.setAttribute("address", user.getAddress());
        }

        int totalWeightGram = calcTotalWeightFromDao(cart);
        request.setAttribute("totalWeightGram", totalWeightGram);

        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String fullName  = (firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName);
        String email     = request.getParameter("email");
        String phone     = request.getParameter("phone");
        String notes     = request.getParameter("notes");

        String streetAddress = request.getParameter("address");
        String ward          = request.getParameter("ward");      
        String district      = request.getParameter("district");  
        String city          = request.getParameter("city");      
        String region        = request.getParameter("region");

        int    districtId = 0;
        String wardCode   = "";
        try {
            String dp = request.getParameter("districtId");
            if (dp != null && !dp.isEmpty()) districtId = Integer.parseInt(dp);
        } catch (NumberFormatException ignored) {}
        String wc = request.getParameter("wardCode");
        if (wc != null) wardCode = wc.trim();

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        int totalWeightGram = calcTotalWeightFromDao(cart);

        BigDecimal shipFee = BigDecimal.ZERO;
        if (districtId > 0 && !wardCode.isEmpty()) {
            shipFee = ghn.calculateShippingFee(districtId, wardCode, totalWeightGram);
        } else {
            try {
                String sf = request.getParameter("ship_fee");
                if (sf != null && !sf.isEmpty()) shipFee = new BigDecimal(sf);
            } catch (Exception ignored) {}
        }

        BigDecimal subTotal = BigDecimal.ZERO;
        for (CartItem item : cart) {
            subTotal = subTotal.add(
                item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        BigDecimal grandTotal = subTotal.add(shipFee);

        String fullAddress = streetAddress + ", Phường " + ward
                + ", Quận " + district + ", Thành Phố " + city;

        String createAccount   = request.getParameter("createAccount");
        String accountPassword = request.getParameter("accountPassword");
        User user = (User) session.getAttribute("user");

        if (user == null) {
            try {
                if ("on".equals(createAccount)) {
                    if (dao.isEmailNameExists(email, email)) {
                        request.setAttribute("errorMessage",
                            "Gmail này đã được đăng ký tài khoản trên Nõn Cà Phê. "
                            + "Xin hãy đăng nhập hoặc tiếp tục mua hàng mà không tích chọn 'Tạo tài khoản mới'.");
                        request.setAttribute("firstName", firstName);
                        request.setAttribute("lastName",  lastName);
                        request.setAttribute("email",     email);
                        request.setAttribute("phone",     phone);
                        request.setAttribute("address",   streetAddress);
                        request.getRequestDispatcher("checkout.jsp").forward(request, response);
                        return;
                    }
                    User newUser = new User();
                    newUser.setUsername(email);
                    newUser.setPassword(accountPassword);
                    newUser.setEmail(email);
                    newUser.setFullName(fullName.trim());
                    newUser.setPhone(phone);
                    newUser.setAddress(fullAddress);
                    newUser.setRole("USER");
                    newUser.setStatus("ACTIVE");

                    if (dao.register(newUser)) {
                        User logged = dao.login(email, accountPassword);
                        if (logged != null) {
                            session.setAttribute("user", logged);
                            user = logged;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Lỗi khi tạo tài khoản: " + e.getMessage());
                request.getRequestDispatcher("checkout.jsp").forward(request, response);
                return;
            }
        }

        Order order = new Order();
        if (user != null) order.setUserId(user.getId());
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(fullAddress);
        order.setCity(city);
        order.setState(region);
        order.setDistrict(district);
        order.setWard(ward);
        order.setEmail(email);
        order.setPhone(phone);
        order.setNotes(notes);
        order.setTotalAmount(grandTotal);    
        order.setStatus("PENDING");
        order.setPaymentMethod("PENDING");
        order.setGhnDistrictId(districtId > 0 ? districtId : null);
        order.setGhnWardCode(!wardCode.isEmpty() ? wardCode : null);
        order.setShipFee(shipFee);

        List<OrderItem> orderItems = new ArrayList<>();
        Dao daoLocal = new Dao();

        for (CartItem cartItem : cart) {
            Product p = daoLocal.getProductById(String.valueOf(cartItem.getProduct().getId()));
            OrderItem oi = new OrderItem();
            oi.setProductId(p.getId());
            oi.setProductName(p.getName());
            oi.setPrice(p.getPrice());
            oi.setPricein(p.getPricein());
            oi.setQuantity(cartItem.getQuantity());
            oi.setSubtotal(p.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            orderItems.add(oi);
        }
        order.setOrderItems(orderItems);

        try {
            Long orderId = daoLocal.createOrder(order, orderItems);
            order.setId(orderId);

            daoLocal.addTrackingLog(orderId, "PENDING",
                    GHNService.translateStatus("PENDING"),
                    "Kho Nõn Cà Phê – Đà Nẵng",
                    "Đơn hàng #" + orderId + " vừa được tạo thành công", "SYSTEM");

            session.setAttribute("pendingOrder", order);
            response.sendRedirect("payment-method.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi tạo đơn hàng: " + e.getMessage());
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
    }

    
   private int calcTotalWeightFromDao(List<CartItem> cart) {
        int total = 0;
        Dao tmpDao = new Dao();
        for (CartItem item : cart) {
            int wg = 250;
            try {
                Product p = tmpDao.getProductById(String.valueOf(item.getProduct().getId()));
                if (p != null && p.getWeightGram() > 0) wg = p.getWeightGram();
            } catch (Exception ignored) {}
            total += wg * item.getQuantity();
        }
        return Math.max(total, 100);
    }
}