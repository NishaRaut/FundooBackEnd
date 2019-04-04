package com.bridgelabz.fundoo.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bridgelabz.fundoo.note.controller.NoteController;

public class Util {
	private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	/**
	 * get the server public IP address
	 * @return public IP
	 */
	public static String getHostPublicIP() {
		// Find public IP address 
        String systemipaddress = ""; 
        try
        { 
            URL url_name = new URL("http://bot.whatismyipaddress.com"); 
            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream())); 
            // reads system IPAddress 
            systemipaddress = sc.readLine().trim(); 
        } 
        catch (Exception e) 
        { 
            systemipaddress = "Cannot Execute Properly"; 
        } 
		logger.info("Host Public IP : " + systemipaddress);
		logger.trace("Note Creation");
        return systemipaddress;
}

}
