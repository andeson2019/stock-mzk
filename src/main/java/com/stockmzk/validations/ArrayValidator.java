package com.stockmzk.validations;

import com.stockmzk.exceptions.ValidatorException;

public interface ArrayValidator<T, K> {

  void notAllowRepetedObjectArray(T object, K array ) throws ValidatorException;

}
