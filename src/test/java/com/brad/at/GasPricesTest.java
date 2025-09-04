package com.brad.at;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class GasPricesTest
{
    @Test(alwaysRun = true)
    public void firstTest()
    {
        SoftAssert softAssert = new SoftAssert();

        String coloradoURL = "https://api.collectapi.com/gasPrice/stateUsaPrice?state=CO";
        String washingtonURL = "https://api.collectapi.com/gasPrice/stateUsaPrice?state=WA";

        List<Header> headerList = new ArrayList<>();
        headerList.add(new Header("content-type", "application/json"));
        headerList.add(new Header("authorization", "apikey 7mLlwwRsILn613tegzu5fz:3CHp2SZW7ycWRTcs6TT7ee"));
        //headerList.add(new Header("Accept", "application/json"));
        Headers headers = new Headers(headerList);



        //RestAssured restAssured = new RestAssured();
        //RestAssured.given().headers(headers).when().get(coloradoURL).then().assertThat().body("", equalTo(""));
        Response response =
                RestAssured.given().log().all().headers(headers).when().get(coloradoURL).then().log().body().extract().response();

        softAssert.assertAll();
    }
}
