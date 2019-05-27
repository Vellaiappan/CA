package sg.edu.nus.ca.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import sg.edu.nus.ca.repository.LeaveAppPaginationRepository;
import sg.edu.nus.ca.repository.LeaveApplicationRepository;
import sg.edu.nus.ca.repository.LeaveBalanceRepository;
import sg.edu.nus.ca.repository.LeaveEntitleRepository;
import sg.edu.nus.ca.repository.PublicHolidayRepository;
import sg.edu.nus.ca.service.EmailServiceImpl;
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
	@Autowired
	private LeaveAppPaginationRepository appPageRepo;
	@Autowired
	private EmailServiceImpl emailservice;
	
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
	
public void setAppPageRepo(LeaveAppPaginationRepository appPageRepo) {
		this.appPageRepo = appPageRepo;
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
		double balance=balRepo.getBalance(userid, l.getLeavetype());
		balance=balance+l.getNumofdays();
        LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(userid,l.getLeavetype()),balance);
		balRepo.save(lbal);
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
		if(l.getEmployee().getRole().equals("Employee"))
	       return "redirect:/viewleave/"+userid;
		else
			return "redirect:/viewmanleave/"+userid;
	}
	
	
	@RequestMapping(path = "/saveleave", method = RequestMethod.POST)
    public String saveLeaveType(LeaveApplication leave,@RequestParam("userid") String id,@RequestParam("days") String days,Model model) {
		int numofdays;
		double balance;
		String leaveid,operation;
		Employee e = empRepo.findById(id).orElse(null);
		System.out.println("--------------"+leave.getId());
		if(leave.getId()==null)
		{
			operation="create";
			leaveid="";
		}
		else
		{
			operation="update";
			leaveid=leave.getId().toString();
		}
		
		// leave start or end date cannot be on public holiday or weekend
		if( !LeaveCalculation.isWorkingDay(leave.getStartdate()) || !LeaveCalculation.isWorkingDay(leave.getEnddate()) )
		{
			model.addAttribute("Error", "error");
			model.addAttribute("Message", "The start date or end date cannot be on public holiday or weekend.");
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Employee");
			model.addAttribute("userid", id);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			model.addAttribute("status",leave.getStatus());
			model.addAttribute("leaveapplication", leave);
			return "leaveform";	
		}
		
		List<LeaveApplication> lalist=appRepo.getLeaveAppForEmployee(id, "Applied", "Updated", "Approved",leaveid);
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
		System.out.println("----------------Operation:"+operation);
		if(operation.equals("update"))
		{
			System.out.println("----------balance:"+balance);
			balance=balance+appRepo.getnumofleaves(leaveid);
			System.out.println("----------balance:"+balance+"leave.getNumofdays();"+appRepo.getnumofleaves(leaveid));
		}
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
        emailservice.sendSimpleMessage(e.getEmailid(),"Your Leave Application Submitted","Leave Id:"+leave.getId().toString());
        emailservice.sendSimpleMessage(empRepo.getManemail(leave.getManager()),"Your Subordinate Leave Application is Submitted in your queue","Leave Id:"+leave.getId().toString());
        balance=balance-numofdays;
        LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(id,leave.getLeavetype()),balance);
		balRepo.save(lbal);
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
		
		//for the list of employees on leave within leave period
		
		int days = Period.between(l.getStartdate().toLocalDate(), l.getEnddate().toLocalDate()).getDays();
		
		List<LocalDate> dates = new ArrayList<LocalDate>(days); 
		List<String> datestring = new ArrayList<String>();
		
		for (int i=0; i <= days; i++) {
		    LocalDate d = l.getStartdate().toLocalDate().plusDays(i);
		    dates.add(d);
		    datestring.add(d.toString());
		}
		System.out.println("Dates on Leave : "+dates.toString());

		List<String> emponleave = new ArrayList<String>();
		
		for(LocalDate date : dates){
			List<String> emp = appRepo.getEmployeeNameByDate(date);
			if (emp.toString()!="") {
				emponleave.add(emp.toString().substring(1, emp.toString().length() - 1));
			}
			else { 
				emponleave.add("No employee on leave".toString()); // I failed to do this, maybe someone can help 
				System.out.println("No employee on leave");
			}
		}
		
		System.out.println("Employees on Leave : "+emponleave.toString());
		model.addAttribute("emponleave", emponleave);
		model.addAttribute("datestring", datestring);
		
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
		emailservice.sendSimpleMessage(l.getEmployee().getEmailid(),"Your Leave Application is Rejected","Leave Id:"+l.getId().toString());
		double balance=balRepo.getBalance(l.getEmployee().getId(), l.getLeavetype());
		balance=balance+l.getNumofdays();
		LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(l.getEmployee().getId(),l.getLeavetype()),balance);
		balRepo.save(lbal);
	    return "redirect:/approveleave/"+userid;
	}
	
	@RequestMapping(path = "/approveleave/approve/{id}/{userid}/{managercomment}", method = RequestMethod.GET)
    public String acceptSubmitLeave(Model model, @PathVariable(value = "id") String id,@PathVariable(value = "userid") String userid,
    		@PathVariable(value = "managercomment") String comment) {
		LeaveApplication l=appRepo.findById(Integer.parseInt(id)).orElse(null);
		l.setStatus("Approved");
		l.setManagercomment(comment);
		appRepo.save(l);
		emailservice.sendSimpleMessage(l.getEmployee().getEmailid(),"Your Leave Application is Approved","Leave Id:"+l.getId().toString());
		//double balance=balRepo.getBalance(l.getEmployee().getId(), l.getLeavetype());
		//balance=balance-l.getNumofdays();
        //LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(l.getEmployee().getId(),l.getLeavetype()),balance);
		//balRepo.save(lbal);
	    return "redirect:/approveleave/"+userid;
	}
	
	@RequestMapping(path = "/viewallleave/{id}", method = RequestMethod.GET)
    public String ViewAllLeave(Model model, @PathVariable(value = "id") String id,@PageableDefault(size = 10) Pageable pageable) {
		Page<LeaveApplication> leavelist=appPageRepo.findAll(pageable);
		List<LeaveEntitlement> leavetypes=entRepo.findAll();
		model.addAttribute("userid", id);
		//model.addAttribute("leavelist", leavelist);
		model.addAttribute("leavetype", leavetypes);
		model.addAttribute("role", "NoView");
		model.addAttribute("page", leavelist);
		return "ViewAllLeaveHistory";
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
	
	@RequestMapping(path = "/manapplyleave/{id}", method = RequestMethod.GET)
    public String ApplyLeave(Model model, @PathVariable(value = "id") String id) {
		model.addAttribute("leaveapplication", new LeaveApplication());
		Employee e = empRepo.findById(id).orElse(null);
		System.out.println("------------------"+e.getManagerid()+""+id);
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Manager");
		model.addAttribute("userid", id);
		model.addAttribute("employee", e);
		model.addAttribute("leavetypes", leavetypes);
		model.addAttribute("status", "Applied");
		return "managerleaveform";
	}
	
	@RequestMapping(path = "/savemanleave", method = RequestMethod.POST)
    public String saveManLeaveType(LeaveApplication leave,@RequestParam("userid") String id,@RequestParam("days") String days,Model model) {
		int numofdays;
		double balance;
		String leaveid,operation;
		Employee e = empRepo.findById(id).orElse(null);
		System.out.println("--------------"+leave.getId());
		if(leave.getId()==null)
		{
			leaveid="";
			operation="create";
		}
		else
		{
			leaveid=leave.getId().toString();
			operation="update";
		}
		
		// leave start or end date cannot be on public holiday or weekend
		if( !LeaveCalculation.isWorkingDay(leave.getStartdate()) || !LeaveCalculation.isWorkingDay(leave.getEnddate()) )
		{
			model.addAttribute("Error", "error");
			model.addAttribute("Message", "The start date or end date cannot be on public holiday or weekend.");
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Manager");
			model.addAttribute("userid", id);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			model.addAttribute("status",leave.getStatus());
			model.addAttribute("leaveapplication", leave);
			return "managerleaveform";	
		}

		List<LeaveApplication> lalist=appRepo.getLeaveAppForEmployee(id, "Applied", "Updated", "Approved",leaveid);
		if(LeaveCalculation.checkLeaveAppDates(lalist, leave.getStartdate().toLocalDate(), leave.getEnddate().toLocalDate()))
		{
			model.addAttribute("Error", "error");
			model.addAttribute("Message", "You have some leaves applied/approved between these dates....Please delete and add...");
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Manager");
			model.addAttribute("userid", id);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			model.addAttribute("status",leave.getStatus());
			model.addAttribute("leaveapplication", leave);
			return "managerleaveform";	
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
		System.out.println("----------------operation"+operation);
		if(operation.equals("update"))
		{
			balance=balance+appRepo.getnumofleaves(leaveid);
			System.out.println("---------------balance"+balance+"appRepo.getnumofleaves(leaveid)"+appRepo.getnumofleaves(leaveid));
		}
		if(balance<numofdays)
		{
			model.addAttribute("Error", "error");
			model.addAttribute("Message", "Out of Balance for selected type....");
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Manager");
			model.addAttribute("userid", id);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			model.addAttribute("status", leave.getStatus());
			model.addAttribute("leaveapplication", leave);
			return "managerleaveform";
		}
		leave.setNumofdays(numofdays);
		leave.setStatus("Approved");
		leave.setManagercomment("Auto-Approved");
        appRepo.save(leave);
        balance=balance-numofdays;
        LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(id,leave.getLeavetype()),balance);
		balRepo.save(lbal);
        model.addAttribute("id", id);
        return "redirect:/managerhome/"+id;
	}
	
	@RequestMapping(path = "/viewmanleave/{id}", method = RequestMethod.GET)
    public String viewManLeave(Model model, @PathVariable(value = "id") String id) {
		List<LeaveApplication> leavelist=appRepo.getLeaveByEmployee(id);
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Manager");
		model.addAttribute("userid", id);
		model.addAttribute("leavelist", leavelist);
		model.addAttribute("leavetype", leavetypes);
		model.addAttribute("role", "Manager");
		return "ViewManLeaveHistory";
	}

	@RequestMapping(path = "/savemanleave/edit/{id}/{userid}", method = RequestMethod.GET)
    public String updateManLeave(Model model, @PathVariable(value = "id") String id,@PathVariable(value = "userid") String userid) {
		LeaveApplication l=appRepo.findById(Integer.parseInt(id)).orElse(null);
		System.out.println("---------------"+l.getStatus());
		if(l.getStatus().equals("Approved"))
		{
			model.addAttribute("status",l.getStatus());
			Employee e = empRepo.findById(userid).orElse(null);
			model.addAttribute("leaveapplication", l);
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Manager");
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
			List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Manager");
			model.addAttribute("userid", userid);
			model.addAttribute("employee", e);
			model.addAttribute("leavetypes", leavetypes);
			return "ReadonlyLeaveForm";	
		}
		
	}
	@RequestMapping(path = "/viewmanbalance/{id}", method = RequestMethod.GET)
    public String viewManBalance(Model model, @PathVariable(value = "id") String id) {
		List<LeaveEntitlement> leavetypes=entRepo.getLeaveByRole("Manager");
		model.addAttribute("leavetype", leavetypes);
		List<LeaveBalance> ballist=balRepo.getEmployeeBalance(id);
		for(LeaveBalance l:ballist)
			System.out.println("-------------------"+l.getLeavebalance());
		model.addAttribute("ballist", ballist);
		model.addAttribute("userid", id);
		return "viewmanBalance";
	}
	
//For both Manager and Employee
	
	@RequestMapping(path = "/movementregister/{id}", method = RequestMethod.GET)
    public String movementRegister(Model model, @PathVariable(value = "id") String id) {
		    String current=LocalDate.now().getMonth().name();
		    String next=LocalDate.now().plusMonths(1).getMonth().name();
		    String previous=LocalDate.now().minusMonths(1).getMonth().name();
		    int currentmonth=LocalDate.now().getMonthValue();
		    int nextmonth=LocalDate.now().plusMonths(1).getMonthValue();
		    int previousmonth=LocalDate.now().minusMonths(1).getMonthValue();
		    int year=LocalDate.now().getYear();
		    model.addAttribute("current", current);
		    model.addAttribute("next", next);
		    model.addAttribute("previous", previous);
		    model.addAttribute("currentmonth", currentmonth);
		    model.addAttribute("nextmonth", nextmonth);
		    model.addAttribute("previousmonth", previousmonth);
		    model.addAttribute("year", year);
		    model.addAttribute("userid", id);
		    model.addAttribute("role", empRepo.getRole(id));
		    return "movementregisterform";
			}
	@RequestMapping(path = "/viewmovement", method = RequestMethod.POST)
    public String viewMovement(Model model, @RequestParam(value = "userid") String id,@RequestParam(value="year") String year,
    		@RequestParam(value="role") String role,@RequestParam String month) {
		List<LeaveApplication> leavelist=appRepo.getMovement(month, year, "Approved");
		model.addAttribute("userid", id);
		model.addAttribute("role", role);
		model.addAttribute("leavelist",leavelist);
		List<LeaveEntitlement> leavetypes=entRepo.findAll();
		model.addAttribute("leavetype", leavetypes);
		return "viewmovement";
	}
	
}
