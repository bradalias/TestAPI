package com.brad.at.services;

import com.brad.at.TestConfig;
import com.brad.at.util.testreport.ExtentLogFilter;
import com.brad.at.util.testreport.ExtentLogger;
import com.brad.at.util.testreport.ReportListener;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestExecutionListeners;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = TestConfig.class)
@TestExecutionListeners(value = ReportListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class BaseTest extends AbstractTestNGSpringContextTests
{
    @Autowired
    protected ApplicationContext context;
    @Autowired
    protected ExtentLogFilter extentLogFilter;
    @Autowired
    protected ExtentLogger extentLogger;

    protected String baseURL = "https://potterapi-fedeperin.vercel.app/en";

    @PostConstruct
    public void initializeFilters()
    {
        //ADDED DUE TO LIST DUPLICATING LOGGING CALLS
        if(extentLogFilter != null && !RestAssured.filters().contains(extentLogFilter))
        {
            RestAssured.filters(extentLogFilter);
        }
    }

    public CustomSoftAssert getCustomSoftAssert()
    {
        return context.getBean(CustomSoftAssert.class);
    }

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
