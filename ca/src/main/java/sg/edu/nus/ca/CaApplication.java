package sg.edu.nus.ca;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaApplication {
	
	@PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone // It will print UTC timezone
    }

	public static void main(String[] args) {
		SpringApplication.run(CaApplication.class, args);
	}

}
