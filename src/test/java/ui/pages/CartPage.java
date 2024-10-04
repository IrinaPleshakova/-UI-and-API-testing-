package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
	WebDriver driver;

	@FindBy(xpath = "//td[contains(@class, 'cart_description')]//a")
	private List<WebElement> cartItems;
	//private List<WebElement> productName;

	@FindBy(xpath = "//td[contains(@class, 'cart_price')]/p")
	private List<WebElement> price;

	@FindBy(xpath = "//td[contains(@class, 'cart_quantity')]/button")
	private List<WebElement> quantity;

	@FindBy(xpath = "//p[contains(@class, 'cart_total_price')]")
	private List<WebElement> totalPrice;

	@FindBy(css = "a[class='btn btn-default check_out']")
	private WebElement proceedToCheckoutButton;

	@FindBy(css = "a[class='cart_quantity_delete']")
	private List<WebElement> deleteButtons;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void navigateTo() {
		driver.get("http://automationexercise.com/view_cart");
	}

	public int getCartItemCount() {
		return cartItems.size() - 1; // минус один, чтобы исключить заголовок таблицы
	}

	public void clickProceedToCheckout() {
		proceedToCheckoutButton.click();
	}

	public void deleteItem(int index) {
		deleteButtons.get(index).click();
	}

	public List<String> getProductNamesInCart() {
		return cartItems.stream()
				.map(item -> item.getText())
				.collect(Collectors.toList());
	}

	public List<String> getPrices() {
		return price
				.stream()
				.map(WebElement::getText)
				.collect(Collectors.toList());
	}

	public List<String> getQuantity() {
		return quantity
				.stream()
				.map(WebElement::getText)
				.collect(Collectors.toList());
	}

	public void removeProductByName(String productName) {
		for (int i = 0; i < cartItems.size(); i++) {
			String name = cartItems.get(i).getText();
			if (name.equals(productName)) {
				deleteButtons.get(i).click();
				// Wait for the cart to update
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(ExpectedConditions.stalenessOf(cartItems.get(i)));
				break;
			}
		}
	}
}
