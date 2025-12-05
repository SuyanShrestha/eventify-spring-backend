-- RSVPS
CREATE TABLE IF NOT EXISTS rsvps (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'INTERESTED',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_rsvp_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_rsvp_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT unique_rsvp UNIQUE (user_id, event_id)
);

-- TICKETS
CREATE TABLE IF NOT EXISTS tickets (
    id SERIAL PRIMARY KEY,
    ticket_code VARCHAR(255) UNIQUE NOT NULL,
    event_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    purchase_date TIMESTAMP,
    quantity INTEGER,
    unit_price NUMERIC(10,2),
    total_price NUMERIC(10,2),
    status VARCHAR(10) NOT NULL DEFAULT 'RESERVED',
    created_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_ticket_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT fk_ticket_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- BOOKED TICKETS
CREATE TABLE IF NOT EXISTS booked_tickets (
    id SERIAL PRIMARY KEY,
    ticket_id INTEGER UNIQUE NOT NULL,
    qr_code_data VARCHAR(255) UNIQUE NOT NULL,
    qr_code_image VARCHAR(255),
    is_checked_in BOOLEAN DEFAULT FALSE,
    checked_in_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_booked_ticket FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE
);

-- PAYMENTS
CREATE TABLE IF NOT EXISTS payments (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    ticket_id INTEGER UNIQUE NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    transaction_id VARCHAR(100),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_payment_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_payment_ticket FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE
);

-- FEEDBACKS
CREATE TABLE IF NOT EXISTS feedbacks (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_feedback_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_feedback_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);

-- NOTIFICATIONS
CREATE TABLE IF NOT EXISTS notifications (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    event_id INTEGER,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_notification_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);
