package org.vodafone.consumer.iot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.vodafone.consumer.iot.exception.GPSDataNotFoundException;
import org.vodafone.consumer.iot.exception.ProductIdNotFoundException;
import org.vodafone.consumer.iot.model.CsvDeviceData;
import org.vodafone.consumer.iot.model.DeviceDetailsResponse;
import org.vodafone.consumer.iot.model.SuccesResponse;
import org.vodafone.consumer.iot.repository.IoTDataRepository;
import org.vodafone.consumer.iot.service.impl.IoTDataServiceImpl;

/**
 * @author NareshB
 *IoTDataServiceImpl test class
 */
@ExtendWith(SpringExtension.class)
public class IoTDataServiceImplTest {

	@InjectMocks
	private IoTDataServiceImpl ioTDataServiceImpl;
	
	@Mock
	private IoTDataRepository  ioTDataRepository;
	
	Map<String, List<CsvDeviceData>> data=new HashMap<>();
	
	@BeforeTestMethod
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
	
	/**
	 * @throws IOException
	 */
	@Test
	public void NotloadingLotDataTest() throws IOException{
		when(ioTDataRepository.loadIoTData(Mockito.anyString())).thenReturn(data);
		
		SuccesResponse success=ioTDataServiceImpl.loadIoTData(Paths.get("src/test/resources/testdata/data.csv").toFile().getAbsolutePath());
		assertNull(success);
		String resourceDirectory = Paths.get("src/test/resources/testdata/data.csv").toFile().getAbsolutePath();
		System.out.println("FILE PATH"+resourceDirectory);
		System.out.println("DATA LINES"+data);
	}
	
	/**
	 * loadLotData test method 
	 * @throws IOException
	 */
	@Test
	public void loadLotDataTest() throws IOException{
		when(ioTDataRepository.loadIoTData(Mockito.anyString())).thenReturn(createLoadData());
		
		SuccesResponse success=ioTDataServiceImpl.loadIoTData(Paths.get("src/test/resources/testdata/data.csv").toFile().getAbsolutePath());
		assertEquals("data refreshed",success.getDescription());
		String resourceDirectory = Paths.get("src/test/resources/testdata/data.csv").toFile().getAbsolutePath();
		System.out.println("FILE PATH"+resourceDirectory);
		System.out.println("DATA LINES"+data);
	}
	
	/**
	 * getDeviceLocation test method
	 */
	@Test
	public void getDeviceLocationtest() {
		when(ioTDataRepository.getDeviceData(Mockito.anyString())).thenReturn(createList1());
		DeviceDetailsResponse  deviceDetailsResponse = ioTDataServiceImpl.getDeviceLocation("WG11155638", "1582605077000");
		assertEquals("SUCCESS: Location identified.",deviceDetailsResponse.getDescription());
	}
	

	@Test
	public void getDeviceLocationWithTimeDiff() {
		when(ioTDataRepository.getDeviceData(Mockito.anyString())).thenReturn(createList1());
		DeviceDetailsResponse  deviceDetailsResponse = ioTDataServiceImpl.getDeviceLocation("WG11155638", "1582605485948");
		assertEquals("SUCCESS: Location identified.",deviceDetailsResponse.getDescription());
	}
	
	@Test
	public void NoGPSDataTestAiplaneModeON() {
		when(ioTDataRepository.getDeviceData(Mockito.anyString())).thenReturn(createList4());
		DeviceDetailsResponse  deviceDetailsResponse = ioTDataServiceImpl.getDeviceLocation("6900233111", "1582605615000");
		assertEquals("SUCCESS: Location not available: Plase turn off aiplane mode.", deviceDetailsResponse.getDescription());
		
	}
	
	@Test
	public void NoGPSDataTestAiplaneModeOFF() {
		when(ioTDataRepository.getDeviceData(Mockito.anyString())).thenReturn(createList4());
		
		GPSDataNotFoundException gPSDataNotFoundException=assertThrows(GPSDataNotFoundException.class,
	            ()->{
	            	ioTDataServiceImpl.getDeviceLocation("6900233111", "1582612875000");
	            });
		
		assertEquals("No GPS data available in the csv file::", gPSDataNotFoundException.getMessage());
	}
	
	@Test
	public void NotfoundProductID() {
		when(ioTDataRepository.getDeviceData(Mockito.anyString())).thenReturn(new ArrayList<CsvDeviceData>());
		 
		 ProductIdNotFoundException productIdNotFoundException=assertThrows(ProductIdNotFoundException.class,
		            ()->{
		            	ioTDataServiceImpl.getDeviceLocation("69002331110", "1582612875000");
		            });
			
			assertEquals("ProductId Not found in CSV file", productIdNotFoundException.getMessage());
	}
	
