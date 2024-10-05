Feature: Search Product API

#  @api
  @ignore
  @positive
  Scenario: POST to Search product with valid name
    Given I have a valid product name to search
    When I send a POST request to "/api/searchProduct" endpoint
    Then I should receive a response code 200
    And the products list should contain searched products

 @api
 @negative
  Scenario: POST to Search product without search_product parameter
    Given I do not provide a product name for search
    When I send a POST request to "/api/searchProduct" endpoint
    Then I should receive a response code 400
    And the response message should be "Bad request, search_product parameter is missing in POST request."

#  API 5: POST To Search Product
#  API URL: https://automationexercise.com/api/searchProduct
#  Request Method: POST
#  Request Parameter: search_product (For example: top, tshirt, jean)
#  Response Code: 200
#  Response JSON: Searched products list
#  actual {"responseCode": 405, "message": "This request method is not supported."}

#  API 6: POST To Search Product without search_product parameter
#  API URL: https://automationexercise.com/api/searchProduct
#  Request Method: POST
#  Response Code: 400
#  Response Message: Bad request, search_product parameter is missing in POST request.
