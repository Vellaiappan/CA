package sg.edu.nus.ca.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.ca.model.LeaveApplication;

@Repository
public interface LeaveAppPaginationRepository extends PagingAndSortingRepository<LeaveApplication,Integer> {

	Page<LeaveApplication> findAll(Pageable pageable);

}
