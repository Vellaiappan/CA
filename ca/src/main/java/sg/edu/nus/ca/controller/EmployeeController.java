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
import sg.edu.nus.ca.model.Employee;
import sg.edu.nus.ca.model.LeaveBalance;
import sg.edu.nus.ca.model.LeaveBalanceIdentity;
import sg.edu.nus.ca.model.LeaveEntitlement;
import sg.edu.nus.ca.repository.EmployeeRepository;
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
	public void setEmpRepo(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}
	
	public void setEntRepo(LeaveEntitleRepository entRepo) {
		this.entRepo = entRepo;
	}

	public void setBalRepo(LeaveBalanceRepository balRepo) {
		this.balRepo = balRepo;
	}


	@RequestMapping(path = "/employees", method = RequestMethod.GET)
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", empRepo.findAll());
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
        	for(LeaveEntitlement l:leavelist)
        	{
        		LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(empRepo.findByEmail(employee.getEmailid()),l.getId()),l.getLeavecount());
        		balRepo.save(lbal);
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
    	List<LeaveEntitlement> leavelist=entRepo.getLeaveByRole(e.getRole());
    	for(LeaveEntitlement l:leavelist)
    	{
    		LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(empRepo.findByEmail(e.getEmailid()),l.getId()),l.getLeavecount());
    		balRepo.delete(lbal);
    	}
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
}
