package com.brad.at.util.testreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

@Component
public class ExtentLogFilter implements Filter
{
    @Autowired
    ExtentLogger extentLogger;

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        Response response = ctx.next(requestSpec, responseSpec);
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(requestSpec.getMethod());
        requestBuilder.append("\n\n");
        requestBuilder.append(requestSpec.getURI());
        requestBuilder.append("\n\n");
        if(requestSpec.getHeaders() != null) {
            requestBuilder.append(requestSpec.getHeaders().toString());
        }
        requestBuilder.append("\n\n");
        if(requestSpec.getBody() != null) {
            requestBuilder.append(requestSpec.getBody().toString());
        }

        extentLogger.logRequestInfo(requestBuilder.toString());

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(response.getStatusLine());
        responseBuilder.append("\n\n");
        responseBuilder.append(response.getBody().asString());
        extentLogger.logRequestInfo(responseBuilder.toString());

        return response;
    }
}
