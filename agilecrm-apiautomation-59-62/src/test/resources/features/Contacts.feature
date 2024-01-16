Feature: verify contact api's

  @GetContactList
  Scenario: verify get all contact api response
    Given I get contacts information
    Then I verify all contacts in response

  Scenario: verify get contact by id api with valid contact id
    Given I setup request structure to get all contacts
    When I hit an get contact by id api
    Then I verify contact by id in response

  @GetContactById
  Scenario: verify get contact by id api with valid contact id
    Given I get contact information by id
    Then I verify contact by id in response


  Scenario: verify get contact by id api
    Given I setup request structure to get all contacts
    When I hit an get all contact api
    Then I verify all contacts in response
    When I setup request structure to get all contacts
    And  I hit an get contact by id api
    Then I verify contact by id in response

  Scenario: verify get contact by id api
    Given I setup request structure to get all contacts
    When I hit an get all contact api
    Then I verify all contacts in response
    When I setup request structure to get contact by id
    And  I hit an get contact by id api with path param
    Then I verify contact by id in response


    @Sorting
    Scenario: verify sorting in descending order
      Given I setup request structure to get all users info using dynamic filters
      When I hit an api to get the response
      Then I verify response is getting sorted in descending order

      @GetWithQueryParam
      Scenario: verify get contacts with page size query param
        Given I get all contacts with page size query param
        Then I verify response according to query param


