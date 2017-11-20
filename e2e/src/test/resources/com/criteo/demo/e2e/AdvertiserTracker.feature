Feature: I get user statistics

  Scenario: User statistics per product

    Given A user id "5" visits the product id "101"
    Then I my system should store the following statistics
      | userId | productId |
      | 5      | 101       |
