package sg.edu.nus.ca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Admin {
	
	@Id
	@NotEmpty
	private String id;
	@NotEmpty
	private String password;
	private String role;
	private String email;
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(String id, String password, String role, String email) {
		super();
		this.id = id;
		this.password = password;
		this.role = role;
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
