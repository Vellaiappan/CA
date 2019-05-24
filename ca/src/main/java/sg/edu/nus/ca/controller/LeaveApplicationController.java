package sg.edu.nus.ca.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.ca.model.Employee;
import sg.edu.nus.ca.model.LeaveApplication;
import sg.edu.nus.ca.model.LeaveBalance;
import sg.edu.nus.ca.model.LeaveBalanceIdentity;
import sg.edu.nus.ca.model.LeaveEntitlement;
import sg.edu.nus.ca.repository.EmployeeRepository;
import sg.edu.nus.ca.repository.LeaveApplicationRepository;
import sg.edu.nus.ca.repository.LeaveBalanceRepository;
import sg.edu.nus.ca.repository.LeaveEntitleRepository;
import sg.edu.nus.ca.repository.PublicHolidayRepository;
import sg.edu.nus.ca.service.LeaveCalculation;

@Controller
public class LeaveApplicationController {

	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private LeaveEntitleRepository entRepo;
	@Autowired
	private LeaveApplicationRepository appRepo;
	@Autowired
	private PublicHolidayRepository pubRepo;
	@Autowired
	private LeaveBalanceRepository balRepo;
	
	public void setEmpRepo(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}

	public void setEntRepo(LeaveEntitleRepository entRepo) {
		this.entRepo = entRepo;
	}

	public void setAppRepo(LeaveApplicationRepository appRepo) {
		this.appRepo = appRepo;
	}

	public void setPubRepo(PublicHolidayRepository pubRepo) {
		this.pubRepo = pubRepo;
	}

	public void setBalRepo(LeaveBalanceRepository balRepo) {
		this.balRepo = balRepo;
	}
//For Employee
	@RequestMapping(path = "/submitleave/{id}", method = RequestMethod.GET)
    public String submitLeave(Model model, @PathVariable(value = "id") String id) {
		model.addAttribute("leaveapplication", new LeaveApplication());
		Employee e = empRepo.findById(id).orElse(null);
		System.out.println("------------------"+e.getManagerid()+""+id);
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
		model.addAttribute("userid", id);
		model.addAttribute("employee", e);
		model.addAttribute("leavetypes", leavetypes);
		model.addAttribute("status", "Applied");
		return "leaveform";
	}
	
	@RequestMapping(path = "/saveleave/edit/{id}/{userid}", method = RequestMethod.GET)
    public String updateSubmitLeave(Model model, @PathVariable(value = "id") String id,@PathVariable(value = "userid") String userid) {
		LeaveApplication l=appRepo.findById(Integer.parseInt(id)).orElse(null);
		System.out.println("---------------"+l.getStatus());
		if(l.getStatus().equals("Applied") || l.getStatus().equals("Updated")) {
		model.addAttribute("status","Updated");
		model.addAttribute("leaveapplication", l);
		Employee e = empRepo.findById(userid).orElse(null);
		System.out.println("------------------"+e.getManagerid()+""+id);
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
		model.addAttribute("userid", userid);
		model.addAttribute("employee", e);
		model.addAttribute("leavetypes", leavetypes);
		return "leaveform";
		}
		else if(l.getStatus().equals("Deleted"))
		{
			model.addAttribute("status","Deleted");
			Employee e = empRepo.findById(userid).orElse(null);
			model.addAttribute("leaveapplication", l);
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
			model.addAttribute("userid", userid);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			return "ReadonlyLeaveForm";
		}
		else if(l.getStatus().equals("Approved"))
		{
			model.addAttribute("status","Approved");
			Employee e = empRepo.findById(userid).orElse(null);
			model.addAttribute("leaveapplication", l);
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
			model.addAttribute("userid", userid);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			return "ReadonlyLeaveForm";
		}
		else
		{
			model.addAttribute("status",l.getStatus());
			Employee e = empRepo.findById(userid).orElse(null);
			model.addAttribute("leaveapplication", l);
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
			model.addAttribute("userid", userid);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			return "ReadonlyLeaveForm";	
		}
	}
	@RequestMapping(path = "/saveleave/delete/{id}/{userid}", method = RequestMethod.GET)
    public String deleteSubmitLeave(Model model, @PathVariable(value = "id") String id,@PathVariable(value = "userid") String userid) {
		LeaveApplication l=appRepo.findById(Integer.parseInt(id)).orElse(null);
		l.setStatus("Deleted");
		appRepo.save(l);
	    return "redirect:/viewleave/"+userid;
	}
	
