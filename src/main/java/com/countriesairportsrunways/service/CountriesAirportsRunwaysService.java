package com.countriesairportsrunways.service;

import java.util.List;
import java.util.Map;

import com.countriesairportsrunways.model.ResponseAirport;
import com.countriesairportsrunways.model.ResponseByCountry;
import com.countriesairportsrunways.service.exceptions.GenericApplicationException;

public interface CountriesAirportsRunwaysService {

  List<ResponseByCountry> getListOfRunwaysByCountryCodeOrName(String country) throws GenericApplicationException ;

  Map<String, ResponseAirport> getTopTenCountriesHavingMaxAirports() throws GenericApplicationException;

}
