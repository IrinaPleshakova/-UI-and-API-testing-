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

	@FindBy(xpath = "//a[contains(text(),'Logged in as')]")
	private WebElement loggedInAsElement;

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

	public boolean isLoggedIn() {
		try {
			return logoutLink.isDisplayed();
		} catch (Exception e) {
			return false;
		}
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
		wait.until(ExpectedConditions.elementToBeClickable(deleteAccountButton)).click();

		logger.info("Clicked on 'Delete Account' button.");
	}
}
