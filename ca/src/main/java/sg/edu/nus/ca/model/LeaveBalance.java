package sg.edu.nus.ca.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity
public class LeaveBalance {

	@EmbeddedId
	private LeaveBalanceIdentity balanceIdentity;
	private double leavebalance;
	public LeaveBalance() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeaveBalance(LeaveBalanceIdentity balanceIdentity, double leavebalance) {
		super();
		this.balanceIdentity = balanceIdentity;
		this.leavebalance = leavebalance;
	}
	public LeaveBalanceIdentity getBalanceIdentity() {
		return balanceIdentity;
	}
	public void setBalanceIdentity(LeaveBalanceIdentity balanceIdentity) {
		this.balanceIdentity = balanceIdentity;
	}
	public double getLeavebalance() {
		return leavebalance;
	}
	public void setLeavebalance(double leavebalance) {
		this.leavebalance = leavebalance;
	}
	
	
}
