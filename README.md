# ExpenseFlow - Personal Finance Management System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![JWT](https://img.shields.io/badge/JWT-Auth-yellow)
![License](https://img.shields.io/badge/License-MIT-green)

## 📋 Overview

ExpenseFlow is a comprehensive **Personal Finance Management System** built with Spring Boot. It provides a robust REST API for managing expenses, budgets, categories, and financial analytics. The system features JWT-based authentication, role-based access control, and comprehensive financial reporting capabilities.

---


## 🛠️ Technology Stack

- **Backend Framework**: Spring Boot 3.5.6
- **Language**: Java 17
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security + JWT
- **API Documentation**: Swagger/OpenAPI 3
- **Build Tool**: Maven
- **Validation**: Jakarta Validation
  
## 🚀 Features

### Core Functionality
- **User Authentication & Authorization**
  - User registration and login
  - JWT-based authentication
  - Role-based access control (USER, ADMIN)
  - Secure password hashing with BCrypt

- **Expense Management**
  - Create, read, update, and delete expenses
  - Track expenses by category
  - Filter expenses by date range
  - Multiple payment methods (Cash, Credit Card, Debit Card, UPI, Bank Transfer)
  - Expense history and tracking

- **Category Management**
  - Create custom expense categories
  - Organize expenses by categories
  - Category icons and color coding
  - User-specific categories

- **Budget Management**
  - Create budgets for different time periods (Daily, Weekly, Monthly, Yearly, Custom)
  - Set budget limits by category or overall
  - Track budget usage and remaining amounts
  - Budget progress indicators
  - Active budget monitoring

- **Financial Analytics**
  - Comprehensive expense reports
  - Category-wise spending analysis
  - Monthly and yearly expense trends
  - Payment method statistics
  - Budget vs. actual spending comparison
  - Savings calculations

### Technical Features
- **RESTful API Design**
  - Clean and intuitive API endpoints
  - Proper HTTP status codes
  - Request/Response DTOs
  - Input validation

- **Security**
  - Spring Security integration
  - JWT token-based authentication
  - Password encryption
  - CORS configuration

- **Documentation**
  - Swagger/OpenAPI documentation
  - Interactive API testing interface
  - Comprehensive endpoint documentation

- **Database**
  - MySQL database integration
  - JPA/Hibernate ORM
  - Database indexing for performance
  - Automatic schema updates


## 📦 Project Structure

```
src/main/java/Auth/Check/
├── config/
│   ├── jwt/
│   │   ├── JwtAuthFilter.java
│   │   └── JwtUtils.java
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
├── controller/
│   ├── AuthController.java
│   ├── ExpenseController.java
│   ├── CategoryController.java
│   ├── BudgetController.java
│   └── AnalyticsController.java
├── dto/
│   ├── RegisterRequest.java
│   ├── LoginRequest.java
│   ├── AuthResponse.java
│   ├── ExpenseRequest.java
│   ├── ExpenseResponse.java
│   ├── CategoryRequest.java
│   ├── CategoryResponse.java
│   ├── BudgetRequest.java
│   ├── BudgetResponse.java
│   └── AnalyticsResponse.java
├── entity/
│   ├── User.java
│   ├── Expense.java
│   ├── Category.java
│   └── Budget.java
├── repository/
│   ├── UserRepository.java
│   ├── ExpenseRepository.java
│   ├── CategoryRepository.java
│   └── BudgetRepository.java
├── service/
│   ├── AuthService.java
│   ├── ExpenseService.java
│   ├── CategoryService.java
│   ├── BudgetService.java
│   ├── AnalyticsService.java
│   └── CustomUserDetailsService.java
├── exception/
│   ├── GlobaleExceptionHandler.java
│   └── UserNotFounundException.java
└── AuthCheckApplication.java
```

## 🔧 Setup & Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd AuthCheck
   ```

2. **Configure Database**
   - Create a MySQL database named `expenseflow_db`
   - Update database credentials in `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/expenseflow_db
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Or run the `AuthCheckApplication.java` class directly from your IDE.

5. **Access the application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - API Docs: `http://localhost:8080/v3/api-docs`

## 📚 API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Expenses
- `GET /api/expenses` - Get all expenses
- `GET /api/expenses/{id}` - Get expense by ID
- `POST /api/expenses` - Create new expense
- `PUT /api/expenses/{id}` - Update expense
- `DELETE /api/expenses/{id}` - Delete expense
- `GET /api/expenses/category/{categoryId}` - Get expenses by category
- `GET /api/expenses/date-range?startDate=...&endDate=...` - Get expenses by date range

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create new category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Budgets
- `GET /api/budgets` - Get all budgets
- `GET /api/budgets/active` - Get active budgets
- `GET /api/budgets/{id}` - Get budget by ID
- `POST /api/budgets` - Create new budget
- `PUT /api/budgets/{id}` - Update budget
- `DELETE /api/budgets/{id}` - Delete budget

### Analytics
- `GET /api/analytics/current-month` - Get current month analytics
- `GET /api/analytics/date-range?startDate=...&endDate=...` - Get analytics for date range
- `GET /api/analytics/year/{year}` - Get yearly analytics

## 🔐 Authentication

All endpoints (except `/api/auth/**`) require JWT authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/expenses \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

## 📊 Database Schema

### Users Table
- id (Primary Key)
- username (Unique)
- email (Unique)
- password (Encrypted)
- firstName
- lastName
- role (USER/ADMIN)
- createdAt
- updatedAt

### Categories Table
- id (Primary Key)
- name
- description
- icon
- color
- userId (Foreign Key)
- createdAt

### Expenses Table
- id (Primary Key)
- title
- description
- amount
- expenseDate
- categoryId (Foreign Key)
- userId (Foreign Key)
- paymentMethod
- createdAt
- updatedAt

### Budgets Table
- id (Primary Key)
- name
- description
- totalAmount
- startDate
- endDate
- categoryId (Foreign Key, nullable)
- userId (Foreign Key)
- budgetType
- createdAt
- updatedAt

## 🧪 Testing the API

### Using Swagger UI
1. Navigate to `http://localhost:8080/swagger-ui.html`
2. Click on "Authorize" button
3. Enter your JWT token (obtained from login)
4. Explore and test all endpoints

### Using cURL

**Register User:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "password123"
  }'
