package org.vodafone.consumer.iot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Vodafone-Consumer-IoT API", version = "1.0", description = "IoT location tracking  devices generate the data that was sent from device to the backend platform in specific location and stored as csv file."
		+ "This Vodafone-Consumer-IoT API will collect device information & location details, And it will circulate the device Information when request required."))
public class VodafoneConsumerIoTApplication {

	public static void main(String[] args) {
		SpringApplication.run(VodafoneConsumerIoTApplication.class, args);
	}

}
