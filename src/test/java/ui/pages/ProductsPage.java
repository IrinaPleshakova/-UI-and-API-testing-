package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class ProductsPage {
	WebDriver driver;

	@FindBy(xpath = "//div[@class='product-image-wrapper']")
	private List<WebElement> productList;

	@FindBy(xpath = "//div[@class='productinfo text-center']/p")
	private List<WebElement> productNames;

	@FindBy(xpath = "//div[@class='productinfo text-center']/h2")
	private List<WebElement> productPrices;

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

	public void clickViewProduct(int index) {
		viewProductLinks.get(index).click();
	}

	public void clickWomenCategory() {
		womenCategoryLink.click();
	}

	public void clickDressSubcategory() {
		dressSubcategoryLink.click();
	}
}
