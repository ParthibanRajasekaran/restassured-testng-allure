package com.template.project.common;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.naming.ConfigurationException;
import lombok.SneakyThrows;

public class Constants {

  public static final int MAX_RETRY_COUNT = 1;

  public static String CONFIG_FILE_DIRECTORY;

  public static String DATA_FILE_DIRECTORY;

  static {
    try {
      CONFIG_FILE_DIRECTORY = findResource("config").getPath();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
    try {
      DATA_FILE_DIRECTORY = findResource("data").getPath();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
  }

  protected static URL findResource(final String name) throws ConfigurationException {
    if (null == name || "".equals(name)) {
      throw new ConfigurationException("Must provide a valid folder name");
    }
    final URL url = Constants.class.getClassLoader().getResource(name);
    if (null == url) {
      throw new ConfigurationException(name + " could not be found");
    }
    return url;
  }

  /**
   * The configured path to configuration files.
   *
   * @return The location of the config folder.
   */
  @SneakyThrows
  public static String getConfigPath() {
    return URLDecoder.decode(CONFIG_FILE_DIRECTORY, String.valueOf(StandardCharsets.UTF_8));
  }

  /**
   * The configured path to data files.
   *
   * @return The location of the data folder.
   */
  @SneakyThrows
  public static String getDataPath() {
    return URLDecoder.decode(DATA_FILE_DIRECTORY, String.valueOf(StandardCharsets.UTF_8));
  }

}
