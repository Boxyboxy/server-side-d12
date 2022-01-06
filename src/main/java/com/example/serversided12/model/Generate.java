package com.example.serversided12.model;

import java.io.Serializable;

//represents the form
public class Generate implements Serializable {
  private int numberVal;

  public void setNumberVal(int numberVal) {
    this.numberVal = numberVal;
  };

  public int getNumberVal() {
    return this.numberVal;
  }
}
