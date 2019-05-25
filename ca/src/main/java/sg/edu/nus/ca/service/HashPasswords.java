package sg.edu.nus.ca.service;

import java.util.Base64;

public class HashPasswords {

	 public static String encodeSimple(String password) {
		 String encodedString = Base64.getEncoder().encodeToString(password.getBytes());
		 return encodedString;
		  }
        
}
