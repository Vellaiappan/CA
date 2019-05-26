package sg.edu.nus.ca.controller;

import java.sql.Date;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.nus.ca.model.PublicHolidays;
import sg.edu.nus.ca.repository.PublicHolidayRepository;

@Controller
public class PublicHolidayController {
	
	@Autowired
	private PublicHolidayRepository holiRepo;

	public void setHoliRepo(PublicHolidayRepository holiRepo) {
		this.holiRepo = holiRepo;
	}
	
	@RequestMapping(path="/publicholidays",method=RequestMethod.GET)
	public String getPublicHolidays(Model model)
	{
		model.addAttribute("holidays", holiRepo.findAll());
		return "publicholidays";
	}
	
	@RequestMapping(path = "/publicholidays/add", method = RequestMethod.GET)
    public String createPublicHoliday(Model model) {
        model.addAttribute("publicholidays", new PublicHolidays());
        return "AddPublicHoliday";
    }
	
	@RequestMapping(path = "/saveholiday", method = RequestMethod.POST)
    public String savePublicHoliday(@Valid PublicHolidays holi,BindingResult bindingresult) {
		if(bindingresult.hasErrors())
		{
			return "AddPublicHoliday";
		}
        holiRepo.save(holi);
        return "redirect:/publicholidays";
    }
	

    @RequestMapping(path = "/publicholiday/delete/{date}", method = RequestMethod.GET)
    public String deletePublicHoliday(@PathVariable(name = "date") Date date) {
        holiRepo.delete(holiRepo.getPublicHoliday(date.toString()));
        return "redirect:/publicholidays";
    }

}
