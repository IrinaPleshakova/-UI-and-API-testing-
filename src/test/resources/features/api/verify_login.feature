Feature: Verify Login API

  @api
  @positive
  @login
  Scenario: Verify login with valid credentials
    Given I have valid login credentials
    When I send a POST request to "/api/verifyLogin" endpoint
    Then I should receive a response code 200
    And the response message should be "User exists!"

  @api
  @negative
  @login
  Scenario: Verify login with invalid credentials
    Given I have invalid login credentials
    When I send a POST request to "/api/verifyLogin" endpoint
    Then I should receive a response code 404
    And the response message should be "User not found!"


