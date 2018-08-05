@Regression
Feature: Verify Vehicle data with DVLA

  Background: 
    Given Read Vehicle Data from Files for supported mime types

  Scenario: Verify Vehicle Data Matches what is held with DVLA
    When I Navigate to Get vehicle information from DVLA website
    And I Click on the Start Now link
    Then Verify Vehicle Data on our files Matches what is on DVLA
