Feature: Trainer Work Hours Management

  Scenario: Add new training workload
    Given a trainer with username "admin_admin", first name "admin", last name "admin" has only worked duration of 2 hours in the january of 2024 and is now stored in the system
    When a trainer modifies a training with username "admin_admin", first name "admin", last name "admin", training date "2024-01-03", duration "2", and action type "ADD"
    Then the training hours of username "admin_admin" should be 4

  Scenario: Delete existing training workload
    Given a trainer with username "admin_admin", first name "admin", last name "admin" has only worked duration of 2 hours in the january of 2024 and is now stored in the system
    When a trainer modifies a training with username "admin_admin", first name "admin", last name "admin", training date "2024-01-03", duration "2", and action type "DELETE"
    Then the training hours of username "admin_admin" should be 0

  Scenario: Retrieve training summary
    Given a trainer with username "admin_admin", first name "admin", last name "admin" has only worked duration of 2 hours in the january of 2024 and is now stored in the system
    Then the response of training summary should contain the correct training summary duration for the trainer "admin_admin"

  Scenario: Calculate monthly training summary for non-existent trainer
    Given system database is clean and there is no trainer's summary stored
    Then the request of training summary of the trainer "admin_admin" should throw exception


