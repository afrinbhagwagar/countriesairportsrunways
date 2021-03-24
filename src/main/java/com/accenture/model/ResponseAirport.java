package com.accenture.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseAirport {
  private String countryName;
  private int totalNoAirports;
}
