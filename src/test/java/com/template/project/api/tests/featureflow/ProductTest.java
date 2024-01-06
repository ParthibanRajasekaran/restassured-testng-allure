package com.template.project.api.tests.featureflow;

import com.template.project.api.request.SampleRequests;
import com.template.project.api.utils.CustomFunctions;
import com.template.project.product.request.ProductRequest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.template.project.common.RestAssuredUtils.verifyStatusCode;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@Feature("Product")
public class ProductTest {

    @Test
    @Description("Verify Product creation")
    public void testProductCreation() throws IOException {
        Response rawRes = ProductRequest.createProduct("Apple-Orange", 5.49);
        verifyStatusCode(rawRes, 201);

        rawRes.then()
                .body("id", notNullValue())
                .body("name", equalTo("Apple-Orange"));

        Double actualPrice = rawRes.jsonPath().getDouble("price");
        Assert.assertEquals(actualPrice, Double.valueOf(5.49));
    }
}
