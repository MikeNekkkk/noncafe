package Entity;

import java.math.BigDecimal;
import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long categoryId;
	private String name;
	private BigDecimal price;
	private BigDecimal pricein;
	private String detailDescription;
	private String imageUrl;
	private Integer stockQuantity;
	private Integer reserved;
	private String categoryName;
	private String status;
	private Integer weightGram ;
	

	public Integer getWeightGram() {
	    return weightGram;
	}

	public void setWeightGram(Integer weightGram) {
	    this.weightGram = weightGram;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Product() {
		super();
	}

	public Product(Long id, Long categoryId, String name, BigDecimal price, BigDecimal pricein,
			String detailDescription,Integer reserved, String imageUrl, Integer stockQuantity) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.price = price;
		this.pricein = pricein;
		this.detailDescription = detailDescription;
		this.reserved = reserved;
		this.imageUrl = imageUrl;
		this.stockQuantity = stockQuantity;
	}

	public Integer getReserved() {
		return reserved;
	}

	public void setReserved(Integer reserved) {
		this.reserved = reserved;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getPricein() {
		return pricein;
	}

	public String getDetailDescription() {
		return detailDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setPricein(BigDecimal pricein) {
		this.pricein = pricein;
	}

	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", categoryId=" + categoryId + ", name=" + name + ", price=" + price + ", pricein="
				+ pricein + ", stockQuantity=" + stockQuantity + "]";
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}