package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class SignupPage {
	WebDriver driver;

	@FindBy(id = "id_gender1")
	private WebElement mrRadioButton;

	@FindBy(id = "id_gender2")
	private WebElement mrsRadioButton;

	@FindBy(id = "password")
	private WebElement passwordField;

	@FindBy(id = "days")
	private WebElement daysDropdown;

	@FindBy(id = "months")
	private WebElement monthsDropdown;

	@FindBy(id = "years")
	private WebElement yearsDropdown;

	@FindBy(id = "newsletter")
	private WebElement newsletterCheckbox;

	@FindBy(id = "optin")
	private WebElement specialOffersCheckbox;

	@FindBy(id = "first_name")
	private WebElement firstNameField;

	@FindBy(id = "last_name")
	private WebElement lastNameField;

	@FindBy(id = "company")
	private WebElement companyField;

	@FindBy(id = "address1")
	private WebElement address1Field;

	@FindBy(id = "address2")
	private WebElement address2Field;

	@FindBy(id = "country")
	private WebElement countryDropdown;

	@FindBy(id = "state")
	private WebElement stateField;

	@FindBy(id = "city")
	private WebElement cityField;

	@FindBy(id = "zipcode")
	private WebElement zipcodeField;

	@FindBy(id = "mobile_number")
	private WebElement mobileNumberField;

	@FindBy(css = "[data-qa='create-account']")
	private WebElement createAccountButton;

	public SignupPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectTitle(String title) {
		if (title.equalsIgnoreCase("Mr")) {
			mrRadioButton.click();
		} else if (title.equalsIgnoreCase("Mrs")) {
			mrsRadioButton.click();
		}
	}

	public void enterPassword(String password) {
		passwordField.sendKeys(password);
	}

	public void selectDateOfBirth(String day, String month, String year) {
		Select selectDay = new Select(daysDropdown);
		selectDay.selectByValue(day);

		Select selectMonth = new Select(monthsDropdown);
		selectMonth.selectByValue(month);

		Select selectYear = new Select(yearsDropdown);
		selectYear.selectByValue(year);
	}

	public void checkNewsletter() {
		if (!newsletterCheckbox.isSelected()) {
			newsletterCheckbox.click();
		}
	}

	public void checkSpecialOffers() {
		if (!specialOffersCheckbox.isSelected()) {
			specialOffersCheckbox.click();
		}
	}

	public void enterFirstName(String firstName) {
		firstNameField.sendKeys(firstName);
	}

	public void enterLastName(String lastName) {
		lastNameField.sendKeys(lastName);
	}

	public void enterCompany(String company) {
		companyField.sendKeys(company);
	}

	public void enterAddress1(String address1) {
		address1Field.sendKeys(address1);
	}

	public void enterAddress2(String address2) {
		address2Field.sendKeys(address2);
	}

	public void selectCountry(String country) {
		Select selectCountry = new Select(countryDropdown);
		selectCountry.selectByVisibleText(country);
	}

	public void enterState(String state) {
		stateField.sendKeys(state);
	}

	public void enterCity(String city) {
		cityField.sendKeys(city);
	}

	public void enterZipcode(String zipcode) {
		zipcodeField.sendKeys(zipcode);
	}

	public void enterMobileNumber(String mobileNumber) {
		mobileNumberField.sendKeys(mobileNumber);
	}

	public void clickCreateAccountButton() {
		createAccountButton.click();
	}
}

