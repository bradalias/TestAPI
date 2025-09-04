package com.brad.at;

import com.brad.at.services.harrypotter.HarryPotterBaseTest;
import com.brad.at.services.harrypotter.HarryPotterHeaders;
import com.brad.at.valueobjects.harrypotter.BookVO;
import com.brad.at.valueobjects.harrypotter.BooksVO;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

public class HarryPotterBooksTest extends HarryPotterBaseTest
{
    @Test
    public void success()
    {
        SoftAssert softAssert = new SoftAssert();

        HarryPotterHeaders harryPotterHeaders = new HarryPotterHeaders();
        Response response = get(harryPotterHeaders.getHeaders(), getBooksURL());

        List<BookVO> booksVO = Arrays.asList(response.as(BookVO[].class));

        

        softAssert.assertAll();
    }
}
