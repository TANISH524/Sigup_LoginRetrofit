# Retrofit Authentication Demo - Android

A beginner Android project demonstrating Retrofit
library usage with MockAPI for user authentication.

## Technologies Used
- Kotlin
- Jetpack Compose
- Retrofit2
- MockAPI (Fake REST API)
- Coroutines

## Concepts Covered

### 1. Retrofit Setup
- Base URL configuration
- Retrofit instance creation
- API interface creation

### 2. API Interface
- @POST for signup
- @POST for login
- @Body for sending data

### 3. Jetpack Compose UI
- Signup Screen
- Login Screen
- State management

## Project Structure
```
app/src/main/java
└── com.example.app
    ├── api/
    │   ├── RetrofitInstance.kt
    │   └── ApiService.kt
    ├── model/
    │   └── User.kt
    └── screens/
        ├── SignupScreen.kt
        └── LoginScreen.kt
```

## API Endpoints (MockAPI)

### Signup:
POST /users
{
    "username": "Tanish",
    "password": "1234"
}

### Login:
POST /users
{
    "username": "Tanish",
    "password": "1234"
}

## How Retrofit Works
- Base URL    → MockAPI URL
- ApiService  → Endpoints define karo
- Retrofit    → HTTP calls karta hai
- Coroutines  → Background me run hota hai

## Key Learning
- How to integrate Retrofit in Android
- How to send data to REST API
- How to build UI with Jetpack Compose
- How to connect Android app with backend API
