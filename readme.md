# UserTaskService
## Test application for Java Developer role by Andras Marton (martonandras84@gmail.com) 

Project specification including REST APIs: https://bitbucket.org/one-way/blackswan-tests/src/master/bs-mid-to-senior-java-dev.md

###Implementation details and highlights
####Resiliency
User creation endpoint is returning object created based on input data.
If input contains an existing user name then application is returning the existing user object.
No warning is shown for the user. This should be confirmed by product team in a real project.

####Exception handling
- A custom Exception is designed for the project and is thrown from service classes in case of invalid or inconsistent inputs.
Exception handling is implemented using @ControllerAdvice to keep the main code clean and readable.
- Validation of input data is implemented using AOP. Custom aspects are created for user and task validation.

####JavaDoc
JavaDoc is added for all classes and methods.

####Database
- Using built in h2 database
- Database migration is prepared using FlyWay
- Audit fields are added to entities like creationTimestamp, updateTimestamp and version. This is a minimum value here.
Creator and modifier users could also be added. Also, we can consider storing each version of our entities for further audit purposes.
This should depend on the detailed business requirements.
- Indexes are created for entities. Unique indexes as constraints and non unique indexes to speed up querying.

####Containerization
Containerization is prepared using Docker

####Code quality
- JaCoCo test coverage report added. Minimum limit of instruction coverage is set 75% for the project.
Build is failing if instruction coverage is below the limit.
- Current branch coverage is 100% and instruction coverage is 87%.

- Code quality could be further improved by adding integration tests (using @AutoConfigureMockMvc or @WebMvcTest).
The trade of is that integration tests can significantly increase build time.
If the project is focusing on manual testing then we can stick with and fast build and no integration test.
Preferred way could be to have automated integration tests but execute them separately from the build process.
For instance we could use a dedicated test env and schedule automated testing every night.