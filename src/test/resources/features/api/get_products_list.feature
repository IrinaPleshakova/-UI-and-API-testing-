Feature: Products List API

  @api
  @positive
  @product
  Scenario: Get All Products List
    Given the product catalog contains multiple categories
    When I send a GET request to "/api/productsList" endpoint
    Then I should receive a status code 200
    And the response message should contain a non-empty list of products
    And each product should have mandatory fields: "id", "name", "price", "category"
    And all product prices should be greater than 0

  @api
  @negative
  @regression
  @product
  Scenario: Get products list with invalid endpoint
    When I send a GET request to "/api/invalidProductsList" endpoint
    Then I should receive a status code 404
