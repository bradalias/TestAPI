package com.brad.at;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GasPricesTest
{
    @Test(alwaysRun = true)
    public void firstTest()
    {
        SoftAssert softAssert = new SoftAssert();



        softAssert.assertAll();
    }
}
