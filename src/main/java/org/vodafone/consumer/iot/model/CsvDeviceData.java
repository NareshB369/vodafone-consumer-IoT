package org.vodafone.consumer.iot.model;

import com.opencsv.bean.CsvBindByPosition;


/**
 * @author NareshB
 *This class is used to map the CSV columns into java properties
 */
public class CsvDeviceData {

	@CsvBindByPosition(position = 0)
	private String dateTime;
	@CsvBindByPosition(position = 1)
	private String eventId;
	@CsvBindByPosition(position = 2)
	private String productId;
	@CsvBindByPosition(position = 3)
	private String latitude;
	@CsvBindByPosition(position = 4)
	private String longitude;
	@CsvBindByPosition(position = 5)
	private String battery;
	@CsvBindByPosition(position = 6)
	private String light;
	@CsvBindByPosition(position = 7)
	private String airplaneMode;
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getBattery() {
		return battery;
	}
	public void setBattery(String battery) {
		this.battery = battery;
	}
	public String getLight() {
		return light;
	}
	public void setLight(String light) {
		this.light = light;
	}
	public String getAirplaneMode() {
		return airplaneMode;
	}
	public void setAirplaneMode(String airplaneMode) {
		this.airplaneMode = airplaneMode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result;
		if ((obj == null) || (getClass() != obj.getClass())) {
			result = false;
		} else {
			CsvDeviceData csvDeviceData = (CsvDeviceData) obj;
			result = dateTime.equals(csvDeviceData.getDateTime()) && eventId.equals(csvDeviceData.getEventId())
					&& productId.equals(csvDeviceData.getProductId()) && latitude.equals(csvDeviceData.getLatitude())
					&& longitude.equals(csvDeviceData.getLongitude()) && battery.equals(csvDeviceData.getBattery())
					&& light.equals(csvDeviceData.getLight()) && airplaneMode.equals(csvDeviceData.getAirplaneMode());
		}
		return result;
	}
	
}
