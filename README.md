# Eventify Backend

Eventify is an Event Management System backend built using Spring Boot. It provides APIs for managing events, bookings, tickets, feedback, notifications, and administrative operations. The frontend is implemented in React and available at [Eventify Frontend](https://github.com/SuyanShrestha/eventify-frontend) running on port `5173`.

## Base URL

`http://127.0.0.1:8080/`

---

## Modules Overview

### 1. **Notifications Module**

* **Purpose:** Manage user notifications.
* **Endpoints:**

  * `GET /api/notifications/` → Retrieve notifications
  * `PUT /api/notifications/mark-all-as-read/`
  * `PUT /api/notifications/mark-as-read/<id>/`
* **Authentication:** Required (user-level)
* **Features:** Fetch unread/read notifications, mark as read

### 2. **Feedback Module**

* **Purpose:** Allow users to submit and manage feedback for events.
* **Endpoints:**

  * `GET /api/feedback/event/<event_id>/`
  * `POST /api/feedback/event/<event_id>/`
  * `GET /api/feedback/<feedback_id>/`
  * `PUT /api/feedback/<feedback_id>/`
  * `DELETE /api/feedback/<feedback_id>/`
* **Authentication:** Required (user-level)
* **Features:** CRUD operations, permission validation (user cannot give feedback to own event)

### 3. **Ticketing Module**

* **Purpose:** Handle event ticket bookings and check-ins.
* **Endpoints:**

  * `POST /api/tickets/` → Book event (temporary direct booking before Stripe integration)
  * `POST /api/tickets/check-in/` → Ticket check-in by organizers
* **Authentication:** User for booking, Organizer for check-in
* **Features:** Ticket creation, QR code generation, email notification, check-in validation

### 4. **Admin Module**

* **Purpose:** High-level administrative operations.
* **Access:** Only users with **SUPERUSER** privileges
* **Controllers/Endpoints:**

  * **Event Categories:** `POST /api/admin/categories/` → create category
  * **Events:**

    * `GET /api/admin/events/pending/` → list pending events
    * `POST /api/admin/events/{id}/approve/` → approve event
* **Features:** Manage categories, review and approve events

### 5. **Event Module**

* **Purpose:** Event management and orchestration.
* **Features:** Event CRUD, categorization, pending approval workflow, ticket allocation

### 6. **User / Authentication Module**

* **Purpose:** Handle user authentication and authorization.
* **Features:** JWT/Bearer authentication, roles (user, organizer, SUPERUSER), permission checks

### 7. **Supporting Modules**

* **QR Code Module:** Generate and manage QR codes for tickets
* **Email Module:** Sending emails with attachments for bookings
* **Mapper Module:** Convert between DTOs and entities
* **Service Layer:** Encapsulates business logic for events, bookings, tickets, and admin operations

---

## Running the Backend

1. Ensure Docker and Docker Compose are installed.
2. Create your own `application-local.properties` in `src/main/resources/` based on the `application-example.properties` file provided.
3. Run the backend with Docker Compose:

   ```bash
   docker-compose up --build
   ```
4. The backend will be available at `http://127.0.0.1:8080/`
5. Ensure the frontend is running at port `5173` for proper integration.

---

## Authentication & Roles

* **Users:** Can book events, submit feedback, view notifications.
* **Organizers:** Can manage check-ins for their events.
* **SUPERUSER:** Can access the admin module for managing categories and approving events.

---

## Frontend Integration

* React frontend: [https://github.com/SuyanShrestha/eventify-frontend](https://github.com/SuyanShrestha/eventify-frontend)
* Backend on port 8080, frontend on port 5173 for full integration.
