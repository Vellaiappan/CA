package sg.edu.nus.ca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class LeaveEntitlement {

	@Id
	@GeneratedValue
	private Integer id;
	@NotEmpty
	private String role;
	@NotEmpty
	private String leavetype;
	@NotNull
	private double leavecount;
	public LeaveEntitlement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeaveEntitlement(String role, String leavetype, double leavecount) {
		super();
		this.role = role;
		this.leavetype = leavetype;
		this.leavecount = leavecount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLeavetype() {
		return leavetype;
	}
	public void setLeavetype(String leavetype) {
		this.leavetype = leavetype;
	}
	public double getLeavecount() {
		return leavecount;
	}
	public void setLeavecount(double leavecount) {
		this.leavecount = leavecount;
	}
	
	
}
