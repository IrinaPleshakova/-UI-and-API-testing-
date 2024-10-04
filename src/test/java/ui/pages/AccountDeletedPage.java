package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page class for the Account Deleted page.
 */
public class AccountDeletedPage {
	WebDriver driver;

	@FindBy(css = "h2[data-qa='account-deleted']")
	private WebElement accountDeletedMessage;

	@FindBy(css = "a[data-qa='continue-button']")
	private WebElement continueButton;

	public AccountDeletedPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean isAccountDeletedMessageDisplayed(String expectedMessage) {
		try {
			String actualMessage = accountDeletedMessage.getText();
			return actualMessage.equalsIgnoreCase(expectedMessage);
		} catch (Exception e) {
			return false;
		}
	}

	public void clickContinueButton() {
		continueButton.click();
	}
}
