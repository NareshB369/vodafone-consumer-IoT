package org.vodafone.consumer.iot.constant;

/**
 * @author NareshB
 *This enum created to used GpsStatus constants.
 */
public enum GpsStatus {

	INACTIVE, ACTIVE;
	
	
	/**
	 * @return
	 */
	public String getStatus() {
		switch (this) {
		case INACTIVE:
			return "Inactive";
		case ACTIVE:
			return "Active";
		default:
		      return "unKnow";
		}
	}
}
