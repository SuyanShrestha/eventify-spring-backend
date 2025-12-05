CREATE TABLE event_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE events (
    id SERIAL PRIMARY KEY,

    organizer_id INTEGER NOT NULL,
    category_id INTEGER,

    banner VARCHAR(255),
    title VARCHAR(100) NOT NULL,
    subtitle VARCHAR(100),
    details TEXT,

    event_type VARCHAR(20) NOT NULL,
    venue VARCHAR(255),

    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    booking_deadline TIMESTAMP,

    total_tickets INTEGER,
    is_free BOOLEAN DEFAULT FALSE,
    ticket_price NUMERIC(10,2) DEFAULT 0.00,

    is_approved BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_event_user FOREIGN KEY (organizer_id)
        REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT fk_event_category FOREIGN KEY (category_id)
        REFERENCES event_categories(id) ON DELETE SET NULL,

    CONSTRAINT unique_event_per_organizer UNIQUE (organizer_id, title, start_date, event_type)
);


CREATE TABLE saved_events (
    id SERIAL PRIMARY KEY,

    user_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL,
    saved_at TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_saved_user FOREIGN KEY (user_id)
        REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT fk_saved_event FOREIGN KEY (event_id)
        REFERENCES events(id) ON DELETE CASCADE,

    CONSTRAINT unique_saved_event UNIQUE (user_id, event_id)
);
