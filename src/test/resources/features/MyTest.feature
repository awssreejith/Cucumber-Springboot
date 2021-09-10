Feature: The user can leverage by adding a new Student
  Scenario: The user can add new Student through /api/student GET endpoint
    Given The system up and running
    When The user calls the /api/student with GET verb and Student details
    Then The new student is added and new student ID is returned


  Scenario Outline: The user will be able to query the result through GET
    Given The system up and running
    When The user calls /api/student/id endpoint with "<valid>" id
    Then The student details are returned "<success>"

    Examples:
    | valid | success |
    | good | true     |
    | bad | false   |