package org.vodafone.consumer.iot.service;

import org.vodafone.consumer.iot.model.DeviceDetailsResponse;
import org.vodafone.consumer.iot.model.SuccesResponse;

public interface IoTDataService {

	/**
	 * @Method is used to load the csv data from the given file path.
	 * @param filepath
	 * @return
	 */
	public SuccesResponse loadIoTData(String filepath);

	/**
	 * This method is used to find the DeviceLocation based on the productId and
	 * tstmp.
	 * 
	 * @param productId
	 * @param tstmp
	 */
	public DeviceDetailsResponse getDeviceLocation(String productId, String tstmp);
}
