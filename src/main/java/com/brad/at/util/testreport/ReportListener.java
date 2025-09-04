package com.brad.at.util.testreport;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ReportListener extends AbstractTestExecutionListener
{
    private ExtentReports reports;
    private ExtentLogger extentLogger;

    private static final ThreadLocal<ExtentTest> TEST_THREAD_LOCAL = new ThreadLocal<>();

    //Get ExtentReports and ExtentLogger from spring Application Context
    public void init(TestContext testContext)
    {
        reports = testContext.getApplicationContext().getBean(ExtentReports.class);
        extentLogger = testContext.getApplicationContext().getBean(ExtentLogger.class);
    }

    // The below methods handle the appropriate instance of ExtentReports in relation to the test
    // lifecycle. A test name is assigned and a ThreadLocal version is set.ThreadLocal is used
    // to support parallel running of tests
    @Override
    public void beforeTestClass(TestContext testContext)
    {
        init(testContext);

        ExtentTest test = null;
        test = reports.createTest(testContext.getTestClass().getSimpleName() + "BeforeClass");
        test.assignCategory(testContext.getTestClass().getName());
        setExtentTest(test);
    }

    @Override
    public void beforeTestMethod(TestContext testContext)
    {
        ExtentTest test = null;

        test = TEST_THREAD_LOCAL.get();
        List<ITestResult> currentTestResult = getTestResults();
        ITestResult tr = null;
        if(currentTestResult.size() > 0) {
            tr = currentTestResult.get(0);
            if(tr.getMethod().getConstructorOrMethod().getMethod().getAnnotation(BeforeClass.class) !=null) {
                if(tr.getStatus() == 2) {
                    test.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
                    test.log(Status.FAIL,tr.getThrowable());
                }
            }
        }else if(!test.getModel().hasLog()) {
            test.getExtent().removeTest(test);
        }

        test= reports.createTest(testContext.getTestMethod().getName());
        test.assignCategory(testContext.getTestClass().getName());
        setExtentTest(test);
        reports.flush();
    }

    @Override
    public void beforeTestExecution(TestContext testContext)
    {

    }

    @Override
    public void afterTestMethod(TestContext testContext) throws JSONException
    {
        List<ITestResult> currentTestResult = getTestResults();
        for(ITestResult result : currentTestResult) {
            reportTestResult(result, testContext.getTestMethod());
        }
        reports.flush();

        ExtentTest test = null;
        test = reports.createTest(testContext.getTestClass().getSimpleName() + "AfterClass");
        test.assignCategory(testContext.getTestClass().getName());
        setExtentTest(test);
    }

    @Override
    public void afterTestClass(TestContext testContext)
    {
        ExtentTest test = null;
        test = TEST_THREAD_LOCAL.get();
        List<ITestResult> currentTestResult = getTestResults();
        ITestResult tr = null;
        if(currentTestResult.size() > 0) {
            tr = currentTestResult.get(0);
            if(tr.getMethod().getConstructorOrMethod().getMethod().getAnnotation(AfterClass.class) !=null) {
                if(tr.getStatus() == 2) {
                    test.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
                    test.log(Status.FAIL,tr.getThrowable());
                }
            }
        }else if(!test.getModel().hasLog()) {
            test.getExtent().removeTest(test);
        }
        reports.flush();
    }

    @Override
    public void afterTestExecution(TestContext testContext)
    {
        reports.flush();
    }

    // Test Results are retrieved  as a ITestContext using the TestNg Reporter.
    // the context is then cleared in order to prevent duplicate results
    private List<ITestResult> getTestResults()
    {
        ITestContext context = Reporter.getCurrentTestResult().getTestContext();
        List<ITestResult> allResults = new ArrayList<>();
        allResults.addAll(context.getPassedTests().getAllResults());
        allResults.addAll(context.getFailedTests().getAllResults());
        allResults.addAll(context.getSkippedTests().getAllResults());
        allResults.addAll(context.getFailedConfigurations().getAllResults());
        context.getFailedTests().getAllResults().clear();
        context.getPassedTests().getAllResults().clear();
        context.getSkippedTests().getAllResults().clear();
        context.getFailedConfigurations().getAllResults().clear();

        return allResults;
    }

    // Method allows for test result reporting and handling of a dataprovider specific
    // scenario, if applicable. Note that piece will only handle a failure at the @Test level
    // and will report the related stacktrace if a failure does occur. Reporting at the
    // individual assertion level can be implemented by using the CustomSoftAssert class.
    private void reportTestResult(ITestResult tr, Method method)
    {
        ExtentTest test = null;
        test = TEST_THREAD_LOCAL.get();
        boolean beforeMethod=false;
        boolean afterMethod=false;

        if(tr.getMethod().getConstructorOrMethod().getMethod().getAnnotation(BeforeMethod.class) !=null)
            beforeMethod=true;
        if(tr.getMethod().getConstructorOrMethod().getMethod().getAnnotation(AfterMethod.class) !=null)
            afterMethod=true;

        if (tr.getParameters().length > 0) {
            Object[] params = tr.getParameters();
            String name = (String) params[0];
            test.getModel().setName(test.getModel().getName() + " - " + name);
        }
        if (tr.getAttribute("retry") != null && tr.getAttribute("retry").toString().equals("true")) {
            reports.removeTest(test);
        }else {
            switch (tr.getStatus()) {
                case 1:
                    test.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
                    break;
                case 2:
                    if(beforeMethod || afterMethod) {
                        test.log(Status.INFO, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
                        test.log(Status.INFO, tr.getThrowable());
                    }else if(tr.getMethod().getMethodName().equals(method.getName())) {
                        test.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
                        test.log(Status.FAIL,tr.getThrowable());
                    }

                    break;
                case 3:
                    test.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
                    if(tr.getThrowable() != null) {
                        test.log(Status.INFO, tr.getThrowable());

                    }
                    break;
            }
        }
    }

    // Set the THREAD_LOCAL version of ExtentTest
    private void setExtentTest(ExtentTest test)
    {
        TEST_THREAD_LOCAL.set(test);
        extentLogger.setExtentTest(TEST_THREAD_LOCAL.get());
    }
}
