@S3 @framework
Feature: Move the ghost
  As a ghost, want to catch the player


  @S3.1
  Scenario: A ghost moves.
    Given the game has started for ghost
    And  a ghost is next to an empty cell
    When  a tick event occurs
    Then  the ghost can move to that cell

  @S3.2
  Scenario: The ghost moves over a square with a pellet.
    Given the game has started for ghost
    And  a ghost is next to a cell containing a pellet
    When  a tick event occurs
    Then  the ghost can move to the cell with the pellet
    And  the pellet on that cell is not visible anymore
  @S3.3
  Scenario: The ghost leaves a cell with a pellet.
    Given a ghost is on a cell with a pellet
    When  a tick event occurs
    Then  the ghost can move away from the cell with the pellet
    And  the pellet on that cell is visible again
  @S3.4
  Scenario: The player dies
    Given the game has started for ghost
    And  a ghost is next to a cell containing the player
    When  a tick event occurs
    Then  the ghost can move to the player
    And  the game is over