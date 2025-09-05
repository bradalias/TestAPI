package com.brad.at.testdata.harrypotter;


import org.testng.annotations.DataProvider;

public class HarryPotterDataProvider
{
    private static final String ERROR_MESSAGE = "Invalid Index";

    @DataProvider
    public Object[][] getBooksError()
    {
        //Scenario
        //Header index
        //Header max
        //Header page
        //Header search
        //Status Code
        //Error Message
        return new Object[][]
        {
                //{"1.1 Index header null", null, 8, 8, "Harry", 200}, //ALLOWED
                {"1.1 Index header lower boundary", "-1", "8", "8", "Harry", 404, ERROR_MESSAGE},
                {"1.2 Index header upper boundary", "9", "8", "8", "Harry", 404, ERROR_MESSAGE},
                {"1.3 Max header lower boundary", null, "-1", null, "Harry", 404, "Parameter \"max\" should be greater than 0"},
                {"1.4 Page header lower boundary", null, "1", "-1", "Harry", 404, "Invalid Params"},
                {"1.5 Page header upper boundary", null, "2", "5", "Harry", 404, "Invalid Params"}
                //{"1.6 Search header returns empty list", null, null, null, "Blah", 200}
        };
    }
}
