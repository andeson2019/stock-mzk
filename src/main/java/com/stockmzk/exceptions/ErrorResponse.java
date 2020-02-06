package com.stockmzk.exceptions;

import io.vertx.core.json.JsonObject;

public class ErrorResponse {

  private final int code;
  private final String message;

  public ErrorResponse(int statusCode, String message) {
    this.code = statusCode;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String toJson() {
    return JsonObject.mapFrom(this).encodePrettily();
  }

}
