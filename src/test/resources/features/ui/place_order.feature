Feature: Placing an Order

  @ui
  @positive
  @order
  Scenario: Place Order - Register while Checkout
    Given I open the homepage
    When I add 1 unique random products to the cart
    And I should see a confirmation message
    Then I click "View Cart" button
    Then I should be on the cart page
    When I click "Proceed To Checkout" button
    And I click on "RegisterLogin" button on the checkout page
    And I enter a new user name and email
    And I click on the "Signup" button on the login page
    And I fill out all required fields in the registration form
    And I submit the registration form
    Then I should see the "ACCOUNT CREATED!" message
    When I click on the "Continue" button on the account created page
    Then I should be logged in as the "new user"
    And I click "Cart" button
    And I click "Proceed To Checkout" button
    Then I should see Address Details and Review Your Order
    When I enter description in comment text area and click "Place Order"
    And I enter payment details
    And I click "Pay and Confirm Order" button
    Then I should see the message "Congratulations! Your order has been confirmed!"
    And I click "Continue on payment page" button
    When I click "Delete Account" button
    Then I should see the "ACCOUNT DELETED!" message
    When I click "Continue on account deleted page" button
    Then I should be redirected to the homepage

  @ui
  @positive
  @order
  Scenario: Place Order - Login before Checkout
    Given I open the homepage
    When I click on the "SignupLogin" link on the homepage
    And I enter valid login credentials
    And I click on the "Login" button on the login page
    Then I should be logged in as the user
    When I add 2 unique random products to the cart
    And I should see a confirmation message
    Then I click "View Cart" button
    Then I should be on the cart page
    When I click "Proceed To Checkout" button
    Then I should see Address Details and Review Your Order
    When I enter description in comment text area and click "Place Order"
    And I enter payment details
    And I click "Pay and Confirm Order" button
    Then I should see the message "Congratulations! Your order has been confirmed!"
