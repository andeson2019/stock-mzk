package com.stockmzk.main;

import com.stockmzk.exceptions.ValidatorException;
import com.stockmzk.model.Product;
import com.stockmzk.service.ProductService;
import io.vertx.ext.web.handler.sockjs.impl.JsonCodec;
import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
public class TestProductService {

  ProductService service;
  Product product;
  String jsonString;

  @BeforeEach
  public void setup() {
    service = new ProductService();
    jsonString = "{\"serialNumber\": 6, \"name\": \"Casaco preto\", \"barCode\": \"154646\", \"amountInStock\":10 }";
    product = convert_JsonString_To_Product(jsonString);
  }

  @Test
  public void deve_cadastrar_produto() {
    try {
      service.addProduct(product);
    } catch (ValidatorException e) {
      e.printStackTrace();
    }
    assertTrue(!service.getProducts().isEmpty());
  }

  private Product convert_JsonString_To_Product(String jsonString) {
    return JsonCodec.decodeValue(jsonString, Product.class);
  }

}
