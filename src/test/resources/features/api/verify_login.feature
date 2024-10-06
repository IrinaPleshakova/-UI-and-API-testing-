Feature: Verify Login API

  @ignore # Test disabled due to incorrect handling of POST requests: the server returns an error about missing fields when valid data is sent
  @api
  @positive
  @login
  Scenario: Verify login with valid credentials
    Given I have valid login credentials
    When I send a POST request to "/api/verifyLogin" endpoint
    Then I should receive a response code 200
    And the response message should be "User exists!"

  @ignore # Test disabled due to incorrect handling of POST requests: the server returns an error about missing fields when valid data is sent
  @api
  @negative
  @login
  Scenario: Verify login with invalid credentials
    Given I have invalid login credentials
    When I send a POST request to "/api/verifyLogin" endpoint
    Then I should receive a response code 404
    And the response message should be "User not found!"

#  API 7: POST To Verify Login with valid details
#  API URL: https://automationexercise.com/api/verifyLogin
#  Request Method: POST
#  Request Parameters: email, password
#  Response Code: 200
#  Response Message: User exists!
#  actual {"responseCode": 405, "message": "This request method is not supported."}

#  API 10: POST To Verify Login with invalid details
#  API URL: https://automationexercise.com/api/verifyLogin
#  Request Method: POST
#  Request Parameters: email, password (invalid values)
#  Response Code: 404
#  Response Message: User not found!
#  actual {"responseCode": 405, "message": "This request method is not supported."}

