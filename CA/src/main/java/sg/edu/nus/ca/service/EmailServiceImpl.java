package sg.edu.nus.ca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
	
	@Autowired
    public JavaMailSender emailSender;
 
	
	@Autowired
	public EmailServiceImpl(JavaMailSender emailSender) {
		super();
		this.emailSender = emailSender;
	}


    public void sendSimpleMessage(
      String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }

}
