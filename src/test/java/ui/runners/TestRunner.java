package ui.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;

/**
 * TestRunner class to execute Cucumber tests with TestNG.
 */
@CucumberOptions(
		features = "src/test/resources/features/ui",
		glue = {"ui.stepsdef", "utils"},
		plugin = {"pretty", "html:target/cucumber-reports.html", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
		monochrome = true,
		tags = "@ui"
)
public class TestRunner extends AbstractTestNGCucumberTests {
	@BeforeClass
	public void checkTestType() {
		String testType = System.getProperty("testType");
		if (testType != null && !testType.equals("ui")) {
			throw new SkipException("Skipping UI tests because testType is set to " + testType);
		}
	}
}
