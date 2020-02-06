package com.stockmzk.main;

import com.stockmzk.api.ApiResponse;
import com.stockmzk.exceptions.ErrorResponse;
import com.stockmzk.exceptions.ValidatorException;
import com.stockmzk.model.Product;
import com.stockmzk.service.ProductService;
import com.stockmzk.service.SaleService;
import com.stockmzk.validations.ApiValidator;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.List;

public class MainVerticle extends AbstractVerticle {

  private final ProductService productService = new ProductService();
  private final SaleService saleService = new SaleService();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.get("/api/v1/products/").handler(this::getProducts);

    router.post("/api/v1/products/").handler(this::addProducts);

    router.get("/api/v1/sales/").handler(this::getSales);

    router.post("/api/v1/sales/").handler(this::newSale);

    vertx.createHttpServer().requestHandler(router).listen(8080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8080");
      } else {
        startPromise.fail(http.cause());
      }
    });

  }

  /* DESCOMENTAR ESTE TRECHO DE CÓDIGO PARA EXECUTAR DIRETAMENTE PELA IDE
    public static void main(String[] args) {
    io.vertx.core.Launcher.executeCommand("run", MainVerticle.class.getName());
  }*/

  private void getProducts(RoutingContext routingContext) {
    var content = productService.getProducts();
    int status = content.size() > 0 ? 200 : 204;
    successRespnse(routingContext, new ApiResponse(status, content));
  }

  private void addProducts(RoutingContext routingContext) {
    try {
      var jsonString = routingContext.getBodyAsString();
      var product = getIfValidJson(jsonString, Product.class);
      productService.addProduct(product);
      successRespnse(routingContext, new ApiResponse(201, product));
    } catch (ValidatorException e) {
      e.printStackTrace();
      errorResponse(routingContext, new ErrorResponse(400, e.getMessage()));
    }
  }

  private void newSale(RoutingContext routingContext) {
    try {
      var jsonString = routingContext.getBodyAsString();
      var product = getListIfValidJson(jsonString, Product.class);
      var result = saleService.newSale(product);
      int status = result.size() > 0 ? 200 : 204;
      successRespnse(routingContext, new ApiResponse(status, result));
    } catch (ValidatorException e) {
      e.printStackTrace();
      errorResponse(routingContext, new ErrorResponse(400, e.getMessage()));
    }
  }

  private void getSales(RoutingContext routingContext) {
    var content = saleService.getSales();
    int status = content.size() > 0 ? 200 : 204;
    successRespnse(routingContext, new ApiResponse(status, content));
  }

  private void errorResponse(RoutingContext routingContext, ErrorResponse response) {
    routingContext.response()
      .putHeader("content-type", "application/json")
      .setStatusCode(response.getCode())
      .end(response.toJson());
  }

  private void successRespnse(RoutingContext routingContext, ApiResponse response) {
    routingContext.response().setStatusCode(response.getCode())
      .putHeader("content-type", "application/json")
      .end(response.toJson());
  }

  public <T> T getIfValidJson(String json, Class<T> object) throws ValidatorException {
    return new ApiValidator().getObjectIfValidJson(json, object);
  }

  public <T> List<T> getListIfValidJson(String json, Class<T> object) throws ValidatorException {
    return new ApiValidator().getListIfValidJson(json, object);
  }

}
