Feature: Viewing Product Information

  @ui
  @positive
  @product_information
  Scenario: View product details
    Given I open the homepage
    And I click on "Products" link
    When I click on button "View Product" at the first product
    Then I should see product name, price, availability, condition, and brand
