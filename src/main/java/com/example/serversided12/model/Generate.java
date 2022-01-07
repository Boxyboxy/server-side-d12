package com.example.serversided12.model;

import java.io.Serializable;

//represents the form
// the instance of this class is being used/ streamed in multiple clusters
// Any server application that interfaces with network must deal with serialization
public class Generate implements Serializable {
  private int numberVal;

  public void setNumberVal(int numberVal) {
    this.numberVal = numberVal;
  };

  public int getNumberVal() {
    return this.numberVal;
  }
}
