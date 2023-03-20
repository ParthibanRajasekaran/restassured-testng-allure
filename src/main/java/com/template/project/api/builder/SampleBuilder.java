package com.template.project.api.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.template.project.api.model.SampleModel;
import com.template.project.common.RestAssuredUtils;

public class SampleBuilder {

  public static String getJsonPayload(SampleModel sampleModel) throws JsonProcessingException {
    final String jsonPayload = RestAssuredUtils.serializeToJson(sampleModel, true);
    System.out.println(jsonPayload);

    return jsonPayload;
  }

  public static SampleModel addCustomer(String firstname, String lastname) {
    SampleModel sampleModel = new SampleModel();
    sampleModel.setFirstname(firstname);
    sampleModel.setLastname(lastname);
    return sampleModel;
  }

  public static SampleModel updateCustomerDetails(String firstname, String lastname) {
    SampleModel sampleModel = new SampleModel();
    sampleModel.setFirstname(firstname);
    sampleModel.setLastname(lastname);
    return sampleModel;
  }
}
