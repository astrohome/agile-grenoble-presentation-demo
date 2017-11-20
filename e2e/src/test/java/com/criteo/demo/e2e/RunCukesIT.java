package com.criteo.demo.e2e;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//@CucumberOptions(format = { "html:target/cucumber-html-report", "json:target/cucumber-json-report.json" }, tags = { "@TORUN" }, features="src/test/resources/com/criteo/cem/router/service/cucumber/it")
@CucumberOptions(format = {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json"})
public class RunCukesIT {
}
