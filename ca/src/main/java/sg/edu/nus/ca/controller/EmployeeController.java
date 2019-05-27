package sg.edu.nus.ca.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sg.edu.nus.ca.model.Employee;
import sg.edu.nus.ca.model.LeaveApplication;
import sg.edu.nus.ca.model.LeaveBalance;
import sg.edu.nus.ca.model.LeaveBalanceIdentity;
import sg.edu.nus.ca.model.LeaveEntitlement;
import sg.edu.nus.ca.repository.EmployeePaginationRepository;
import sg.edu.nus.ca.repository.EmployeeRepository;
import sg.edu.nus.ca.repository.LeaveApplicationRepository;
import sg.edu.nus.ca.repository.LeaveBalanceRepository;
import sg.edu.nus.ca.repository.LeaveEntitleRepository;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private LeaveEntitleRepository entRepo;
	@Autowired
	private LeaveBalanceRepository balRepo;
	@Autowired
	private EmployeePaginationRepository empPageRepo;
	@Autowired
	private LeaveApplicationRepository appRepo;
	
	@Autowired
	public void setEmpRepo(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}
	
	public void setEntRepo(LeaveEntitleRepository entRepo) {
		this.entRepo = entRepo;
	}

	public void setBalRepo(LeaveBalanceRepository balRepo) {
		this.balRepo = balRepo;
	}
    
	public void setEmpPageRepo(EmployeePaginationRepository empPageRepo) {
		this.empPageRepo = empPageRepo;
	}

	@RequestMapping(path = "/employees", method = RequestMethod.GET)
    public String getAllEmployees(Model model,@PageableDefault(size = 10) Pageable pageable) {
        model.addAttribute("page", empPageRepo.findAll(pageable));
        return "employees";
    }
	
	@RequestMapping(path = "employees", method = RequestMethod.POST)
    public String saveEmployee(@Valid Employee employee,BindingResult bindingResult,Model model) {
		if (bindingResult.hasErrors()) {
			List<Employee> mlist=empRepo.findByRole("Manager");
	        model.addAttribute("mlist", mlist);
            return "AddEmployee";
        }
		try
		{
        empRepo.save(employee);
        	List<LeaveEntitlement> leavelist=entRepo.getLeaveByRole(employee.getRole());
        	List<LeaveApplication> leaveapp=appRepo.getLeaveByEmployee(employee.getId());
        	for(LeaveEntitlement l:leavelist)
        	{
        		LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(empRepo.findByEmail(employee.getEmailid()),l.getId()),l.getLeavecount());
        		balRepo.save(lbal);
        	}
        	if(leaveapp.size()!=0)
        	{
        		for(LeaveApplication la:leaveapp)
        		{
        			la.setManager(employee.getManagerid());
        			appRepo.save(la);
        		}
        	}
         return "redirect:/employees";
		}
		catch(Exception e)
		{
			model.addAttribute("Error", "error");
			model.addAttribute("message", e.getMessage());
			return "AddEmployee";
		}
        
    }
	
	@RequestMapping(path = "/employees/add", method = RequestMethod.GET)
    public String createEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        List<Employee> mlist=empRepo.findByRole("Manager");
        model.addAttribute("mlist", mlist);
        return "AddEmployee";
    }
	
	@RequestMapping(path = "/employees/edit/{id}", method = RequestMethod.GET)
    public String editEmployee(Model model, @PathVariable(value = "id") String id) {   	
    	Employee e = empRepo.findById(id).orElse(null);
    	//List<LeaveEntitlement> leavelist=entRepo.getLeaveByRole(e.getRole());
    	//for(LeaveEntitlement l:leavelist)
    	//{
    		//LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(empRepo.findByEmail(e.getEmailid()),l.getId()),l.getLeavecount());
    		//balRepo.delete(lbal);
    	//}
        model.addAttribute("employee", e);
        List<Employee> mlist=empRepo.findByRole("Manager");
        model.addAttribute("mlist", mlist);
        return "AddEmployee";
    }

    @RequestMapping(path = "/employees/delete/{id}", method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable(name = "id") String id) {
        empRepo.delete(empRepo.findById(id).orElse(null));
        return "redirect:/employees";
    }
    
    @RequestMapping(path = "/movementregister", method = RequestMethod.GET)
    public String movementRegister(Model model) {
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
		    model.addAttribute("userid", "admin");
		    model.addAttribute("role", "admin");
		    return "movementregisterform";
			}
}
