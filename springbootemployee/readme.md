task:

- SB app to manage employees
- support CRUD operations
- handle different type of employees with unique salary calculation algo
- export employee data to an Excel file
- data is persisted in H2 in memory database
- 3 types of employees, FullTimeEmployee, PartTimeEmployee, ContractorEmployee
- FullTimeEmployee gets base salary + 20% bonus
- PartTimeEmployee: workinghours * 50 SGD
- ContractorEmployee: base salary
- Create stockoptionservice that is called via rest api that returns a static number that is used during salary calculation which represents number of stocks
- only fulltimeemployees will have stock options

configuring h2 database during testing so h2 database properties will be picked up
- https://stackoverflow.com/questions/44134297/h2-embedded-database-not-picking-up-properties-during-test-on-spring-boot