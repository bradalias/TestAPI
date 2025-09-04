package com.brad.at.util.testreport;

import org.springframework.stereotype.Component;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

@Component
public class ExtentLogger
{
    private static final ThreadLocal<ExtentTest> TEST_THREAD_LOCAL = new ThreadLocal<>();

    public void setExtentTest(ExtentTest test) {
        TEST_THREAD_LOCAL.set(test);
    }

    public ExtentTest getExtentTest() {
        return TEST_THREAD_LOCAL.get();
    }

    public void logInfo(String message) {
        ExtentTest test = getExtentTest();
        test.info(message);
    }

    public void logRequestInfo(String message) {
        ExtentTest test = getExtentTest();
        Markup m = MarkupHelper.createCodeBlock(message);
        test.info(m);
    }

    public void logPass(String message) {
        ExtentTest test = getExtentTest();
        test.log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }

    public void logFail(String message) {
        ExtentTest test = getExtentTest();
        test.log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
    }
}
