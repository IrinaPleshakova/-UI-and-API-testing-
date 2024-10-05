Feature: Get User Detail By Email API

  @api
  @positive
  Scenario: Get user detail with valid email
    Given I have a valid email for getting user detail
    When I send a GET request to "/api/getUserDetailByEmail" endpoint
    Then I should receive a response code 200

  @api
  @negative
  Scenario: Get user detail with invalid email
    Given I have an invalid email for getting user detail
    When I send a GET request to "/api/getUserDetailByEmail" endpoint
    Then I should receive a response code 404
    And the response message should be "Account not found with this email, try another email!"

#  API 14: GET user account detail by email
#  API URL: https://automationexercise.com/api/getUserDetailByEmail
#  Request Method: GET
#  Request Parameters: email
#  Response Code: 200
#  Response JSON: User Detail
#
#{"responseCode": 200, "user": {"id": 419254, "name": "Valid User", "email": "validuser@example.com", "title": "",
#"birth_day": "", "birth_month": "", "birth_year": "", "first_name": "Valid", "last_name": "User", "company": "",
#"address1": "test 1", "address2": "", "country": "India", "state": "Dnipro", "city": "Dnipro", "zipcode": "49000"}}
#  {"responseCode": 400, "message": "Bad request, email parameter is missing in GET request."}
# {"responseCode": 404, "message": "Account not found with this email, try another email!"}


