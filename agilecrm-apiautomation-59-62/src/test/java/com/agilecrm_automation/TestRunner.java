package com.agilecrm_automation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.agilecrm_automation.stepdefinitions",
                "com.agilecrm_automation.common"},
        plugin = {
                "html:target/cucumber-reports.html",
                "junit:target/cucumber-reports/Cucumber.xml"
        },
        tags = "@CreateDeal"
)
public class TestRunner {
}
