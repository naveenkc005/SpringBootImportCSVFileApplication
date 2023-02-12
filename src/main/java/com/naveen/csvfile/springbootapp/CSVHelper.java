package com.naveen.csvfile.springbootapp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class CSVHelper {
	
	static Logger logger = (Logger) LoggerFactory.getLogger(CSVHelper.class);
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "PRIMARY_KEY", "NAME", "DESCRIPTION", "UPDATED_TIMESTAMP" };

  public static boolean hasCSVFormat(MultipartFile file) {
	  System.out.println(file.getContentType());
    if (TYPE.equals(file.getContentType())
    		|| file.getContentType().equals("application/vnd.ms-excel")) {
      return true;
    }

    return false;
  }

  public static List<DeveloperTutorial> csvToTutorials(InputStream is) {
	  logger.info("Entry method csvToTutorials ==>");
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<DeveloperTutorial> developerTutorialList = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
      DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


      for (CSVRecord csvRecord : csvRecords) {
    	  DeveloperTutorial developerTutorial = null;
		try {
			if(!csvRecord.get("PRIMARY_KEY").isEmpty())
			{
			developerTutorial = new DeveloperTutorial(
			      Long.parseLong(csvRecord.get("PRIMARY_KEY")),
			      csvRecord.get("NAME"),
			      csvRecord.get("DESCRIPTION"),
			      df1.parse(csvRecord.get("UPDATED_TIMESTAMP"))
			    );
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	  developerTutorialList.add(developerTutorial);
      }
      logger.info("Exit method csvToTutorials ==>");
      return developerTutorialList;
    } catch (IOException e) {
    	logger.info("Exception in method csvToTutorials ==>"+e.getMessage());
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream tutorialsToCSV(List<DeveloperTutorial> developerTutorialList) {
	  logger.info("Entry method tutorialsToCSV ==>");
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (DeveloperTutorial developerTutorial : developerTutorialList) {
        List<String> data = Arrays.asList(
              String.valueOf(developerTutorial.getId()),
              developerTutorial.getName(),
              developerTutorial.getDescription(),
              String.valueOf(developerTutorial.getUpdatedTimestamp())
            );

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      logger.info("Exit method tutorialsToCSV ==>");
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
    	logger.info("Exception in method tutorialsToCSV ==>"+e.getMessage());
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }
}
