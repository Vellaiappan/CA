package sg.edu.nus.ca.service;

import org.apache.commons.codec.digest.DigestUtils;

public class HashPasswords {

	 public static String encodeSimple(String password) {
		 String encodedString = DigestUtils.md5Hex(password);
		 return encodedString;
		  }
        
}
