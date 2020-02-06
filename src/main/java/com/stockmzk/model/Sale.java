package com.stockmzk.model;

public class Sale {

  private Product product;
  private int amount;

  public Sale() {
  }

  public Sale(Product product, int amount) {
    this.product = product;
    this.amount = amount;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "Sale{" +
      "product=" + product +
      ", amount=" + amount +
      '}';
  }
}
