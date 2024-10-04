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

	public int getProductCount() {
		return productList.size();
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

	public void scrollToProduct(int index) {
		WebElement product = viewProductLinks.get(index);

		// Scroll the product into view
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
		// Perform the click on the product
		new Actions(driver).moveToElement(product).click().perform();
		logger.info("Scrolled to the product at index: " + index + " and clicked.");
	}

	// Method to click on the "View Product" button for any product
	public void clickViewProduct(int index) {
		if (index == 0) {
			// If the index is 0, click on the first product using the direct locator
			logger.info("Clicking on the first product");
			new Actions(driver).moveToElement(viewProduct1Button).click().perform();
			logger.info("Successfully clicked on the first product");
		} else if (index >= 0 && index < viewProductLinks.size()) {
			// For other products, click using the list of "View Product" buttons
			logger.info("Clicking on the product at index: " + index);
			new Actions(driver).moveToElement(viewProductLinks.get(index)).click().perform();
			logger.info("Successfully clicked on the product at index: " + index);
		} else {
			logger.error("Invalid product index: " + index);
			throw new IndexOutOfBoundsException("Invalid product index: " + index);
		}
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
