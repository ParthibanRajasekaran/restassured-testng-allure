package com.template.project.common;

import static com.template.project.common.Constants.MAX_RETRY_COUNT;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.util.RetryAnalyzerCount;

/**
 * Created by Parthiban.
 */
@Slf4j
public class Retry extends RetryAnalyzerCount {

  public Retry() {
    setCount(MAX_RETRY_COUNT);
  }

  @Override
  public boolean retryMethod(@NonNull ITestResult result) {
    final String methodName = result.getMethod().getMethodName();
    log.warn("RETRY failed test '{}', {} more time)", methodName, MAX_RETRY_COUNT);
    return null != methodName;
  }
}
