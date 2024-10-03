Feature: Viewing Products and Filtering by Category

@ui
  Scenario: View all products
    Given I open the homepage
    When I click on "Products" link
    Then I should see a list of products

@ui
  Scenario: Filter products by category "Women > Dress"
    Given I open the homepage
    When I click on "Women" category
    And I click on "Dress" subcategory
    Then I should see products filtered by "Women > Dress"