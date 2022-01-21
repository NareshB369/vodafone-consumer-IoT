package org.vodafone.consumer.iot.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vodafone.consumer.iot.constant.BatteryStatus;

/**
 *  
 * @author NareshB
 *
 * DeviceDataMapperHelper
 */
public class DeviceDataMapperHelper {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(DeviceDataMapperHelper.class);

	/**
	 *To find the ProductType 
	 * @param productId
	 * @return
	 */
	public static String findProductType(String productId) {

		String productType = null;
		if (productId.startsWith("WG")) {
			productType = "CyclePlusTracker";
		} else if (productId.startsWith("69")) {
			productType = "GeneralTracker";
		}
		LOGGER.info("findProductType : productId : {} , ProductName: {}", productId, productType);
		return productType;
	}
	
	/**
	 * @param battery
	 * @return
	 */
	public static String checkBatteryLife(String battery) {
		if (battery != null) {
			int batteryPercent = (int) (Float.parseFloat(battery) * 100);
			if (batteryPercent >= 98)
				return BatteryStatus.FULL.getStatus();
			if (60 <= batteryPercent && 98 > batteryPercent)
				return BatteryStatus.HIGH.getStatus();
			if (40 <= batteryPercent && 60 > batteryPercent)
				return BatteryStatus.MEDIUM.getStatus();
			if (10 <= batteryPercent && 40 > batteryPercent)
				return BatteryStatus.LOW.getStatus();
			if (10 >= batteryPercent)
				return BatteryStatus.CRITICAL.getStatus();
		}
		return "";
	}
	

	
	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static boolean checkGPSData(String latitude, String longitude){
		return (latitude != null && longitude !=null) && (!latitude.isEmpty() && !longitude.isEmpty());
	}
}
