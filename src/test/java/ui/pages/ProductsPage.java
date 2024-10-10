package ui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProductsPage {
	private static final Logger logger = LogManager.getLogger(ProductsPage.class);
	WebDriver driver;

	@FindBy(xpath = "//div[@class='product-image-wrapper']")
	private List<WebElement> productList;

	@FindBy(xpath = "//div[@class='productinfo text-center']/p")
	private List<WebElement> productNames;

	@FindBy(xpath = "//div[@class='productinfo text-center']/h2")
	private List<WebElement> productPrices;

	@FindBy(css = "a[href='/product_details/1']")
	private WebElement viewProduct1Button;

	@FindBy(xpath = "(//a[contains(text(),'View Product')])[1]")
	private WebElement viewFirstProductButton;

	@FindBy(xpath = "//a[contains(text(),'View Product')]")
	private List<WebElement> viewProductLinks;

	@FindBy(css = "a[href='#Women']")
	private WebElement womenCategoryLink;

	@FindBy(css = "a[href='/category_products/1']")
	private WebElement dressSubcategoryLink;

	public ProductsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Get the number of products on the page
	public int getProductCount() {
		// Wait until all products are visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(productList));
		return productList.size();
	}

	// Verify that all displayed products are from the Dress category
	public boolean areAllProductsRelatedToDress() {
		for (WebElement productName : productNames) {
			if (!productName.getText().contains("Dress")) {
				logger.error("Product {} does not belong to the Dress category", productName.getText());
				return false;
			}
		}
		return true;
	}

	// Verify that the page URL corresponds to the Dress category
	public boolean isOnDressCategoryPage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.urlContains("/category_products/1"));
	}

	public void closeBannerIfPresent() {
		try {
			WebElement bannerCloseButton = driver.findElement(By.xpath("/html/body/ins[2]/div[1]//ins/span/svg/path"));
			if (bannerCloseButton.isDisplayed()) {
				bannerCloseButton.click();
				logger.info("Banner closed successfully.");

				// Add explicit wait to ensure the banner is closed
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
				wait.until(ExpectedConditions.invisibilityOf(bannerCloseButton));
				logger.info("Confirmed banner is no longer visible.");
			}
		} catch (NoSuchElementException e) {
			logger.info("No banner found, proceeding.");
		}
	}

	//Scrolls to the product at the specified index and clicks on "View Product" button.
	//Handles both the first product and other products via list.
	public void clickViewProduct(int index) {
		if (index == 0) {
			// For the first product, use a direct locator
			logger.info("Clicking on the first product");
			scrollToElement(viewFirstProductButton);
			clickElement(viewFirstProductButton);
		} else if (index >= 0 && index < viewProductLinks.size()) {
			// For other products, click using the list of "View Product" buttons
			logger.info("Clicking on the product at index: " + index);
			WebElement product = viewProductLinks.get(index);
			scrollToElement(product);
			clickElement(product);
		} else {
			logger.error("Invalid product index: " + index);
			throw new IndexOutOfBoundsException("Invalid product index: " + index);
		}
	}

	//Scrolls the given WebElement into view using JavaScript.
	private void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	//Clicks the given WebElement using Actions to ensure the element is clicked reliably.
	private void clickElement(WebElement element) {
		new Actions(driver).moveToElement(element).click().perform();
		logger.info("Clicked on the element: {}", element);
	}

	public void navigateTo() {
		driver.get("http://automationexercise.com/products");
	}

	public String selectRandomProduct() {
		int randomIndex = ThreadLocalRandom.current().nextInt(productList.size());
		WebElement randomProduct = productList.get(randomIndex);
		// Get the product name
		String productName = randomProduct.findElement(By.cssSelector(".productinfo p")).getText();
		// Scroll to the product
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomProduct);
		new Actions(driver).moveToElement(randomProduct).perform();
		// Click on "View Product"
		randomProduct.findElement(By.cssSelector("a[href*='product_details']")).click();
		logger.info("Selected random product: " + productName);
		return productName;
	}
}
