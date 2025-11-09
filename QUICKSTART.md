# ExpenseFlow - Quick Start Guide

## 🚀 Quick Setup

### 1. Database Setup
```sql
CREATE DATABASE expenseflow_db;
```

### 2. Update Configuration
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

### 4. Access Swagger UI
Open browser: `http://localhost:8080/swagger-ui.html`

## 📝 Quick Test

### Step 1: Register a User
```bash
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123",
  "firstName": "Test",
  "lastName": "User"
}
```

### Step 2: Login
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "password123"
}
```

**Save the token from the response!**

### Step 3: Create a Category
```bash
POST http://localhost:8080/api/categories
Authorization: Bearer <your_token>
Content-Type: application/json

{
  "name": "Food",
  "description": "Food and dining expenses",
  "icon": "🍔",
  "color": "#FF5733"
}
```

### Step 4: Create an Expense
```bash
POST http://localhost:8080/api/expenses
Authorization: Bearer <your_token>
Content-Type: application/json

{
  "title": "Lunch",
  "description": "Lunch at restaurant",
  "amount": 25.50,
  "categoryId": 1,
  "paymentMethod": "CREDIT_CARD"
}
```

### Step 5: Create a Budget
```bash
POST http://localhost:8080/api/budgets
Authorization: Bearer <your_token>
Content-Type: application/json

{
  "name": "Monthly Food Budget",
  "description": "Budget for food expenses",
  "totalAmount": 500.00,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31",
  "categoryId": 1,
  "budgetType": "MONTHLY"
}
```

### Step 6: Get Analytics
```bash
GET http://localhost:8080/api/analytics/current-month
Authorization: Bearer <your_token>
```

## 🎯 Key Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/auth/register` | POST | Register new user |
| `/api/auth/login` | POST | Login and get token |
| `/api/expenses` | GET/POST | Get/Create expenses |
| `/api/categories` | GET/POST | Get/Create categories |
| `/api/budgets` | GET/POST | Get/Create budgets |
| `/api/analytics/current-month` | GET | Get current month analytics |

## 🔑 Authentication

All endpoints (except `/api/auth/**`) require JWT token in header:
```
Authorization: Bearer <your_jwt_token>
```

## 📚 Full Documentation

See `README.md` for complete documentation.

## 🐛 Troubleshooting

### Database Connection Error
- Check MySQL is running
- Verify database credentials in `application.properties`
- Ensure database `expenseflow_db` exists

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`

### JWT Token Expired
- Token expires after 24 hours
- Login again to get a new token

## 💡 Tips

1. Use Swagger UI for interactive API testing
2. Token is valid for 24 hours
3. All dates should be in `YYYY-MM-DD` format
4. Amounts should be in decimal format (e.g., `25.50`)

---

Happy coding! 🎉

