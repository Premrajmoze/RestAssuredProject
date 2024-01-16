Feature: verify reqres api's

  @GetMockData
  Scenario: verify get all users info
    Given I setup request structure to get all users info
    When I hit an get all users api
    Then I verify all users response

  @ReqRes
    Scenario: verify total user records on all pages
      Given I setup request structure to get all users info
      When I hit an get all users api
      Then I verify total users in response


    #Serialization :

  @ReqRes
  Scenario: verify total user records on all pages
    Given I setup request structure to create users using serialization
    When I hit a api to create user
    Then I verify user created successfully

    #Deserialization :

@Deserialization
  Scenario: verify get all users info using deserialization
    Given I setup request structure to get all users info
    When I hit an get all users api
    Then I verify all users response using deserialization


  @CreateUserUsingSerAndDes
  Scenario: verify user creation using serialization and deserialization
    Given I setup request structure to create users using serialization
    When I hit a api to create user
    Then I verify user created successfully using deserialization
