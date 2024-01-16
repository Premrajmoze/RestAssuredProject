Feature: Verify login feature for agilecrm application

  Background: launch browser
    Given I Launch the browser and navigate to website
      | firefox |
      | chrome  |
      | edge    |

  @Tag1 @Sanity
  Scenario: verify login feature with valid credentials
    When I Enter valid username and password
    Then I Verify user logged in successfully into the application

  @Tag2
  Scenario: verify login feature with valid credentials
    When I Enter valid username
    And I Enter valid  password
    Then I Verify user logged in successfully into the application

    @Tag1 @Tag2
  Scenario: verify login feature with valid credentials
    Given I launch "chrome" browser and navigate to website "https://apiautomation.agilecrm.com/"
    When I enter username "api_test@yopmail.com"
    And I enter password "Test@1234"
    Then I Verify user logged in successfully into the application

  Scenario: Test Scenario
    Given I have 10 mangoes in bucket
    When I sell 5 mangoes
    Then I left with 5 mangoes

  Scenario: Browser details
    Given I have following browsers
      | chrome  |
      | mozilla |
      | edge    |
      | safari  |

  Scenario: Animals
    Given We have following animals in zoo
      | name  |
      | Tiger |
      | Lion  |
      | Deer  |
      | Fox   |

  Scenario: Browser details
    Given I have below browsers
      | chrome, mozilla, edge, safari |

  Scenario: verify user details
    Given I create a user with following details
      | name    | email               | mobile | add    | empId | profile     |
      | Cyber   | cyber@yopmail.com   | 123456 | deccan | 001   | QA Engineer |
      | Success | success@yopmail.com | 123336 | deccan | 002   | QA Engineer |


  Scenario: Add 5 companies in the application with different data
    Given I add company with following details
      | companyName | url              |
      | Google      | www.google.com   |
      | Facebook    | www.facebook.com |
      | Amazon      | www.amazon.in    |

  Scenario: verify create employee
    Given I create emp with following info
      | name   | cyber success     |
      | empId  | 001               |
      | email  | cyber@yopmail.com |
      | mobile | 9090909090909     |

  Scenario Outline: verify login feature with invalid credentials
    Given I launch "chrome" browser and navigate to website "https://apiautomation.agilecrm.com/"
    When I enter username "<username>"
    And I enter password "<password>"
    Then I Verify user logged in successfully into the application
    Examples:
      | username             | password  |
      | api_test@yopmail.com | Test@1234 |
      | api_test@yopmail.com | 123456    |
      | api@yopmail.com      | Test@1234 |
      |                      | Test@1234 |
      | api_test@yopmail.com |           |
      |                      |           |
      | !@#$%%$$#@           | @#$%%#@   |


