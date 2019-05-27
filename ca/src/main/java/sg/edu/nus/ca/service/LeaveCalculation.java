package sg.edu.nus.ca.service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.ca.model.LeaveApplication;
import sg.edu.nus.ca.repository.PublicHolidayRepository;

@Service
public class LeaveCalculation {

	private static PublicHolidayRepository phRepo;
	
	@Autowired
    public LeaveCalculation(PublicHolidayRepository phRepo) {
        LeaveCalculation.phRepo = phRepo;
    }
	
	// calculate number of working days between 2 dates
	public static int numOfWorkingDays(PublicHolidayRepository phRep, LocalDate start, LocalDate end) {
		
		int count = 0;
		ArrayList<Date> phDates1 = phRep.findAllPublicHolidayDates();
		ArrayList<LocalDate> phDates=new ArrayList<LocalDate>();
		for(Date d:phDates1)
		{
			phDates.add(d.toLocalDate());
		}
		for(LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
			System.out.println(date.getDayOfWeek());
			if(date.getDayOfWeek() == DayOfWeek.SATURDAY)
				continue;
			if(date.getDayOfWeek() == DayOfWeek.SUNDAY)
				continue;
			if(phDates.contains(date))
				continue;
			count++;
		}
		return count;
	}

	// returns true if the current leave application overlaps with any of the past leave applications
    public static boolean checkLeaveAppDates(List<LeaveApplication> lalist,LocalDate start,LocalDate end)
    {
    	LocalDate appStartDate,appEndDate;
    	long days;
    	long daysbet=start.until(end,ChronoUnit.DAYS);
    	for(LeaveApplication la:lalist)
    	{
    		
    		appStartDate=la.getStartdate().toLocalDate();
    		appEndDate=la.getEnddate().toLocalDate();
    		
    		days=appStartDate.until(appEndDate, ChronoUnit.DAYS);
    		
    		 if(daysbet>days)
    		 {
    		  for(LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
    			if(date.isEqual(appStartDate) || date.isEqual(appEndDate))
    				return true;
    		  }
    		 }
    		 else
    		 {
    			for(LocalDate date = appStartDate; date.isBefore(appEndDate.plusDays(1)); date = date.plusDays(1)) {
        			if(date.isEqual(start) || date.isEqual(end))
        				return true;
        	  }	
    		 }
    		
    	}
    	return false;
    }
     
    public static boolean checkCancelDate(LocalDate start)
    {
    	LocalDate current=LocalDate.now();
    	System.out.println(start+"---------"+current);
    	if(start.isBefore(current) || start.isEqual(current))
    	{
    		System.out.println("----------------------");
    		return true;
    	}
    	System.out.println("Problem");
    	return false;
    }
    
    // returns true if the date is a working day, i.e. not weekend or public holiday
    public static boolean isWorkingDay(Date d) {
    	LocalDate date = d.toLocalDate();
    	ArrayList<LocalDate> phDates = getPhDates();
    	
    	if(date.getDayOfWeek() == DayOfWeek.SATURDAY)
			return false;
		if(date.getDayOfWeek() == DayOfWeek.SUNDAY)
			return false;
		if(phDates.contains(date))
			return false;

		return true;    	
    }
    
    // get list of public holidays in LocalDate format
    public static ArrayList<LocalDate> getPhDates() {
    	ArrayList<Date> phDates = phRepo.findAllPublicHolidayDates();
    	ArrayList<LocalDate> phLocalDates = new ArrayList<LocalDate>();
    	
		for(Date phDate : phDates) {
			phLocalDates.add(phDate.toLocalDate());
		}
		
		return phLocalDates;
    }
}