package ui.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigProvider;

import java.time.Duration;

public class HomePage {
	private static final Logger logger = LogManager.getLogger(HomePage.class);
	WebDriver driver;

	@FindBy(css = "a[href*='/login']")
	private WebElement signupLoginLink;

	@FindBy(xpath = "//a[contains(text(),'Logout')]")
	private WebElement logoutLink;

	@FindBy(css = "a[href='/products']")
	private WebElement productsLink;

	@FindBy(css = "a[href='/view_cart']")
	private WebElement cartLink;

	@FindBy(xpath = "//a[@href='/delete_account']")
	private WebElement deleteAccountButton;

	@FindBy(xpath = "//a[@href='/logout']")
	private WebElement logoutButton;

	@FindBy(xpath = "//a[contains(text(),'Logged in as')]")
	private WebElement loggedInAsElement;

	@FindBy(css = "a[href='#Women']")
	private WebElement womenCategoryLink;

	@FindBy(css = "a[href='/category_products/1']")
	private WebElement dressSubcategoryLink;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickSignupLogin() {
		signupLoginLink.click();
	}

	public void clickLogout() {
		logoutLink.click();
	}

	public void clickProducts() {
		productsLink.click();
	}

	public void clickCart() {
		cartLink.click();
	}

	public void clickWomenCategory() {
		womenCategoryLink.click();
	}

	public void clickDressSubcategory() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			WebElement dressSubcategory = wait.until(ExpectedConditions.elementToBeClickable(dressSubcategoryLink));
			// Use JavascriptExecutor to scroll to an element
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dressSubcategory);
			// Use Actions to perform the click
			Actions actions = new Actions(driver);
			actions.moveToElement(dressSubcategory).click().perform();
			logger.info("Clicked on Dress subcategory successfully");
		} catch (TimeoutException e) {
			logger.error("Dress subcategory is not clickable within the timeout period");
		} catch (Exception e) {
			logger.error("Failed to click on Dress subcategory: " + e.getMessage());
		}
	}

	public boolean isLoggedIn() {
		try {
			return logoutLink.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public LoginPage ClickOnLogOutButton() {
		logoutButton.click();
		return new LoginPage(driver);
	}

	public String getLoggedInUserName() {
		return loggedInAsElement.getText().replace("Logged in as ", "").trim();
	}

	public void clickDeleteAccount() {
		String currentUrl = driver.getCurrentUrl();
		String homePageUrl = ConfigProvider.getBaseUri();  // URL домашней страницы

		if (!currentUrl.startsWith(homePageUrl)) {
			logger.error("Not on the homepage. Current URL: " + currentUrl);
			throw new IllegalStateException("You must be on the homepage to delete the account.");
		} else {
			logger.info("Confirmed: on the homepage.");
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement deleteAccountButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/delete_account']")));

		deleteAccountButton.click();
		logger.info("Clicked on 'Delete Account' button.");
	}


}
