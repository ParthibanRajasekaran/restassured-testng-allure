package com.template.project.api.tests.featureflow;

import static com.template.project.common.Logger.logInfo;
import static com.template.project.common.RestAssuredUtils.verifyStatusCode;

import com.template.project.api.request.SampleRequests;
import com.template.project.api.utils.CustomFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Shop")
public class SampleTest {

  @Test
  @Description("Verify Customer creation")
  public void testCustomerCreation() throws IOException {
    Response rawRes = SampleRequests.createCustomer("customerFirstName", "customerLastName");
    verifyStatusCode(rawRes, 201);
    CustomFunctions.verifyCustomerIdNotNull(rawRes);
    Assert.assertEquals(CustomFunctions.getCustomerFirstname(rawRes), "customerFirstName",
        "Invalid First name");
    Assert.assertEquals(CustomFunctions.getCustomerLastname(rawRes), "customerLastName",
        "Invalid Last name");
  }

  @Test
  @Description("Verify Customer creation and retrieve created Customer details")
  public void testCustomerDetailsRetrieval() throws IOException {
    Response rawRes = SampleRequests.createCustomer("customerFirstName", "customerLastName");
    verifyStatusCode(rawRes, 201);
    String customerId = CustomFunctions.getCustomerId(rawRes);
    CustomFunctions.verifyCustomerIdNotNull(rawRes);

    rawRes = SampleRequests.getCustomerDetails(customerId);
    verifyStatusCode(rawRes, 200);

    logInfo("Customer details are retrieved successfully");
  }

  @Test
  @Description("Verify Customer creation and update Customer details")
  public void testUpdateCustomerDetails() throws IOException {
    Response rawRes = SampleRequests.createCustomer("customerFirstName", "customerLastName");
    verifyStatusCode(rawRes, 201);
    String customerId = CustomFunctions.getCustomerId(rawRes);
    CustomFunctions.verifyCustomerIdNotNull(rawRes);

    rawRes = SampleRequests.getCustomerDetails(customerId);
    verifyStatusCode(rawRes, 200);

    rawRes =
        SampleRequests.updateCustomerDetails(
            customerId, "updatedCustomerFirstName", "updatedCustomerLastName");
    verifyStatusCode(rawRes, 200);

    rawRes = SampleRequests.getCustomerDetails(customerId);
    verifyStatusCode(rawRes, 200);
    Assert.assertEquals(CustomFunctions.getCustomerFirstname(rawRes), "updatedCustomerFirstName",
        "Invalid First name");
    Assert.assertEquals(CustomFunctions.getCustomerLastname(rawRes), "updatedCustomerLastName",
        "Invalid Last name");

    logInfo("Customer details are retrieved successfully");
  }

  @Test
  @Description("Verify Customer deletion")
  public void testCustomerDeletion() throws IOException {
    Response rawRes = SampleRequests.createCustomer("customerFirstName", "customerLastName");
    verifyStatusCode(rawRes, 201);
    String customerId = CustomFunctions.getCustomerId(rawRes);
    CustomFunctions.verifyCustomerIdNotNull(rawRes);

    rawRes = SampleRequests.deleteCustomerDetails(customerId);
    verifyStatusCode(rawRes, 200);

    rawRes = SampleRequests.getCustomerDetails(customerId);
    verifyStatusCode(rawRes, 404);

    logInfo("Customer details deleted successfully");
  }
}
