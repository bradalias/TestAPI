package com.brad.at;

import com.brad.at.services.CustomSoftAssert;
import com.brad.at.services.harrypotter.HarryPotterBaseTest;
import com.brad.at.services.harrypotter.HarryPotterHeaders;
import com.brad.at.valueobjects.harrypotter.BookVO;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class HarryPotterBooksTest extends HarryPotterBaseTest
{
    @Test
    public void success()
    {
        CustomSoftAssert customSoftAssert = getCustomSoftAssert();

        HarryPotterHeaders harryPotterHeaders = new HarryPotterHeaders();
        Response response = get(harryPotterHeaders.getHeaders(), getBooksURL());

        List<BookVO> booksVO = Arrays.asList(response.as(BookVO[].class));

        customSoftAssert.assertEquals(
                response.getStatusCode(),
                200,
                "Was the correct status code of 200 returned?");

        //booksVO.stream().filter(x-> x.getIndex())

        customSoftAssert.assertAll();
    }
}
