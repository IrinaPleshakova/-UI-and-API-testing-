Feature: User Registration

  @ui
  Scenario: Successful user registration
    Given I open the homepage
    When I click on the "SignupLogin" link on the homepage
    And I enter a new user name and email
    And I click on the "Signup" button on the login page
    And I fill out all required fields in the registration form
    And I submit the registration form
    Then I should see the "ACCOUNT CREATED!" message
    When I click on the "Continue" button on the account created page
    Then I should be logged in as the new user
