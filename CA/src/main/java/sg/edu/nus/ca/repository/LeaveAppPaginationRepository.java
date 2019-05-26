package sg.edu.nus.ca.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import sg.edu.nus.ca.model.LeaveApplication;

public interface LeaveAppPaginationRepository extends PagingAndSortingRepository<LeaveApplication,Integer> {

	Page<LeaveApplication> findAll(Pageable pageable);

}
