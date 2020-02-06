package com.stockmzk.service;

import com.stockmzk.exceptions.ValidatorException;
import com.stockmzk.model.Product;
import com.stockmzk.repository.ProductRepository;
import com.stockmzk.validations.ProductValidator;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.impl.JsonCodec;

public class ProductService {

  private static final ProductRepository repository = new ProductRepository();

  public void addProduct(Product product) throws ValidatorException {
    validate(product);
    validateObjectInArray(product, repository.getProducts());
    var object = JsonObject.mapFrom(product);
    repository.add(object);
  }

  private void validate(Product product) throws ValidatorException {
    new ProductValidator().validate(product);
  }

  private void validateObjectInArray(Product product, JsonArray array) throws ValidatorException {
    new ProductValidator().notAllowRepetedObjectArray(product, array);
  }

  public JsonObject getProductToSale(Product object) {
    var array = getProducts();
    return (JsonObject) array.stream()
      .filter(p -> {
        var product = JsonCodec.decodeValue(p.toString(), Product.class);
        return product.getSerialNumber() == object.getSerialNumber()
          && product.getBarCode().equals(object.getBarCode())
          && product.getAmountInStock() > 0;
      })
      .findFirst()
      .orElseGet(JsonObject::new);
  }

  public void delete(JsonObject jsonObject) {
    repository.delete(jsonObject);
  }

  public JsonArray getProducts() {
    return repository.getProducts();
  }

}
