Feature: User Logout

  @ui
  @positive
  @logout
  Scenario: Successful logout
    Given I am logged in as a user
    When I click on the "Logout" link on the homepage
    Then I should be redirected to the homepage
    And I should not be logged in

