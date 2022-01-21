package org.vodafone.consumer.iot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vodafone.consumer.iot.model.DataLoadRequest;
import org.vodafone.consumer.iot.model.DeviceDetailsResponse;
import org.vodafone.consumer.iot.model.SuccesResponse;
import org.vodafone.consumer.iot.service.IoTDataService;

/**
 * @author NareshB
 * 
 *This controller will handle the Post and GET request of the iot event v1.
 */
@RestController
@RequestMapping("/iot/event")
public class IoTDeviceDataController {

	private static final Logger LOGGER=LoggerFactory.getLogger(IoTDeviceDataController.class);
	
	@Autowired
	private IoTDataService  ioTDataService;
	
	/**
	 * This will handle the POST request and return the success response
	 * based on the given file path.
	 * @param request
	 * @return
	 */
	@Operation(summary = "Load CSV file data into Vodafone-Consumer-IoT API", description="It will load the device data from csv file in this API")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Data Refreshed", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = SuccesResponse.class)) }),
	  @ApiResponse(responseCode = "404", description = "No Data file found", 
	    content = @Content), 
	  @ApiResponse(responseCode = "500", description = "A technical exception occured", 
	    content = @Content) })
	
	@PostMapping(value="/v1")
	public ResponseEntity<SuccesResponse> loadIoTData( @io.swagger.v3.oas.annotations.parameters.RequestBody(description="Request body containe filepath like sample EX: 'filepath' : 'c:/path/to/data.csv' ", content={@Content(schema=@Schema(implementation=DataLoadRequest.class))})@RequestBody DataLoadRequest  request){
		LOGGER.info("Loading IoT Data with filepath: {}",request.getFilepath());
		SuccesResponse deviceDetailsResponse=ioTDataService.loadIoTData(request.getFilepath());
		 
		 return new ResponseEntity<SuccesResponse>(deviceDetailsResponse,HttpStatus.OK);
	}
	
	/**
	 * This will handle the GET request and returns the Device details
	 * based in the ProductId & time stamp.
	 * @param productId
	 * @param tstmp
	 * @return
	 */
	@Operation(summary = "Retrieves and returns the device and location details", description="Access the Device inormation and location details by ProductID and timestamp")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Device details and location identified", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = DeviceDetailsResponse.class)) }),
	  @ApiResponse(responseCode = "404", description = "Product ID Not found", 
	    content = @Content), 
	  @ApiResponse(responseCode = "400", description = "Device could not be located", 
	    content = @Content), 
	  @ApiResponse(responseCode = "500", description = "A technical exception occured", 
	    content = @Content)})
	
	@GetMapping(value="/v1")
	public DeviceDetailsResponse getDeviceLocation(@Parameter(description="ProductId which is used to find the device name & deails", example="WG11155638")@RequestParam(name = "ProductId", required = true) String productId,
			@Parameter(description="Timestamp(in milliseconds) which is used to find the device deails/location", example="1582605137000") @RequestParam(name = "tstmp", required = false) String tstmp) {
		LOGGER.info("Started looking device details by productId:{} , tstmp:{} ", productId, tstmp);
		return ioTDataService.getDeviceLocation(productId, tstmp);
	}
}
