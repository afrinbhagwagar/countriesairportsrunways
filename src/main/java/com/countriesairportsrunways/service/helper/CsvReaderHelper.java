package com.countriesairportsrunways.service.helper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.countriesairportsrunways.controller.CountriesAirportsRunwaysController;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

/**
 * Few methods to read CSV files
 *
 */
public class CsvReaderHelper {

  private static Logger logger = LoggerFactory.getLogger(CsvReaderHelper.class);
  private static List<String[]> csvReadOutput;

  public static List<String[]> getCsvReadOutput() {
    return csvReadOutput;
  }

  static {
    csvReadOutput = completeUrlAndFileRead("com/countriesairportsrunways/countries.csv");
  }

  public static List<String[]> completeUrlAndFileRead(String nameOfFile) {
    File file = retrieveFile(nameOfFile);

    List<String[]> returnOutput = null;
    try (CSVReader reader = new CSVReader(new FileReader(file))) {
      returnOutput = reader.readAll();
    } catch (IOException | CsvException ex) {
      logger.error("Problem in reading input CSV file : {}", nameOfFile);
    }
    return returnOutput;
  }

  public static File retrieveFile(String nameOfFile) {
    URL resource = CountriesAirportsRunwaysController.class.getClassLoader().getResource(nameOfFile);
    File file = null;
    try {
      file = Paths.get(resource.toURI()).toFile();
    } catch (URISyntaxException e) {
      logger.error("String not parsed as URI resource for input CSV file : {}", nameOfFile);
    }
    return file;
  }
}
