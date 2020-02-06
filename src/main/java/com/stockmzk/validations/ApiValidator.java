package com.stockmzk.validations;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmzk.exceptions.ValidatorException;
import io.vertx.ext.web.handler.sockjs.impl.JsonCodec;

import java.util.List;


public class ApiValidator implements JsonValidator {

  @Override
  public <T> T getObjectIfValidJson(String json, Class<T> objectType) throws ValidatorException {
    try {
      return JsonCodec.decodeValue(json, objectType);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ValidatorException("Objeto JSON inválido");
    }
  }

  @Override
  public <T> List<T> getListIfValidJson(String json, Class<T> clazz) throws ValidatorException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
      return (List<T>) mapper.readValue(json, type);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ValidatorException("Objeto JSON inválido");
    }
  }
}
