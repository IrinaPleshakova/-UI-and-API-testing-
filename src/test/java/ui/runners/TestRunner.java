package ui.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeSuite;
import utils.ConfigProvider;

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
//		tags = "@important"
)
public class TestRunner extends AbstractTestNGCucumberTests {
	@BeforeSuite
	public void setupConfig() {
		ConfigProvider configProvider = new ConfigProvider();
		configProvider.setConfigFile("ui");  // Устанавливаем конфигурацию для API тестов
	}
}
