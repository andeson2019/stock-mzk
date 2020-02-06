package com.stockmzk.validations;

import com.stockmzk.exceptions.ValidatorException;
import com.stockmzk.model.Product;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.handler.sockjs.impl.JsonCodec;

public class ProductValidator implements ObjectValidator<Product>, ArrayValidator<Product, JsonArray> {

  @Override
  public void validate(Product p) throws ValidatorException {
    if (p.getBarCode() == null || p.getBarCode().isBlank()) {
      throw new ValidatorException("Código de barras não pode ser nulo ou vazio.");
    }
    if (p.getName() == null || p.getName().isBlank()) {
      throw new ValidatorException("Nome não pode ser nulo ou vazio.");
    }
    if (p.getSerialNumber() <= 0) {
      throw new ValidatorException("Número de série deve ser maior que 0 (zero).");
    }
  }

  @Override
  public void notAllowRepetedObjectArray(Product object, JsonArray array) throws ValidatorException {
    if (array.stream()
      .anyMatch(p -> {
        var product = JsonCodec.decodeValue(p.toString(), Product.class);
        return product.getSerialNumber() == object.getSerialNumber()
          && product.getBarCode().equals(object.getBarCode());
      })) {
      throw new ValidatorException("Objeto já cadastrado.");
    }
  }

}
