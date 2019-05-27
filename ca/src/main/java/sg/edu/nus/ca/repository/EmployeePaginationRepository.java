package sg.edu.nus.ca.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.ca.model.Employee;

@Repository
public interface EmployeePaginationRepository extends PagingAndSortingRepository<Employee,String> {
	
	Page<Employee> findAll(Pageable pagable);

}
