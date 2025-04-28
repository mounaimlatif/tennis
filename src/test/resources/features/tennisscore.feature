Feature: Tennis scoring

  Scenario: Player A wins directly
    When player plays "ABABAA"
    Then the result should contain "Player A wins the game"

  Scenario: Game goes to Deuce and Player A wins
    When player plays "ABABABAA"
    Then the result should contain "Deuce"
    And the result should contain "Player A wins the game"


  Scenario: the player without advantage wins the ball back at “deuce”.
    When player plays "AAABBBAB"
    Then the result should contain "Deuce"
    And the result should not contain "Player A wins the game"
    And the result should not contain "Player B wins the game"

  Scenario: Advantage lost back to Deuce
    When player plays "ABABABAABB"
    Then the result should contain "Deuce"

  Scenario: Input with invalid characters
    When player plays "ABACX"
    Then an error should occur with message "Input must only contain characters A or B"

  Scenario: Input with invalid characters
    When player plays ""
    Then an error should occur with message "Input cannot be blank"
