package sg.edu.nus.ca.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Embeddable
public class LeaveBalanceIdentity implements Serializable{

	@NotNull
	private String employeeid;
	@NotNull
	private Integer leavetypeid;
	public LeaveBalanceIdentity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeaveBalanceIdentity(@NotNull String employeeid, @NotNull Integer leavetypeid) {
		super();
		this.employeeid = employeeid;
		this.leavetypeid = leavetypeid;
	}
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public Integer getLeavetypeid() {
		return leavetypeid;
	}
	public void setLeavetypeid(Integer leavetypeid) {
		this.leavetypeid = leavetypeid;
	}
	
	
}
