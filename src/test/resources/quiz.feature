@Quiz
Feature: Quiz
  As a user
  I want to interact with quizzes
  So that I might test what I know

  Scenario Outline: Create a new quiz
    Given I am an authenticated user
    And I have a quiz
    And The quiz has title with <number> characters
    When I submit the quiz
    Then I should receive a success response

    Examples:
      | number |
      | 255    |
      | 1      |
      | 25     |
      | 52     |
      | 250    |
      | 205    |

  Scenario Outline: Create a quiz without required data
    Given I am an authenticated user
    And I have a quiz
    And The quiz has title with <number> characters
    When I submit the quiz
    Then I should receive an InvalidPayloadException

    Examples:
      | number |
      | 0      |
      | 256    |

  Scenario: Get a quiz
    Given I am an authenticated user
    And A quiz exists
    When I get the quiz
    Then I should receive a success response
    And A quiz should be received

  Scenario: Update an existing quiz
    Given I am an authenticated user
    And A quiz exists
    And The quiz's title is modified to be 50 characters
    When I submit the quiz for update
    Then I should receive a success response

  Scenario: Delete a quiz
    Given I am an authenticated user
    And A quiz exists
    When I delete the quiz
    Then I should receive a success response
    And The quiz should be removed

  Scenario Outline: Create a quiz as an unauthenticated user
    Given I have a quiz
    And The quiz has title with <number> characters
    When I submit the quiz
    Then I should receive an unauthenticated response

    Examples:
      | number |
      | 15     |

  Scenario: Get a quiz as an unauthenticated user
    Given A quiz exists
    When I get the quiz
    Then I should receive an unauthenticated response

  Scenario: Update an existing quiz as an unauthenticated user
    Given A quiz exists
    And The quiz's title is modified to be 50 characters
    When I submit the quiz for update
    Then I should receive an unauthenticated response

  Scenario: Delete a quiz as an unauthenticated user
    Given A quiz exists
    When I delete the quiz
    Then I should receive an unauthenticated response

  Scenario: Get all quizzes
    Given I am an authenticated user
    And A quiz exists
    When I get all quizzes
    Then I should receive a success response

  Scenario: Get all quizzes as an unauthenticated user
    Given A quiz exists
    When I get all quizzes
    Then I should receive a success response

  Scenario: Starts a quiz
    Given I am an authenticated user
    And A quiz exists
    When I start a quiz
    Then I should receive a success response

  Scenario: Starts a quiz as an unauthenticated user
    Given A quiz exists
    When I start a quiz
    Then I should receive a success response

  Scenario: Complete a quiz
    Given I am an authenticated user
    And A quiz exists
    When I complete a quiz
    Then I should receive a success response

  Scenario: Complete a quiz as an unauthenticated user
    Given A quiz exists
    When I complete a quiz
    Then I should receive a success response

  Scenario: Add a question
    Given I am an authenticated user
    And A quiz exists
    When I add a question
    Then I should receive a success response

  Scenario: Add a question as an unauthenticated user
    Given A quiz exists
    When I add a question
    Then I should receive a success response

  Scenario: Remove a question
    Given I am an authenticated user
    And A quiz exists
    When I remove a question
    Then I should receive a success response

  Scenario: Remove a question as an unauthenticated user
    Given A quiz exists
    When I remove a question
    Then I should receive a success response
