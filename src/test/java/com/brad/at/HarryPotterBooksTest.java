package com.brad.at;

import com.brad.at.services.CustomSoftAssert;
import com.brad.at.services.harrypotter.HarryPotterBaseTest;
import com.brad.at.services.harrypotter.HarryPotterHeaders;
import com.brad.at.valueobjects.harrypotter.BookVO;
import com.brad.at.valueobjects.harrypotterbooksgithub.BookGitHubVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class HarryPotterBooksTest extends HarryPotterBaseTest
{
    @Test
    public void success() throws JsonProcessingException {
        CustomSoftAssert customSoftAssert = getCustomSoftAssert();

        HarryPotterHeaders harryPotterHeaders = new HarryPotterHeaders();

        String responseGitHub =
            getStringResponse(
                harryPotterHeaders.getGitHubBooksHeaders(),
                "https://raw.githubusercontent.com/fedeperin/potterapi/refs/heads/main/assets/data/en/books.en.js");

        String responseJsonArray = responseGitHub.substring(responseGitHub.indexOf('['), responseGitHub.lastIndexOf(']') + 1);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BookGitHubVO> bookGitHubVO = objectMapper.readValue(responseJsonArray, new TypeReference<List<BookGitHubVO>>() {});

        customSoftAssert.assertEquals(bookGitHubVO.size(), 8, "Was the correct number of books returned?");





        Response response = get(harryPotterHeaders.getHeaders(), getBooksURL());

        //TWO DIFFERENT WAYS TO GET THE BODY AS A LIST OF BOOKS
        List<BookVO> booksVO = Arrays.asList(response.as(BookVO[].class));
        List<BookVO> booksVOAlternate = response.getBody().as(new TypeRef<List<BookVO>>() {});

        customSoftAssert.assertEquals(
                response.getStatusCode(),
                200,
                "Was the correct status code of 200 returned?");

        //booksVO.stream().filter(x-> x.getIndex())

        customSoftAssert.assertAll();
    }
}
