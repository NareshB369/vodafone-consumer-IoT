package org.vodafone.consumer.iot.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vodafone.consumer.iot.exception.ProductIdNotFoundException;
import org.vodafone.consumer.iot.mapper.DeviceDataMapper;
import org.vodafone.consumer.iot.model.CsvDeviceData;
import org.vodafone.consumer.iot.model.DeviceDetailsResponse;
import org.vodafone.consumer.iot.model.SuccesResponse;
import org.vodafone.consumer.iot.repository.IoTDataRepository;
import org.vodafone.consumer.iot.service.IoTDataService;

/**
 * @author NareshB
 *
 */
@Service
public class IoTDataServiceImpl implements IoTDataService{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(IoTDataServiceImpl.class);
	
	@Autowired
	private IoTDataRepository  ioTDataRepository;

	
	@Override
	public SuccesResponse loadIoTData(String filepath) {
		SuccesResponse deviceDetailsResponse=null;
		Map<String, List<CsvDeviceData>> data=ioTDataRepository.loadIoTData(filepath);
		if(data.size()>1){
			deviceDetailsResponse=new SuccesResponse();
			deviceDetailsResponse.setDescription("data refreshed");
			LOGGER.info("Loading new CSV file data completed");
		}
		return deviceDetailsResponse;
	}

	
	@Override
	public DeviceDetailsResponse getDeviceLocation(String productId, String tstmp) {
		CsvDeviceData csvDeviceData=null;
		DeviceDetailsResponse deviceDetailsResponse=null;
		List<CsvDeviceData> deviceList=ioTDataRepository.getDeviceData(productId);
		if(deviceList != null && !deviceList.isEmpty()){
			LOGGER.info("Found device data for the ProductID: {}",productId);
		Predicate<CsvDeviceData> predicate= (dData)-> (dData.getDateTime().equals((tstmp))); 
		deviceList.stream().map(d->d.getDateTime()).forEach(System.out::println);
		csvDeviceData=deviceList.stream().filter(predicate).findFirst().orElse(null);
		if(csvDeviceData == null){
			deviceList.stream().sorted((t1,t2)->t2.getDateTime().compareTo(t1.getDateTime())).collect(Collectors.toList()).forEach(System.out::println);
			csvDeviceData= deviceList.stream().sorted((t1,t2)->t2.getDateTime().compareTo(t1.getDateTime())).collect(Collectors.toList()).get(0);
			LOGGER.info("Found device for the given tstmp: {}",tstmp);
		}
		deviceDetailsResponse=DeviceDataMapper.mapCsvDataToDeviceDetailsResponse(csvDeviceData);
		}else {
			LOGGER.info("Not device data available for the ProductID: {}",productId);
			throw new ProductIdNotFoundException("ProductId Not found in CSV file");
		}
		return deviceDetailsResponse;
	}

}
