package org.vodafone.consumer.iot.repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.vodafone.consumer.iot.exception.FilePathNotFoundException;
import org.vodafone.consumer.iot.model.CsvDeviceData;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * @author NareshB
 *
 */
@Repository
public class IoTDataRepository {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(IoTDataRepository.class);
	/**
	 * DELIMITER
	 */
	private static final char DELIMITER=',';
	
	/**
	 * This dataLoad is used to store the CSV Data.
	 */
	Map<String, List<CsvDeviceData>> dataLoad=new HashMap<String, List<CsvDeviceData>>();
	
	
	/**
	 * This method is used to reading the data from CSV file based on comma(,) delimiter,
	 * And Store it into Map object.
	 * @param path
	 * @return
	 * @throws FileNotFoundException 
	 */
	public Map<String, List<CsvDeviceData>> loadIoTData(String filepath) {
		
		 Reader reader;
		try {
			reader = new BufferedReader(new FileReader(filepath));
			LOGGER.info("Strated reading CSV Data from {}", filepath);
		} catch (FileNotFoundException e) {
			LOGGER.error("Error occured due to Incorrect FileName or FilePath {}", filepath);
			throw new FilePathNotFoundException("Incorrect FileName or FilePath ! ::");
		}
		CsvToBean<CsvDeviceData> csvReader = new CsvToBeanBuilder<CsvDeviceData>(reader)
		                .withType(CsvDeviceData.class)
		                .withSeparator(DELIMITER)
		                .withIgnoreLeadingWhiteSpace(true)
		                .withSkipLines(2)
		                .build();
		
		/**
		 *Below condition is used to clear the old CSV Data.
		 **/
		if(dataLoad != null){
			dataLoad.clear();
			LOGGER.info("Old CSV Data Cleared...!!");
			}
		
		dataLoad = csvReader.parse().stream().filter(d->d!= null).collect(Collectors.groupingBy(CsvDeviceData::getProductId, Collectors.toList()));;
		LOGGER.info("New CSV Data loaded and refreshed ...!!");
		return dataLoad;
	}

	/**Fetch the device data by using productId.
	 * @param productId
	 * @return
	 */
	public List<CsvDeviceData> getDeviceData(String productId) {
		return dataLoad.get(productId);
	}
	
}
