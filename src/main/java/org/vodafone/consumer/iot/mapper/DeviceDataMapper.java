package org.vodafone.consumer.iot.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vodafone.consumer.iot.constant.GpsStatus;
import org.vodafone.consumer.iot.exception.GPSDataNotFoundException;
import org.vodafone.consumer.iot.model.CsvDeviceData;
import org.vodafone.consumer.iot.model.DeviceDetailsResponse;
import org.vodafone.consumer.iot.utill.DataConverterUtill;

/**
 * @author NareshB
 *DeviceDataMapper will help to mapCsvDataToDeviceDetailsResponse.
 */
public class DeviceDataMapper {

	private static final Logger LOGGER=LoggerFactory.getLogger(DeviceDataMapper.class);
	/**It can be used to map the CsvData to Device details response.
	 * @param csvDeviceData
	 * @return
	 */
	public static DeviceDetailsResponse mapCsvDataToDeviceDetailsResponse(CsvDeviceData csvDeviceData){
		
		LOGGER.info("Started mapping CsvDataToDeviceDetailsResponse...!");
		DeviceDetailsResponse deviceDetailsResponse=new DeviceDetailsResponse();
		
		deviceDetailsResponse.setId(csvDeviceData.getProductId());
		deviceDetailsResponse.setName(DeviceDataMapperHelper.findProductType(csvDeviceData.getProductId()));
		deviceDetailsResponse.setStatus(setSatusFlag(csvDeviceData,deviceDetailsResponse));
		deviceDetailsResponse.setBattery(DeviceDataMapperHelper.checkBatteryLife(csvDeviceData.getBattery()));
		
		setGPSData(csvDeviceData, deviceDetailsResponse);
		
		deviceDetailsResponse.setDatetime(DataConverterUtill.convertDateTime(csvDeviceData.getDateTime()));
		LOGGER.info("Completed mapping CsvDataToDeviceDetailsResponse...!");
		return deviceDetailsResponse;
	}


	/**
	 * Can be used to set the Latitude & Longitude values to deviceDetailsResponse.
	 * @param csvDeviceData
	 * @param deviceDetailsResponse
	 */
	private static void setGPSData(CsvDeviceData csvDeviceData, DeviceDetailsResponse deviceDetailsResponse) {
		if("OFF".equals(csvDeviceData.getAirplaneMode())){
			if (DeviceDataMapperHelper.checkGPSData(csvDeviceData.getLatitude(), csvDeviceData.getLongitude())) {
				deviceDetailsResponse.setLat(csvDeviceData.getLatitude());
				deviceDetailsResponse.setLnog(csvDeviceData.getLongitude());
				deviceDetailsResponse.setDescription("SUCCESS: Location identified.");
				LOGGER.info("Location identified. AirplaneMode:{}", csvDeviceData.getAirplaneMode());
			}else {
				LOGGER.info("No GPS data available in the csv file. AirplaneMode:{}", csvDeviceData.getAirplaneMode());
				throw new GPSDataNotFoundException("No GPS data available in the csv file::");
			}
		} else if("ON".equals(csvDeviceData.getAirplaneMode())){
			deviceDetailsResponse.setLat("");
			deviceDetailsResponse.setLnog("");
			deviceDetailsResponse.setDescription("SUCCESS: Location not available: Plase turn off aiplane mode.");
			LOGGER.info("Location not available: Plase turn off aiplane mode. AirplaneMode:{}", csvDeviceData.getAirplaneMode());
		}
	}
	
	
	
	
	/**
	 * @param csvDeviceData
	 * @return
	 */
	private static String setSatusFlag(CsvDeviceData csvDeviceData, DeviceDetailsResponse deviceDetailsResponse){
		
		String status=GpsStatus.INACTIVE.getStatus();
		if(csvDeviceData.getLatitude()!= null && csvDeviceData.getLongitude() != null){
			status=GpsStatus.ACTIVE.getStatus();
		}
		//1.1.3 Functionality implemented for CyclePlusTracker, i have used light value which we have already read from csv file.
		if("CyclePlusTracker".equals(deviceDetailsResponse.getName()) && "OFF".equals(csvDeviceData.getLight())){
			status=GpsStatus.INACTIVE.getStatus();
		} else if("CyclePlusTracker".equals(deviceDetailsResponse.getName()) && "N/A".equals(csvDeviceData.getLight())){
			status="N/A";
		}
		LOGGER.info("Found device status : {} ", status);
		return status;
	}
	
	

}
