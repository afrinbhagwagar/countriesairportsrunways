package com.countriesairportsrunways.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.countriesairportsrunways.model.ResponseAirport;
import com.countriesairportsrunways.model.ResponseByCountry;
import com.countriesairportsrunways.service.CountriesAirportsRunwaysService;
import com.countriesairportsrunways.service.exceptions.GenericApplicationException;


@RestController
@RequestMapping("/countries")
public class CountriesAirportsRunwaysController {

  @Autowired
  private CountriesAirportsRunwaysService countriesAirportsRunwaysService;

  /**
   * List of runways by country code or name.
   * @param country code / name as input
   * @return List of runways
   * @throws GenericApplicationException if IO or CSV exception
   */
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/runways")
  public List<ResponseByCountry> getListOfRunwaysByCountryCodeOrName(
      @RequestParam(required = false, name = "country") String country) throws GenericApplicationException {
    return countriesAirportsRunwaysService.getListOfRunwaysByCountryCodeOrName(country);
  }

  /**
   * Top ten countries having max airport
   * @return map of country as key and airport
   * @throws GenericApplicationException if IO or CSV exception
   */
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/airports")
  public Map<String, ResponseAirport> getTopTenCountriesHavingMaxAirports() throws GenericApplicationException {
    return countriesAirportsRunwaysService.getTopTenCountriesHavingMaxAirports();
  }

}
