Feature: Get User Detail By Email API

  @api
  @positive
  @userDetail
  Scenario: Get user detail with valid email
    Given I have a valid email for getting user detail
    When I send a GET request to "/api/getUserDetailByEmail" endpoint
    Then I should receive a response code 200
    And the response message should contain user details

  @api
  @negative
  @userDetail
  Scenario: Get user detail with invalid email
    Given I have an invalid email for getting user detail
    When I send a GET request to "/api/getUserDetailByEmail" endpoint
    Then I should receive a response code 404
    And the response message should be "Account not found with this email, try another email!"




