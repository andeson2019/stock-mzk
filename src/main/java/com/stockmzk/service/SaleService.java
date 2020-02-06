package com.stockmzk.service;

import com.stockmzk.exceptions.ValidatorException;
import com.stockmzk.model.Product;
import com.stockmzk.model.Sale;
import com.stockmzk.repository.SaleRepository;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.impl.JsonCodec;

import java.util.ArrayList;
import java.util.List;

public class SaleService {

  private static final SaleRepository repository = new SaleRepository();

  private static final ProductService productService = new ProductService();

  public List<JsonObject> newSale(List<Product> products) {

    final List<JsonObject> resultList = new ArrayList<>();

    products.forEach(item -> {
      var p = productService.getProductToSale(item);

      if (!p.isEmpty()) {
        var product = JsonCodec.decodeValue(p.toString(), Product.class);
        productService.delete(JsonObject.mapFrom(product));
        var s = repository.ifPresent(item);
        Sale sale = new Sale();
        product.setAmountInStock(product.getAmountInStock() - 1);
        sale.setProduct(product);
        var amount = s.getProduct() != null ? s.getAmount() + 1 : 1;
        sale.setAmount(amount);

        try {
          productService.addProduct(product);
        } catch (ValidatorException e) {
          e.printStackTrace();
        }

        repository.add(JsonObject.mapFrom(sale));
        resultList.add(JsonObject.mapFrom(product));
      }
    });

    return resultList;
  }

  public JsonArray getSales() {
    return repository.getSales();
  }

}
