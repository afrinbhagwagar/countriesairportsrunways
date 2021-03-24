package com.accenture.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Airports {

  private int id;
  private String ident;
  private String type;
  private String name;
  private String latitudeDeg;
  private String longitudeDeg;
  private String elevationFt;
  private String continent;
  private String isoCountry;
  private String isoRegion;
  private String municipality;
  private String scheduledService;
  private String gpsCode;
  private String iataCode;
  private String localCode;
  private String homeLink;
  private String wikipediaLink;
  private String keywords;
  
  public Airports(int id, String ident, String type, String name) {
    this.id = id;
    this.ident = ident;
    this.type = type;
    this.name = name;
  }

}
