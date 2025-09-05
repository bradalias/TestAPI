package com.brad.at;

import com.brad.at.services.CustomSoftAssert;
import com.brad.at.services.harrypotter.HarryPotterBaseTest;
import com.brad.at.services.harrypotter.HarryPotterHeaders;
import com.brad.at.testdata.harrypotter.HarryPotterDataProvider;
import com.brad.at.valueobjects.common.ErrorResponseVO;
import com.brad.at.valueobjects.harrypotter.BookVO;
import com.brad.at.valueobjects.harrypotterbooksgithub.BookGitHubVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class HarryPotterBooksTest extends HarryPotterBaseTest
{
    @Test(dataProvider = "getBooksError", dataProviderClass = HarryPotterDataProvider.class)
    public void error(String inScenario, String inHeaderIndex, String inHeaderMax, String inHeaderPage,
                      String inSearch, int inStatusCode, String inMessage) throws JsonProcessingException {
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

    @Test
    public void success()
    {
        CustomSoftAssert customSoftAssert = getCustomSoftAssert();

        String responseGitHub =
                getStringResponse(
                        headers.getGitHubBooksHeaders(),
                        "https://raw.githubusercontent.com/fedeperin/potterapi/refs/heads/main/assets/data/en/books.en.js");

        String responseJsonArray = responseGitHub.substring(responseGitHub.indexOf('['), responseGitHub.lastIndexOf(']') + 1);
        responseJsonArray = responseJsonArray.replaceAll(",\\s*}", "}");
        ObjectMapper objectMapper = new ObjectMapper();
        List<BookGitHubVO> bookGitHubVO = objectMapper.readValue(responseJsonArray, new TypeReference<List<BookGitHubVO>>() {});

        //customSoftAssert.assertEquals(bookGitHubVO.size(), 8, "Was the correct number of books returned?");

        Response response = get(headers.getHeaders(), getBooksURL());

        Assert.assertEquals(response.statusCode(), 200, "Was the correct status code returned?");

        //TWO DIFFERENT WAYS TO GET THE BODY AS A LIST OF BOOKS
        //List<BookVO> booksVOAlternate = response.getBody().as(new TypeRef<List<BookVO>>() {});
        List<BookVO> booksVO = Arrays.asList(response.as(BookVO[].class));

        BookVO bookVO =
                booksVO.stream()
                        .filter(x -> Objects.equals(x.getTitle(), "Harry Potter and the Goblet of Fire"))
                        .findFirst().orElse(new BookVO());

        customSoftAssert.assertAll();
    }
}
