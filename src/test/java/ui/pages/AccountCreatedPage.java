package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountCreatedPage {
	WebDriver driver;

	@FindBy(css = "h2[data-qa='account-created']")
	private WebElement accountCreatedMessage;

	@FindBy(css = "a[data-qa='continue-button']")
	private WebElement continueButton;

	public AccountCreatedPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isAccountCreatedMessageDisplayed(String expectedMessage) {
		try {
			String actualMessage = accountCreatedMessage.getText();
			return actualMessage.equalsIgnoreCase(expectedMessage);
		} catch (Exception e) {
			return false;
		}
	}

	public void clickContinueButton() {
		continueButton.click();
	}
}

