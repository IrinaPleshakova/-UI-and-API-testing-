package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
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

	public void hoverOverWomenCategory() {
		Actions actions = new Actions(driver);
		actions.moveToElement(womenCategoryLink).perform();
	}

	public void clickDressSubcategory() {
		dressSubcategoryLink.click();
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

//	public DeleteAccountPage
}