	@RequestMapping(path = "/saveleave/cancel/{id}/{userid}", method = RequestMethod.GET)
    public String cancelSubmitLeave(Model model, @PathVariable(value = "id") String id,@PathVariable(value = "userid") String userid) {
		LeaveApplication l=appRepo.findById(Integer.parseInt(id)).orElse(null);
		if(LeaveCalculation.checkCancelDate(l.getStartdate().toLocalDate()))
		{
			model.addAttribute("status",l.getStatus());
			Employee e = empRepo.findById(userid).orElse(null);
			model.addAttribute("leaveapplication", l);
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
			model.addAttribute("userid", userid);
			model.addAttribute("employee", e);
			model.addAttribute("Error", "error");
			model.addAttribute("message", "Cannot cancel leave now as the leave started already.....");
			model.addAttribute("leavetypes", leavetypes);
			return "ReadonlyLeaveForm";	
		}
		l.setStatus("Cancelled");
		appRepo.save(l);
		double balance=balRepo.getBalance(userid, l.getLeavetype());
		balance=balance+l.getNumofdays();
        LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(userid,l.getLeavetype()),balance);
		balRepo.save(lbal);
	    return "redirect:/viewleave/"+userid;
	}
	
	
	@RequestMapping(path = "/saveleave", method = RequestMethod.POST)
    public String saveLeaveType(LeaveApplication leave,@RequestParam("userid") String id,@RequestParam("days") String days,Model model) {
		int numofdays;
		double balance;
		Employee e = empRepo.findById(id).orElse(null);
		List<LeaveApplication> lalist=appRepo.getLeaveAppForEmployee(id, "Applied", "Updated", "Approved");
		if(LeaveCalculation.checkLeaveAppDates(lalist, leave.getStartdate().toLocalDate(), leave.getEnddate().toLocalDate()))
		{
			model.addAttribute("Error", "error");
			model.addAttribute("Message", "You have some leaves applied/approved between these dates....Please delete and add...");
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
			model.addAttribute("userid", id);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			model.addAttribute("status",leave.getStatus());
			model.addAttribute("leaveapplication", leave);
			return "leaveform";	
		}
		leave.setEmployee(e);
		System.out.println(Integer.parseInt(days));
		if(Integer.parseInt(days)<=14)
		{
			numofdays=LeaveCalculation.numOfWorkingDays(pubRepo, leave.getStartdate().toLocalDate(), leave.getEnddate().toLocalDate());
			System.out.println(numofdays);
		}
		else
		{
			numofdays=Integer.parseInt(days);
			System.out.println(numofdays);
		}
		balance=balRepo.getBalance(id, leave.getLeavetype());
		if(balance<numofdays)
		{
			model.addAttribute("Error", "error");
			model.addAttribute("Message", "Out of Balance for selected type....");
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
			model.addAttribute("userid", id);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			model.addAttribute("status", leave.getStatus());
			model.addAttribute("leaveapplication", leave);
			return "leaveform";
		}
		leave.setNumofdays(numofdays);
        appRepo.save(leave);
        //balance=balance-numofdays;
        //LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(id,leave.getLeavetype()),balance);
		//balRepo.save(lbal);
        model.addAttribute("id", id);
        return "redirect:/employeehome/"+id;
	}
	
	@RequestMapping(path = "/viewleave/{id}", method = RequestMethod.GET)
    public String viewLeave(Model model, @PathVariable(value = "id") String id) {
		List<LeaveApplication> leavelist=appRepo.getLeaveByEmployee(id);
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
		model.addAttribute("userid", id);
		model.addAttribute("leavelist", leavelist);
		model.addAttribute("leavetype", leavetypes);
		model.addAttribute("role", "Employee");
		return "ViewLeaveHistory";
	}
	
	@RequestMapping(path = "/viewbalance/{id}", method = RequestMethod.GET)
    public String viewBalance(Model model, @PathVariable(value = "id") String id) {
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
		model.addAttribute("leavetype", leavetypes);
		List<LeaveBalance> ballist=balRepo.getEmployeeBalance(id);
		model.addAttribute("ballist", ballist);
		model.addAttribute("userid", id);
		return "viewBalance";
	}
	
