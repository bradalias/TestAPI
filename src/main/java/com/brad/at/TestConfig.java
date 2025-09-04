package com.brad.at;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.brad.at")
@PropertySource(value = "report.properties", ignoreResourceNotFound = true)
@Data
public class TestConfig
{
    @Value("${report.name:#{null}}")
    private String reportName;

    /*
     * This Bean is used for the setup of ExtentReport. Report properties are set
     * and the bean is available for use across the entire test lifecycle
     */
    @Bean
    public ExtentReports extentTestFactory() {
        ExtentReports reports = new ExtentReports();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");

        String reportDateTime = dtf.format(now);

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("C:\\Workspace\\IntelliJ Workspace\\TestAPI\\test-output/extentreport_" + reportDateTime + ".html");
        if(reportName !=null)
            htmlReporter.config().setReportName(reportName);

        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().enableOfflineMode(false);
        //reports.setSystemInfo("Environment", serviceEnv);
        //reports.setSystemInfo("Database Environment", dbEnv);
        reports.attachReporter(htmlReporter);
        reports.setReportUsesManualConfiguration(true);

        return reports;
    }
}
