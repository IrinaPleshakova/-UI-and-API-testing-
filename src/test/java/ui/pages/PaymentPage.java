package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class PaymentPage {
	WebDriver driver;

	@FindBy(css = "input[data-qa='name-on-card']")
	private WebElement nameOnCardField;

	@FindBy(css = "input[data-qa='card-number']")
	private WebElement cardNumberField;

	@FindBy(css = "input[data-qa='cvc']")
	private WebElement cvcField;

	@FindBy(css = "input[data-qa='expiry-month']")
	private WebElement expiryMonthField;

	@FindBy(css = "input[data-qa='expiry-year']")
	private WebElement expiryYearField;

	@FindBy(id = "submit")
	private WebElement payAndConfirmOrderButton;

	@FindBy(xpath = "//p[contains(text(),'Congratulations! Your order has been confirmed!')]")
	private WebElement orderConfirmationMessage;

	@FindBy(css = "a[class='btn btn-default check_out']")
	private WebElement downloadInvoiceButton;

	@FindBy(css = "a[data-qa='continue-button']")
	private WebElement continueButton;

	public PaymentPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void enterPaymentDetails(String nameOnCard, String cardNumber, String cvc, String expiryMonth, String expiryYear) {
		nameOnCardField.sendKeys(nameOnCard);
		cardNumberField.sendKeys(cardNumber);
		cvcField.sendKeys(cvc);
		expiryMonthField.sendKeys(expiryMonth);
		expiryYearField.sendKeys(expiryYear);
	}

	public void clickPayAndConfirmOrder() {
		payAndConfirmOrderButton.click();
	}

	public boolean isOrderConfirmationDisplayed() {
		try {
			return orderConfirmationMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public PaymentPage downloadInvoiceButtonClick() {
		downloadInvoiceButton.click();
		return this;
	}

	public HomePage continueButtonClick() {
		continueButton.click();
		return new HomePage(driver);
	}
}

