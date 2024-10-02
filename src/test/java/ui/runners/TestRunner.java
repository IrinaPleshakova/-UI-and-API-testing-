package ui.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestRunner class to execute Cucumber tests with TestNG.
 */
@CucumberOptions(
		features = "src/test/resources/features",
		glue = {"ui.stepsdef", "utils"},
//		plugin = {"pretty", "html:target/cucumber-reports.html"},
		plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
		monochrome = true,
		tags = "@ui"
)
public class TestRunner extends AbstractTestNGCucumberTests {
	// No need to implement any methods here
}