//For Manager
	@RequestMapping(path = "/approveleave/{id}", method = RequestMethod.GET)
    public String ApproveLeave(Model model, @PathVariable(value = "id") String id) {
		List<LeaveApplication> leavelist=appRepo.getLeaveByManager(id,"Applied","Updated");
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
		model.addAttribute("userid", id);
		model.addAttribute("leavelist", leavelist);
		model.addAttribute("leavetype", leavetypes);
		model.addAttribute("role", "Manager");
		return "ViewLeaveHistory";
	}
	
	@RequestMapping(path = "/approveleave/edit/{id}/{userid}", method = RequestMethod.GET)
    public String approveSubmitLeave(Model model, @PathVariable(value = "id") String id,@PathVariable(value = "userid") String userid) {
		LeaveApplication l=appRepo.findById(Integer.parseInt(id)).orElse(null);
		Employee e = empRepo.findById(l.getEmployee().getId()).orElse(null);
		model.addAttribute("leaveapplication", l);
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
		model.addAttribute("userid", userid);
		model.addAttribute("employee", e);
		model.addAttribute("leavetypes", leavetypes);
		model.addAttribute("status", l.getStatus());
		return "ManagerApprovalForm";
	}
	
	@RequestMapping(path = "/approveleave/reject/{id}/{userid}/{managercomment}", method = RequestMethod.GET)
    public String rejectSubmitLeave(Model model, @PathVariable(value = "id") String id,@PathVariable(value = "userid") String userid,
    		@PathVariable(value = "managercomment") String comment) {
		LeaveApplication l=appRepo.findById(Integer.parseInt(id)).orElse(null);
		l.setStatus("Rejected");
		System.out.println("++++++++++++"+comment);
		l.setManagercomment(comment);
		appRepo.save(l);
	    return "redirect:/approveleave/"+userid;
	}
	
	@RequestMapping(path = "/approveleave/approve/{id}/{userid}/{managercomment}", method = RequestMethod.GET)
    public String acceptSubmitLeave(Model model, @PathVariable(value = "id") String id,@PathVariable(value = "userid") String userid,
    		@PathVariable(value = "managercomment") String comment) {
		LeaveApplication l=appRepo.findById(Integer.parseInt(id)).orElse(null);
		l.setStatus("Approved");
		l.setManagercomment(comment);
		appRepo.save(l);
		double balance=balRepo.getBalance(l.getEmployee().getId(), l.getLeavetype());
		balance=balance-l.getNumofdays();
        LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(l.getEmployee().getId(),l.getLeavetype()),balance);
		balRepo.save(lbal);
	    return "redirect:/approveleave/"+userid;
	}
	
	@RequestMapping(path = "/viewallleave/{id}", method = RequestMethod.GET)
    public String ViewAllLeave(Model model, @PathVariable(value = "id") String id) {
		List<LeaveApplication> leavelist=appRepo.getAllLeave();
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
		model.addAttribute("userid", id);
		model.addAttribute("leavelist", leavelist);
		model.addAttribute("leavetype", leavetypes);
		model.addAttribute("role", "NoView");
		return "ViewLeaveHistory";
	}
	@RequestMapping(path = "/viewspecific/{id}", method = RequestMethod.GET)
    public String ViewSpecificLeave(Model model, @PathVariable(value = "id") String id) {
		model.addAttribute("userid", id);
		List<Employee> elist=empRepo.getAllSubEmp(id);
		model.addAttribute("elist", elist);
		return "ViewSpecificHistory";
	}
    
	@RequestMapping(path = "/searchapp", method = RequestMethod.POST)
    public String searchEmployeeApp(@RequestParam("userid") String id,@RequestParam("sub") String empid,Model model) {
		List<LeaveApplication> leavelist=appRepo.getLeaveByEmployee(empid);
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
		model.addAttribute("userid", id);
		model.addAttribute("leavelist", leavelist);
		model.addAttribute("leavetype", leavetypes);
		model.addAttribute("role", "NoView");
		return "ViewLeaveHistory";
	}

	
}
