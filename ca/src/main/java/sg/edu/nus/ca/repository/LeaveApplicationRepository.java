package sg.edu.nus.ca.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.ca.model.*;


@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication,Integer> {

	@Query(value="select * from leave_application where employeeid=?1",nativeQuery=true)
	List<LeaveApplication> getLeaveByEmployee(String empid);
	
	@Query(value="select * from leave_application where manager=?1 and (status=?2 or status=?3)",nativeQuery=true)
	List<LeaveApplication> getLeaveByManager(String mngid,String status1,String status2);
	
	@Query(value="select * from leave_application",nativeQuery=true)
	List<LeaveApplication> getAllLeave();	
	
	@Query(value="select * from leave_application where employeeid=?1 and (status=?2 or status=?3 or status=?4) and id not in(?5)" ,nativeQuery=true)
	List<LeaveApplication> getLeaveAppForEmployee(String empid,String st1,String st2,String st3,String id);
	
	@Query(value="select * from leave_application where month(startdate)=?1 and year(startdate)=?2 and status=?3",nativeQuery=true)
	List<LeaveApplication> getMovement(String month,String year,String status);
	
	@Query(value="select numofdays from leave_application where id=?1",nativeQuery=true)
	int getnumofleaves(String id);
	
	//Employees on leave during leave period
	@Query(value="SELECT distinct name from employee, leave_application la where employee.id = la.employeeid and status='approved' and (?1 between la.startdate and la.enddate)",nativeQuery=true)
	List<String> getEmployeeNameByDate(LocalDate date);
}
