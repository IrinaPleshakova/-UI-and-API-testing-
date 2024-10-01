package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	WebDriver driver;

	@FindBy(css = "input[data-qa='login-email']")
	private WebElement loginEmailField;

	@FindBy(css = "input[data-qa='login-password']")
	private WebElement loginPasswordField;

	@FindBy(css = "button[data-qa='login-button']")
	private WebElement loginButton;

	@FindBy(css = "input[data-qa='signup-name']")
	private WebElement signupNameField;

	@FindBy(css = "input[data-qa='signup-email']")
	private WebElement signupEmailField;

	@FindBy(css = "button[data-qa='signup-button']")
	private WebElement signupButton;

	@FindBy(xpath = "//p[contains(text(),'Your email or password is incorrect!')]")
	private WebElement incorrectLoginMessage;

//	@FindBy(xpath = "//section/div/div/div[3]/div/form/p")
//	private WebElement emailAddressAlreadyExist;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void enterLoginEmail(String email) {
		loginEmailField.sendKeys(email);
	}

	public void enterLoginPassword(String password) {
		loginPasswordField.sendKeys(password);
	}

	public void clickLoginButton() {
		loginButton.click();
	}

	public void enterSignupName(String name) {
		signupNameField.sendKeys(name);
	}

	public void enterSignupEmail(String email) {
		signupEmailField.sendKeys(email);
	}

	public void clickSignupButton() {
		signupButton.click();
	}

	public boolean isIncorrectLoginMessageDisplayed() {
		try {
			return incorrectLoginMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
