package com.template.project.common;

import org.testng.annotations.DataProvider;

public class SampleDataProvider {

  @DataProvider(name = "testData")
  public Object[][] testData() {
    return new Object[][]{{"A", "A"}, {"1", "1"}};
  }
}
