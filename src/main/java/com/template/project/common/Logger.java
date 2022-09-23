package com.template.project.common;

import static org.testng.Reporter.log;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;

/**
 * Adds content to allure reporting.
 */
public class Logger {

  static final org.apache.logging.log4j.Logger log = LogManager.getLogger(Logger.class);

  /**
   * This method generates log message in the console & the allure report.
   *
   * @param message Content of message to be added to allure report
   */
  @Step("INFO: {0}")
  public static void logInfo(String message) {
    if (null == message) {
      return;
    }
    log("INFO: " + message);
    log.info(message);
  }

  /**
   * This method generates warning log message in the console & the allure report.
   *
   * @param message Content of message to be added to allure report
   */
  @Step("WARN: {0}")
  public static void logWarn(String message) {
    if (null == message) {
      return;
    }
    log("WARN: " + message);
    log.warn(message);
  }

  /**
   * This method generates error log message in the console & the allure report.
   *
   * @param message Content of message to be added to allure report
   */
  @Step("ERROR: {0}")
  public static void logError(String message) {
    if (null == message) {
      return;
    }
    log("ERROR: " + message);
    log.error(message);
  }

  /**
   * This method generates a stacktrace log message in the console & the allure report.
   *
   * @param trace Content of message to be added to allure report
   */
  @Step("TRACE: {0}")
  public static void logTrace(StackTraceElement trace) {
    if (null == trace) {
      return;
    }
    log("TRACE:\n" + trace);
    log.trace(trace);
  }

  /**
   * Log payload of an Http Request in the allure report.
   *
   * @param message Content of message to be added to allure report
   */
  @Attachment(value = "request body :")
  public static String logRequest(String message) {
    if (null == message) {
      return "null request";
    }
    return message;
  }

  /**
   * Log response body in the allure report, note that it does not write to the system log.
   *
   * @param rawRes a valid not null restassured response instance
   */
  @Attachment(value = "response body :")
  public static String logResponse(final Response rawRes) {
    if (null == rawRes) {
      log.debug("Response was null");
      return "null Response";
    }
    return convertRawToString(rawRes);
  }

  /**
   * This method is to convert response body in Response format to String.
   *
   * @param rawRes A not null restassured response instance.
   * @return an empty string if {@code rawRes is null}
   */
  public static String convertRawToString(final Response rawRes) {
    if (null == rawRes) {
      log.debug("Attempting to convert null Response to String, will return empty string");
      return "";
    }
    return rawRes.asString();
  }

  /**
   * Print the console message as a attachment to the allure report.
   *
   * @param message Text which is to be added to the allure report.
   * @return The {@code message}, unchanged.
   */
  @Attachment
  public static String saveTextLog(final String message) {
    log.debug("Saving text log to allure");
    return message;
  }

  /**
   * Attach the configured system properties to the allure log.
   *
   * @return Should generally not be used by calling code, this is consumed by allure.
   */
  @Attachment(value = "System Environment", type = "text/plain")
  public byte[] attachSystemProperties() {
    log.debug("Attaching system properties to allure report");
    final Properties props = System.getProperties();
    final StringBuilder result = new StringBuilder();
    for (final String prop : props.stringPropertyNames()) {
      if (prop.toLowerCase().contains("password")) {
        // Dont log things that look like passwords
        result.append(prop).append(" = ").append("********").append("\n");
      } else {
        result.append(prop).append(" = ").append(System.getProperty(prop)).append("\n");
      }
    }
    final String propertyBlock = result.toString();
    log.debug(propertyBlock);
    return propertyBlock.getBytes();
  }
}
