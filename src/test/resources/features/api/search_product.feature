Feature: Search Product API

  @positive
  @search
  Scenario: POST to Search product with valid name
    Given I have a valid product name to search
    When I send a POST request to "/api/searchProduct" endpoint
    Then I should receive a response code 200
    And the products list should contain searched products

  @api
  @negative
  @search
  Scenario: POST to Search product without search_product parameter
    Given I do not provide a product name for search
    When I send a POST request to "/api/searchProduct" endpoint
    Then I should receive a response code 400
    And the response message should be "Bad request, search_product parameter is missing in POST request."


