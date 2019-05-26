package sg.edu.nus.ca.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import sg.edu.nus.ca.model.Employee;

public interface EmployeePaginationRepository extends PagingAndSortingRepository<Employee,String> {
	
	Page<Employee> findAll(Pageable pagable);

}
