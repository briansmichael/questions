@Questions
Feature: Questions
  As a user
  I want to interact with questions
  So that I might learn about aviation

  Scenario: Create a new question
    Given I have a question
    When I submit the question
    Then I should receive a question added response
