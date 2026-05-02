package Entity;

import java.math.BigDecimal;

public class OrderItem {
	private Long id;
	private Long orderId;
	private Long productId;
	private String productName;
	private BigDecimal price;
	private BigDecimal pricein;
	private Integer quantity;
	private BigDecimal subtotal;
	private int weightGram;
	public int getWeightGram() { return weightGram; }
	public void setWeightGram(int weightGram) { this.weightGram = weightGram; }

	public OrderItem() {
	}

	public OrderItem(Long orderId, Long productId, String productName, BigDecimal price,BigDecimal pricein, Integer quantity,
			BigDecimal subtotal) {
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.pricein = pricein;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPricein() {
		return pricein;
	}

	public void setPricein(BigDecimal pricein) {
		this.pricein = pricein;
	}
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", productName=" + productName + ", quantity=" + quantity + ", subtotal="
				+ subtotal + "]";
	}
}