package com.brad.at.services;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class BaseTest
{
    protected String baseURL = "https://potterapi-fedeperin.vercel.app";

    protected Response get(Headers inHeaders, String inURL)
    {
        return RestAssured
                .given()
                    .log().all()
                    .headers(inHeaders)
                .when()
                    .get(inURL)
                .then()
                    .log().body()
                    .extract().response();
    }

    protected Response put(Headers inHeaders, String inURL, Object inBody) {
        return RestAssured
                .given()
                    .log().all()
                    .headers(inHeaders)
                    .body(inBody)
                .when()
                    .put(inURL)
                .then()
                    .log().body()
                    .extract().response();
    }

    protected Response post(Headers inHeaders, String inURL, Object inBody)
    {
        return RestAssured
                .given()
                    .log().all()
                    .headers(inHeaders)
                    .body(inBody)
                .when()
                    .post(inURL)
                .then()
                    .log().body()
                    .extract().response();
    }
}
