Feature: verify deals CRUD api's

  @CreateDeal
  Scenario:verify create deals with valid details
    Given I create a deal using serialization
    Then I verify deal created successfully
    And I verify created deal in get by id api

  @CreateDeal
  Scenario Outline: verify create deals with in-valid details
    Given I create a deal using serialization with invalid details
      | name   |
      | <name> |
    Then I verify error messages in response
      | statusCode   | errorMessage   |
      | <statusCode> | <errorMessage> |

    Examples:
      | name          | statusCode | errorMessage             |
      | null          | 400        | Deal not saved properly. |
      |               | 400        | Deal not saved properly. |
      | @&#$&@%$&@&#^ | 400        | Deal not saved properly. |
