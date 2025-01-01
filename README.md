
# Account Microservice

This project is a **demo** built to showcase my skills and learning in building microservices using **Spring Boot**. It serves as an example for managing accounts through CRUD operations and demonstrates the integration of Docker for containerization, along with the use of custom message handling and Spring Actuator for health monitoring.

## Features
- CRUD operations for accounts.
- Health check endpoint exposed via Spring Actuator.
- Docker support for easy containerization and deployment.
- Error and info message management via custom message classes.

## Technologies Used
- **Spring Boot**: For building the microservice.
- **Spring Actuator**: For health checks and monitoring.
- **Docker**: For containerization.
- **JUnit**: For unit testing.
- **Maven**: For build and dependency management.

## Requirements
- Docker (for containerization and running the application in containers).
- Java 21 (or compatible version) for building and running the Spring Boot application.

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/eofe/account-microservice.git
cd account-microservice
```

### 2. Build the project

If you don't have Docker and want to run the service locally, you can build it using Maven:

```bash
mvn clean install
```

### 3. Running the microservice

You can run the microservice directly using Docker or locally with Maven.

#### Using Docker:
To run the microservice in Docker containers, use the `docker-compose.yml` file provided:

```bash
docker-compose up
```

This will start the services defined in the `docker-compose.yml` file.

#### Using Maven:
Alternatively, you can run the microservice locally using Maven:

```bash
mvn spring-boot:run
```

This will start the Spring Boot application on `http://localhost:8080`.

### 4. Accessing the Health Check Endpoint

Once the application is running, you can check the health of the microservice by accessing the following URL:

```bash
http://localhost:8080/actuator/health
```

### 5. Running Tests

To run unit tests for the application, use the following Maven command:

```bash
mvn test
```

This will execute all tests and output the results to the console.

### 6. Accessing the API

The microservice exposes a few endpoints to manage accounts.

- **POST /accounts**: Create a new account.
- **GET /accounts/{accountNumber}**: Retrieve account details by account number.
- **PUT /accounts/{accountNumber}**: Update account details.
- **DELETE /accounts/{accountNumber}**: Delete an account.

You can interact with these endpoints using tools like [Postman](https://www.postman.com/) or [Curl](https://curl.se/).

## Docker

The project uses Docker for containerization. The `Dockerfile` in the root directory is used to create a Docker image of the application.

### Build the Docker image

You can manually build the Docker image using:

```bash
docker build -t account-microservice .
```

### Run the Docker container

After building the image, you can run the container:

```bash
docker run -p 8080:8080 account-microservice
```

This will start the application in a Docker container, mapping port 8080 on your machine to port 8080 in the container.

## Configuration

Configuration settings can be found in the `application.yml` file. This includes environment-specific settings such as database configuration, message handling, etc.

## License

This project is licensed under the **Creative Commons Attribution-NonCommercial 4.0 International License (CC BY-NC 4.0)**. You are free to:

- Share: Copy and redistribute the material in any medium or format.
- Adapt: Remix, transform, and build upon the material for non-commercial purposes.

However, **commercial use is not allowed** without explicit permission from the author.

For more details, see the full text of the license in the `LICENSE` file.

## Contributing

This project is primarily a **demo** for showcasing my skills. However, if you'd like to contribute or provide feedback, feel free to open a pull request or an issue.

