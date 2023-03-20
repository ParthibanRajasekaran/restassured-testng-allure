package com.template.project.api.request;

import static com.template.project.api.payload.SamplePayload.buildCustomerCreationPayload;
import static com.template.project.api.payload.SamplePayload.buildUpdateCustomerDetailsPayload;
import static com.template.project.common.ConfigFileReaderUtils.getValueFromEnvironmentFile;
import static io.restassured.RestAssured.given;

import com.template.project.api.resource.SampleResource;
import com.template.project.common.Logger;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.IOException;

public class SampleRequests {

  @Step("Create Customer using POST request")
  public static Response createCustomer(String firstname, String lastname) throws IOException {
    final String host = getValueFromEnvironmentFile("host_api");

    final String resourcePath = SampleResource.CUSTOMER_RESOURCE;

    final String requestBody = buildCustomerCreationPayload(firstname, lastname);

    Logger.logInfo("Base URI : " + host);
    Logger.logInfo("Path : " + resourcePath);
    Logger.logRequest(requestBody);

    Response rawRes =
        given()
            .filter(new AllureRestAssured())
            .baseUri(host)
            .basePath(resourcePath)
            .body(requestBody)
            .contentType(ContentType.JSON)
            .and()
            .when()
            .post()
            .then()
            .log()
            .body()
            .contentType(ContentType.JSON)
            .and()
            .extract()
            .response();

    Logger.logResponse(rawRes);
    return rawRes;
  }

  @Step("Get Customer details using GET request")
  public static Response getCustomerDetails(String customerId) {

    final String host = getValueFromEnvironmentFile("host_api");
    final String resourcePath = SampleResource.CUSTOMER_RESOURCE + customerId;

    Logger.logInfo("Base URI : " + host);
    Logger.logInfo("Path : " + resourcePath);

    Response rawRes =
        given()
            .filter(new AllureRestAssured())
            .baseUri(host)
            .basePath(resourcePath)
            .contentType(ContentType.JSON)
            .and()
            .when()
            .get()
            .then()
            .contentType(ContentType.JSON)
            .and()
            .extract()
            .response();

    Logger.logResponse(rawRes);
    return rawRes;
  }

  @Step("Update Customer details using PUT request")
  public static Response updateCustomerDetails(
      String customerId, String newFirstName, String newLastName) throws IOException {

    String host = getValueFromEnvironmentFile("host_api");
    final String resourcePath = SampleResource.CUSTOMER_RESOURCE + customerId;

    String requestBody = buildUpdateCustomerDetailsPayload(newFirstName, newLastName);

    Logger.logInfo("Base URI : " + host);
    Logger.logInfo("Path : " + resourcePath);
    Logger.logRequest(requestBody);

    Response rawRes =
        given()
            .filter(new AllureRestAssured())
            .baseUri(host)
            .basePath(resourcePath)
            .body(requestBody)
            .contentType(ContentType.JSON)
            .and()
            .when()
            .put()
            .then()
            .contentType(ContentType.JSON)
            .and()
            .extract()
            .response();

    Logger.logResponse(rawRes);
    return rawRes;
  }

  @Step("Delete Customer details using DELETE request")
  public static Response deleteCustomerDetails(String customerId) {

    final String host = getValueFromEnvironmentFile("host_api");
    final String resourcePath = SampleResource.CUSTOMER_RESOURCE + customerId;

    Logger.logInfo("Base URI : " + host);
    Logger.logInfo("Path : " + resourcePath);

    Response rawRes =
        given()
            .filter(new AllureRestAssured())
            .baseUri(host)
            .basePath(resourcePath)
            .contentType(ContentType.JSON)
            .and()
            .when()
            .delete()
            .then()
            .extract()
            .response();

    Logger.logResponse(rawRes);
    return rawRes;
  }
}
