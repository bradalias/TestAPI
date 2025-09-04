package com.brad.at.services.harrypotter;

import io.restassured.http.Header;
import io.restassured.http.Headers;
//import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class HarryPotterHeaders
{
    public Headers getHeaders()
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("accept", "application/json"));
        //headers.add(new Header("content-type", "application/json"));
        return new Headers(headers);
    }
}
