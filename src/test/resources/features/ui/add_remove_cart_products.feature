Feature: Adding and Removing Products from Cart

  @ui
  Scenario: Add random products to cart
    Given I open the homepage
    When I add a random product to the cart
    And I should see a confirmation message
    Then I click "Continue Shopping" button
    And I add a random product to the cart
    Then I should see a confirmation message
    And I click "View Cart" button
    Then I should see both products in the cart


  @ui
  Scenario: Remove a product from the cart
    Given I have two products in my cart
    When I remove one product from the cart
    Then I should not see that product in the cart
    And I should see only the remaining product in the cart
