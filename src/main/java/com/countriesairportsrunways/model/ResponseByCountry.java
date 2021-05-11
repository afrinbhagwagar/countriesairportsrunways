package com.countriesairportsrunways.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseByCountry {
  
  private String countryCode;
  private String countryName;
  private String airportIdentity;
  private String airportType;
  private String airportName;
  private String runwayId;
  private String runwaySurface;
  
}
