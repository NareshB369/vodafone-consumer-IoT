package org.vodafone.consumer.iot.exception;

/**
 * @author NareshB
 *
 *This exception class can be used throw when Product data not found.
 */
public class ProductIdNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductIdNotFoundException(String errorMessage){
		super(errorMessage);
	}
}
