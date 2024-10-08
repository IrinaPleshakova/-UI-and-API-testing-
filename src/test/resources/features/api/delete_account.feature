Feature: Delete Account API

  @api
  @positive
  @smoke
  @account
  Scenario: Delete account with valid credentials
    Given I have valid credentials for account deletion
    When I send a DELETE request to "/api/deleteAccount" endpoint
    Then I should receive a response code 200
    And the response message should be "Account deleted!"

  @api
  @negative
  @account
  Scenario: Delete account with invalid credentials
    Given I have invalid credentials for account deletion
    When I send a DELETE request to "/api/deleteAccount" endpoint
    Then I should receive a response code 404
    And the response message should be "Account not found!"



