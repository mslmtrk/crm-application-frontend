# CRM Application Frontend
#### This is the frontend side of the CRM Application, which consumes [the backend side](https://github.com/mslmtrk/Crm-Application-Backend) of the application. It has been deployed to AWS.

#### Live Application: http://crm-frontend.eu-central-1.elasticbeanstalk.com/ 

#### Live Api Documentation: http://crm-backend.eu-central-1.elasticbeanstalk.com/api/swagger-ui/index.html

![Screenshot_12](https://user-images.githubusercontent.com/60064079/227638029-2d6984e0-8dd3-479d-b6da-3f1b260f5b18.png)

## Technologies
- Java 11
- Spring MVC
- Java Bean Validation
- Thymeleaf
- HTML, CSS
- JavaScript
- Bootstrap

## To run on your own computer
1. Install PostgreSQL.
2. Configure datasource credentials in application.properties.
3. Open two console in the path of the backend and the frontend app.
4. Run `mvnw install` or `./mvnw install` in Unix System.
5. Run `mvnw spring-boot:run` or `./mvnw spring-boot:run` in Unix System.
6. The frontend app is running on localhost:8081, and the backend app is running on localhost:8080.
