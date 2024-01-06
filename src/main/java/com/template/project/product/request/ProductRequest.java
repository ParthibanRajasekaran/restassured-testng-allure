package com.template.project.product.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.template.project.api.resource.SampleResource;
import com.template.project.common.Logger;
import com.template.project.product.model.Product;
import com.template.project.product.resource.ProductResource;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.template.project.common.ConfigFileReaderUtils.getValueFromEnvironmentFile;
import static com.template.project.product.payload.ProductPayload.buildProductCreationPayload;
import static io.restassured.RestAssured.given;

public class ProductRequest {

    public static Response createProduct(String productName, double productPrice) throws JsonProcessingException {
        final String host = getValueFromEnvironmentFile("host_api");

        final String resourcePath = ProductResource.PRODUCT_RESOURCE;

        final String requestBody = buildProductCreationPayload(productName, productPrice);

        Logger.logInfo("Base URI : " + host);
        Logger.logInfo("Path : " + resourcePath);

        return given()
                .baseUri(host)
                .basePath(resourcePath)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post();
    }
}

