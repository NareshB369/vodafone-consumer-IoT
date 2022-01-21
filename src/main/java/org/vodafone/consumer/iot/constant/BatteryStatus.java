package org.vodafone.consumer.iot.constant;

/**
 * @author NareshB
 *This enum created to used BatteryStatus constants.
 */
public enum BatteryStatus {
	FULL, HIGH, MEDIUM, LOW, CRITICAL;

	/**
	 * @return
	 */
	public String getStatus() {
		switch (this) {
		case FULL:
			return "Full";
		case HIGH:
			return "High";
		case MEDIUM:
			return "Medium";
		case LOW:
			return "Low";
		case CRITICAL:
			return "Critical";
		default:
		      return "";
		}
	}
}
