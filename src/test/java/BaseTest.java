import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest {
	protected WebDriver driver;
	protected Logger logger = LogManager.getLogger(this.getClass());

	@BeforeMethod
	public void setUp() {
		logger.info("Setting up WebDriver");
		WebDriverManager.chromedriver().setup();

		// Используем ChromeOptions для настройки браузера
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("--headless"); // Раскомментируйте для headless режима
		driver = new ChromeDriver(options);

		// Настройка неявного ожидания
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// Открытие базового URL
		driver.get("https://automationexercise.com");
		logger.info("Navigated to https://automationexercise.com");
	}

	@AfterMethod
	public void tearDown() {
		logger.info("Tearing down WebDriver");
		if (driver != null) {
			driver.quit();
		}
	}
}

