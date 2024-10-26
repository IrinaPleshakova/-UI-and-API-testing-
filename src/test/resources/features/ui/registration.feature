Feature: User Registration

  Background:
    Given I open the homepage
    And I click on the "SignupLogin" link on the homepage

  @ui
  @positive
  @account_create
  Scenario: Successful user registration
    Given I enter a new user name and email
    When I click on the "Signup" button on the login page
    And I fill out all required fields in the registration form
    And I submit the registration form
    Then I should see the "ACCOUNT CREATED!" message
    When I click on the "Continue" button on the account created page
    And I should be logged in as the "new user"

  # Hidden post-condition step
    # The account will be deleted automatically after the test using an @After hook.