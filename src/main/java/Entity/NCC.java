package Entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class NCC implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nameKho;
	private String nameQuanKho;
	private String phone;
	private String address;;
	private String status;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public NCC() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameKho() {
		return nameKho;
	}

	public void setNameKho(String nameKho) {
		this.nameKho = nameKho;
	}

	public String getNameQuanKho() {
		return nameQuanKho;
	}

	public void setNameQuanKho(String nameQuanKho) {
		this.nameQuanKho = nameQuanKho;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "NCC [id=" + id + ", nameKho=" + nameKho + ", nameQuanKho=" + nameQuanKho + ", phone=" + phone
				+ ", address=" + address + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", getId()=" + getId() + ", getNameKho()=" + getNameKho() + ", getNameQuanKho()="
				+ getNameQuanKho() + ", getPhone()=" + getPhone() + ", getAddress()=" + getAddress() + ", getStatus()="
				+ getStatus() + ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
