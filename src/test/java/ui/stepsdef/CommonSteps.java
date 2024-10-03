package ui.stepsdef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import ui.pages.HomePage;
import ui.pages.LoginPage;
import ui.pages.ProductsPage;
import utils.DriverManager;
import utils.ConfigProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CommonSteps class to hold step definitions shared across multiple features.
 */
public class CommonSteps {

	private WebDriver driver = DriverManager.getDriver();
	private HomePage homePage;
	private LoginPage loginPage = new LoginPage(driver);
	private ProductsPage productsPage;
	private static final Logger logger = LogManager.getLogger(CommonSteps.class);

	@Given("I open the homepage")
	public void iOpenTheHomepage() {
		driver.get(ConfigProvider.getBaseUri());
		homePage = new HomePage(driver);
		logger.info("Opened the homepage");
	}

	@When("I click on the \"SignupLogin\" link on the homepage")
	public void iClickOnSignupLoginLinkOnTheHomepage() {
		homePage.clickSignupLogin();
		logger.info("Clicked on Signup / Login link");
	}

	@When("I click on the \"Signup\" button on the login page")
	public void iClickOnSignupButtonOnTheLoginPage() {
		loginPage.clickSignupButton();
		logger.info("Clicked on Signup button");
	}

	@And("I click on the \"Login\" button on the login page")
	public void iClickOnTheLoginButtonOnTheLoginPage() {
		logger.info("Clicking login button");
		loginPage.clickLoginButton();
	}

	@And("I click on \"Products\" link")
	public void iClickOnProductsLink() {
		homePage.clickProducts();
		productsPage = new ProductsPage(driver);
		logger.info("Clicked on Products link");
	}
}
