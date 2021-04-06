package com.accenture.service;

import java.util.List;
import java.util.Map;

import com.accenture.model.ResponseAirport;
import com.accenture.model.ResponseByCountry;
import com.accenture.service.exceptions.GenericApplicationException;

public interface CountriesAirportsRunwaysService {

  List<ResponseByCountry> getListOfRunwaysByCountryCodeOrName(String country) throws GenericApplicationException ;

  Map<String, ResponseAirport> getTopTenCountriesHavingMaxAirports() throws GenericApplicationException;

}
