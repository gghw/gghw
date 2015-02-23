Feature: Loans

  Scenario: successful loan application
    When Alice applies for a 250.00 EUR loan with a 30 day term
    Then Alice should have a loan with 250.00 EUR sum and 25.00 EUR interest

  Scenario: successful loan extension
    Given Alice has applied for a 250.00 EUR loan with a 30 day term
    When Alice extends loan 1
    Then Alice should have a loan with 250.00 EUR sum and 37.50 EUR interest

