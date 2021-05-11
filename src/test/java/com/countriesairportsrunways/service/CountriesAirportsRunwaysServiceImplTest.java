package com.countriesairportsrunways.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.countriesairportsrunways.model.ResponseAirport;
import com.countriesairportsrunways.model.ResponseByCountry;
import com.countriesairportsrunways.service.CountriesAirportsRunwaysServiceImpl;
import com.countriesairportsrunways.service.exceptions.GenericApplicationException;

@RunWith(SpringRunner.class)
public class CountriesAirportsRunwaysServiceImplTest {

  @TestConfiguration
  static class CountriesAirportsRunwaysServiceImplTestConfiguration {

    @Bean
    public CountriesAirportsRunwaysServiceImpl serviceInitiate() {
      return new CountriesAirportsRunwaysServiceImpl();
    }

  }
  @Autowired
  private CountriesAirportsRunwaysServiceImpl countriesAirportsRunwaysServiceImpl;
  
  @Test
  public void testTopTenPositiveScenario() throws GenericApplicationException {
    Map<String, ResponseAirport> result = countriesAirportsRunwaysServiceImpl.getTopTenCountriesHavingMaxAirports();
    assertNotNull(result);
    assertEquals(10, result.size());
  }
  
  @Test
  public void testListOfRunwaysWhenCountryCodeOrNamePassedAndCompare() throws GenericApplicationException {
    List<ResponseByCountry> resultFromCountryCode = countriesAirportsRunwaysServiceImpl.getListOfRunwaysByCountryCodeOrName("NL");
    assertNotNull(resultFromCountryCode);
    assertFalse(resultFromCountryCode.isEmpty());
    
    List<ResponseByCountry> resultFromCountryName = countriesAirportsRunwaysServiceImpl.getListOfRunwaysByCountryCodeOrName("NETHERLANDS");
    assertNotNull(resultFromCountryName);
    assertFalse(resultFromCountryName.isEmpty());
    
    assertEquals(resultFromCountryCode.size(), resultFromCountryName.size());
  }
  
  @Test
  public void testListOfRunwaysWhenCountryCodeOrNameIsNull() throws GenericApplicationException {
    List<ResponseByCountry> resultFromNullCountryCode = countriesAirportsRunwaysServiceImpl.getListOfRunwaysByCountryCodeOrName(null);
    assertNotNull(resultFromNullCountryCode);
    assertTrue(resultFromNullCountryCode.isEmpty());
  }
  
  @Test
  public void testListOfRunwaysWhenCountryCodeOrNameIsEmpty() throws GenericApplicationException {
    List<ResponseByCountry> resultFromEmptyCountryCode = countriesAirportsRunwaysServiceImpl.getListOfRunwaysByCountryCodeOrName("");
    assertNotNull(resultFromEmptyCountryCode);
    assertTrue(resultFromEmptyCountryCode.isEmpty());
  }
  
  @Test
  public void testListOfRunwaysWhenCountryCodeOrNameNotFound() throws GenericApplicationException {
    List<ResponseByCountry> result = countriesAirportsRunwaysServiceImpl.getListOfRunwaysByCountryCodeOrName("OH");
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
  
}