package com.brad.at.services.harrypotter;

import com.brad.at.services.BaseTest;
import com.brad.at.util.harrypotter.HarryPotterConstants;

public class HarryPotterBaseTest extends BaseTest
{
    protected String getBooksURL()
    {
        return baseURL + HarryPotterConstants.Paths.BOOKS;
    }
}
