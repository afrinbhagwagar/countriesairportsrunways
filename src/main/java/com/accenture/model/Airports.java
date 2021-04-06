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
  
}
