package com.template.project.common;

import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestResult;

@Slf4j
public class RestAssuredListener extends AllureTestNg {

  @Override
  public void onStart(final ITestContext arg0) {
    log.info("Test suite: {}", arg0.getName());
  }

  @Override
  public void onTestStart(final ITestResult arg0) {
    log.info("Starting test {}", arg0.getMethod());
  }

  @Override
  public void onTestSuccess(final ITestResult tr) {
    log.info("{} --- SUCCESS ---\n", tr.getName());
  }

  @Step("Step onFailure")
  @Override
  public void onTestFailure(final ITestResult tr) {
    log.error("{} --- FAILED --- ", tr.getName());
    final Throwable ex = tr.getThrowable();
    if (ex != null) {
      final String cause = ex.toString();
      log.error("{}\n", cause);
    }
  }

  @Override
  public void onTestSkipped(ITestResult tr) {
    log.info("{} --- SKIPPED ---\n", tr.getName());
    final Throwable ex = tr.getThrowable();
    if (ex != null) {
      final String cause = ex.toString();
      log.error("{}\n", cause);
    }
  }
}
