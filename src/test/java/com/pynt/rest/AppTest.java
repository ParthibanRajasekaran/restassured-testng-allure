package com.pynt.rest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.*;
import static org.testng.AssertJUnit.fail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {
    private static final String BASE_URI = "http://44.202.3.35";
    private static final int PORT = 6000;
    private static String jamesToken;
    private static String larsToken;
    private static String jamesUid;
    private static String larsUid;

    @BeforeClass
    public static void setup() {
        baseURI = BASE_URI;
        port = PORT;
    }

    @Test
    public void step1_testJamesCanLogin() {
        try {
            JSONObject body = new JSONObject()
                    .put("userName", "James")
                    .put("password", "ILoveGuitars");

            jamesToken = given()
                    .contentType("application/json")
                    .body(body.toString())
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response()
                    .getBody()
                    .jsonPath()
                    .getString("token");

            System.out.println(jamesToken);
        } catch (JSONException e) {
            System.out.println(e);
        }
    }

    @Test
    public void step2_testGetJamesAccount() {
        System.out.println("Will use James token " + jamesToken);

        jamesUid = given()
                .header("Authorization", jamesToken)
                .when()
                .get("/account")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .getBody()
                .jsonPath()
                .getString("userId");

        System.out.println(jamesUid);
    }

    @Test
    public void step3_testGetJamesTransactions() {
        String resp = given()
                .header("Authorization", jamesToken)
                .queryParam("userId", jamesUid)
                .queryParam("limit", 5)
                .when()
                .get("/transactions")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .getBody()
                .asString();

        System.out.println(resp);
    }

    @Test
    public void step4_testGetMoreOfJamesTransactions() {
        String resp = given()
                .header("Authorization", jamesToken)
                .queryParam("userId", jamesUid)
                .queryParam("limit", 10)
                .when()
                .get("/transactions")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .getBody()
                .asString();

        System.out.println(resp);
    }

    @Test
    public void step5_testLarsCanLogin() {
        JSONObject body = new JSONObject()
                .put("userName", "Lars")
                .put("password", "ILoveDrums");

        try {
            larsToken = given()
                    .contentType("application/json")
                    .body(body.toString())
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response()
                    .getBody()
                    .jsonPath()
                    .getString("token");

            System.out.println(larsToken);
        } catch (Exception e) {
            fail("Failed to log in Lars: " + e.getMessage());
        }
    }


    @Test
    public void step6_testGetLarsAccount() {
        larsUid = given()
                .header("Authorization", larsToken)
                .when()
                .get("/account")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("userId");

        System.out.println(larsUid);
    }

    @Test
    public void step7_testGetLarsTransactions() {
        String resp = given()
                .header("Authorization", larsToken)
                .queryParam("userId", larsUid)
                .queryParam("limit", 5)
                .when()
                .get("/transactions")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .getBody()
                .asString();

        System.out.println(resp);
    }
}