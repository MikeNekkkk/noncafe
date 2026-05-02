package Dao;

import connection.DatabaseConnection;
import Entity.Product;
import Entity.ProductAI;
import Entity.StockMovementLog;
import Entity.TrackingLog;
import Entity.User;
import Entity.Blog;
import Entity.Category;
import Entity.ChatMessage;
import Entity.Contact;
import Entity.NCC;
import Entity.Order;
import Entity.OrderItem;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

public class Dao {
	private Connection connection;
	private static final String Product = null;
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	private Connection getConnection() throws Exception {
		return DatabaseConnection.getConnection();
	}

	public boolean iscategorynameExists(String username, Long id) throws Exception {
		String query = "SELECT id FROM category WHERE name = ?AND id!=?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setLong(2, id);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public String getCategoryNameById(long categoryId) {
	    String sql = "SELECT name FROM category WHERE id = ?";
	    try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, categoryId);
			rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getString("name");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	public boolean iscategorynameExists2(String username) throws Exception {
		String query = "SELECT id FROM category WHERE name =?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Category> getAllCategories2() throws Exception {
		List<Category> list = new ArrayList<>();
		String query = "SELECT id, name,status FROM category";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Category category = new Category(rs.getLong("id"), rs.getString("name"), rs.getString("status"));
				list.add(category);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<Category> getAllCategories() throws Exception {
		List<Category> list = new ArrayList<>();
		String query = """
				SELECT * FROM category WHERE status ="ACTIVE"
				""";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Category category = new Category(rs.getLong("id"), rs.getString("name"), rs.getString("status"));
				list.add(category);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public boolean iscategoryInAnyOrder(Long categoryId) {
		String sql = "SELECT COUNT(*) FROM product WHERE category_id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, categoryId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean addcategory(Category category) throws Exception {
		String query = """
				INSERT INTO category (name ,  status)
				VALUES (?,  'ACTIVE')
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, category.getName());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean softDeletecategory(Long categoryId) {
		String sql = "UPDATE category SET status = 'INACTIVE' WHERE id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, categoryId);

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean deletecategory(Long categoryId) {
		if (iscategoryInAnyOrder(categoryId)) {
			return softDeletecategory(categoryId);
		} else {
			String sql = "DELETE FROM category WHERE id = ?";
			try {
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setLong(1, categoryId);

				int rows = ps.executeUpdate();
				return rows > 0;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (ps != null)
						ps.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	public boolean updatecategory(Category category) throws Exception {
		String sql = """
				UPDATE category
				SET name = ?, status=?
				WHERE id = ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, category.getName());
			ps.setString(2, category.getStatus());
			ps.setLong(3, category.getId());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Product> getAllProducts() {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT p.id, p.category_id, c.name AS category_name, p.name, "
				+ "p.price,p.pricein , p.detail_description, p.image_url, p.weight_gram, p.stock_quantity - p.reserved AS stock_quantity,p.reserved , p.status "
				+ "FROM product p " + "LEFT JOIN category c ON p.category_id = c.id " + "WHERE p.status = 'ACTIVE' "
				+ "ORDER BY p.id DESC";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setCategoryId(rs.getLong("category_id"));
				product.setCategoryName(rs.getString("category_name"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getBigDecimal("price"));
				product.setPricein(rs.getBigDecimal("pricein"));
				product.setDetailDescription(rs.getString("detail_description"));
				product.setImageUrl(rs.getString("image_url"));
				product.setStockQuantity(rs.getInt("stock_quantity"));
				product.setReserved(rs.getInt("reserved"));
				product.setStatus(rs.getString("status"));
				product.setWeightGram(rs.getInt("weight_gram"));
				list.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public Product getProductById(String id) {
		String sql = "SELECT p.id, p.category_id, c.name AS category_name, p.name, "
				+ "p.price,p.pricein, p.detail_description, p.image_url, p.stock_quantity,p.weight_gram, p.status "
				+ "FROM product p " + "LEFT JOIN category c ON p.category_id = c.id " + "WHERE p.id = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setCategoryId(rs.getLong("category_id"));
				product.setCategoryName(rs.getString("category_name"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getBigDecimal("price"));
				product.setPricein(rs.getBigDecimal("pricein"));
				product.setDetailDescription(rs.getString("detail_description"));
				product.setImageUrl(rs.getString("image_url"));
				product.setStockQuantity(rs.getInt("stock_quantity"));
				product.setStatus(rs.getString("status"));
				product.setWeightGram(rs.getInt("weight_gram"));
				return product;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean isProductInAnyOrder(String productId) {
		String sql = "SELECT COUNT(*) FROM order_items WHERE product_id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, productId); // ✅ setString
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean isEmailNameExists(String email, String name) throws Exception {
		String query = "SELECT id FROM users WHERE email = ? or username=?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, name);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean softDeleteProduct(String productId) {
		String sql = "UPDATE product SET status = 'INACTIVE' WHERE id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, productId);

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean deleteProduct(String productId) {
		if (isProductInAnyOrder(productId)) {
			return softDeleteProduct(productId);
		} else {
			String sql = "DELETE FROM product WHERE id = ?";
			try {
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, productId);

				int rows = ps.executeUpdate();
				return rows > 0;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (ps != null)
						ps.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public List<Product> getSimilarProducts(String currentProductId, String categoryId, int limit) {
		List<Product> list = new ArrayList<>();
		String query = """
				SELECT
				    p.id, p.category_id, c.name AS category_name,
				    p.name, p.price, p.detail_description,
				    p.image_url, p.stock_quantity
				FROM
				    product p
				JOIN
				    category c ON p.category_id = c.id
				WHERE
				    p.category_id = ? AND p.id != ?
				ORDER BY RAND()
				LIMIT ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, categoryId);
			ps.setString(2, currentProductId);
			ps.setInt(3, limit);
			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getLong("id"));
				p.setCategoryId(rs.getLong("category_id"));
				p.setCategoryName(rs.getString("category_name"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getBigDecimal("price"));
				p.setDetailDescription(rs.getString("detail_description"));
				p.setImageUrl(rs.getString("image_url"));
				p.setStockQuantity(rs.getInt("stock_quantity"));
				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public List<Product> getAllProductsForAdmin() {
	    List<Product> list = new ArrayList<>();
	    String sql = "SELECT p.id, p.category_id, c.name AS category_name, p.name, "
	            + "p.price, p.pricein, p.detail_description, p.image_url, p.weight_gram, "
	            + "p.stock_quantity - p.reserved AS stock_quantity, p.reserved, p.status "
	            + "FROM product p "
	            + "LEFT JOIN category c ON p.category_id = c.id "
	            + "ORDER BY p.id DESC";

	    try {
	        conn = getConnection();
	        ps = conn.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Product product = new Product();
	            product.setId(rs.getLong("id"));
	            product.setCategoryId(rs.getLong("category_id"));
	            product.setCategoryName(rs.getString("category_name"));
	            product.setName(rs.getString("name"));
	            product.setPrice(rs.getBigDecimal("price"));
	            product.setPricein(rs.getBigDecimal("pricein"));
	            product.setDetailDescription(rs.getString("detail_description"));
	            product.setImageUrl(rs.getString("image_url"));
	            product.setStockQuantity(rs.getInt("stock_quantity"));
	            product.setReserved(rs.getInt("reserved"));
	            product.setStatus(rs.getString("status"));
	            product.setWeightGram(rs.getInt("weight_gram")); // ← đã có sẵn
	            list.add(product);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return list;
	}
	public List<Product> getProductsByCategoryId(String categoryId) {
		List<Product> list = new ArrayList<>();
		String query = """
				SELECT
				    p.id, p.category_id, c.name AS category_name,
				    p.name, p.price, p.detail_description,
				    p.image_url, p.stock_quantity
				FROM
				    product p
				JOIN
				    category c ON p.category_id = c.id
				WHERE
				    p.category_id = ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, categoryId);
			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setCategoryId(rs.getLong("category_id"));
				p.setCategoryName(rs.getString("category_name"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getBigDecimal("price"));
				p.setDetailDescription(rs.getString("detail_description"));
				p.setImageUrl(rs.getString("image_url"));
				p.setStockQuantity(rs.getInt("stock_quantity"));
				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public boolean updateProduct(Product product) {
		String sql = "UPDATE product SET name = ?, category_id = ?, price = ?,pricein = ?, "
				+ "stock_quantity = ?, image_url = ?, detail_description = ?, status = ?,weight_gram= ? " + "WHERE id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, product.getName());
			ps.setLong(2, product.getCategoryId());
			ps.setBigDecimal(3, product.getPrice());
			ps.setBigDecimal(4, product.getPricein());
			ps.setInt(5, product.getStockQuantity());
			ps.setString(6, product.getImageUrl());
			ps.setString(7, product.getDetailDescription());
			ps.setString(8, product.getStatus() != null ? product.getStatus() : "ACTIVE");
			ps.setInt(9, product.getWeightGram());
			ps.setLong(10, product.getId());

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean updateProductStock(Long Kho ,Long productId, int quantity, String note, String createdBy) throws Exception {

		Connection conn = null;
		PreparedStatement psUpdate = null;
		PreparedStatement psLog = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			PreparedStatement psSet = conn.prepareStatement("SET @skip_stock_log = 1");
			psSet.executeUpdate();
			String updateSQL = "UPDATE product " + "SET stock_quantity = stock_quantity + ? " + "WHERE id = ?";

			psUpdate = conn.prepareStatement(updateSQL);
			psUpdate.setInt(1, quantity);
			psUpdate.setLong(2, productId);

			int rowsAffected = psUpdate.executeUpdate();

			if (rowsAffected == 0) {
				conn.rollback();
				return false;
			}

			String movementType = quantity > 0 ? "IN" : "OUT";
			String logSQL = "INSERT INTO stock_movement_log "
					+ "(product_id, movement_type,NCC_id, quantity, note, created_by) " + "VALUES (?,?,?, ?, ?, ?)";

			psLog = conn.prepareStatement(logSQL);
			psLog.setLong(1, productId);
			psLog.setString(2, movementType);			
			if (Kho == null) {
			    psLog.setNull(3, java.sql.Types.BIGINT); 
			} else {
			    psLog.setLong(3, Kho);
			}			psLog.setInt(4, Math.abs(quantity));
			psLog.setString(5, note);
			psLog.setString(6, createdBy);
			psLog.executeUpdate();

			conn.commit();
			return true;

		} catch (Exception e) {
			if (conn != null) {
				conn.rollback();
			}
			throw e;

		} finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}

			try {
				if (psUpdate != null)
					psUpdate.close();
				if (psLog != null)
					psLog.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Product> searchProductsByName(String keyword) throws Exception {
		List<Product> list = new ArrayList<>();
		String query = """
				SELECT
				    p.id, p.category_id, c.name AS category_name,
				    p.name, p.price, p.detail_description,
				    p.image_url, p.stock_quantity
				FROM
				    product p
				JOIN
				    category c ON p.category_id = c.id
				WHERE
				    p.name LIKE ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + keyword + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getLong("id"));
				p.setCategoryId(rs.getLong("category_id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getBigDecimal("price"));
				p.setDetailDescription(rs.getString("detail_description"));
				p.setImageUrl(rs.getString("image_url"));
				p.setStockQuantity(rs.getInt("stock_quantity"));
				p.setCategoryName(rs.getString("category_name"));
				list.add(p);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<Product> ProductsSoldout() throws Exception {
		List<Product> list = new ArrayList<>();
		String query = """
				SELECT
				    p.id, p.category_id, c.name AS category_name,
				    p.name, p.price, p.detail_description,
				    p.image_url, p.stock_quantity
				FROM
				    product p
				JOIN
				    category c ON p.category_id = c.id
				WHERE
				    p.stock_quantity = 0
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getLong("id"));
				p.setCategoryId(rs.getLong("category_id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getBigDecimal("price"));
				p.setDetailDescription(rs.getString("detail_description"));
				p.setImageUrl(rs.getString("image_url"));
				p.setStockQuantity(rs.getInt("stock_quantity"));
				p.setCategoryName(rs.getString("category_name"));
				list.add(p);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<Order> getOrdersbyiduser(long id) throws Exception {
		List<Order> list = new ArrayList<>();
		String query = """
				SELECT orders.id, orders.user_id, first_name, last_name, orders.email, orders.phone, orders.address,
				       orders.city, orders.state, total_amount, orders.status, payment_method,
				       orders.created_at, orders.updated_at
				FROM orders
				            join users us on us.id =  orders.user_id
				where orders.user_id = ? ;
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setFirstName(rs.getString("first_name"));
				order.setLastName(rs.getString("last_name"));
				order.setEmail(rs.getString("email"));
				order.setPhone(rs.getString("phone"));
				order.setAddress(rs.getString("address"));
				order.setCity(rs.getString("city"));
				order.setState(rs.getString("state"));
				order.setTotalAmount(rs.getBigDecimal("total_amount"));
				order.setStatus(rs.getString("status"));
				order.setPaymentMethod(rs.getString("payment_method"));
				order.setCreatedAt(rs.getTimestamp("created_at"));
				order.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(order);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Order> getAllOrders() throws Exception {
		List<Order> list = new ArrayList<>();
		String query = """
				SELECT id, first_name, last_name, email, phone, address,
				       city, state, total_amount, status, payment_method,
				       created_at, updated_at
				FROM orders
				ORDER BY created_at DESC
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setFirstName(rs.getString("first_name"));
				order.setLastName(rs.getString("last_name"));
				order.setEmail(rs.getString("email"));
				order.setPhone(rs.getString("phone"));
				order.setAddress(rs.getString("address"));
				order.setCity(rs.getString("city"));
				order.setState(rs.getString("state"));
				order.setTotalAmount(rs.getBigDecimal("total_amount"));
				order.setStatus(rs.getString("status"));
				order.setPaymentMethod(rs.getString("payment_method"));
				order.setCreatedAt(rs.getTimestamp("created_at"));
				order.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(order);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Order> getAllOrdersLN() throws Exception {
		List<Order> list = new ArrayList<>();
		String query = """
				SELECT
				    o.id, o.first_name, o.last_name, o.status, o.created_at, o.total_amount,
				    GROUP_CONCAT(oi.product_name SEPARATOR ', ') AS all_product_names,
				    SUM(COALESCE(oi.quantity, 0)) AS total_quantity,
				    SUM(COALESCE(oi.price * oi.quantity - oi.pricein * oi.quantity, 0)) AS total_profit
				FROM orders o
				LEFT JOIN order_items oi ON o.id = oi.order_id
				WHERE o.status = 'COMPLETED'
				 GROUP BY o.id, o.first_name, o.last_name, o.status, o.created_at, o.total_amount
				 ORDER BY o.created_at DESC
				""";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setFirstName(rs.getString("first_name"));
				order.setLastName(rs.getString("last_name"));
				order.setTotalAmount(rs.getBigDecimal("total_amount"));
				order.setStatus(rs.getString("status"));
				order.setAllProductNames(rs.getString("all_product_names"));
				order.setCreatedAt(rs.getTimestamp("created_at"));
				order.setQuantity(rs.getInt("total_quantity")); // Lấy từ AS total_quantity
				order.setTotalProfit(rs.getBigDecimal("total_profit"));
				list.add(order);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Order> getOrdersByDate(String type, String date) throws Exception {
		List<Order> list = new ArrayList<>();
		String condition = "";

		if (type != null)
			type = type.trim().toLowerCase();

		if ("year".equals(type) && date != null && date.length() >= 4) {
			date = date.substring(0, 4);
		}
		System.out.println("TYPE = " + type);
		System.out.println("DATE = " + date);
		switch (type) {
		case "day":
			condition = " DATE(o.created_at) = ? ";
			break;
		case "month":
			condition = " DATE_FORMAT(o.created_at, '%Y-%m') = ? ";
			break;
		case "year":
			condition = " YEAR(o.created_at) = ? ";
			break;
		default:
			throw new Exception("Type không hợp lệ: " + type);
		}

		String query = "SELECT " + "    o.id, o.first_name, o.last_name, o.status, o.created_at, o.total_amount, "
				+ "    GROUP_CONCAT(oi.product_name SEPARATOR ',  ') AS all_product_names, "
				+ "    SUM(oi.quantity) AS total_quantity, "
				+ "    SUM(oi.price * oi.quantity - oi.pricein * oi.quantity) AS total_profit " + "FROM orders o "
				+ "LEFT JOIN order_items oi ON o.id = oi.order_id " + "WHERE " + condition
				+ " AND o.status= 'COMPLETED' "
				+ " GROUP BY o.id, o.first_name, o.last_name, o.status, o.created_at, o.total_amount "
				+ " ORDER BY o.created_at DESC";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, date);
			rs = ps.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setFirstName(rs.getString("first_name"));
				order.setLastName(rs.getString("last_name"));
				order.setTotalAmount(rs.getBigDecimal("total_amount"));
				order.setStatus(rs.getString("status"));
				order.setAllProductNames(rs.getString("all_product_names"));
				order.setCreatedAt(rs.getTimestamp("created_at"));
				order.setQuantity(rs.getInt("total_quantity"));
				order.setTotalProfit(rs.getBigDecimal("total_profit"));
				list.add(order);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return list;
	}

	public List<Double> getRevenueByMonth() throws Exception {
	    List<Double> list = new ArrayList<>();

	    String sql = "SELECT MONTH(created_at) as m, SUM(total_amount) as total "
	               + "FROM orders WHERE status='COMPLETED' "
	               + "GROUP BY MONTH(created_at) ORDER BY m";

	    double[] months = new double[12];

	    try {
	        conn = getConnection(); // 🔥 giống hàm trên
	        ps = conn.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            int m = rs.getInt("m");
	            months[m - 1] = rs.getDouble("total");
	        }

	    } finally {
	        if (rs != null)
	            rs.close();
	        if (ps != null)
	            ps.close();
	        if (conn != null)
	            conn.close();
	    }

	    for (double d : months) {
	        list.add(d);
	    }

	    return list;
	}
	public List<OrderItem> getOrderItemsByOrderId(Long orderId) throws Exception {
		List<OrderItem> list = new ArrayList<>();
		String query = """
				SELECT oi.id, oi.order_id, oi.product_id, oi.product_name,
				       oi.price, oi.quantity, oi.subtotal,
				       COALESCE(p.weight_gram, 250) AS weight_gram
				FROM order_items oi
				LEFT JOIN product p ON p.id = oi.product_id
				WHERE oi.order_id = ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setLong(1, orderId);
			rs = ps.executeQuery();

			while (rs.next()) {
				OrderItem item = new OrderItem();
				item.setId(rs.getLong("id"));
				item.setOrderId(rs.getLong("order_id"));
				item.setProductId(rs.getLong("product_id"));
				item.setProductName(rs.getString("product_name"));
				item.setPrice(rs.getBigDecimal("price"));
				item.setQuantity(rs.getInt("quantity"));
				item.setSubtotal(rs.getBigDecimal("subtotal"));
				item.setWeightGram(rs.getInt("weight_gram")); // weight 1 đơn vị sản phẩm
				list.add(item);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public boolean deleteOrder(Long orderId) {
		try {
			Order order = getOrderById(orderId);

			if (order == null) {
				throw new RuntimeException("Đơn hàng không tồn tại!");
			}

			if ("COMPLETED".equals(order.getStatus())) {
				throw new RuntimeException("Không thể xóa đơn hàng đã hoàn thành!");
			}

			String sql = "DELETE FROM orders WHERE id = ?";
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, orderId);

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Lỗi xóa đơn hàng: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean isNCCInAnyOrder(Long NCCId) {
		String sql = "SELECT COUNT(*) FROM stock_movement_log WHERE NCC_id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, NCCId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean isNCCnameExistsExcept(String username, Long userId) throws Exception {
		String query = "SELECT id FROM NCC WHERE nameKho = ? AND id != ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setLong(2, userId);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isUsernameExistsExcept(String username, Long userId) throws Exception {
		String query = "SELECT id FROM users WHERE username = ? AND id != ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setLong(2, userId);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Tìm kiếm đơn hàng theo từ khóa
	 */
	public List<Order> searchOrders(String keyword) throws Exception {
		List<Order> list = new ArrayList<>();
		String query = """
				SELECT id, first_name, last_name, email, phone, address,
				       city, state, total_amount, status, payment_method,
				       created_at, updated_at
				FROM orders
				WHERE first_name LIKE ? OR last_name LIKE ?
				   OR email LIKE ? OR phone LIKE ?
				ORDER BY created_at DESC
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			String searchPattern = "%" + keyword + "%";
			ps.setString(1, searchPattern);
			ps.setString(2, searchPattern);
			ps.setString(3, searchPattern);
			ps.setString(4, searchPattern);
			rs = ps.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setFirstName(rs.getString("first_name"));
				order.setLastName(rs.getString("last_name"));
				order.setEmail(rs.getString("email"));
				order.setPhone(rs.getString("phone"));
				order.setAddress(rs.getString("address"));
				order.setCity(rs.getString("city"));
				order.setState(rs.getString("state"));
				order.setTotalAmount(rs.getBigDecimal("total_amount"));
				order.setStatus(rs.getString("status"));
				order.setPaymentMethod(rs.getString("payment_method"));
				order.setCreatedAt(rs.getTimestamp("created_at"));
				order.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(order);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Order getLatestOrderByUserId(Long userId) {
		String sql = """
				    SELECT id, first_name, last_name, email, phone, address,
				           city, state, district, ward, total_amount,
				           status, payment_method, created_at
				    FROM orders
				    WHERE user_id = ?
				    ORDER BY created_at DESC
				    LIMIT 1
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, userId);
			rs = ps.executeQuery();

			if (rs.next()) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setAddress(rs.getString("address"));
				order.setCity(rs.getString("city"));
				order.setState(rs.getString("state"));
				order.setDistrict(rs.getString("district"));
				order.setWard(rs.getString("ward"));
				return order;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getProductStock(Long productId) throws Exception {
		String sql = "SELECT stock_quantity FROM product WHERE id = ?";

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setLong(1, productId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("stock_quantity");
			}

			return 0;
		}
	}

	public Long createOrder(Order order, List<OrderItem> orderItems) throws Exception {

		try {
			PreparedStatement psCheckStock = null;
			conn = getConnection();
			conn.setAutoCommit(false);
			String checkStockSQl = "SELECT id, name, stock_quantity from product where id=? and status = 'ACTIVE' for update";
			psCheckStock = conn.prepareStatement(checkStockSQl);
			for (OrderItem item : orderItems) {
				psCheckStock.setLong(1, item.getProductId());
				rs = psCheckStock.executeQuery();
				if (rs.next()) {
					int currentStock = rs.getInt("stock_quantity");
					String productName = rs.getString("name");
					if (currentStock < item.getQuantity()) {
						conn.rollback();
						throw new Exception("Sản phẩm " + productName + " không đủ số lượng trong kho.");
					}
				} else {
					conn.rollback();
					throw new Exception("Sản phẩm ID " + item.getProductId() + " không tồn tại hoặc đã ngưng bán");
				}
				rs.close();
			}

			String orderQuery = """
					INSERT INTO orders (user_id,first_name, last_name, company, address, city,
					                   state,ward,district, email, phone, notes, total_amount,
					                   status, payment_method, ghn_district_id, ghn_ward_code, ship_fee, created_at)
					VALUES (?, ?, ?, ?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
					""";

			Long orderId = null;
			ps = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
			if (order.getUserId() != null) {
				ps.setLong(1, order.getUserId());
			} else {
				ps.setNull(1, java.sql.Types.BIGINT);
			}
			ps.setString(2, order.getFirstName());
			ps.setString(3, order.getLastName());
			ps.setString(4, order.getCompany());
			ps.setString(5, order.getAddress());
			ps.setString(6, order.getCity());
			ps.setString(7, order.getState());
			ps.setString(8, order.getWard());
			ps.setString(9, order.getDistrict());
			ps.setString(10, order.getEmail());
			ps.setString(11, order.getPhone());
			ps.setString(12, order.getNotes());
			ps.setBigDecimal(13, order.getTotalAmount());
			ps.setString(14, order.getStatus());
			ps.setString(15, order.getPaymentMethod());
			// GHN fields
			if (order.getGhnDistrictId() != null) ps.setInt(16, order.getGhnDistrictId());
			else                                   ps.setNull(16, java.sql.Types.INTEGER);
			ps.setString(17, order.getGhnWardCode() != null ? order.getGhnWardCode() : "");
			if (order.getShipFee() != null) ps.setBigDecimal(18, order.getShipFee());
			else                            ps.setBigDecimal(18, java.math.BigDecimal.ZERO);

			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				conn.rollback();
				throw new SQLException("Tạo đơn hàng thất bại!");
			}

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getLong(1);
			} else {
				conn.rollback();
				throw new SQLException("Không lấy được order_id!");
			}
			ps.close();

			if (orderId == null) {
				throw new SQLException("orderId is NULL - cannot create order items!");
			}

			String orderItemQuery = """
					INSERT INTO order_items (order_id, product_id, product_name, price, pricein, quantity, subtotal)
					VALUES (?, ?, ?, ?, ?, ?, ?)
					""";
			String updatereserved = "UPDATE product SET reserved = reserved + ?  WHERE id = ?";

			PreparedStatement psupdatereserved = null;
			ps = conn.prepareStatement(orderItemQuery);
			psupdatereserved = conn.prepareStatement(updatereserved);

			for (OrderItem item : orderItems) {
				ps.setLong(1, orderId);
				ps.setLong(2, item.getProductId());
				ps.setString(3, item.getProductName());
				ps.setBigDecimal(4, item.getPrice());
				ps.setBigDecimal(5, item.getPricein());
				ps.setInt(6, item.getQuantity());
				ps.setBigDecimal(7, item.getSubtotal());
				ps.addBatch();
				psupdatereserved.setInt(1, item.getQuantity());
				psupdatereserved.setLong(2, item.getProductId());
				psupdatereserved.addBatch();
			}

			ps.executeBatch();
			psupdatereserved.executeBatch();

			conn.commit();
			return orderId;

		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean updateOrderStatus(Long orderId, String status, String paymentMethod) {
		try {
			Order currentOrder = getOrderById(orderId);

			if (currentOrder == null) {
				throw new RuntimeException("Đơn hàng không tồn tại!");
			}

			StringBuilder sql = new StringBuilder(
					"UPDATE orders SET status = ?, payment_method = ?, updated_at = NOW() WHERE id = ?");
			conn = getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, status);
			ps.setString(2, paymentMethod);
			ps.setLong(3, orderId);
			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Lỗi cập nhật đơn hàng: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Order getOrderById(Long orderId) {
		String sql = "SELECT * FROM orders WHERE id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, orderId);
			rs = ps.executeQuery();

			if (rs.next()) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setStatus(rs.getString("status"));
				order.setPaymentMethod(rs.getString("payment_method"));
				order.setTotalAmount(rs.getBigDecimal("total_amount"));
				order.setFirstName(rs.getString("first_name"));
				order.setLastName(rs.getString("last_name"));
				order.setEmail(rs.getString("email"));
				order.setPhone(rs.getString("phone"));
				order.setAddress(rs.getString("address"));
				order.setCity(rs.getString("city"));
				order.setState(rs.getString("state"));
				order.setNotes(rs.getString("notes"));
				order.setCreatedAt(rs.getTimestamp("created_at"));
				try {
					order.setGhnDistrictId(rs.getInt("ghn_district_id"));
				} catch (Exception ignored) {
				}
				try {
					order.setGhnWardCode(rs.getString("ghn_ward_code"));
				} catch (Exception ignored) {
				}
				try {
					order.setShipFee(rs.getBigDecimal("ship_fee"));
				} catch (Exception ignored) {
				}
				try {
					order.setGhnOrderCode(rs.getString("ghn_order_code"));
				} catch (Exception ignored) {
				}
				return order;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Product> getProductsByCategoryId4() {

		List<Product> list = new ArrayList<>();

		String query = """

				SELECT

				    p.id, p.category_id, c.name AS category_name,
				    p.name, p.price, p.detail_description,
				    p.image_url, p.stock_quantity
				FROM
				    product p
				JOIN
				    category c ON p.category_id = c.id

				WHERE
				    p.category_id = 4

				""";
		try {

			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getLong("id"));
				p.setCategoryId(rs.getLong("category_id"));
				p.setCategoryName(rs.getString("category_name"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getBigDecimal("price"));
				p.setDetailDescription(rs.getString("detail_description"));
				p.setImageUrl(rs.getString("image_url"));
				p.setStockQuantity(rs.getInt("stock_quantity"));
				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// user
	public static String md5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(password.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Đăng nhập user
	 */
	public User login(String username, String password) throws Exception {
		String query = """
				SELECT * FROM users
				WHERE username = ? AND password = ? AND status = 'ACTIVE'
				""";

		User user = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, md5(password)); // Mã hóa password
			rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setFullName(rs.getString("full_name"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setRole(rs.getString("role"));
				user.setStatus(rs.getString("status"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				user.setUpdatedAt(rs.getTimestamp("updated_at"));
			}

			return user;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean addUser(User user) throws Exception {
		String query = """
				INSERT INTO users (username, password, email, full_name, phone,
				                  address, role, status)
				VALUES (?, ?, ?, ?, ?, ?, 'USER', 'ACTIVE')
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, md5(user.getPassword()));
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getFullName());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getAddress());

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Cập nhật thông tin user
	 */
	public boolean updateUser(User user) throws Exception {
		String sql = """
				UPDATE users
				SET username = ?, email = ?, full_name = ?, phone = ?,
				    address = ?, updated_at = NOW()
				WHERE id = ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getFullName());
			ps.setString(4, user.getPhone());
			ps.setString(5, user.getAddress());
			ps.setLong(6, user.getId());

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<User> AllUser() throws Exception {
		List<User> list = new ArrayList<>();

		String query = """
				SELECT * FROM users where role = 'USER'
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setFullName(rs.getString("full_name"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setRole(rs.getString("role"));
				user.setStatus(rs.getString("status"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				user.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(user);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//NCC
	public List<NCC> AllNCC() throws Exception {
		List<NCC> list = new ArrayList<>();

		String query = """
				SELECT * FROM NCC
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				NCC NCC = new NCC();
				NCC.setId(rs.getLong("id"));
				NCC.setNameKho(rs.getString("nameKho"));
				NCC.setNameQuanKho(rs.getString("nameQuankho"));
				;
				NCC.setPhone(rs.getString("phone"));
				NCC.setAddress(rs.getString("address"));
				NCC.setStatus(rs.getString("status"));
				NCC.setCreatedAt(rs.getTimestamp("created_at"));
				NCC.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(NCC);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public List<NCC> AllNCCfornhap() throws Exception {
		List<NCC> list = new ArrayList<>();

		String query = """
				SELECT * FROM NCC WHERE status ="ACTIVE"
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				NCC NCC = new NCC();
				NCC.setId(rs.getLong("id"));
				NCC.setNameKho(rs.getString("nameKho"));
				NCC.setNameQuanKho(rs.getString("nameQuankho"));
				;
				NCC.setPhone(rs.getString("phone"));
				NCC.setAddress(rs.getString("address"));
				NCC.setStatus(rs.getString("status"));
				NCC.setCreatedAt(rs.getTimestamp("created_at"));
				NCC.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(NCC);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean updateNCC(NCC NCC) throws Exception {
		String sql = """
				UPDATE NCC
				SET nameKho = ?, nameQuanKho = ?, phone = ?,
				    address = ?,status=?, updated_at = NOW()
				WHERE id = ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, NCC.getNameKho());
			ps.setString(2, NCC.getNameQuanKho());
			ps.setString(3, NCC.getPhone());
			ps.setString(4, NCC.getAddress());
			ps.setString(5, NCC.getStatus());
			ps.setLong(6, NCC.getId());

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean addNCC(NCC NCC) throws Exception {
		String query = """
				INSERT INTO NCC (nameKho , nameQuanKho , phone,
				                  address, status)
				VALUES (?, ?, ?, ?,  'ACTIVE')
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, NCC.getNameKho());
			ps.setString(2, NCC.getNameQuanKho());
			ps.setString(3, NCC.getPhone());
			ps.setString(4, NCC.getAddress());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean softDeleteNCC(Long NCCId) {
		String sql = "UPDATE ncc SET status = 'INACTIVE' WHERE id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, NCCId);

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean deleteNCC(Long NCCId) {
		if (isNCCInAnyOrder(NCCId)) {
			return softDeleteNCC(NCCId);
		} else {
			String sql = "DELETE FROM ncc WHERE id = ?";
			try {
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setLong(1, NCCId);

				int rows = ps.executeUpdate();
				return rows > 0;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (ps != null)
						ps.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	/**
	 * Đăng ký user mới
	 */
	public boolean register(User user) throws Exception {
		String query = """
				INSERT INTO users (username, password, email, full_name, phone,
				                  address, role, status)
				VALUES (?, ?, ?, ?, ?, ?, 'USER', 'ACTIVE')
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, md5(user.getPassword())); // Mã hóa password
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getFullName());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getAddress());

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean deleteUser(Long userId) throws Exception {
		String sql = "DELETE FROM users WHERE id = ? AND role = 'USER'";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, userId);

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isSDTExists(String phone) throws Exception {
		String query = "SELECT id FROM users WHERE phone = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, phone);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Kiểm tra email tồn tại (trừ user hiện tại)
	 */
	public boolean isEmailExistsExcept(String email, Long userId) throws Exception {
		String query = "SELECT id FROM users WHERE email = ? AND id != ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setLong(2, userId);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isOrderNotComplete(String email, String phone) throws Exception {
		String query = "SELECT status FROM orders WHERE email = ? AND phone = ? AND status IN ('PENDING','PROCESSING')";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, phone);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Kiểm tra username đã tồn tại chưa
	 */
	public boolean isUsernameExists(String username) throws Exception {
		String query = "SELECT id FROM users WHERE username = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isNCCnameExists(String username) throws Exception {
		String query = "SELECT id FROM NCC WHERE nameKho = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isEmailExists(String email) throws Exception {
		String query = "SELECT id FROM users WHERE email = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();

			return rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public User getUserById(Long userId) throws Exception {
		String query = "SELECT * FROM users WHERE id = ?";
		User user = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setLong(1, userId);
			rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setFullName(rs.getString("full_name"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setRole(rs.getString("role"));
				user.setStatus(rs.getString("status"));
				user.setCreatedAt(rs.getTimestamp("created_at"));

			}

			return user;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getTotalCustomers() throws Exception {
		String query = "SELECT COUNT(*) AS total FROM users WHERE role = 'USER'";
		int total = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt("total");
			}

			return total;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Đếm tổng số sản phẩm
	 */
	public int getTotalProducts() throws Exception {
		String query = "SELECT COUNT(*) AS total FROM product";
		int total = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt("total");
			}

			return total;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Đếm tổng số đơn hàng
	 */
	public int getTotalOrders() throws Exception {
		String query = "SELECT COUNT(*) AS total FROM orders";
		int total = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt("total");
			}

			return total;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Đếm số sản phẩm sắp hết hàng (stock_quantity < 10)
	 */
	public int getLowStockProducts() throws Exception {
		String query = "SELECT COUNT(*) AS total FROM product WHERE stock_quantity < 10";
		int total = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt("total");
			}

			return total;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public List<User> getRecentCustomers(int limit) throws Exception {
		List<User> list = new ArrayList<>();
		String query = """
				SELECT id, username,address, full_name, phone, email, created_at
				FROM users
				WHERE role = 'USER'
				ORDER BY created_at DESC
				LIMIT ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, limit);
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setFullName(rs.getString("full_name"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setEmail(rs.getString("email"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				list.add(user);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Order> getRecentOrders(int limit) throws Exception {
		List<Order> list = new ArrayList<>();
		String query = """
				SELECT id, first_name, last_name, total_amount, status, created_at
				FROM orders
				ORDER BY created_at DESC
				LIMIT ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, limit);
			rs = ps.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				order.setId(rs.getLong("id"));
				order.setFirstName(rs.getString("first_name"));
				order.setLastName(rs.getString("last_name"));
				order.setTotalAmount(rs.getBigDecimal("total_amount"));
				order.setStatus(rs.getString("status"));
				order.setCreatedAt(rs.getTimestamp("created_at"));
				list.add(order);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// ==================== BLOG METHODS (THÊM VÀO DAO.JAVA) ====================

	public List<Blog> getAllBlogs() throws Exception {
		List<Blog> list = new ArrayList<>();
		String query = """
				SELECT * FROM blog  where status ='PUBLISHED'
				ORDER BY published_at DESC
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Blog blog = new Blog();
				blog.setId(rs.getLong("id"));
				blog.setTitle(rs.getString("title"));
				blog.setSlug(rs.getString("slug"));
				blog.setThumbnailUrl(rs.getString("thumbnail_url"));
				blog.setContent(rs.getString("content"));
				blog.setExcerpt(rs.getString("excerpt"));
				blog.setAuthorId(rs.getLong("author_id"));
				blog.setAuthorName(rs.getString("author_name"));
				blog.setCategory(rs.getString("category"));
				blog.setViewCount(rs.getInt("view_count"));
				blog.setStatus(rs.getString("status"));
				blog.setPublishedAt(rs.getTimestamp("published_at"));
				blog.setCreatedAt(rs.getTimestamp("created_at"));
				blog.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(blog);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Blog> getAllBlogsAD() throws Exception {
		List<Blog> list = new ArrayList<>();
		String query = """
				SELECT * FROM blog
				ORDER BY published_at DESC
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Blog blog = new Blog();
				blog.setId(rs.getLong("id"));
				blog.setTitle(rs.getString("title"));
				blog.setSlug(rs.getString("slug"));
				blog.setThumbnailUrl(rs.getString("thumbnail_url"));
				blog.setContent(rs.getString("content"));
				blog.setExcerpt(rs.getString("excerpt"));
				blog.setAuthorId(rs.getLong("author_id"));
				blog.setAuthorName(rs.getString("author_name"));
				blog.setCategory(rs.getString("category"));
				blog.setViewCount(rs.getInt("view_count"));
				blog.setStatus(rs.getString("status"));
				blog.setPublishedAt(rs.getTimestamp("published_at"));
				blog.setCreatedAt(rs.getTimestamp("created_at"));
				blog.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(blog);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Lấy blog theo ID
	 */
	public Blog getBlogById(Long id) throws Exception {
		String query = "SELECT * FROM blog WHERE id = ?";
		Blog blog = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				blog = new Blog();
				blog.setId(rs.getLong("id"));
				blog.setTitle(rs.getString("title"));
				blog.setSlug(rs.getString("slug"));
				blog.setThumbnailUrl(rs.getString("thumbnail_url"));
				blog.setContent(rs.getString("content"));
				blog.setExcerpt(rs.getString("excerpt"));
				blog.setAuthorId(rs.getLong("author_id"));
				blog.setAuthorName(rs.getString("author_name"));
				blog.setCategory(rs.getString("category"));
				blog.setViewCount(rs.getInt("view_count"));
				blog.setStatus(rs.getString("status"));
				blog.setPublishedAt(rs.getTimestamp("published_at"));
				blog.setCreatedAt(rs.getTimestamp("created_at"));
				blog.setUpdatedAt(rs.getTimestamp("updated_at"));
			}

			return blog;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Thêm blog mới
	 */
	public boolean addBlog(Blog blog) throws Exception {
		String sql = """
				INSERT INTO blog (title, slug, thumbnail_url, content, excerpt,
				                 author_id, author_name, category, status)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, blog.getTitle());
			ps.setString(2, blog.getSlug());
			ps.setString(3, blog.getThumbnailUrl());
			ps.setString(4, blog.getContent());
			ps.setString(5, blog.getExcerpt());
			ps.setLong(6, blog.getAuthorId());
			ps.setString(7, blog.getAuthorName());
			ps.setString(8, blog.getCategory());
			ps.setString(9, blog.getStatus());

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Cập nhật blog
	 */
	public boolean updateBlog(Blog blog) throws Exception {
		String sql = """
				UPDATE blog
				SET title = ?, slug = ?, thumbnail_url = ?, content = ?,
				    excerpt = ?, category = ?, status = ?
				WHERE id = ?
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, blog.getTitle());
			ps.setString(2, blog.getSlug());
			ps.setString(3, blog.getThumbnailUrl());
			ps.setString(4, blog.getContent());
			ps.setString(5, blog.getExcerpt());
			ps.setString(6, blog.getCategory());
			ps.setString(7, blog.getStatus());
			ps.setLong(8, blog.getId());

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Xóa blog
	 */
	public boolean deleteBlog(Long id) throws Exception {
		String sql = "DELETE FROM blog WHERE id = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String createSlug(String title) {
		String slug = title.toLowerCase();
		slug = slug.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
		slug = slug.replaceAll("[èéẹẻẽêềếệểễ]", "e");
		slug = slug.replaceAll("[ìíịỉĩ]", "i");
		slug = slug.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
		slug = slug.replaceAll("[ùúụủũưừứựửữ]", "u");
		slug = slug.replaceAll("[ỳýỵỷỹ]", "y");
		slug = slug.replaceAll("đ", "d");
		slug = slug.replaceAll("[^a-z0-9\\s-]", "");
		slug = slug.trim().replaceAll("\\s+", "-");
		return slug;
	}

	public List<Blog> searchBlogs(String keyword) throws Exception {
		List<Blog> list = new ArrayList<>();
		String query = """
				SELECT * FROM blog
				WHERE title LIKE ? OR content LIKE ? OR category LIKE ?
				ORDER BY published_at DESC
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			String searchPattern = "%" + keyword + "%";
			ps.setString(1, searchPattern);
			ps.setString(2, searchPattern);
			ps.setString(3, searchPattern);
			rs = ps.executeQuery();

			while (rs.next()) {
				Blog blog = new Blog();
				blog.setId(rs.getLong("id"));
				blog.setTitle(rs.getString("title"));
				blog.setSlug(rs.getString("slug"));
				blog.setThumbnailUrl(rs.getString("thumbnail_url"));
				blog.setContent(rs.getString("content"));
				blog.setExcerpt(rs.getString("excerpt"));
				blog.setAuthorName(rs.getString("author_name"));
				blog.setCategory(rs.getString("category"));
				blog.setStatus(rs.getString("status"));
				blog.setPublishedAt(rs.getTimestamp("published_at"));
				list.add(blog);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//    contact : liên hệ
	public boolean createContact(Contact contact) throws Exception {
		String sql = """
				INSERT INTO contacts (user_id, name, email, subject, message, status)
				VALUES (?, ?, ?, ?, ?, ?)
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);

			if (contact.getUserId() != null) {
				ps.setLong(1, contact.getUserId());
			} else {
				ps.setNull(1, java.sql.Types.BIGINT);
			}

			ps.setString(2, contact.getName());
			ps.setString(3, contact.getEmail());
			ps.setString(4, contact.getSubject());
			ps.setString(5, contact.getMessage());
			ps.setString(6, contact.getStatus() != null ? contact.getStatus() : "NEW");

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Contact> getAllContacts() throws Exception {
		List<Contact> list = new ArrayList<>();
		String query = """
				SELECT c.*, u.username
				FROM contacts c
				LEFT JOIN users u ON c.user_id = u.id
				ORDER BY c.created_at DESC
				""";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Contact contact = new Contact();
				contact.setId(rs.getLong("id"));
				contact.setUserId(rs.getLong("user_id"));
				contact.setName(rs.getString("name"));
				contact.setEmail(rs.getString("email"));
				contact.setSubject(rs.getString("subject"));
				contact.setMessage(rs.getString("message"));
				contact.setStatus(rs.getString("status"));
				contact.setCreatedAt(rs.getTimestamp("created_at"));
				contact.setUpdatedAt(rs.getTimestamp("updated_at"));
				list.add(contact);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Contact getContactById(Long id) throws Exception {
		String query = "SELECT * FROM contacts WHERE id = ?";
		Contact contact = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				contact = new Contact();
				contact.setId(rs.getLong("id"));
				contact.setUserId(rs.getLong("user_id"));
				contact.setName(rs.getString("name"));
				contact.setEmail(rs.getString("email"));
				contact.setSubject(rs.getString("subject"));
				contact.setMessage(rs.getString("message"));
				contact.setStatus(rs.getString("status"));
				contact.setCreatedAt(rs.getTimestamp("created_at"));
				contact.setUpdatedAt(rs.getTimestamp("updated_at"));
			}

			return contact;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean updateContactStatus(Long id, String status) throws Exception {
		String sql = "UPDATE contacts SET status = ?, updated_at = NOW() WHERE id = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, status);
			ps.setLong(2, id);

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean deleteContact(Long id) throws Exception {
		String sql = "DELETE FROM contacts WHERE id = ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Đếm tổng số liên hệ
	 */
	public int getTotalContacts() throws Exception {
		String query = "SELECT COUNT(*) AS total FROM contacts";
		int total = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt("total");
			}

			return total;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getNewContactsCount() throws Exception {
		String query = "SELECT COUNT(*) AS total FROM contacts WHERE status = 'NEW'";
		int total = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt("total");
			}

			return total;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Lưu tin nhắn chat vào database
	 */
	public boolean saveChatMessage(ChatMessage chatMessage) {
		String sql = "INSERT INTO chatbot_history (session_id, user_id, user_message, bot_response) "
				+ "VALUES (?, ?, ?, ?)";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, chatMessage.getSessionId());

			if (chatMessage.getUserId() != null) {
				ps.setLong(2, chatMessage.getUserId());
			} else {
				ps.setNull(2, java.sql.Types.BIGINT);
			}

			ps.setString(3, chatMessage.getUserMessage());
			ps.setString(4, chatMessage.getBotResponse());

			int result = ps.executeUpdate();
			return result > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<ChatMessage> getChatHistory(String sessionId, int limit) {
		List<ChatMessage> history = new ArrayList<>();
		String sql = "SELECT * FROM chatbot_history " + "WHERE session_id = ? " + "ORDER BY created_at DESC LIMIT ?";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, sessionId);
			ps.setInt(2, limit);

			rs = ps.executeQuery();

			while (rs.next()) {
				ChatMessage msg = new ChatMessage();
				msg.setId(rs.getLong("id"));
				msg.setSessionId(rs.getString("session_id"));
				msg.setUserId(rs.getLong("user_id"));
				msg.setUserMessage(rs.getString("user_message"));
				msg.setBotResponse(rs.getString("bot_response"));
				msg.setCreatedAt(rs.getTimestamp("created_at"));
				history.add(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return history;
	}

	public List<Product> searchProducts(String keyword) {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM product " + "WHERE status = 'ACTIVE' "
				+ "AND (name LIKE ? OR detail_description LIKE ?) " + "LIMIT 10";

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);

			String searchPattern = "%" + keyword + "%";
			ps.setString(1, searchPattern);
			ps.setString(2, searchPattern);

			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getLong("id"));
				p.setCategoryId(rs.getLong("category_id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getBigDecimal("price"));
				p.setDetailDescription(rs.getString("detail_description"));
				p.setImageUrl(rs.getString("image_url"));
				p.setStockQuantity(rs.getInt("stock_quantity"));
				p.setStatus(rs.getString("status"));
				products.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}

	public boolean updateProductStock(String Kho, Long productId, int quantity, String note, String createdBy)
			throws Exception {

		Connection conn = null;
		PreparedStatement psUpdate = null;
		PreparedStatement psLog = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			PreparedStatement psSet = conn.prepareStatement("SET @skip_stock_log = 1");
			psSet.executeUpdate();
			String updateSQL = "UPDATE product " + "SET stock_quantity = stock_quantity + ? " + "WHERE id = ?";

			psUpdate = conn.prepareStatement(updateSQL);
			psUpdate.setInt(1, quantity);
			psUpdate.setLong(2, productId);

			int rowsAffected = psUpdate.executeUpdate();

			if (rowsAffected == 0) {
				conn.rollback();
				return false;
			}

			String movementType = quantity > 0 ? "IN" : "OUT";
			String logSQL = "INSERT INTO stock_movement_log "
					+ "(product_id, movement_type,Kho, quantity, note, created_by) " + "VALUES (?,?,?, ?, ?, ?)";

			psLog = conn.prepareStatement(logSQL);
			psLog.setLong(1, productId);
			psLog.setString(2, movementType);
			psLog.setString(3, Kho);
			psLog.setInt(4, Math.abs(quantity));
			psLog.setString(5, note);
			psLog.setString(6, createdBy);
			psLog.executeUpdate();

			conn.commit();
			return true;

		} catch (Exception e) {
			if (conn != null) {
				conn.rollback();
			}
			throw e;

		} finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}

			try {
				if (psUpdate != null)
					psUpdate.close();
				if (psLog != null)
					psLog.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<StockMovementLog> getStockMovementLog(Long productId, int limit) throws Exception {

		List<StockMovementLog> logs = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT l.*, p.name as product_name ");
		sql.append("FROM stock_movement_log l ");
		sql.append("JOIN product p ON l.product_id = p.id ");

		if (productId != null) {
			sql.append("WHERE l.product_id = ? ");
		}

		sql.append("ORDER BY l.created_at DESC ");
		sql.append("LIMIT ?");

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

			int paramIndex = 1;
			if (productId != null) {
				ps.setLong(paramIndex++, productId);
			}
			ps.setInt(paramIndex, limit);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				StockMovementLog log = new StockMovementLog();
				log.setId(rs.getLong("id"));
				log.setProductId(rs.getLong("product_id"));
				log.setProductName(rs.getString("product_name"));
				log.setOrderId(rs.getObject("order_id", Long.class));
				log.setMovementType(rs.getString("movement_type"));
				log.setQuantity(rs.getInt("quantity"));
				log.setNote(rs.getString("note"));
				log.setCreatedBy(rs.getString("created_by"));
				log.setCreatedAt(rs.getTimestamp("created_at"));

				logs.add(log);
			}

			return logs;
		}
	}

	public boolean ResetPassword(Long id, String username, String full_name, String curentPassWord,
			String ChangePassword) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			if (ChangePassword == null || ChangePassword.trim().isEmpty()) {
				String sqlNameOnly = "UPDATE users SET full_name = ?, username =? WHERE id = ?";
				ps = conn.prepareStatement(sqlNameOnly);
				ps.setString(1, full_name);
				ps.setString(2, username);
				ps.setLong(3, id);
				return ps.executeUpdate() > 0;
			}

			String checkMK = "SELECT password FROM users WHERE id = ?";
			ps = conn.prepareStatement(checkMK);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				String dbPassword = rs.getString("password");

				if (dbPassword != null && dbPassword.equals(md5(curentPassWord))) {
					ps.close();

					String sql = "UPDATE users SET password = ?, full_name = ?, username =? WHERE id = ?";
					ps = conn.prepareStatement(sql);

					ps.setString(1, md5(ChangePassword));
					ps.setString(2, full_name);
					ps.setString(3, username);
					ps.setLong(4, id);

					return ps.executeUpdate() > 0;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void saveProductEmbeddingFull(long productId, String descriptionText, String brewMethods) throws Exception {
		String sql = """
				INSERT INTO product_embeddings (product_id, description_text, brew_methods, generated_by)
				VALUES (?, ?, ?, 'ai-admin')
				ON DUPLICATE KEY UPDATE
				description_text = VALUES(description_text),
				brew_methods     = VALUES(brew_methods),
				generated_by     = 'ai-admin',
				updated_at       = CURRENT_TIMESTAMP
				""";
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, productId);
			ps.setString(2, descriptionText);
			ps.setString(3, brewMethods);
			ps.executeUpdate();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void saveProductAttribute(long productId, String attrKey, String attrValue, String attrType)
			throws Exception {
		String sql = """
				INSERT INTO product_attributes (product_id, attr_key, attr_value, attr_type)
				VALUES (?, ?, ?, ?)
				ON DUPLICATE KEY UPDATE
				attr_value = VALUES(attr_value),
				attr_type  = VALUES(attr_type),
				updated_at = CURRENT_TIMESTAMP
				""";
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, productId);
			ps.setString(2, attrKey);
			ps.setString(3, attrValue);
			ps.setString(4, attrType);
			ps.executeUpdate();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void saveProductAttributes(long productId, JsonObject attributes) throws Exception {
		String sql = """
				INSERT INTO product_attributes (product_id, attr_key, attr_value)
				VALUES (?, ?, ?)
				ON DUPLICATE KEY UPDATE attr_value = VALUES(attr_value)
				""";
		conn = getConnection(); // ← THÊM DÒNG NÀY
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			for (String key : attributes.keySet()) {
				ps.setLong(1, productId);
				ps.setString(2, key);
				ps.setString(3, attributes.get(key).getAsString());
				ps.addBatch();
			}
			ps.executeBatch();
		} finally {
			if (conn != null)
				conn.close(); // ← THÊM DÒNG NÀY
		}
	}

	public void saveProductEmbedding(long productId, String descriptionText) throws Exception {
		String sql = """
				INSERT INTO product_embeddings (product_id, description_text, generated_by)
				VALUES (?, ?, 'ai-auto')
				ON DUPLICATE KEY UPDATE
				    description_text = VALUES(description_text),
				    generated_by     = 'ai-auto',
				    updated_at       = CURRENT_TIMESTAMP
				""";
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, productId);
			ps.setString(2, descriptionText);
			ps.executeUpdate();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public List<ProductAI> getProductsForAI() throws Exception {
		String sql = """
				SELECT id, name, price, stock_quantity,
				       flavor_intensity, bitterness_level, acidity_level, sweetness_level,
				       roast_level, caffeine_level, taste_notes,
				       suitable_for, recommended_time,
				       description_text, brew_methods,
				       processing_method, grind_size
				FROM v_product_for_chatbot
				ORDER BY id
				""";
		List<ProductAI> list = new ArrayList<>();
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				ProductAI p = new ProductAI();
				p.setId(rs.getLong("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				p.setStockQuantity(rs.getInt("stock_quantity"));
				p.setFlavorIntensity(rs.getInt("flavor_intensity"));
				p.setBitternessLevel(rs.getInt("bitterness_level"));
				p.setAcidityLevel(rs.getInt("acidity_level"));
				p.setSweetnessLevel(rs.getInt("sweetness_level"));
				p.setRoastLevel(rs.getString("roast_level"));
				p.setCaffeineLevel(rs.getString("caffeine_level"));
				p.setTasteNotes(rs.getString("taste_notes"));
				p.setSuitableFor(rs.getString("suitable_for"));
				p.setRecommendedTime(rs.getString("recommended_time"));
				p.setBrewMethods(rs.getString("brew_methods"));
				p.setProcessingMethod(rs.getString("processing_method"));
				p.setGrindSize(rs.getString("grind_size"));
				list.add(p);
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return list;
	}

	public Map<String, Object> getAiTrainingStats() throws Exception {
		String sql = """
				SELECT
				    COUNT(*) as total_predictions,
				    SUM(CASE WHEN admin_correction IS NOT NULL THEN 1 ELSE 0 END) as total_corrections,
				    ROUND(AVG(CASE WHEN accuracy_score IS NOT NULL THEN accuracy_score END), 1) as avg_accuracy,
				    SUM(CASE WHEN admin_correction IS NULL THEN 1 ELSE 0 END) as accepted_without_change
				FROM ai_training_logs
				""";
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			Map<String, Object> stats = new HashMap<>();
			if (rs.next()) {
				stats.put("total", rs.getInt("total_predictions"));
				stats.put("corrections", rs.getInt("total_corrections"));
				stats.put("avgAccuracy", rs.getDouble("avg_accuracy"));
				stats.put("accepted", rs.getInt("accepted_without_change"));
			}
			return stats;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public Map<String, String> getProductAttributes(long productId) throws Exception {
		String sql = "SELECT attr_key, attr_value FROM product_attributes WHERE product_id = ?";
		Map<String, String> result = new HashMap<>();
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, productId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					result.put(rs.getString("attr_key"), rs.getString("attr_value"));
				}
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public boolean isProductNameExists(String name) throws Exception {
		String sql = "SELECT COUNT(*) FROM product WHERE name = ? AND status != 'DELETED'";
		conn = getConnection(); // ← THÊM DÒNG NÀY
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, name);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public boolean isProductNameExistsExcept(String name, String excludeId) throws Exception {

		String sql = "SELECT COUNT(*) FROM product WHERE name = ? AND id != ? AND status != 'DELETED'";
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.setString(2, excludeId);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public Product addProduct(Product product) throws Exception {
		String sql = """
				INSERT INTO product
				    (category_id, name, price, pricein, detail_description, image_url, stock_quantity, status,weight_gram)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		conn = getConnection(); 
		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setLong(1, product.getCategoryId());
			ps.setString(2, product.getName());
			ps.setBigDecimal(3, product.getPrice());
			ps.setBigDecimal(4, product.getPricein());
			ps.setString(5, product.getDetailDescription());
			ps.setString(6, product.getImageUrl());
			ps.setInt(7, product.getStockQuantity());
			ps.setString(8, product.getStatus() != null ? product.getStatus() : "ACTIVE");
			ps.setInt(9, product.getWeightGram());
			int affected = ps.executeUpdate();
			if (affected == 0)
				return null;
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					product.setId(generatedKeys.getLong(1));
					return product;
				}
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}

	public User getUserByGoogleId(String googleId) throws Exception {
		String sql = "SELECT * FROM users WHERE google_id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, googleId);
			rs = ps.executeQuery();
			if (rs.next())
				return mapUser(rs);
			return null;
		} finally {
			closeResources();
		}
	}

	public User getUserByEmail(String email) throws Exception {
		String sql = "SELECT * FROM users WHERE email = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next())
				return mapUser(rs);
			return null;
		} finally {
			closeResources();
		}
	}

	public User createUserFromGoogle(String googleId, String email, String fullName, String avatar) throws Exception {
		String sql = "INSERT INTO users (username, password, email, full_name, "
				+ "avatar, google_id, role, status, profile_complete) "
				+ "VALUES (?, NULL, ?, ?, ?, ?, 'USER', 'ACTIVE', 1)";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, email); // username = email
			ps.setString(2, email);
			ps.setString(3, fullName);
			ps.setString(4, avatar);
			ps.setString(5, googleId);
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getLong(1));
				user.setUsername(email);
				user.setEmail(email);
				user.setFullName(fullName);
				user.setAvatar(avatar);
				user.setGoogleId(googleId);
				user.setRole("USER");
				user.setStatus("ACTIVE");
				user.setProfileComplete(true);
				return user;
			}
			return null;
		} finally {
			closeResources();
		}
	}

	public boolean linkGoogleId(Long userId, String googleId, String avatar) throws Exception {
		String sql = "UPDATE users SET google_id=?, avatar=? WHERE id=?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, googleId);
			ps.setString(2, avatar);
			ps.setLong(3, userId);
			return ps.executeUpdate() > 0;
		} finally {
			closeResources();
		}
	}

	public boolean updateUserContact(Long userId, String email, String phone) throws Exception {
		String sql = "UPDATE users SET email=?, phone=?, profile_complete=1 WHERE id=?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, phone);
			ps.setLong(3, userId);
			return ps.executeUpdate() > 0;
		} finally {
			closeResources();
		}
	}

	// Cập nhật chỉ SĐT cho Google user (đã có email rồi)
	public boolean updateUserPhone(Long userId, String phone) throws Exception {
		String sql = "UPDATE users SET phone=?, profile_complete=1 WHERE id=?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, phone);
			ps.setLong(2, userId);
			return ps.executeUpdate() > 0;
		} finally {
			closeResources();
		}
	}

	// Lấy fullName theo email (dùng trong ForgotPasswordServlet)
	public String getFullNameByEmail(String email) throws Exception {
		String sql = "SELECT full_name FROM users WHERE email = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getString("full_name");
			return "Bạn";
		} finally {
			closeResources();
		}
	}

	// Lưu token reset mật khẩu
	public boolean saveResetToken(String email, String token, java.sql.Timestamp expiry) throws Exception {
		String sql = "UPDATE users SET reset_token=?, reset_token_expiry=? WHERE email=?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, token);
			ps.setTimestamp(2, expiry);
			ps.setString(3, email);
			return ps.executeUpdate() > 0;
		} finally {
			closeResources();
		}
	}

	// Tìm user theo reset token còn hạn
	public User getUserByResetToken(String token) throws Exception {
		String sql = "SELECT * FROM users WHERE reset_token=? AND reset_token_expiry > NOW()";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, token);
			rs = ps.executeQuery();
			if (rs.next())
				return mapUser(rs);
			return null;
		} finally {
			closeResources();
		}
	}

	// Cập nhật mật khẩu và xóa token
	public boolean updatePasswordAndClearToken(Long userId, String newPassword) throws Exception {
		String sql = "UPDATE users SET password=?, reset_token=NULL, " + "reset_token_expiry=NULL WHERE id=?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, md5(newPassword)); // giữ nhất quán với login()
			ps.setLong(2, userId);
			return ps.executeUpdate() > 0;
		} finally {
			closeResources();
		}
	}

	private User mapUser(ResultSet rs) throws Exception {
		User u = new User();
		u.setId(rs.getLong("id"));
		u.setUsername(rs.getString("username"));
		u.setPassword(rs.getString("password"));
		u.setEmail(rs.getString("email"));
		u.setFullName(rs.getString("full_name"));
		u.setPhone(rs.getString("phone"));
		u.setAddress(rs.getString("address"));
		u.setRole(rs.getString("role"));
		u.setStatus(rs.getString("status"));
		u.setCreatedAt(rs.getTimestamp("created_at"));
		u.setUpdatedAt(rs.getTimestamp("updated_at"));
		// Các cột mới — dùng try/catch riêng phòng trường hợp cột chưa tồn tại
		try {
			u.setGoogleId(rs.getString("google_id"));
		} catch (Exception ignored) {
		}
		try {
			u.setFacebookId(rs.getString("facebook_id"));
		} catch (Exception ignored) {
		}
		try {
			u.setAvatar(rs.getString("avatar"));
		} catch (Exception ignored) {
		}
		try {
			u.setProfileComplete(rs.getInt("profile_complete") == 1);
		} catch (Exception ignored) {
			u.setProfileComplete(true);
		}
		return u;
	}

	// Đóng tài nguyên
	private void closeResources() {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception ignored) {
		}
		try {
			if (ps != null)
				ps.close();
		} catch (Exception ignored) {
		}
		try {
			if (conn != null)
				conn.close();
		} catch (Exception ignored) {
		}
	}

	public void saveProductProfile(long productId, int flavorIntensity, int bitterness, int acidity, int sweetness,
			String roastLevel, String caffeineLevel, String flavorTags, String suitableFor, String recommendedTime,
			String grindSize, String processingMethod, String brewMethods) throws Exception {

		String sql = """
				INSERT INTO product_profile
				    (product_id, flavor_intensity, bitterness_level, acidity_level,
				     sweetness_level, roast_level, caffeine_level, taste_notes,
				     suitable_for, recommended_time, grind_size, processing_method, brew_methods)
				VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
				ON DUPLICATE KEY UPDATE
				    flavor_intensity  = VALUES(flavor_intensity),
				    bitterness_level  = VALUES(bitterness_level),
				    acidity_level     = VALUES(acidity_level),
				    sweetness_level   = VALUES(sweetness_level),
				    roast_level       = VALUES(roast_level),
				    caffeine_level    = VALUES(caffeine_level),
				    taste_notes       = VALUES(taste_notes),
				    suitable_for      = VALUES(suitable_for),
				    recommended_time  = VALUES(recommended_time),
				    grind_size        = VALUES(grind_size),
				    processing_method = VALUES(processing_method),
				    brew_methods      = VALUES(brew_methods)
				""";
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, productId);
			ps.setInt(2, flavorIntensity);
			ps.setInt(3, bitterness);
			ps.setInt(4, acidity);
			ps.setInt(5, sweetness);
			ps.setString(6, roastLevel);
			ps.setString(7, caffeineLevel);
			ps.setString(8, flavorTags);
			ps.setString(9, suitableFor);
			ps.setString(10, recommendedTime);
			ps.setString(11, grindSize);
			ps.setString(12, processingMethod);
			ps.setString(13, brewMethods);
			ps.executeUpdate();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void updateEmbeddingVector(long productId, String vectorJson) throws Exception {
		String sql = """
				UPDATE product_profile
				SET embedding_vector = ?
				WHERE product_id = ?
				""";
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, vectorJson);
			ps.setLong(2, productId);
			ps.executeUpdate();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public java.util.Map<String, Object> getProductProfile(long productId) throws Exception {
		String sql = """
				SELECT flavor_intensity, bitterness_level, acidity_level, sweetness_level,
				       roast_level, caffeine_level, taste_notes, suitable_for,
				       recommended_time, grind_size, processing_method, brew_methods,
				       embedding_vector
				FROM product_profile
				WHERE product_id = ?
				""";
		conn = getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, productId);
			try (java.sql.ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					java.util.Map<String, Object> map = new java.util.HashMap<>();
					map.put("flavorIntensity", rs.getInt("flavor_intensity"));
					map.put("bitternessLevel", rs.getInt("bitterness_level"));
					map.put("acidityLevel", rs.getInt("acidity_level"));
					map.put("sweetnessLevel", rs.getInt("sweetness_level"));
					map.put("roastLevel", rs.getString("roast_level"));
					map.put("caffeineLevel", rs.getString("caffeine_level"));
					map.put("tasteNotes", rs.getString("taste_notes"));
					map.put("suitableFor", rs.getString("suitable_for"));
					map.put("recommendedTime", rs.getString("recommended_time"));
					map.put("grindSize", rs.getString("grind_size"));
					map.put("processingMethod", rs.getString("processing_method"));
					map.put("brewMethods", rs.getString("brew_methods"));
					map.put("embeddingVector", rs.getString("embedding_vector"));
					return map;
				}
				return null; // chưa có profile
			}
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public java.util.List<Entity.ProductAI> getProductsWithEmbedding() throws Exception {
		String sql = """
				SELECT id, name, price, stock_quantity,
				       flavor_intensity, bitterness_level, acidity_level, sweetness_level,
				       roast_level, caffeine_level,
				       suitable_for, recommended_time,
				       description_text, brew_methods,
				       processing_method, grind_size,
				       embedding_vector
				FROM v_product_for_chatbot
				WHERE embedding_vector IS NOT NULL
				ORDER BY id
				""";
		java.util.List<Entity.ProductAI> list = new java.util.ArrayList<>();
		conn = getConnection();
		try (java.sql.PreparedStatement ps = conn.prepareStatement(sql); java.sql.ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Entity.ProductAI p = new Entity.ProductAI();
				p.setId(rs.getLong("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				p.setStockQuantity(rs.getInt("stock_quantity"));
				p.setFlavorIntensity(rs.getInt("flavor_intensity"));
				p.setBitternessLevel(rs.getInt("bitterness_level"));
				p.setAcidityLevel(rs.getInt("acidity_level"));
				p.setSweetnessLevel(rs.getInt("sweetness_level"));
				p.setRoastLevel(rs.getString("roast_level"));
				p.setCaffeineLevel(rs.getString("caffeine_level"));
				p.setSuitableFor(rs.getString("suitable_for"));
				p.setRecommendedTime(rs.getString("recommended_time"));
				p.setBrewMethods(rs.getString("brew_methods"));
				p.setProcessingMethod(rs.getString("processing_method"));
				p.setGrindSize(rs.getString("grind_size"));
				p.setDescriptionText(rs.getString("description_text"));
				String vec = rs.getString("embedding_vector");
				if (vec != null)
					p.setEmbeddingVector(vec);
				list.add(p);
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return list;
	}
	public void addTrackingLog(Long orderId, String statusCode, String statusName, String location, String note,
			String updatedBy) {
		String sql = "INSERT INTO order_tracking_log "
				+ "(order_id, status_code, status_name, location, note, updated_by, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, NOW())";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, orderId);
			ps.setString(2, statusCode);
			ps.setString(3, statusName);
			ps.setString(4, location != null ? location : "");
			ps.setString(5, note != null ? note : "");
			ps.setString(6, updatedBy != null ? updatedBy : "SYSTEM");
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	/**
	 * Lấy toàn bộ hành trình đơn hàng (DB nội bộ), mới nhất lên đầu
	 */
	public List<TrackingLog> getTrackingLogsByOrderId(Long orderId) {
		List<TrackingLog> list = new ArrayList<>();
		String sql = "SELECT * FROM order_tracking_log WHERE order_id = ? ORDER BY created_at DESC";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, orderId);
			rs = ps.executeQuery();
			while (rs.next()) {
				TrackingLog t = new TrackingLog();
				t.setId(rs.getLong("id"));
				t.setOrderId(rs.getLong("order_id"));
				t.setStatusCode(rs.getString("status_code"));
				t.setStatusName(rs.getString("status_name"));
				t.setLocation(rs.getString("location"));
				t.setNote(rs.getString("note"));
				t.setUpdatedBy(rs.getString("updated_by"));
				t.setCreatedAt(rs.getTimestamp("created_at"));
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return list;
	}

	public String getGhnOrderCode(Long orderId) {
		String sql = "SELECT ghn_order_code FROM orders WHERE id=?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, orderId);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getString("ghn_order_code");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}

	// Đóng tài nguyên
	
	public boolean updateGhnOrderCode(Long orderId, String ghnOrderCode) {
		String sql = "UPDATE orders SET ghn_order_code=?, updated_at=NOW() WHERE id=?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, ghnOrderCode);
			ps.setLong(2, orderId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	public int getOrderTotalWeightGram(Long orderId) {
		String sql = "SELECT SUM(oi.quantity * COALESCE(p.weight_gram, 250)) AS total_weight "
				+ "FROM order_items oi "
				+ "JOIN product p ON oi.product_id = p.id "
				+ "WHERE oi.order_id = ?";
		try {
			conn = getConnection();
			ps   = conn.prepareStatement(sql);
			ps.setLong(1, orderId);
			rs   = ps.executeQuery();
			if (rs.next()) {
				int w = rs.getInt("total_weight");
				return w > 0 ? w : 500;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return 500; // fallback 500g
	}
	public boolean isOrderCancelRequested(Long userId, Long orderId) {
		String sql = "SELECT COUNT(*) FROM contacts WHERE user_id = ? AND subject LIKE ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, userId);
			ps.setString(2, "%#" + orderId + "%");
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return false;
	}
	public boolean updateQualityproduct(Long orderId) {
		try {
			Order currentOrder = getOrderById(orderId);

			if (currentOrder == null) {
				throw new RuntimeException("Đơn hàng không tồn tại!");
			}

			StringBuilder sql = new StringBuilder(
					"UPDATE product p\r\n" + "JOIN order_items oi ON p.id = oi.product_id\r\n" + "SET \r\n"
							+ "    p.stock_quantity = p.stock_quantity - oi.quantity,\r\n"
							+ "    p.reserved = p.reserved - oi.quantity\r\n" + "WHERE \r\n" + "    oi.order_id = ?;");

			conn = getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, orderId);
			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Lỗi nhật số lượng sản phẩm: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean updateQualityproducthuy(Long orderId) {
		try {
			Order currentOrder = getOrderById(orderId);

			if (currentOrder == null) {
				throw new RuntimeException("Đơn hàng không tồn tại!");
			}

			StringBuilder sql = new StringBuilder(
					"UPDATE product p\r\n" + "JOIN order_items oi ON p.id = oi.product_id\r\n" + "SET \r\n"
							+ "    p.reserved = p.reserved - oi.quantity\r\n" + "WHERE \r\n" + "    oi.order_id = ?;");

			conn = getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, orderId);
			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Lỗi cập nhật số lượng sản phẩm: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
