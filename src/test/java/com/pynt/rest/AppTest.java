package com.pynt.rest;

import org.testng.annotations.Test;
import org.json.JSONObject;
import org.json.JSONException;

import static io.restassured.RestAssured.* ;

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

    @Test(priority = 2)
    public void step2_testGetJamesAccount() {
        baseURI = "http://44.202.3.35";
        port = 6000;
        System.out.println("Will use james token " + jamesToken);
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

    @Test(priority = 3)
    public void step3_testGetJamesTransactions() {
        baseURI = "http://44.202.3.35";
        port = 6000;

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

    @Test(priority = 4)
    public void step4_testGetMoreOfJamesTransactions() {
        baseURI = "http://44.202.3.35";
        port = 6000;

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

    @Test(priority = 5)
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

    @Test(priority = 6)
    public void testGetLarsAccount() {
        baseURI = "http://44.202.3.35";
        port = 6000;

        larsUid = given()
                .header("Authorization", larsToken)
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

    @Test(priority = 7)
    public void testGetLarsTransactions() {
        baseURI = "http://44.202.3.35";
        port = 6000;

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


