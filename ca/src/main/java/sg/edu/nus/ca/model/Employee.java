package sg.edu.nus.ca.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import sg.edu.nus.ca.dataloader.IDGenerator;
import sg.edu.nus.ca.service.HashPasswords;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.util.DigestUtils;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    @GenericGenerator(
        name = "emp_seq", 
        strategy = "sg.edu.nus.ca.dataloader.IDGenerator", 
        parameters = {
            @Parameter(name = IDGenerator.INCREMENT_PARAM, value = "50"),
            @Parameter(name = IDGenerator.VALUE_PREFIX_PARAMETER, value = "EMP"),
            @Parameter(name = IDGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String id;
	@NotEmpty
	private String name;
	private String password=HashPasswords.encodeSimple("123");
	private String role;
	@NotEmpty
	private String designation;
	private String managerid;
	@NotEmpty
	@Column(unique=true)
	private String emailid;
	@OneToMany(targetEntity= LeaveApplication.class, mappedBy="employee")
	private List<LeaveApplication> leaveapplist;
	
	
	public List<LeaveApplication> getLeaveapplist() {
		return leaveapplist;
	}
	public void setLeaveapplist(List<LeaveApplication> leaveapplist) {
		this.leaveapplist = leaveapplist;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(String name,String role, String designation, String managerid,
			String emailid,int medicalleave,int annualleave,double compensationleave) {
		super();
		this.name = name;
		this.role = role;
		this.designation = designation;
		this.managerid = managerid;
		this.emailid = emailid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getManagerid() {
		return managerid;
	}
	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", role=" + role + ", designation=" + designation + ", managerid=" + managerid
				+ ", emailid=" + emailid + "]";
	}
	
	
	
	
}
