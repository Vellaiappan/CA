package sg.edu.nus.ca.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.ca.model.LeaveApplication;
import sg.edu.nus.ca.repository.LeaveApplicationRepository;
import sg.edu.nus.ca.service.WriteDataToCSV;

@RestController
@RequestMapping("/api/leaveapplication")
public class CsvDownloadController {
	@Autowired
	private LeaveApplicationRepository appRepo;

	public void setAppRepo(LeaveApplicationRepository appRepo) {
		this.appRepo = appRepo;
	}
	
	@GetMapping("/download/leaveapplication.csv")
	  public void downloadCSV(HttpServletResponse response) throws IOException{
	    response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", "attachment; file=customers.csv");
	    
	    List<LeaveApplication> leaveapp = (List<LeaveApplication>) appRepo.getAllLeave();
	    
	    WriteDataToCSV.writeDataToCsvUsingStringArray(response.getWriter(), leaveapp);
	  }
	
}
