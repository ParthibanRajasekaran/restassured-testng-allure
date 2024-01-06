package com.template.project.product.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.template.project.api.model.SampleModel;
import com.template.project.common.RestAssuredUtils;
import com.template.project.product.model.Product;

public class ProductBuilder {

    public static String getJsonPayload(Product product) throws JsonProcessingException {
        final String jsonPayload = RestAssuredUtils.serializeToJson(product, true);
        System.out.println(jsonPayload);
        return jsonPayload;
    }


    public static Product addProduct(String productName, double productPrice) {
        Product product = new Product();
        product.setName(productName);
        product.setPrice(productPrice);
        return product;
    }
}

