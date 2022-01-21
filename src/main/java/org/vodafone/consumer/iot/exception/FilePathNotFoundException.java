package org.vodafone.consumer.iot.exception;

/**
 * @author NareshB
 *This exception class can be used throw when resource path not found in the system.
 */
public class FilePathNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilePathNotFoundException(String errorMessage){
		super(errorMessage);
	}
}
