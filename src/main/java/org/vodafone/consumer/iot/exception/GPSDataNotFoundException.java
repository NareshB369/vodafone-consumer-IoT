package org.vodafone.consumer.iot.exception;

/**
 * @author NareshB
 *This exception class can be used throw when GPS data(latitude, longitude) not found in the csv file.
 */
public class GPSDataNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GPSDataNotFoundException(String errorMessage){
		super(errorMessage);
	}
}
