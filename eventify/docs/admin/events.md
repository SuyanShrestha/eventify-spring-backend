# Admin Events API Documentation

## Base URL

`http://127.0.0.1:8080/api/admin/events/`

---

# 1. Get Pending Events

## Endpoint

`GET /api/admin/events/pending/`

## Description

This API allows admins to retrieve a list of events that are pending approval.

## Permissions

* **Admin Only**: Only users with admin privileges can access this endpoint.

## Response

```json
[
  {
    "id": 17,
    "banner": "http://127.0.0.1:8080/media/events/banner.png",
    "title": "Event Title",
    "subtitle": "Event Subtitle",
    "event_type": "conference",
    "free_event": false,
    "ticket_price": 50.0,
    "start_date": "2025-12-24T14:00:00Z",
    "end_date": "2025-12-24T18:00:00Z",
    "booking_deadline": "2025-12-23T23:59:00Z",
    "venue": "Convention Center",
    "category_details": {
        "id": 1,
        "name": "Technology"
    },
    "tickets_available": 100,
    "created_at": "2025-12-20T10:00:00Z",
    "updated_at": "2025-12-21T10:00:00Z",
    "organizer": {
        "id": 16,
        "username": "sleepystark",
        "profile_picture": null
    },
    "is_upcoming": true,
    "is_active": false,
    "is_expired": false,
    "attendees_count": 0,
    "is_saved": false
  }
]
```

## Error Responses

* `401 Unauthorized`: User is not authenticated.
* `403 Forbidden`: User does not have admin privileges.

---

# 2. Approve Event

## Endpoint

`POST /api/admin/events/{id}/approve/`

## Description

This API allows admins to approve a pending event.

## URL Parameters

| Parameter | Type | Required | Description                |
| --------- | ---- | -------- | -------------------------- |
| `id`      | Long | Yes      | ID of the event to approve |

## Permissions

* **Admin Only**: Only users with admin privileges can approve events.

## Response

```json
{
    "id": 17,
    "banner": "http://127.0.0.1:8080/media/events/banner.png",
    "title": "Event Title",
    "subtitle": "Event Subtitle",
    "event_type": "conference",
    "free_event": false,
    "ticket_price": 50.0,
    "start_date": "2025-12-24T14:00:00Z",
    "end_date": "2025-12-24T18:00:00Z",
    "booking_deadline": "2025-12-23T23:59:00Z",
    "venue": "Convention Center",
    "category_details": {
        "id": 1,
        "name": "Technology"
    },
    "tickets_available": 100,
    "created_at": "2025-12-20T10:00:00Z",
    "updated_at": "2025-12-21T10:00:00Z",
    "organizer": {
        "id": 16,
        "username": "sleepystark",
        "profile_picture": null
    },
    "is_upcoming": true,
    "is_active": true,
    "is_expired": false,
    "attendees_count": 0,
    "is_saved": false
}
```

## Error Responses

* `400 Bad Request`: Event not found or already approved.
* `401 Unauthorized`: User is not authenticated.
* `403 Forbidden`: User does not have admin privileges.

## How it Works

1. **Authentication**: The user must be logged in as an admin.
2. **Get Pending Events**: Lists events that are awaiting approval.
3. **Approve Event**: Admin approves an event, updating its status.
4. **Response**: Returns the event details after approval.
