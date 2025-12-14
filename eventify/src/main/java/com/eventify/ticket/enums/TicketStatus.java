package com.eventify.ticket.enums;

public enum TicketStatus {
    RESERVED,   // User selected the ticket, but Stripe is still processing the payment
    PAID,       // Stripe success, ticket paid
    CANCELLED   // Stripe fails
}