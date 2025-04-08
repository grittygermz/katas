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


feedback round 1
- HATEOAS principles - to return location header to perform a get request
- excel service refactor
- refactor classes to be more specific rather than having controller,service packages
- having one model for db, one model for backend and one model for presentation

testing of service queries
- using mockitoextension vs springextension eg. updateServiceTest vs readServiceTest
- excelservice testing via file or output of method before writing to file

further extension ideas?
- async export of employees for excel sheet?
- using message queues instead of rest endpoint
- authentication and authorization with spring security
- 