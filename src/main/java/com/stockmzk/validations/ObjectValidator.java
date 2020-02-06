package com.stockmzk.validations;

import com.stockmzk.exceptions.ValidatorException;

public interface ObjectValidator<T> {

  void validate(T object) throws ValidatorException;

}
