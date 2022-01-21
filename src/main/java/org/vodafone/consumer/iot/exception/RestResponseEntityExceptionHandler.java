package org.vodafone.consumer.iot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.vodafone.consumer.iot.model.ErrorResponse;

/**
 * @author NareshB 
 * This exception controllerAdvice class will act as global
 *  exception handler and it having ExceptionHandler method to handle
 *  specific type of exceptions & returns user friendly error message.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);
	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = { FilePathNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleFileNotFoundException(FilePathNotFoundException ex) {
		LOGGER.error("Error occured due to Incorrect FileName or FilePath", ex.getMessage());
		return new ResponseEntity<ErrorResponse>(new ErrorResponse("ERROR: no data file found"), HttpStatus.NOT_FOUND);
	}

	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = { Exception.class})
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		LOGGER.error("Error occured ", ex.getMessage());
		return new ResponseEntity<ErrorResponse>(new ErrorResponse("ERROR: A technical exception occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = { ProductIdNotFoundException.class})
	public ResponseEntity<ErrorResponse> ProductIdNotFoundException(ProductIdNotFoundException ex) {
		LOGGER.error("Error: Product Not available in CSV Data ", ex.getMessage());
		return new ResponseEntity<ErrorResponse>(new ErrorResponse("ERROR: Id <insert productId> not found"), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = { GPSDataNotFoundException.class})
	public ResponseEntity<ErrorResponse> GPSDataNotFoundException(GPSDataNotFoundException ex) {
		LOGGER.error("Error: GPSData not available in csv data ", ex.getMessage());
		return new ResponseEntity<ErrorResponse>(new ErrorResponse("ERROR: Device could not be located"), HttpStatus.BAD_REQUEST);
	}
	
}
