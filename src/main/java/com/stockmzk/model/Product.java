package com.stockmzk.model;

public class Product {

  private int serialNumber;
  private String name;
  private String barCode;
  private int amountInStock;

  public Product() {

  }

  public int getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(int serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBarCode() {
    return barCode;
  }

  public void setBarCode(String barCode) {
    this.barCode = barCode;
  }

  public int getAmountInStock() {
    return amountInStock;
  }

  public void setAmountInStock(int amountInStock) {
    this.amountInStock = amountInStock;
  }

  @Override
  public String toString() {
    return "Product{" +
      "serialNumber=" + serialNumber +
      ", name='" + name + '\'' +
      ", barCode='" + barCode + '\'' +
      ", amountInStock=" + amountInStock +
      '}';
  }
}
