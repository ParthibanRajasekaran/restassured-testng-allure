package com.template.project.common;

import static com.template.project.common.Logger.logInfo;

import io.qameta.allure.Step;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.naming.ConfigurationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyFileReaderUtils {

  private static Properties prop;
  private static String propValue;

  @Step("Get test data from properties file")
  private static String getConfigTestDataFile() throws ConfigurationException {
    String environment = System.getProperty("envConfig");
    if (environment == null || environment.equals("")) {
      environment = "default";
    }
    log.info("Tests are run on " + environment + " testData");
    return Constants.getDataPath()
        + File.separatorChar
        + "test_data_"
        + environment
        + ".properties";
  }

  @Step("Retrieve test data from Test data file")
  public static String getValueFromTestDataFile(String propName) {
    prop = new Properties();
    FileInputStream fis = null;
    try {
      try {
        fis =
            new FileInputStream(
                URLDecoder.decode(getConfigTestDataFile(), String.valueOf(StandardCharsets.UTF_8)));
      } catch (UnsupportedEncodingException e) {
        log.error("UTF-8 encoding not supported", e);
        e.printStackTrace();
      } catch (ConfigurationException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      log.error("Cannot find properties file", e);
      e.printStackTrace();
    }
    try {
      prop.load(fis);
    } catch (IOException e) {
      log.error("Cannot load properties file", e);
      e.printStackTrace();
    }
    propValue = prop.getProperty(propName);
    logInfo("Param - " + propName + " is : " + propValue);
    return propValue;
  }
}
