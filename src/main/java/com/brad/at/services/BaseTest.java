package com.brad.at.services;

//import com.brad.at.services.harrypotter.HarryPotterHeaders;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

//@Configuration
//@ComponentScan
public class BaseTest //extends AbstractTestNGSpringContextTests
{
    //@Autowired
    //protected HarryPotterHeaders harryPotterHeaders;

    protected String baseURL = "https://potterapi-fedeperin.vercel.app/en";

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
