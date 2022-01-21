package org.vodafone.consumer.iot.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vodafone.consumer.iot.model.DeviceDetailsResponse;
import org.vodafone.consumer.iot.service.impl.IoTDataServiceImpl;

/**
 * @author NareshB
 * 
 *         IoTDeviceDataControlle test class
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(IoTDeviceDataController.class)
public class IoTDeviceDataControllerTest {

	@MockBean
	private IoTDataServiceImpl ioTDataServiceImpl;

	@Autowired
	private MockMvc mockMvc;

	/**
	 * GetDeviceLocation test method
	 * 
	 * @throws Exception
	 */
	@Test
	public void getDeviceLocationTest() throws Exception {
		DeviceDetailsResponse deviceDetailsResponse = new DeviceDetailsResponse();
		deviceDetailsResponse.setDescription("Found");
		when(ioTDataServiceImpl.getDeviceLocation(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(deviceDetailsResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/iot/event/v1").param("ProductId", "WG11155638"))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}
}