	@Test
	public void NotfoundProductIDNull() {
		List<CsvDeviceData> list=null;
		when(ioTDataRepository.getDeviceData(Mockito.anyString())).thenReturn(list);
		 
		 ProductIdNotFoundException productIdNotFoundException=assertThrows(ProductIdNotFoundException.class,
		            ()->{
		            	ioTDataServiceImpl.getDeviceLocation("69002331110", "1582612875000");
		            });
			
			assertEquals("ProductId Not found in CSV file", productIdNotFoundException.getMessage());
	}
	
	
	
	//Creating mock test data
	private Map<String, List<CsvDeviceData>> createLoadData() {
		Map<String, List<CsvDeviceData>> data=new HashMap<>();
		
	
		data.put("WG11155638", createList1());
		
		data.put("6900001001", createList2());
		
		data.put("WG11155800", createList3());
		
		data.put("6900233111", createList4());
		data.put("6800233111", createList5());
		
		return data;
	}
	
	public List<CsvDeviceData> createList1() {
		
		CsvDeviceData csvDeviceData1 =createdCsvDeviceData("1582605077000","WG11155638","51.5185","-0.1736","0.99","OFF","OFF");
		CsvDeviceData csvDeviceData2 =createdCsvDeviceData("1582605137000","WG11155638","51.5185","-0.1736","0.99","OFF","OFF");
		CsvDeviceData csvDeviceData3 =createdCsvDeviceData("1582605197000","WG11155638","51.5185","-0.1736","0.99","OFF","OFF");
		List<CsvDeviceData> list1=new ArrayList<>();
		list1.add(csvDeviceData1);
		list1.add(csvDeviceData2);
		list1.add(csvDeviceData3);
		return list1;
	}
	
	
	public List<CsvDeviceData> createList2() {
		CsvDeviceData csvDeviceData4 =createdCsvDeviceData("1582605257000","6900001001","40.73061","-73.935242","0.11","N/A","OFF");
		CsvDeviceData csvDeviceData5 =createdCsvDeviceData("1582605258000","6900001001","40.73071","-73.935242","0.1","N/A","OFF");
		CsvDeviceData csvDeviceData6 =createdCsvDeviceData("1582605259000","6900001001","40.73081","-73.935242","0.1","N/A","OFF");
		List<CsvDeviceData> list2=new ArrayList<>();
		list2.add(csvDeviceData4);
		list2.add(csvDeviceData5);
		list2.add(csvDeviceData6);
		return list2;
	}
	
	public List<CsvDeviceData> createList3() {
		CsvDeviceData csvDeviceData7 =createdCsvDeviceData("1582605317000","WG11155800","45.5185","-12.52029","0.11","ON","OFF");
		CsvDeviceData csvDeviceData8 =createdCsvDeviceData("1582605377000","WG11155800","45.5186","-12.52027","0.1","ON","OFF");
		List<CsvDeviceData> list3=new ArrayList<>();
		list3.add(csvDeviceData7);
		list3.add(csvDeviceData8);
		
		return list3;
	}
	
	public List<CsvDeviceData> createList4() {
		CsvDeviceData csvDeviceData9 =createdCsvDeviceData("1582605615000","6900233111","","","0.1","N/A","ON");
		CsvDeviceData csvDeviceData10 =createdCsvDeviceData("1582612875000","6900233111","","","0.1","N/A","OFF");
		CsvDeviceData csvDeviceData11 =createdCsvDeviceData("1582612875000","6800233111","45.5186","-12.52027","0.1","OFF","OFF");
		List<CsvDeviceData> list4=new ArrayList<>();
		list4.add(csvDeviceData9);
		list4.add(csvDeviceData10);
		list4.add(csvDeviceData11);
		return list4;
	}
	
	public List<CsvDeviceData> createList5() {
		CsvDeviceData csvDeviceData11 =createdCsvDeviceData("1582612875000","6800233111","45.5186","-12.52027","0.1","OFF","OFF");
		List<CsvDeviceData> list5=new ArrayList<>();
		list5.add(csvDeviceData11);
		return list5;
	}
	//Creating mock test data
	private CsvDeviceData createdCsvDeviceData(String dateTime, String productId, String latitude,String longitude, String battery,String light, String airmode) {
		CsvDeviceData csvDeviceData=new CsvDeviceData();
		csvDeviceData.setDateTime(dateTime);
		csvDeviceData.setProductId(productId);
		csvDeviceData.setLatitude(latitude);
		csvDeviceData.setLongitude(longitude);
		csvDeviceData.setLight(light);
		csvDeviceData.setBattery(battery);
		csvDeviceData.setAirplaneMode(airmode);
		
		return csvDeviceData;
	}
}
