package com.pynt.rest;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class AppTest {

    static String jamesToken;
    static String larsToken;
    static String jamesUid;
    static String larsUid;

    @Test(priority = 1)
    public void step1_testJamesCanLogin() {
        baseURI = "http://44.202.3.35";
        port = 6000;

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

    @Test(priority = 2, dependsOnMethods = {"step1_testJamesCanLogin"})
    public void step2_testGetJamesAccount() {
        baseURI = "http://44.202.3.35";
        port = 6000;
        System.out.println("Will use james token " + "Bearer " + jamesToken);
        jamesUid = given()
                .header("Authorization", "Bearer " + jamesToken)
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

    @Test(priority = 3, dependsOnMethods = {"step2_testGetJamesAccount"})
    public void step3_testGetJamesTransactions() {
        baseURI = "http://44.202.3.35";
        port = 6000;

        String resp = given()
                .header("Authorization", "Bearer " + jamesToken)
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

    @Test(priority = 4, dependsOnMethods = {"step3_testGetJamesTransactions"})
    public void step4_testGetMoreOfJamesTransactions() {
        baseURI = "http://44.202.3.35";
        port = 6000;

        String resp = given()
                .header("Authorization", "Bearer " + jamesToken)
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

    @Test(priority = 5, dependsOnMethods = {"step4_testGetMoreOfJamesTransactions"})
    public void step5_testLarsCanLogin() {
        baseURI = "http://44.202.3.35";
        port = 6000;

        try {
            JSONObject body = new JSONObject()
                    .put("userName", "Lars")
                    .put("password", "ILoveDrums");

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 6, dependsOnMethods = {"step5_testLarsCanLogin"})
    public void testGetLarsAccount() {
        baseURI = "http://44.202.3.35";
        port = 6000;

        larsUid = given()
                .header("Authorization", "Bearer " + larsToken)
                .when()
                .get("/account")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .getBody()
                .jsonPath()
                .getString("userId");

        System.out.println(larsUid);
    }

    @Test(priority = 7, dependsOnMethods = {"testGetLarsAccount"})
    public void testGetLarsTransactions() {
        baseURI = "http://44.202.3.35";
        port = 6000;

        String resp = given()
                .header("Authorization", "Bearer " + larsToken)
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


