package sg.edu.nus.ca.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class LeaveApplication {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Date startdate;
	private Date enddate;
	private int numofdays;
	private int leavetype;
	@NotEmpty
	private String reasons;
	private String contact;
	private String status;
	private String manager;
	private String managercomment;
	@ManyToOne
	@JoinColumn(name="employeeid")
	private Employee employee;
	
	
	public String getManagercomment() {
		return managercomment;
	}
	public void setManagercomment(String managercomment) {
		this.managercomment = managercomment;
	}
	public int getNumofdays() {
		return numofdays;
	}
	public void setNumofdays(int numofdays) {
		this.numofdays = numofdays;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getLeavetype() {
		return leavetype;
	}
	public void setLeavetype(int leavetype) {
		this.leavetype = leavetype;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public LeaveApplication() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LeaveApplication(Date startdate, Date enddate, int leavetype, String reasons, String contact,
			 String manager,String status) {
		super();
		this.startdate = startdate;
		this.enddate = enddate;
		this.leavetype = leavetype;
		this.reasons = reasons;
		this.contact = contact;
		this.manager = manager;
		this.status=status;
	}
	
	
	
	
	
	
}
