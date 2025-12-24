# Admin API Folder

This folder contains all API controllers and services related to administrative operations.

## Access Control

* Only users with **SUPERUSER** privileges can access the endpoints in this folder.
* Any request from users without SUPERUSER privileges will receive a **403 Forbidden** response.

## Included Controllers

* `AdminEventCategoryController`: Manage event categories (create, update, delete).
* `AdminEventController`: Manage events, including retrieving pending events and approving them.

## Notes

* Ensure proper authentication and role checks are in place before invoking any of these APIs.
* All responses and request fields follow snake_case conventions.
* This folder is intended for high-level administrative operations only.
