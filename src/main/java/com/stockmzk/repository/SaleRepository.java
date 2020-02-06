package com.stockmzk.repository;

import com.stockmzk.model.Product;
import com.stockmzk.model.Sale;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.impl.JsonCodec;

public class SaleRepository {

  private final JsonArray sales = new JsonArray();

  public void add(JsonObject object) {
    sales.add(object);
  }

  public JsonArray getSales() {
    return sales;
  }

  public Sale ifPresent(Product object) {

    try {
      var sale = (JsonObject) sales.stream().filter(s -> {

        var item = JsonCodec.decodeValue(s.toString(), Sale.class);
        return item.getProduct().getBarCode().equals(object.getBarCode())
          && item.getProduct().getSerialNumber() == object.getSerialNumber();

      }).findFirst()
        .orElseGet(JsonObject::new);
      delete(sale);

      return JsonCodec.decodeValue(sale.toString(), Sale.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void delete(JsonObject object) {
    sales.remove(object);
  }

}
