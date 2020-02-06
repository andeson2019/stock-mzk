package com.stockmzk.validations;

import com.stockmzk.exceptions.ValidatorException;

import java.util.List;

public interface JsonValidator {

  <T> T getObjectIfValidJson(String json, Class<T> objectType) throws ValidatorException;

 <T> List<T> getListIfValidJson(String json, Class<T> clazz) throws ValidatorException;


}
