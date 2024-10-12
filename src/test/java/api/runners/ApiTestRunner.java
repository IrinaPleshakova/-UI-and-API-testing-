package api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;

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
	@BeforeClass
	public void checkTestType() {
		String testType = System.getProperty("testType");
		if (testType != null && !testType.equals("api")) {
			throw new SkipException("Skipping API tests because testType is set to " + testType);
		}
	}
}
