# Spring CRUD Application

This is a simple Spring CRUD (Create, Read, Update, Delete) application that allows you to manage users. The application provides REST endpoints for performing CRUD operations on a *User* model with the following attributes: 
- Name
- Email
- BirthDate

The Application also ensures that email addresses are unique.

The application also includes a Swagger documentation UI that provides an interactive interface for exploring and testing the available endpoints.

## Getting Started
To run the application locally, make sure you have the following prerequisites installed:

- Java JDK (version 17 or later)
- Docker

Follow these steps to run the application:

- Clone the repository to your local machine:
    ```
    git clone https://github.com/manasacharyya25/SpringBootCrudApplication.git
    ```
- Navigate to the project directory:
    ```
    cd crudapp
    ```

- Start the application using Docker Compose. This command will start the application along with its required dependencies (such as a PostgreSQL database).
    ```
    docker-compose up -d
    ```

- Once the application is up and running, you can access the Swagger documentation UI by opening the following URL in your web browser:

    ```
    http://localhost:8080/swagger-ui/index.html#
    ```

This UI provides an overview of all the available endpoints and allows you to execute requests and view responses.

## Test Scenarios
The application covers the following test scenarios:

- Create User: You can create a new user by sending a POST request to the **/users** endpoint with the user details in the request body.

- Get User by ID: You can retrieve a user by their ID by sending a GET request to the **/users/{id}** endpoint, where {id} is the ID of the user.

- Update User: You can update a user's details by sending a PUT request to the **/users/{id}** endpoint with the updated user details in the request body.

- Delete User: You can delete a user by sending a DELETE request to the **/users/{id}** endpoint, where {id} is the ID of the user.

- Get All Users: You can retrieve all users by sending a GET request to the **/users** endpoint.

Unique Email Validation: The application ensures that email addresses for users are unique. If you attempt to create/update a user with an email address that already exists, you will receive an appropriate error response.

Feel free to explore and test the application using the provided Swagger documentation UI.