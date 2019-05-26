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
public class LeaveEntitleController {
	@Autowired
	private LeaveEntitleRepository leaveRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private LeaveBalanceRepository balRepo;
	
	@Autowired
	public void setLeaveRepo(LeaveEntitleRepository leaveRepo) {
		this.leaveRepo = leaveRepo;
	}
	
	public void setEmpRepo(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}

	public void setBalRepo(LeaveBalanceRepository balRepo) {
		this.balRepo = balRepo;
	}


	@RequestMapping(path = "/savetype", method = RequestMethod.POST)
    public String saveLeaveType(@Valid LeaveEntitlement leave,BindingResult bindingresult,Model model) {
		if(bindingresult.hasErrors())
		{
			return "AddLeaveType";
		}
		List<LeaveEntitlement> llist=leaveRepo.checkLeave(leave.getRole(), leave.getLeavetype());
		if(llist.size()!=0)
		{
			 model.addAttribute("leaveentitlement", new LeaveEntitlement());
			model.addAttribute("Error", "error");
			model.addAttribute("Message","Leave Type already exist for this role....");
			return "AddLeaveType";	
		}
        leaveRepo.save(leave);
        List<Employee> elist=empRepo.findByRole(leave.getRole());
        for(Employee e:elist)
    	{
    		LeaveBalance lbal=new LeaveBalance(new LeaveBalanceIdentity(e.getId(),leave.getId()),leave.getLeavecount());
    		balRepo.save(lbal);
    	}
        return "redirect:/leaveentitle";
    }

	@RequestMapping(path="/leaveentitle",method=RequestMethod.GET)
	public String leaveentitle(Model model)
	{
		model.addAttribute("leavelist",leaveRepo.findAll());
		return "leaveentitle";
	}
	
	@RequestMapping(path = "/leaveentitle/add", method = RequestMethod.GET)
    public String createLeaveType(Model model) {
        model.addAttribute("leaveentitlement", new LeaveEntitlement());
        return "AddLeaveType";
    }
	
	@RequestMapping(path = "/leaveentitle/edit/{id}", method = RequestMethod.GET)
    public String editLeaveType(Model model, @PathVariable(value = "id") Integer id) {   	
    	LeaveEntitlement l = leaveRepo.findById(id).orElse(null);
        model.addAttribute("leaveentitlement", l);
        return "AddLeaveType";
    }

    @RequestMapping(path = "/leaveentitle/delete/{id}", method = RequestMethod.GET)
    public String deleteLeaveType(@PathVariable(name = "id") Integer id) {
        leaveRepo.delete(leaveRepo.findById(id).orElse(null));
        return "redirect:/leaveentitle";
    }
}
