Feature: User Login

  Background:
    Given I open the homepage
    And I click on the "SignupLogin" link on the homepage

  @ui
  @positive
  @login
  Scenario: Successful login with valid credentials
    Given I have an existing user account
    When I enter valid login credentials
    And I click on the "Login" button on the login page
    And I should be logged in as the "existing user"

  @ui
  @negative
  @login
  Scenario: Unsuccessful login with invalid credentials
    When I enter invalid login credentials
    When I click on the "Login" button on the login page
    Then I should see an error message "Your email or password is incorrect!"