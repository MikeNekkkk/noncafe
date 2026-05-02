package Entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	private String email;
	private String fullName;
	private String phone;
	private String address;
	private String role;
	private String status;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private String googleId;
	private String facebookId;
	private String avatar;
	private boolean profileComplete = true;

	public User() {
	}

	public User(String username, String password, String email, String fullName) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullName = fullName;
		this.role = "USER";
		this.status = "ACTIVE";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public void setCreatedAt(Timestamp t) {
		this.createdAt = t;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp t) {
		this.updatedAt = t;
	}

	// ── GETTERS/SETTERS MỚI ──
	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String v) {
		this.facebookId = v;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isProfileComplete() {
		return profileComplete;
	}

	public void setProfileComplete(boolean v) {
		this.profileComplete = v;
	}

	public boolean isAdmin() {
		return "ADMIN".equals(this.role);
	}

	public boolean isActive() {
		return "ACTIVE".equals(this.status);
	}

	public boolean isGoogleUser() {
		return googleId != null && !googleId.isEmpty();
	}

	public boolean isFacebookUser() {
		return facebookId != null && !facebookId.isEmpty();
	}

	public boolean needsContactInfo() {
		return (email == null || email.trim().isEmpty()) || (phone == null || phone.trim().isEmpty());
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", fullName=" + fullName + ", role="
				+ role + ", status=" + status + "]";
	}
}