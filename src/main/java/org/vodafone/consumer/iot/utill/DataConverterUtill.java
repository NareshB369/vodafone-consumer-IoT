package org.vodafone.consumer.iot.utill;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author NareshB
 *
 */
public class DataConverterUtill {

	
	/**
	 * @param tstmp
	 * @return
	 */
	public static String convertDateTime(String tstmp){
		String formattedString=null;
		if(tstmp != null){
	      LocalDateTime dateTime =
	              LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(tstmp)), ZoneId.systemDefault());
	      formattedString = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		}
	    return formattedString;  
	}
}