```

**Create Expense:**
```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_token>" \
  -d '{
    "title": "Groceries",
    "description": "Weekly groceries",
    "amount": 150.50,
    "categoryId": 1,
    "paymentMethod": "CREDIT_CARD"
  }'
```

## 🎯 Key Features for Resume

- **RESTful API Development**: Designed and implemented comprehensive REST APIs
- **Spring Boot Framework**: Built scalable backend application using Spring Boot
- **Security Implementation**: Implemented JWT-based authentication and authorization
- **Database Design**: Designed and implemented relational database schema with MySQL
- **Business Logic**: Developed complex business logic for financial management
- **API Documentation**: Created comprehensive API documentation using Swagger/OpenAPI
- **Exception Handling**: Implemented global exception handling with proper error responses
- **Data Validation**: Implemented input validation using Jakarta Validation
- **Software Architecture**: Applied layered architecture (Controller, Service, Repository)
- **Version Control**: Managed project using Git

## 🔒 Security Features

- JWT token-based authentication
- BCrypt password hashing
- Role-based access control
- CORS configuration
- SQL injection prevention (JPA parameterized queries)
- Input validation and sanitization

## 📈 Future Enhancements

- [ ] Email notifications for budget alerts
- [ ] Export reports to PDF/Excel
- [ ] Recurring expense support
- [ ] Multi-currency support
- [ ] Financial goals tracking
- [ ] Mobile app integration
- [ ] Data visualization charts
- [ ] Receipt image upload
- [ ] Expense sharing with family members
- [ ] Integration with banking APIs


## 📝 License

This project is licensed under the MIT License.

## 👤 Author
Tejas Gajanan Amzare


## 📞 Contact

For questions or support, please open an issue in the repository.

📧 Email: tejasamzare@gmail.com

🔗 LinkedIn: https://www.linkedin.com/in/tejas-amzare

**Built with ❤️ using Spring Boot**





