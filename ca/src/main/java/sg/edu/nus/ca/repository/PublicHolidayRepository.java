package sg.edu.nus.ca.repository;


import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.ca.model.PublicHolidays;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHolidays,Date> {

	@Query(value="select * from public_holidays where date=?1",nativeQuery=true)
	PublicHolidays getPublicHoliday(String date);
	
	@Query(value="select date from public_holidays",nativeQuery=true)
	ArrayList<Date> findAllPublicHolidayDates();
}
