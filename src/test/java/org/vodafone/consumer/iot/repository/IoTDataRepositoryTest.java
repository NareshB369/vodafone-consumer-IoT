package org.vodafone.consumer.iot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.vodafone.consumer.iot.exception.FilePathNotFoundException;
import org.vodafone.consumer.iot.model.CsvDeviceData;

/**
 * @author NareshB
 *IoTDataRepository test class
 */
@ExtendWith(SpringExtension.class)
public class IoTDataRepositoryTest {

	Map<String, List<CsvDeviceData>> data=null;
	
	@InjectMocks
	private IoTDataRepository  ioTDataRepository;
	
	
	@BeforeTestMethod
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
	
	/**
	 *  loadIoTData test method
	 */
	@Test
	public void loadIoTDataTest() {
		data=ioTDataRepository.loadIoTData(Paths.get("src/test/resources/testdata/data.csv").toFile().getAbsolutePath());
		assertNotNull(data);
	}
	
	/**
	 * productNotFound test method
	 */
	@Test
	public void productNotFoundTest() {
		FilePathNotFoundException gPSDataNotFoundException=assertThrows(FilePathNotFoundException.class,
	            ()->{
	            	ioTDataRepository.loadIoTData(Paths.get("src/test/resources/testdata/data1.csv").toFile().getAbsolutePath());
	            });
		assertEquals(gPSDataNotFoundException.getMessage(),"Incorrect FileName or FilePath ! ::");
	}
	
	/**
	 * getDeviceData test method
	 */
	@Test
	public void getDeviceData() {
		data=ioTDataRepository.loadIoTData(Paths.get("src/test/resources/testdata/data.csv").toFile().getAbsolutePath());
		List<CsvDeviceData> list=ioTDataRepository.getDeviceData("WG11155800");
		assertNotNull(list);
	}
	
}
