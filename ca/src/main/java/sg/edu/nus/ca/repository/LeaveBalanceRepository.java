package sg.edu.nus.ca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.ca.model.LeaveBalance;
import sg.edu.nus.ca.model.LeaveBalanceIdentity;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance,LeaveBalanceIdentity> {

}
