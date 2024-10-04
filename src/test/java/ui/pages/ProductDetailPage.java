package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage {
	WebDriver driver;

	@FindBy(xpath = "//div[@class='product-information']//h2")
	private WebElement productName;

	@FindBy(xpath = "//div[@class='product-information']//span/span")
	private WebElement productPrice;

	@FindBy(xpath = "//div[@class='product-information']//p[contains(text(),'Category')]")
	private WebElement productCategory;

	@FindBy(xpath = "//div[@class='product-details']/descendant::p[contains(.,'Availability')]")
	private WebElement productAvailability;

	@FindBy(xpath = "//div[@class='product-details']/descendant::p[contains(.,'Condition')]")
	private WebElement productCondition;

	@FindBy(xpath = "//div[@class='product-details']/descendant::p[contains(.,'Brand')]")
	private WebElement productBrand;

	@FindBy(id = "quantity")
	private WebElement quantityField;

	@FindBy(xpath = "//button[contains(@class,'cart')]")
	private WebElement addToCartButton;

	@FindBy(xpath = "//button[@data-dismiss='modal']")
	private WebElement continueShoppingButton;

	@FindBy(xpath = "//div[@id='cartModal']/descendant::a")
	private WebElement viewCartButton;

	@FindBy(xpath = "//div[@class='modal-content']//p")
	private WebElement confirmationMessage;

	public ProductDetailPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getProductName() {
		return productName.getText();
	}

	public String getProductPrice() {
		return productPrice.getText();
	}

	public String getProductCategory() {
		return productCategory.getText();
	}

	public String getProductAvailability() {
		return productAvailability.getText();
	}

	public String getProductCondition() {
		return productCondition.getText();
	}

	public String getProductBrand() {
		return productBrand.getText();
	}

	public void enterQuantity(String quantity) {
		quantityField.clear();
		quantityField.sendKeys(quantity);
	}

	public void clickAddToCart() {
		addToCartButton.click();
	}

	public void clickContinueShopping() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(continueShoppingButton));
		continueShoppingButton.click();
	}

	public void clickViewCart() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(viewCartButton));
		viewCartButton.click();
	}

	public boolean isConfirmationMessageDisplayed() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
		return confirmationMessage.isDisplayed();
	}
}
