package com.brad.at.services.harrypotter;

import com.brad.at.services.BaseTest;
import com.brad.at.util.harrypotter.HarryPotterConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class HarryPotterBaseTest extends BaseTest
{
    @Autowired
    protected HarryPotterHeaders headers;

    protected String getBooksURL()
    {
        return baseURL + HarryPotterConstants.Paths.BOOKS;
    }
}
