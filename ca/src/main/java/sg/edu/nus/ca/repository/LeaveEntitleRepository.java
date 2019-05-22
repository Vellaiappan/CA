package sg.edu.nus.ca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.ca.model.LeaveEntitlement;

public interface LeaveEntitleRepository extends JpaRepository<LeaveEntitlement,Integer>{
	
	@Query(value="select * from leave_entitlement where role=?1",nativeQuery=true)
	List<LeaveEntitlement> getLeaveByRole(String role);

}
