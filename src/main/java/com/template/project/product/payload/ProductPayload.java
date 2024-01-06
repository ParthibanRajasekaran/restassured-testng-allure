package com.template.project.product.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.template.project.api.builder.SampleBuilder;
import com.template.project.api.model.SampleModel;
import com.template.project.product.builder.ProductBuilder;
import com.template.project.product.model.Product;

import static com.template.project.api.builder.SampleBuilder.addCustomer;

public class ProductPayload {

    public static String buildProductCreationPayload(String productName, double productPrice)
            throws JsonProcessingException {
        Product product = ProductBuilder.addProduct(productName, productPrice);
        return ProductBuilder.getJsonPayload(product);
    }
}
