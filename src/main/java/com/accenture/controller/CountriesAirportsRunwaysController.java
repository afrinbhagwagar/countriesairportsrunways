package com.accenture.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.model.ResponseAirport;
import com.accenture.model.ResponseByCountry;
import com.accenture.service.CountriesAirportsRunwaysService;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;


@RestController
@RequestMapping("/countries")
public class CountriesAirportsRunwaysController {

  @Autowired
  private CountriesAirportsRunwaysService countriesAirportsRunwaysService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/runways")
  public List<ResponseByCountry> getListOfRunwaysByCountryCodeOrName(
      @RequestParam(required = false, name = "country") String country) throws IOException, CsvException {
    return countriesAirportsRunwaysService.getListOfRunwaysByCountryCodeOrName(country);

  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/airports")
  public Map<String, ResponseAirport> getTopTenCountriesHavingMaxAirports() throws CsvValidationException, IOException {
    return countriesAirportsRunwaysService.getTopTenCountriesHavingMaxAirports();

  }

}
