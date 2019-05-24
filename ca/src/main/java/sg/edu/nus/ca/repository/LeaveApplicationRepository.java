package sg.edu.nus.ca.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.ca.model.*;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication,Integer> {

	@Query(value="select * from leave_application where employeeid=?1",nativeQuery=true)
	List<LeaveApplication> getLeaveByEmployee(String empid);
	
	@Query(value="select * from leave_application where manager=?1 and (status=?2 or status=?3)",nativeQuery=true)
	List<LeaveApplication> getLeaveByManager(String mngid,String status1,String status2);
	
	@Query(value="select * from leave_application",nativeQuery=true)
	List<LeaveApplication> getAllLeave();	
	
	@Query(value="select * from leave_application where employeeid=?1 and (status=?2 or status=?3 or status=?4)" ,nativeQuery=true)
	List<LeaveApplication> getLeaveAppForEmployee(String empid,String st1,String st2,String st3);
	
}
