package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

	@FindBy(css = "a[class='btn btn-default check_out']")
	private WebElement proceedToCheckoutButton;

	@FindBy(css = "a[class='cart_quantity_delete']")
	private List<WebElement> deleteButtons;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
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
}
