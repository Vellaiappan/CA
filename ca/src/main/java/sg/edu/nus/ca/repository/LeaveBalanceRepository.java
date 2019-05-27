package sg.edu.nus.ca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.ca.model.LeaveBalance;
import sg.edu.nus.ca.model.LeaveBalanceIdentity;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance,LeaveBalanceIdentity> {
	
	@Query(value="select leavebalance from leave_balance where employeeid=?1 and leavetypeid=?2",nativeQuery=true)
	double getBalance(String empid,int leaveid);

	@Query(value="select * from leave_balance where employeeid=?1",nativeQuery=true)
	List<LeaveBalance> getEmployeeBalance(String empid);
}
