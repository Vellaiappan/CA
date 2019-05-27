package sg.edu.nus.ca.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.ca.model.Admin;
import sg.edu.nus.ca.model.Employee;
import sg.edu.nus.ca.repository.EmployeeRepository;
import sg.edu.nus.ca.service.HashPasswords;

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
        return "EmployeeLogin";
    }
	
	@RequestMapping(path = "/asadmin")
    public String LoginAsAdmin(Model model) {
        return "loginform";
    }
	
	@RequestMapping(path = "/asmanager")
    public String LoginAsManager(Model model) {
		return "ManagerLogin";
    }
	
	@RequestMapping(path = "/asemployee")
    public String LoginAsEmployee(Model model) {
		return "EmployeeLogin";
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
	 @RequestMapping(path="/adminhome")
	 public String HomePage()
	 {
		 return "adminhome";
	 }
	
		@RequestMapping(path = "/verifyemployee",method=RequestMethod.POST)
	    public String VerifyEmployee(@RequestParam("userid") String username,@RequestParam("password") String password,Model model) {
			List<Employee> a=empRepo.findEmployee(username, HashPasswords.encodeSimple(password),"Employee");
			System.out.println(password+"---------"+HashPasswords.encodeSimple(password));
			if(a.size()==0) {
				model.addAttribute("Error","error");
				return "EmployeeLogin";
			} else {
				model.addAttribute("userid", username);
				return "employeehome";
			}
		
		}
		
		@RequestMapping(path = "/employeehome/{userid}",method=RequestMethod.GET)
	    public String HomeEmployee(@PathVariable(value="userid") String username,Model model) {
				model.addAttribute("userid", username);
				return "employeehome";
			}
		
	
		@RequestMapping(path = "/verifymanager",method=RequestMethod.POST)
	    public String VerifyManager(@RequestParam("userid") String username,@RequestParam("password") String password,Model model) {
			List<Employee> a=empRepo.findManager(username, HashPasswords.encodeSimple(password),"Manager");
			System.out.println("-------"+HashPasswords.encodeSimple(password));
			if(a.size()==0) {
				model.addAttribute("Error","error");
				return "ManagerLogin";
			} else {
				model.addAttribute("userid", username);
				return "managerhome";
			}
		
		} 
		
		@RequestMapping(path = "/managerhome/{userid}",method=RequestMethod.GET)
	    public String HomeManager(@PathVariable(value="userid") String username,Model model) {
				model.addAttribute("userid", username);
				return "managerhome";
			}
		
}
