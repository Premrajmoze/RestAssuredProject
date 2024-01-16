@GetCompany
Feature: verify crud operations of company module

  Background: get the user information from mock server
    Given I get the contact information from mock server

  Scenario: verify get all company api info
    Given I setup a request structure to get all company information
      | method |
      | POST   |
    When I hit an api to get all company info
    Then I verify company response
    * I get name of all companies
    * I get the name of company which tags are not empty


  @SearchCompany
  Scenario: verify search feature for company
    Given I setup request structure to get all company info with below keyword
      | q         | Spicejet |
      | page_size | 10       |
      | type      | COMPANY  |
    When I hit an api to search company with keywords
    Then I verify the result according to keywords


  Scenario: verify create company with valid details using string request body
    Given I setup request structure to create company with valid details
      | compName | Cyber                    |
      | url      | https://cybersuccess.biz |
    When I hit a create company api
    Then I verify company created successfully


  Scenario: verify create company with valid details using hashmap request body
    Given I setup request structure to create company with hasmap object
      | compName | url                      |
      | Cyber    | https://cybersuccess.biz |
    When I hit a create company api
    Then I verify company created successfully with 200 status code


  Scenario Outline: verify create company with valid details using hashmap request body
    Given I setup request structure to create company with hasmap object
      | compName | url   |
      | <name>   | <url> |
    When I hit a create company api
    Then I verify company created successfully with <statusCode> status code
    Examples:
      | name   | url                      | statusCode |
      | Cyber  | https://cybersuccess.biz | 200        |
      |        | https://google.com       | 400        |
      | Cyber  |                          | 400        |
      |        |                          | 400        |
      | null   | https://google.com       | 200        |
      | Google | null                     | 200        |
      | null   | null                     | 200        |


@CreateCompanyWithFile
  Scenario: verify create company with valid details using json file as request body
    Given I setup request structure to create company using json file
    When I hit a create company api
    Then I verify company created successfully with 200 status code



  @Serialization
  Scenario: verify create company feature using serialization
    Given I setup request structure to create company using seriaization
    When I hit an api to create a company
    Then I verify company created succussfully using serialization


  @CreateUpdate
  Scenario: verify create and update the company feature using serialization
    Given I setup request structure to create company using seriaization
    When I hit an api to create a company
    Then I verify company created successfully using deserialization
    When I update the name and url of a company
    Then I verify updated company information in response
    And I verify updated company information in get all api response
    * I delete the company
    Then I verify company deleted succesfully


    @CreateCompany
    Scenario:  verify create company api using file body
      Given I create a company with valid info
      Then I verify company created successfully
