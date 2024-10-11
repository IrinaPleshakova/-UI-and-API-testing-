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

	@FindBy(css = "a[class='btn btn-default check_out']")
	private WebElement proceedToCheckoutButton;

	@FindBy(css = "a[class='cart_quantity_delete']")
	private List<WebElement> deleteButtons;

	private WebDriverWait wait;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	public void navigateTo() {
		driver.get("http://automationexercise.com/view_cart");
	}

	public void clickProceedToCheckout() {
		wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton));
		proceedToCheckoutButton.click();
	}

	public void deleteItem(int index) {
		wait.until(ExpectedConditions.elementToBeClickable(deleteButtons.get(index)));
		deleteButtons.get(index).click();
		wait.until(ExpectedConditions.stalenessOf(cartItems.get(index)));
	}

	public List<String> getProductNamesInCart() {
		return cartItems.stream()
				.map(WebElement::getText)
				.collect(Collectors.toList());
	}

	public void removeProductByName(String productName) {
		for (int i = 0; i < cartItems.size(); i++) {
			String name = cartItems.get(i).getText();
			if (name.equals(productName)) {
				wait.until(ExpectedConditions.elementToBeClickable(deleteButtons.get(i)));
				deleteButtons.get(i).click();
				// Wait for the cart to update
				wait.until(ExpectedConditions.stalenessOf(cartItems.get(i)));
				break;
			}
		}
	}
}
