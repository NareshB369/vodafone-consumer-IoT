package org.vodafone.consumer.iot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.vodafone.consumer.iot.exception.FilePathNotFoundException;
import org.vodafone.consumer.iot.exception.ProductIdNotFoundException;
import org.vodafone.consumer.iot.model.DataLoadRequest;
import org.vodafone.consumer.iot.model.DeviceDetailsResponse;
import org.vodafone.consumer.iot.model.SuccesResponse;
import org.vodafone.consumer.iot.service.IoTDataService;

/**
 * @author NareshB
 * 
 * Integration test class
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegrationTests {
	
	@Autowired
	IoTDeviceDataController ioTDeviceDataController;
	
	@Mock
	private IoTDataService  ioTDataService;
	
	/**
	 * GetDeviceLocation Test method.
	 */
	@Test
	public void getDeviceLocationTest() {
		when(ioTDataService.getDeviceLocation(Mockito.anyString(), Mockito.anyString())).thenReturn(new DeviceDetailsResponse());		
		
		ProductIdNotFoundException productIdNotFoundException=assertThrows(ProductIdNotFoundException.class,
	            ()->{
	            	ioTDeviceDataController.getDeviceLocation("WG11155638", "1582605077000");
	            });
		
		assertEquals("ProductId Not found in CSV file",productIdNotFoundException.getMessage());
	}
	
	/**
	 * loadData test method
	 */
	@Test
	public void loadDataTest() {
		
		DataLoadRequest dataLoadRequest=new DataLoadRequest();
		dataLoadRequest.setFilepath("/data.csv");
		
		when(ioTDataService.loadIoTData(Mockito.anyString())).thenReturn(new SuccesResponse());
			FilePathNotFoundException gPSDataNotFoundException=assertThrows(FilePathNotFoundException.class,
		            ()->{
		            	ioTDeviceDataController.loadIoTData(dataLoadRequest);
		            });
			assertEquals("Incorrect FileName or FilePath ! ::",gPSDataNotFoundException.getMessage());
			
	}

}
