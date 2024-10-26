Feature: Adding and Removing Products from Cart

  @ui
  @positive
  @shopping_cart
  Scenario: Add multiple random products to the cart
    Given I open the homepage
    When I add 2 unique random products to the cart
    Then I should see a confirmation message
    And I click "View Cart" button
    Then I should see all added products in the cart

  @ui
  @positive
  @shopping_cart
  Scenario: Remove a product from the cart
    Given I have 2 unique products in my cart
    When I remove 1 product from the cart
    Then I should not see the removed product in the cart
    And I should see only the remaining products in the cart

