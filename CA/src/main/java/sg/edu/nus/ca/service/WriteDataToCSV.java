package sg.edu.nus.ca.service;

import java.io.PrintWriter;
import java.util.List;

import com.opencsv.CSVWriter;

import sg.edu.nus.ca.model.LeaveApplication;

public class WriteDataToCSV {

	public static void writeDataToCsvUsingStringArray(PrintWriter writer,List<LeaveApplication> leaveapp) {
	    String[] CSV_HEADER = { "id", "startdate", "enddate","numofdays","leavetype","reasons","contact","status","manager","managercomment","employeeid"};
	    try (
	      CSVWriter csvWriter = new CSVWriter(writer,
	                    CSVWriter.DEFAULT_SEPARATOR,
	                    CSVWriter.NO_QUOTE_CHARACTER,
	                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                    CSVWriter.DEFAULT_LINE_END);
	    ){
	      csvWriter.writeNext(CSV_HEADER);
	 
	      for (LeaveApplication l : leaveapp) {
	        String[] data = {
	            l.getId().toString(),l.getStartdate().toString(),l.getEnddate().toString(),String.valueOf(l.getNumofdays()),
	            String.valueOf(l.getLeavetype()),l.getReasons(),l.getContact(),l.getStatus(),l.getManager(),l.getManagercomment(),l.getEmployee().getId()
	        };
	        
	        csvWriter.writeNext(data);
	      }
	      
	      System.out.println("Write CSV using CSVWriter successfully!");
	    }catch (Exception e) {
	      System.out.println("Writing CSV error!");
	      e.printStackTrace();
	    }
	  }
	
}
