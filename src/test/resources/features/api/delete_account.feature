Feature: Delete Account API

#  @api
  @ignore
  @positive
  Scenario: Delete account with valid credentials
    Given I have valid credentials for account deletion
    When I send a DELETE request to "/api/deleteAccount" endpoint
    Then I should receive a response code 200
    And the response message should be "Account deleted successfully!"

  @ignore
  @negative
  Scenario: Delete account with invalid credentials
    Given I have invalid credentials for account deletion
    When I send a DELETE request to "/api/deleteAccount" endpoint
    Then I should receive a response code 404
    And the response message should be "Account not found!"

#  API 12: DELETE METHOD To Delete User Account
#  API URL: https://automationexercise.com/api/deleteAccount
#  Request Method: DELETE
#  Request Parameters: email, password
#  Response Code: 200
#  Response Message: Account deleted!
#  actual {"responseCode": 400, "message": "Bad request, name parameter is missing in POST request."}

#  Delete Account
#  GET /api/deleteAccount
#  HTTP 405 Method Not Allowed
#  Allow: DELETE, OPTIONS
#  Content-Type: application/json
#  Vary: Accept
#  {
#  "detail": "Method \"GET\" not allowed."
#  }


