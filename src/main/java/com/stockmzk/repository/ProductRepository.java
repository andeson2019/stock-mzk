package com.stockmzk.repository;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class ProductRepository {

  private final JsonArray products = new JsonArray();

  public void add(JsonObject object) {
    products.add(object);
  }

  public JsonArray getProducts() {
    return products;
  }

  public void delete(JsonObject object){
    products.remove(object);
  }
}
