package com.countriesairportsrunways.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.countriesairportsrunways.model.Airports;
import com.countriesairportsrunways.model.ResponseAirport;
import com.countriesairportsrunways.model.ResponseByCountry;
import com.countriesairportsrunways.service.exceptions.GenericApplicationException;
import com.countriesairportsrunways.service.helper.CsvReaderHelper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class CountriesAirportsRunwaysServiceImpl implements CountriesAirportsRunwaysService {

  private static Logger logger = LoggerFactory.getLogger(CountriesAirportsRunwaysServiceImpl.class);

  @Override
  public List<ResponseByCountry> getListOfRunwaysByCountryCodeOrName(String country)
      throws GenericApplicationException {

    String[] countryCode = new String[] {""};
    String countryName = "";
    Optional<String[]> optOfCountry = CsvReaderHelper.getCsvReadOutput().stream()
        .filter(x -> x[1].equalsIgnoreCase(country) || x[2].equalsIgnoreCase(country)).findFirst();
    if (optOfCountry.isPresent()) {
      countryCode[0] = optOfCountry.get()[1];
      countryName = optOfCountry.get()[2];
      logger.debug("countryCode : {}, countryName : {}", countryCode, countryName);
    } else {
      logger.warn("Country code/name is either null, empty or does not exist. Input : {}", country);
      return new ArrayList<>();
    }

    List<Airports> listOfAirports = getListOfAirportsByCountry(countryCode);

    File fileRunways = CsvReaderHelper.retrieveFile("com/countriesairportsrunways/runways.csv");
    return populateOutputByCountry(countryCode, countryName, listOfAirports, fileRunways);

  }

  private List<Airports> getListOfAirportsByCountry(String[] countryCode) {
    List<String[]> allAirports = CsvReaderHelper.completeUrlAndFileRead("com/countriesairportsrunways/airports.csv");
    List<Airports> listOfAirports = new ArrayList<>();

    allAirports.forEach(x -> {
      if (x[8].equalsIgnoreCase(countryCode[0])) {
        listOfAirports.add(new Airports(Integer.parseInt(x[0]), x[1], x[2], x[3]));
      }
    });
    return listOfAirports;
  }

  private List<ResponseByCountry> populateOutputByCountry(String[] countryCode, String countryName,
      List<Airports> listOfAirports, File fileRunways) throws GenericApplicationException {

    List<ResponseByCountry> outputRunwaysByCountry = new ArrayList<>();
    try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileRunways)).withSkipLines(1).build()) {
      String[] nextRunway;
      while ((nextRunway = reader.readNext()) != null) {
        String temp = nextRunway[1];
        Optional<Airports> airport =
            listOfAirports.stream().filter(it -> Integer.parseInt(temp) == (it.getId())).findFirst();
        if (airport.isPresent()) {
          outputRunwaysByCountry.add(new ResponseByCountry(countryCode[0], countryName, airport.get().getIdent(),
              airport.get().getType(), airport.get().getName(), nextRunway[0], nextRunway[5]));
        }
      }
    } catch (IOException ioExc) {
      String message = "FileNotFoundException or IOException while readingCSV files : " + ioExc.getMessage();
      logger.error(message);
      throw new GenericApplicationException(message);
    } catch (CsvValidationException csvExc) {
      String message = "Error in reading/validating CSV file : " + csvExc.getMessage();
      logger.error(message);
      throw new GenericApplicationException(message);
    }
    return outputRunwaysByCountry;
  }

  @Override
  public Map<String, ResponseAirport> getTopTenCountriesHavingMaxAirports() throws GenericApplicationException {

    File fileAirports = CsvReaderHelper.retrieveFile("com/countriesairportsrunways/airports.csv");

    Map<String, ResponseAirport> mapOfAirports = new HashMap<>();
    try (CSVReader reader = new CSVReader(new FileReader(fileAirports))) {
      String[] nextAirport = null;
      while ((nextAirport = reader.readNext()) != null) {
        String countryCode = nextAirport[8];
        if (mapOfAirports.get(countryCode) != null) {
          mapOfAirports.get(countryCode).setTotalNoAirports(mapOfAirports.get(nextAirport[8]).getTotalNoAirports() + 1);
        } else {
          Optional<String[]> optOfCountry =
              CsvReaderHelper.getCsvReadOutput().stream().filter(x -> x[1].equalsIgnoreCase(countryCode)).findFirst();
          if (optOfCountry.isPresent()) {
            mapOfAirports.put(nextAirport[8], new ResponseAirport(optOfCountry.get()[2], 1));
          }

        }
      }
    } catch (IOException ioExc) {
      String message = "FileNotFoundException or IOException while readingCSV files : " + ioExc.getMessage();
      logger.error(message);
      throw new GenericApplicationException(message);
    } catch (CsvValidationException csvExc) {
      String message = "Error in reading/validating CSV file : " + csvExc.getMessage();
      logger.error(message);
      throw new GenericApplicationException(message);
    }

    return mapOfAirports.entrySet().stream()
        .sorted((e1, e2) -> Integer.compare(e2.getValue().getTotalNoAirports(), e1.getValue().getTotalNoAirports()))
        .limit(10)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }

}
