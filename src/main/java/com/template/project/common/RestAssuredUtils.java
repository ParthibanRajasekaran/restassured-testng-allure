package com.template.project.common;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertNotNull;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

@Slf4j
public class RestAssuredUtils {

  /**
   * Class is not to be instantiated. private constructor to hide the implicit public one.
   */
  private RestAssuredUtils() {
  }

  /**
   * This method is to convert the content in Response format to JsonPath.
   *
   * @param rawRes Any instance of {@code Response} which will be converted to json.
   * @return null if {@code rawRes} is null.
   */
  public static JsonPath convertRawToJson(final Response rawRes) {
    if (null == rawRes) {
      log.error("Unable to convert a null response to JSON");
      return null;
    }
    final String responseString = rawRes.asString();
    return new JsonPath(responseString);
  }

  /**
   * This method is to build the Payload in JSON.
   *
   * @param obj    A not null Object which is to be converted to json
   * @param pretty true causes formatting, if false there will be no attempt to format.
   * @return never null
   * @throws JsonProcessingException Most error cases
   */
  public static String serializeToJson(final Object obj, final boolean pretty)
      throws JsonProcessingException {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.setSerializationInclusion(Include.NON_EMPTY);
    if (pretty) {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
    return mapper.writeValueAsString(obj);
  }

  /**
   * Split {@code name} around whitespace.
   *
   * @param name A not null text, potentially containing spaces
   * @return The components of {@code name} split around spaces.
   */
  public static String[] splitName(final String name) {
    if (null == name || name.trim().isEmpty()) {
      return new String[0];
    }
    return name.trim().split(" ");
  }

  /**
   * This method is to get the File placed within the resources.
   *
   * @param filename The name of a file which will be looked up on the classpath
   * @return The data read from a file found with the name {@code filename}.
   */
  public static byte[] findFile(final String filename) {
    byte[] bytes = null;
    try {
      URL in = RestAssuredUtils.class.getResource(filename);
      if (in == null) {
        log.debug("{} not found using class classloader", filename);
        in = Thread.currentThread().getContextClassLoader().getResource(filename);
      }
      assertNotNull("Test is not configured properly, missing file \"" + filename + "\"", in);
      try (InputStream iStream = in.openStream()) {
        bytes = IOUtils.toByteArray(iStream);
      }
    } catch (IOException e) {
      fail("Unable to find " + filename, e);
    }
    return bytes;
  }

  /**
   * This method is to convert the Image to Base64 format.
   *
   * @param file The data read from an image file which will be base64 encoded. Must not be null.
   * @return The encoded data.
   */
  public static String encodeImageToBase64(final byte[] file) {
    return Base64.getEncoder().encodeToString(file);
  }

  /**
   * Verify the http status code of the response received.
   *
   * @param rawRes             A response which should have its response code checked
   * @param expectedStatusCode THe expected response, for example 200
   */
  public static void verifyStatusCode(Response rawRes, int expectedStatusCode) {
    final int statusCode = rawRes.statusCode();
    log.debug("Status code is {}", statusCode);
    assertEquals(
        statusCode, expectedStatusCode, String.format("Invalid status code: %d", statusCode));
  }

  /**
   * Generate bearer token from tokenUri, client id and client secret For oauth2.
   *
   * @param tokenUri     The (valid) url from which the token should be obtained, the Endpoint.
   * @param clientId     The username (to be used with http basic authentication)
   * @param clientSecret THe password corresponding to {@code clientId}
   * @param scope        See {@link https://oauth.net/2/scope/}
   */
  public static String getAuthTokenWithClientIdAndSecret(
      String tokenUri, String clientId, String clientSecret, String scope) {
    Logger.logInfo(String.format("Getting OAuth Token from server %s", tokenUri));
    Response response =
        given()
            .auth()
            .preemptive()
            .basic(clientId, clientSecret)
            .formParam("grant_type", "client_credentials")
            .formParam("scope", scope)
            .when()
            .post(tokenUri);

    final JSONObject jsonObject = new JSONObject(response.getBody().asString());
    final String accessToken = jsonObject.get("access_token").toString();
    final String tokenType = jsonObject.get("token_type").toString();
    Logger.logInfo(String.format("Oauth Token with type %s is %s", tokenType, accessToken));
    return accessToken;
  }

  /**
   * This method is to generate bearer token from tokenUri, client id, client secret and resource
   * owner credentials For oauth2.
   *
   * @param tokenUri     The (valid) url from which the token should be obtained, the Endpoint.
   * @param clientId     The username (to be used with http basic authentication)
   * @param clientSecret THe password corresponding to {@code clientId}
   * @param username     Used for authentcation
   * @param password     Used for authentcation
   * @param scope        See {@link https://oauth.net/2/scope/}
   */
  public static String getAuthTokenWithResourceOwnerLogin(
      final String tokenUri,
      final String clientId,
      final String clientSecret,
      final String username,
      final String password,
      final String scope) {
    Logger.logInfo(String.format("Getting OAuth Token from server %s", tokenUri));
    final Response response =
        given()
            .auth()
            .preemptive()
            .basic(clientId, clientSecret)
            .formParam("grant_type", "password")
            .formParam("username", username)
            .formParam("password", password)
            .formParam("scope", scope)
            .when()
            .post(tokenUri);

    final JSONObject jsonObject = new JSONObject(response.getBody().asString());
    final String accessToken = jsonObject.get("access_token").toString();
    final String tokenType = jsonObject.get("token_type").toString();
    Logger.logInfo(
        String.format("Oauth Token for %s with type %s is %s", username, tokenType, accessToken));
    return accessToken;
  }
}
