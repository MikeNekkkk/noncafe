package AdminControl;

import Dao.Dao;
import Entity.Product;
import Entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/addProduct")
@MultipartConfig(
    fileSizeThreshold = 2 * 1024 * 1024,
    maxFileSize       = 10 * 1024 * 1024,
    maxRequestSize    = 50 * 1024 * 1024
)
public class AddProductServlet extends HttpServlet {

    private Dao dao;
    private static final String UPLOAD_DIR = "img/product/HT";

    @Override
    public void init() { dao = new Dao(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login?error=accessDenied");
            return;
        }
        try {
            request.setAttribute("listC", dao.getAllCategories());
            request.getRequestDispatcher("doc/form-add-san-pham.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Lỗi tải form");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login?error=accessDenied");
            return;
        }

        try {
            String productName = request.getParameter("productName");
            String stockStr    = request.getParameter("stockQuantity");
            String priceStr    = request.getParameter("price");
            String priceinStr  = request.getParameter("pricein");
            String categoryId  = request.getParameter("categoryId");
            String detailDesc  = request.getParameter("detailDescription");
            Part   filePart    = request.getPart("productImage");

            int    flavorIntensity  = parseInt(request.getParameter("flavor_intensity"), 3);
            int    bitterness       = parseInt(request.getParameter("bitterness_level"), 3);
            int    acidity          = parseInt(request.getParameter("acidity_level"), 3);
            int    sweetness        = parseInt(request.getParameter("sweetness_level"), 3);
            String roastLevel       = request.getParameter("roast_level");
            String caffeineLevel    = request.getParameter("caffeine_level");
            String flavorTags       = request.getParameter("flavor_tags");
            String suitableFor      = request.getParameter("suitable_for");
            String recommendedTime  = request.getParameter("recommended_time");
            String grindSize        = request.getParameter("grind_size");
            String processingMethod = request.getParameter("processing_method");
            String brewMethods      = request.getParameter("brew_methods");
            String weightGramStr    = request.getParameter("WeightGram");
            int    weightGram       = Integer.parseInt(weightGramStr);

            if (productName == null || productName.trim().isEmpty())
                throw new IllegalArgumentException("Tên sản phẩm không được để trống!");

            int stock = Integer.parseInt(stockStr != null ? stockStr : "0");
            if (stock < 0)
                throw new IllegalArgumentException("Số lượng không được âm!");

            BigDecimal pricein = new BigDecimal(priceinStr != null ? priceinStr : "0");
            BigDecimal price   = new BigDecimal(priceStr   != null ? priceStr   : "0");
            if (price.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0!");

            if (dao.isProductNameExists(productName.trim()))
                throw new IllegalArgumentException("Tên sản phẩm đã tồn tại!");

            String imageUrl = "/img/product/HT/default.png";
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                if (fileName != null && fileName.contains(".")) {
                    String ext     = fileName.substring(fileName.lastIndexOf("."));
                    String newName = System.currentTimeMillis() + ext;
                    String appPath = request.getServletContext().getRealPath("");
                    File dir = new File(appPath + File.separator + UPLOAD_DIR);
                    if (!dir.exists()) dir.mkdirs();
                    filePart.write(appPath + File.separator + UPLOAD_DIR + File.separator + newName);
                    imageUrl = UPLOAD_DIR + "/" + newName;
                }
            }

            Product product = new Product();
            product.setName(productName.trim());
            product.setStockQuantity(stock);
            product.setPrice(price);
            product.setPricein(pricein);
            product.setCategoryId(Long.parseLong(categoryId));
            product.setDetailDescription(detailDesc != null ? detailDesc.trim() : "");
            product.setStatus("ACTIVE");
            product.setWeightGram(weightGram);
            product.setImageUrl(imageUrl);

            Product saved = dao.addProduct(product);
            if (saved == null || saved.getId() <= 0)
                throw new Exception("Không thể thêm sản phẩm vào CSDL!");

            dao.saveProductProfile(saved.getId(),
                flavorIntensity, bitterness, acidity, sweetness,
                roastLevel, caffeineLevel, flavorTags, suitableFor,
                recommendedTime, grindSize, processingMethod, brewMethods);

            request.setAttribute("successMessage", "Thêm sản phẩm thành công!");
            request.setAttribute("listC", dao.getAllCategories());
            request.getRequestDispatcher("doc/form-add-san-pham.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            loadFormWithError(request, response, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            loadFormWithError(request, response, "Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void loadFormWithError(HttpServletRequest req, HttpServletResponse res, String msg)
            throws ServletException, IOException {
        try { req.setAttribute("listC", dao.getAllCategories()); } catch (Exception ignore) {}
        req.setAttribute("errorMessage", msg);
        req.getRequestDispatcher("doc/form-add-san-pham.jsp").forward(req, res);
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        if (s == null) return false;
        User u = (User) s.getAttribute("user");
        return u != null && "ADMIN".equals(u.getRole());
    }

    private int parseInt(String v, int def) {
        try { return Integer.parseInt(v); } catch (Exception e) { return def; }
    }
}