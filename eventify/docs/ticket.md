# Ticket API Documentation

## Base URL

`http://127.0.0.1:8080/api/tickets/`

---

# 1. Book Event (Temporary, Pre-Stripe Integration)

## Endpoint

`POST /api/tickets/`

## Description

This API allows users to book tickets for an event. Currently, the booking is handled directly and marked as **PAID** temporarily. Stripe payment integration will be added later; once Stripe is integrated, the booking will initially be **RESERVED** and only marked **PAID** upon successful payment.

## Permissions

* **Authenticated Users**: Only logged-in users can book tickets.

## Request Body

```json
{
  "event_id": 17,
  "quantity": 2
}
```

## Response

```json
{
    "ticket_code": "ABC123",
    "event": {
        "id": 17,
        "title": "Event Title",
        "banner": "http://127.0.0.1:8080/media/events/banner.png"
    },
    "user": {
        "id": 16,
        "username": "sleepystark",
        "profile_picture": null
    },
    "quantity": 2,
    "unit_price": 50.0,
    "total_price": 100.0,
    "status": "paid",
    "purchase_date": "2025-12-24T11:00:00Z",
    "qr_code_data": "<QR_CODE_DATA>",
    "qr_code_image": "<QR_CODE_IMAGE_PATH>"
}
```

## Error Responses

* `400 Bad Request`: Invalid quantity or event not found.
* `401 Unauthorized`: User is not logged in.
* `403 Forbidden`: User cannot book this event.
* `409 Conflict`: Insufficient tickets available.

## How it Works

1. **Authentication**: The user must be logged in.
2. **Ticket Availability Check**: For paid events, ensures sufficient tickets are available.
3. **Ticket Creation**: A ticket is created and currently marked as **PAID** (temporary for MVP).
4. **QR Code Generation**: A QR code is generated for the booked ticket.
5. **Email Notification**: The ticket and QR code are sent to the user via email.
6. **Response**: Returns booking details including QR code data and image.

---

# 2. Ticket Check-In

## Endpoint

`POST /api/tickets/check-in/`

## Description

This API is used by event organizers to validate and check-in users' tickets using QR code data.

## Permissions

* **Organizer Permission**: Only event organizers can access this endpoint. Users without the `is_organizer` flag will receive a **403 Forbidden** response.

## Request Body

```json
{
  "qr_code_data": "<QR_CODE_DATA>"
}
```

## Response

### Success (200 OK)

```json
{
  "detail": "Ticket successfully checked in",
  "ticket_info": {
    "event_name": "Event Title",
    "ticket_code": "<TICKET_CODE>",
    "ticket_quantity": 10,
    "ticket_status": "paid",
    "purchase_date": "<PURCHASE_DATE>",
    "attendee_name": "username",
    "check_in_time": "<CHECK_IN_TIME>"
  }
}
```

### Errors

* **Invalid QR Code**: `400 Bad Request`

```json
{
  "detail": "Invalid QR code"
}
```

* **QR Code Data Missing**: `400 Bad Request`

```json
{
  "detail": "QR code data is required"
}
```

* **Event Date Passed**: `400 Bad Request`

```json
{
  "detail": "This event has already passed"
}
```

* **Ticket Already Checked In**: `400 Bad Request`

```json
{
  "detail": "Ticket already checked in",
  "checked_in_time": "<CHECK_IN_TIME>"
}
```

* **Ticket Status Invalid for Check-In**: `400 Bad Request`

```json
{
  "detail": "Ticket status is <TICKET_STATUS>, which is not valid for check-in",
  "valid_statuses": ["paid"]
}
```

* **User is Not Organizer**: `403 Forbidden`

```json
{
  "detail": "You do not have permission for ticket verification"
}
```

* **Organizer Mismatch**: `403 Forbidden`

```json
{
  "detail": "You do not have permission to verify this ticket."
}
```

## How it Works

1. **Authentication**: The user must be logged in as an organizer.
2. **QR Code Validation**: The `qr_code_data` sent in the request is validated against the `BookedTicket` model.
3. **Event Validation**: Ensures the event h
