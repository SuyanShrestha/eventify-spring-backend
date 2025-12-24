# Admin Event Categories API Documentation

## Base URL

`http://127.0.0.1:8080/api/admin/categories/`

---

# 1. Create Event Category

## Endpoint

`POST /api/admin/categories/`

## Description

This API allows admins to create new event categories. Each category can be used to classify events on the platform.

## Permissions

* **Admin Only**: Only users with admin privileges can access this endpoint.

## Request Body

```json
{
  "name": "Technology"
}
```

## Response

```json
{
    "id": 1,
    "name": "Technology"
}
```

## Error Responses

* `400 Bad Request`: Missing or invalid fields.
* `401 Unauthorized`: User is not authenticated.
* `403 Forbidden`: User does not have admin privileges.

## How it Works

1. **Authentication**: The user must be logged in as an admin.
2. **Validation**: Validates the request body fields (`name` is required).
3. **Category Creation**: Creates a new event category in the system.
4. **Response**: Returns the created categor
