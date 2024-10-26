Feature: Create Account API

  @api
  @positive
  @smoke
  @account_create
  Scenario: Create account with valid data
    Given I have a valid user data for account creation
    When I send a POST request to "/api/createAccount" endpoint
    Then I should receive a response code 201
    And the response message should be "User created!"

  @api
  @negative
  @regression
  @account
  Scenario: Create account with existing email
    Given I have user data with existing email for account creation
    When I send a POST request to "/api/createAccount" endpoint
    Then I should receive a response code 400
    And the response message should be "Email already exists!"
