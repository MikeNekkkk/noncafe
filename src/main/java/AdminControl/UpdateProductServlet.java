package AdminControl;

import Dao.Dao;
import Entity.Product;
import Entity.User;
import Service.EmbeddingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@WebServlet("/updateProduct")
@MultipartConfig(fileSizeThreshold = 2 * 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
public class UpdateProductServlet extends HttpServlet {

	private Dao dao;
	private static final String UPLOAD_DIR = "img/product/HT";

	@Override
	public void init() {
		dao = new Dao();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		if (!isAdmin(request)) {
			response.sendRedirect(request.getContextPath() + "/login?error=accessDenied");
			return;
		}
		response.setContentType("application/json; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		try {
			String productId = request.getParameter("productId");
			String productName = request.getParameter("productName");
			String stockStr = request.getParameter("stockQuantity");
			String priceStr = request.getParameter("price");
			String priceinStr = request.getParameter("pricein");
			String categoryId = request.getParameter("categoryId");
			String WeightGram = request.getParameter("WeightGram");
			String status = request.getParameter("status");
			Part filePart = request.getPart("productImage");

			String flavorStr = request.getParameter("flavor_intensity");
			String bitternessStr = request.getParameter("bitterness_level");
			String acidityStr = request.getParameter("acidity_level");
			String sweetnessStr = request.getParameter("sweetness_level");
			String roastLevel = request.getParameter("roast_level");
			String caffeineLevel = request.getParameter("caffeine_level");
			String flavorTags = request.getParameter("flavor_tags");
			String suitableFor = request.getParameter("suitable_for");
			String recommendedTime = request.getParameter("recommended_time");
			String grindSize = request.getParameter("grind_size");
			String processingMethod = request.getParameter("processing_method");
			String brewMethods = request.getParameter("brew_methods");

			if (productName == null || productName.trim().isEmpty())
				throw new IllegalArgumentException("Tên sản phẩm không được để trống!");

			int stock = Integer.parseInt(stockStr);
			if (stock < 0)
				throw new IllegalArgumentException("Số lượng không được âm!");

			BigDecimal price = new BigDecimal(priceStr);
			BigDecimal pricein = new BigDecimal(priceinStr);
			if (price.compareTo(BigDecimal.ZERO) <= 0)
				throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0!");

			Product oldProduct = dao.getProductById(productId);
			if (oldProduct == null)
				throw new IllegalArgumentException("Sản phẩm không tồn tại!");

			if (dao.isProductNameExistsExcept(productName.trim(), productId))
				throw new IllegalArgumentException("Tên sản phẩm đã tồn tại!");

			String imageUrl = oldProduct.getImageUrl();
			if (filePart != null && filePart.getSize() > 0) {
				String fileName = filePart.getSubmittedFileName();
				if (fileName != null && fileName.contains(".")) {
					String ext = fileName.substring(fileName.lastIndexOf("."));
					String newName = System.currentTimeMillis() + ext;
					String appPath = request.getServletContext().getRealPath("");
					File dir = new File(appPath + File.separator + UPLOAD_DIR);
					if (!dir.exists())
						dir.mkdirs();
					filePart.write(appPath + File.separator + UPLOAD_DIR + File.separator + newName);
					imageUrl = UPLOAD_DIR + "/" + newName;
					if (oldProduct.getImageUrl() != null && !oldProduct.getImageUrl().contains("default")) {
						new File(appPath + File.separator + oldProduct.getImageUrl()).delete();
					}
				}
			}

			Product product = new Product();
			product.setId(Long.parseLong(productId));
			product.setName(productName.trim());
			product.setStockQuantity(stock);
			product.setPrice(price);
			product.setPricein(pricein);
			product.setCategoryId(Long.parseLong(categoryId));
			product.setDetailDescription(oldProduct.getDetailDescription());
			product.setStatus(oldProduct.getStatus());
			product.setWeightGram(Integer.parseInt(WeightGram));
			product.setStatus(status);
			product.setImageUrl(imageUrl);

			boolean ok = dao.updateProduct(product);
			if (!ok) {
				response.getWriter().write("{\"success\":false,\"message\":\"Không thể cập nhật!\"}");
				return;
			}

			response.getWriter().write("{\"success\":true,\"message\":\"Cập nhật thành công!\"}");
			if (flavorStr != null && !flavorStr.isEmpty()) {

				final long fId = Long.parseLong(productId);
				final int fFlavor = parseInt(flavorStr, 3);
				final int fBitter = parseInt(bitternessStr, 3);
				final int fAcidity = parseInt(acidityStr, 3);
				final int fSweet = parseInt(sweetnessStr, 3);
				final String fRoast = roastLevel;
				final String fCaffeine = caffeineLevel;
				final String fTags = flavorTags;
				final String fSuitable = suitableFor;
				final String fTime = recommendedTime;
				final String fGrind = grindSize;
				final String fProc = processingMethod;
				final String fBrew = brewMethods;
				final String fName = productName.trim();
				final String fDesc = oldProduct.getDetailDescription() != null ? oldProduct.getDetailDescription() : "";
				final Dao fDao = dao;

				CompletableFuture.runAsync(() -> {
					try {
						fDao.saveProductProfile(fId, fFlavor, fBitter, fAcidity, fSweet, fRoast, fCaffeine, fTags,
								fSuitable, fTime, fGrind, fProc, fBrew);

					} catch (Exception ex) {
						System.err.println("[UpdateProduct] Lỗi: " + ex.getMessage());
					}
				});
			}

		} catch (NumberFormatException e) {
			response.getWriter().write("{\"success\":false,\"message\":\"Dữ liệu số không hợp lệ!\"}");
		} catch (IllegalArgumentException e) {
			response.getWriter().write("{\"success\":false,\"message\":\"" + esc(e.getMessage()) + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"success\":false,\"message\":\"Lỗi hệ thống!\"}");
		}
	}

	private int parseInt(String v, int d) {
		try {
			return Integer.parseInt(v);
		} catch (Exception e) {
			return d;
		}
	}

	private String esc(String t) {
		if (t == null)
			return "";
		return t.replace("\\", "\\\\").replace("\"", "\\\"");
	}

	private boolean isAdmin(HttpServletRequest request) {
		HttpSession s = request.getSession(false);
		if (s == null)
			return false;
		User u = (User) s.getAttribute("user");
		return u != null && "ADMIN".equals(u.getRole());
	}
}