package com.accenture.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.accenture.model.ResponseAirport;
import com.accenture.model.ResponseByCountry;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public interface CountriesAirportsRunwaysService {

  List<ResponseByCountry> getListOfRunwaysByCountryCodeOrName(String country) throws IOException, CsvException ;

  Map<String, List<ResponseAirport>> getTopTenCountriesHavingMaxAirports() throws IOException, CsvValidationException;

}
