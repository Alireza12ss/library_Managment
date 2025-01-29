# Library Management System

This project is an online library management system built using Java, Spring Boot, Hibernate, and PostgreSQL. It provides a RESTful API and includes Swagger for API documentation.

## Features

- **CRUD Operations**: Manage books and book groups.
- **User Requests**: Users can request new books.
- **Admin Role**: An admin user with full access to the system.
- **JWT Authentication**: Secure user authentication with JSON Web Tokens.
- **Shopping Cart**: Users can add items to a cart and place orders for books.

## Technologies Used

- **Programming Language**: Java 17
- **Frameworks**: Spring Boot, Hibernate
- **Database**: PostgreSQL
- **API Documentation**: Swagger
- **Authentication**: JWT (JSON Web Token)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Alireza12ss/library_Managment.git
   ```

2. Navigate to the project directory:
   ```bash
   cd library_Managment
   ```

3. Configure the PostgreSQL database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. Build the project:
   ```bash
   ./mvnw clean install
   ```

5. Run the project:
   ```bash
   ./mvnw spring-boot:run
   ```

## Usage

1. Access the Swagger API documentation at:
   - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

2. Use the provided endpoints to:
   - admin initialize first and you can access with ("admin" , "admin") username and password :)
   - Perform CRUD operations on books and book groups.
   - Sign up, log in, and request new books as a user.
   - Manage all aspects of the library as an admin.

## Contribution

Contributions are welcome! Follow these steps to contribute:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push to your branch.
4. Submit a pull request for review.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

