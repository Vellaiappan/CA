package sg.edu.nus.ca.model;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class PublicHolidays {
	
	@Id
	@NotNull
	private Date date;
	@NotEmpty
	private String name;
	public PublicHolidays() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PublicHolidays(Date date, String name) {
		super();
		this.date = date;
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
