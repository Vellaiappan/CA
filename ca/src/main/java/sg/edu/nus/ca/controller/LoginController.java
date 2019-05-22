package sg.edu.nus.ca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.ca.model.Admin;
import sg.edu.nus.ca.repository.EmployeeRepository;

@Controller
public class LoginController {

	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	public void setEmpRepo(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}
	
	@RequestMapping(path = "/")
    public String Login() {
        return "login";
    }
	
	@RequestMapping(path = "/asadmin")
    public String LoginAsAdmin(Model model) {
        return "loginform";
    }
	
	@RequestMapping(path = "/asmanager")
    public String LoginAsManager(Model model) {
		return "loginform_manager";
    }
	
	@RequestMapping(path = "/asemployee")
    public String LoginAsEmployee(Model model) {
		return "loginform_employee";
    }
	
	@RequestMapping(path = "/verifyadmin",method=RequestMethod.POST)
    public String VerifyAdmin(@RequestParam("userid") String username,@RequestParam("password") String password,Model model) {
		List<Admin> a=empRepo.findAdmin(username, password);
		if(a.size()==0) {
			model.addAttribute("Error","error");
			return "loginform";
		} else {
			return "adminhome";
		} 
    }
	
}
