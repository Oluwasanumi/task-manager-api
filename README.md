# Task Manager API

A RESTful API for managing a todo list with user authentication.

## Features

- User registration and authentication with JWT
- CRUD operations for todo items
- Secure endpoints with proper authorization
- Pagination for todo list
- Data validation
- Error handling

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Tokens)
- Lombok

## Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL

### Database Setup

1. Create a PostgreSQL database named `todolist`
2. Configure database connection in `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todolist
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the following command:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Authentication Flow

1. **Register User**: Creates a new user account and automatically stores the JWT token
2. **Login User**: Authenticates with existing credentials and stores the JWT token

### Task Management Flow

3. **Create Task**: Creates a new task for the authenticated user
4. **Get All Tasks**: Retrieves the paginated list of tasks
5. **Get Task by ID**: Retrieves a specific task by ID
6. **Update Task**: Updates an existing task
7. **Delete Task**: Deletes a task

## API Endpoints

### Authentication

- **POST /register** - Register a new user
- **POST /login** - Authenticate a user

### Tasks

- **POST /todos** - Create a new task
- **GET /todos** - Get all tasks (paginated)
- **GET /todos/{taskId}** - Get a specific task
- **PUT /todos/{taskId}** - Update a task
- **DELETE /todos/{taskId}** - Delete a task

## Security

- All endpoints except `/register` and `/login` require authentication
- JWT tokens are used for authentication
- Passwords are hashed using BCrypt
- Users can only access their own tasks

## License

This project is licensed under the MIT License - see the LICENSE file for details.
