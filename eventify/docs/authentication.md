# user authentication API Documentation

## Base URL
`http://127.0.0.1:8080/api/` (Common endpoint for all url)

## Endpoints Overview

| Endpoint            | Method | Description |
|--------------------|--------|-------------|
| `user/register/`| POST | Register a new user or organizer |
| `user/login/`   | POST | Login a user and obtain user authentication tokens |
| `user/profile/` | GET | Retrieve the authenticated user's profile |

---

## User Registration

### Endpoint
`POST /api/user/register/`

### Request Body

| Field          | Type    | Required | Description |
|--------------|--------|----------|-------------|
| `username`    | string | Yes      | Unique username |
| `email`       | string | Yes      | Unique email address |
| `password`    | string | Yes      | Must be at least 8 characters long |
| `is_organizer` | boolean | No      | `true` for organizers, `false` for normal users |

### Example Request
```json
{
  "username": "tony_stark",
  "email": "mrstark@example.com",
  "password": "securepassword",
  "is_organizer": false
}
```



### Response
```json
{
  "detail": "User registered successfully.",
  "refresh_token": "<refresh_token>",
  "access_token": "<access_token>",
  "user": {
        "username": "tony_stark",
        "email": "mrstark@example.com",
        "is_organizer": false
    }
}
```

### Error Responses
If a user tries to register with an already existing username or email, the server will respond with a 400 Bad Request status code, along with the following error message:
```json
{
  "username": [
    "user with this username already exists."
  ],
  "email": [
    "user with this email already exists."
  ]
}
```


---

## User Login

### Endpoint
`POST /api/user/login/`

### Request Body

| Field     | Type   | Required | Description |
|-----------|--------|----------|-------------|
| `email`    | string | Yes      | Registered email address |
| `password` | string | Yes      | User password |

### Example Request
```json
{
  "email": "mrstark@example.com",
  "password": "securepassword"
}
```

### Response
```json
{
  "detail": "User logged in successfully.",
  "refresh_token": "<refresh_token>",
  "access_token": "<access_token>",
  "user": {
        "username": "username",
        "email": "example@gmail.com",
        "is_organizer": true
    }
}
```
### Error Responses for login
1. If the provided email starks not exist in the system, the server will respond with a 400 Bad Request status code and the following error message:
```json
{
  "detail": "User with this email starks not exist."
}
```

2. If the credentials provided (email/password) are invalid, the server will respond with a 400 Bad Request status code and the following error message:
```json
{
  "detail": "Invalid credentials."
}
```


---

## Retrieve User Profile

### Endpoint
`GET /api/user/profile/`

### Headers
| Key           | Value |
|--------------|-------|
| Authorization | Bearer `<access_token>` |

### Response
```json
{
  "first_name": "tony",
  "last_name": "stark",
  "username": "tony_stark",
  "email": "mrstark@example.com",
  "profile_picture": "http://127.0.0.1:8000/media/profile_pictures/image.jpg",
  "phone_number": "1234567890",
  "address": "123 Main St",
  "is_organizer": false
}
```

---

## user authentication Notes
- Tokens (access & refresh) must be included in the `Authorization` header for protected routes.
---

