Feature: User Login

  @ui
  Scenario: Successful login with valid credentials
    Given I open the homepage
    When I click on the "SignupLogin" link on the homepage
#    And I have an existing user account
#    When I click on the "SignupLogin" link on the homepage
    And I enter valid login credentials
    And I click on the "Login" button on the login page
    Then I should be logged in as the user

  @ui
  Scenario: Unsuccessful login with invalid credentials
    Given I open the homepage
    When I click on the "SignupLogin" link on the homepage
    And I enter invalid login credentials
    And I click on the "Login" button on the login page
    Then I should see an error message "Your email or password is incorrect!"
