package com.accenture.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.accenture.model.Airports;
import com.accenture.model.ResponseAirport;
import com.accenture.model.ResponseByCountry;
import com.accenture.service.helper.CsvReaderHelper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class CountriesAirportsRunwaysServiceImpl implements CountriesAirportsRunwaysService {

  private static Logger logger = LoggerFactory.getLogger(CountriesAirportsRunwaysServiceImpl.class);

  @Override
  public List<ResponseByCountry> getListOfRunwaysByCountryCodeOrName(String country) throws IOException, CsvException {

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

    List<String[]> allAirports = CsvReaderHelper.completeUrlAndFileRead("com/accenture/airports.csv");
    List<Airports> listOfAirports = new ArrayList<>();

    allAirports.forEach(x -> {
      if (x[8].equalsIgnoreCase(countryCode[0])) {
        listOfAirports.add(new Airports(Integer.parseInt(x[0]), x[1], x[2], x[3]));
      }
    });

    File fileRunways = CsvReaderHelper.retrieveFile("com/accenture/runways.csv");
    return populateOutputByCountry(countryCode, countryName, listOfAirports, fileRunways);

  }

  private List<ResponseByCountry> populateOutputByCountry(String[] countryCode, String countryName,
      List<Airports> listOfAirports, File fileRunways) throws IOException, CsvValidationException {

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
    }
    return outputRunwaysByCountry;
  }

  @Override
  public Map<String, List<ResponseAirport>> getTopTenCountriesHavingMaxAirports()
      throws IOException, CsvValidationException {

    File fileAirports = CsvReaderHelper.retrieveFile("com/accenture/airports.csv");

    Map<String, List<ResponseAirport>> mapOfAirports = new HashMap<>();
    try (CSVReader reader = new CSVReader(new FileReader(fileAirports))) {
      String[] nextAirport;
      while ((nextAirport = reader.readNext()) != null) {
        ResponseAirport airport = new ResponseAirport(nextAirport[1], nextAirport[2], nextAirport[3]);
        if (mapOfAirports.get(nextAirport[8]) != null) {
          mapOfAirports.get(nextAirport[8]).add(airport);
        } else {
          List<ResponseAirport> list = new ArrayList<>();
          list.add(airport);
          mapOfAirports.put(nextAirport[8], list);
        }
      }
    }

    Map<String, List<ResponseAirport>> sortMap = sortByValueCount(mapOfAirports);
    return sortMap.entrySet().stream().limit(10).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  private Map<String, List<ResponseAirport>> sortByValueCount(final Map<String, List<ResponseAirport>> homeListMap) {
    return homeListMap.entrySet().stream()
        .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }
}
