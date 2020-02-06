package com.stockmzk.api;

import io.vertx.core.json.JsonObject;

public class ApiResponse<T> {

  private int code;
  private T content;

  public ApiResponse(int code, T content) {
    this.code = code;
    this.content = content;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public T getContent() {
    return content;
  }

  public void setContent(T content) {
    this.content = content;
  }

  public String toJson() {
    return JsonObject.mapFrom(this).encodePrettily();
  }
}
