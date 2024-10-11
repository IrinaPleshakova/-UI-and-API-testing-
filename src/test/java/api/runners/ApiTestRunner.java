package api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Test runner for API tests.
 */
@CucumberOptions(
		features = "src/test/resources/features/api",
		glue = {"api.stepsdef"},
		plugin = {"pretty", "html:target/cucumber-reports.html", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
		tags = "@api"

)
public class ApiTestRunner extends AbstractTestNGCucumberTests {

}
