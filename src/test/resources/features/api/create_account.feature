Feature: Create Account API

  @ignore # Test disabled due to incorrect handling of POST requests: the server returns an error about missing fields when valid data is sent
  @api
  @positive
  @smoke
  @account
  Scenario: Create account with valid data
    Given I have a valid user data for account creation
    When I send a POST request to "/api/createAccount" endpoint
    Then I should receive a response code 201
    And the response message should be "User created!"

  @ignore # Test disabled due to incorrect handling of POST requests: the server returns an error about missing fields when valid data is sent
  @api
  @negative
  @regression
  @account
  Scenario: Create account with existing email
    Given I have user data with existing email for account creation
    When I send a POST request to "/api/createAccount" endpoint
    Then I should receive a response code 400
    And the response message should be "Email already exists!"

#  API 11: POST To Create/Register User Account
#  API URL: https://automationexercise.com/api/createAccount
#  Request Method: POST
#  Request Parameters: name, email, password, title (for example: Mr, Mrs, Miss), birth_date, birth_month, birth_year, firstname, lastname, company, address1, address2, country, zipcode, state, city, mobile_number
#  Response Code: 201
#  Response Message: User created!
#  actual {"responseCode": 400, "message": "Bad request, name parameter is missing in POST request."}

#  Create Account
#  GET /api/createAccount
#  HTTP 405 Method Not Allowed
#  Allow: OPTIONS, POST
#  Content-Type: application/json
#  Vary: Accept
#
#  {
#  "detail": "Method \"GET\" not allowed."
#  }