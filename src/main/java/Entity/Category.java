package Entity;

public class Category {
    private Long id;
    private String name;
	private String status;

    public Category() {
        super();
    }

    public Category(Long id, String name,String status) {
        super();
        this.id = id;
        this.name = name;
        this.status = status;        
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
}