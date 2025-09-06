package com.brad.at;

import com.brad.at.services.CustomSoftAssert;
import com.brad.at.services.harrypotter.HarryPotterBaseTest;
import com.brad.at.testdata.harrypotter.HarryPotterDataProvider;
import com.brad.at.valueobjects.common.ErrorResponseVO;
import com.brad.at.valueobjects.harrypotter.BookVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class HarryPotterBooksTest extends HarryPotterBaseTest
{
    @Test(enabled = false, dataProvider = "getBooksError", dataProviderClass = HarryPotterDataProvider.class)
    public void error(String inScenario, String inHeaderIndex, String inHeaderMax, String inHeaderPage,
                      String inSearch, int inStatusCode, String inMessage)
    {
        CustomSoftAssert customSoftAssert = getCustomSoftAssert();

        extentLogger.logInfo("The current test scenario: " + inScenario);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("index", inHeaderIndex);
        queryParams.put("max", inHeaderMax);
        queryParams.put("page", inHeaderPage);
        queryParams.put("search", inSearch);

        Response response = get(headers.getHeaders(), getBooksURL(), queryParams);

        Assert.assertEquals(response.statusCode(), inStatusCode, "Was the correct status code returned?");

        ErrorResponseVO errorResponseVO = response.getBody().as(ErrorResponseVO.class);

        customSoftAssert.assertEquals(
                errorResponseVO.getError(),
                inMessage,
                "Was the correct error message returned?");

        customSoftAssert.assertAll();
    }

    @Test(enabled = false)
    public void success() throws JsonProcessingException
    {
        CustomSoftAssert customSoftAssert = getCustomSoftAssert();

        Response responseGitHub =
                get(
                      headers.getGitHubBooksHeaders(),
                "https://raw.githubusercontent.com/fedeperin/potterapi/refs/heads/main/assets/data/basefiles/books.js");

        Assert.assertEquals(responseGitHub.statusCode(), 200, "Was the correct status code returned?");

        String responseGitHubString = responseGitHub.asString();

        String responseJsonArray = responseGitHubString.substring(responseGitHubString.indexOf('['), responseGitHubString.lastIndexOf(']') + 1);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BookVO> bookGitHubVO = objectMapper.readValue(responseJsonArray, new TypeReference<List<BookVO>>() {});

        customSoftAssert.assertEquals(bookGitHubVO.size(), 8, "Was the correct number of books returned?");

        Response response = get(headers.getHeaders(), getBooksURL());

        Assert.assertEquals(response.statusCode(), 200, "Was the correct status code returned?");

        //TWO DIFFERENT WAYS TO GET THE BODY AS A LIST OF BOOKS
        //List<BookVO> booksVOAlternate = response.getBody().as(new TypeRef<List<BookVO>>() {});
        List<BookVO> booksVO = Arrays.asList(response.as(BookVO[].class));

        booksVO.forEach(bookVO -> bookVO.setIndex(null));

        customSoftAssert.assertEquals(booksVO, bookGitHubVO, "Are the objects equal?");

        customSoftAssert.assertAll();
    }

    @Test(enabled = true)
    public void successQueryParamTest()
    {
        CustomSoftAssert customSoftAssert = getCustomSoftAssert();

        

        customSoftAssert.assertAll();
    }
}
