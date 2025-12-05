-- USERS
INSERT INTO users (username, email, password, first_name, last_name, role, is_active, created_at, updated_at, last_login)
VALUES
('suyan', 'suyan@gmail.com', '$2a$10$JjRV7We55uwp.nZ.y1HaTeGX2iVID/51irl398twERZpwSnguua52', 'Suyan', 'Shrestha', 'USER', true, NOW(), NOW(), NOW()),
('alice', 'alice@example.com', '$2a$10$JjRV7We55uwp.nZ.y1HaTeGX2iVID/51irl398twERZpwSnguua52', 'Alice', 'Wonder', 'USER', true, NOW(), NOW(), NOW()),
('bob', 'bob@example.com', '$2a$10$JjRV7We55uwp.nZ.y1HaTeGX2iVID/51irl398twERZpwSnguua52', 'Bob', 'Builder', 'ORGANIZER', true, NOW(), NOW(), NOW()),
('charlie', 'charlie@example.com', '$2a$10$JjRV7We55uwp.nZ.y1HaTeGX2iVID/51irl398twERZpwSnguua52', 'Charlie', 'Brown', 'USER', true, NOW(), NOW(), NOW()),
('david', 'david@example.com', '$2a$10$JjRV7We55uwp.nZ.y1HaTeGX2iVID/51irl398twERZpwSnguua52', 'David', 'Smith', 'USER', true, NOW(), NOW(), NOW()),
('emma', 'emma@example.com', '$2a$10$JjRV7We55uwp.nZ.y1HaTeGX2iVID/51irl398twERZpwSnguua52', 'Emma', 'Stone', 'USER', true, NOW(), NOW(), NOW()),
('frank', 'frank@example.com', '$2a$10$JjRV7We55uwp.nZ.y1HaTeGX2iVID/51irl398twERZpwSnguua52', 'Frank', 'Castle', 'ORGANIZER', true, NOW(), NOW(), NOW()),
('grace', 'grace@example.com', '$2a$10$JjRV7We55uwp.nZ.y1HaTeGX2iVID/51irl398twERZpwSnguua52', 'Grace', 'Hopper', 'USER', true, NOW(), NOW(), NOW());

-- EVENT CATEGORIES
INSERT INTO event_categories (name)
VALUES
('Concerts'),
('Workshops'),
('Meetups'),
('Seminars'),
('Webinars');

-- EVENTS (10)
INSERT INTO events (organizer_id, category_id, banner, title, subtitle, details, event_type, venue, start_date, end_date, booking_deadline, total_tickets, is_free, ticket_price, is_approved, created_at, updated_at)
VALUES
(2, 1, 'banner1.jpg', 'Rock Concert', 'Live in City', 'Best rock concert ever', 'REMOTE', 'City Hall', NOW() + INTERVAL '5 days', NOW() + INTERVAL '6 days', NOW() + INTERVAL '4 days', 100, false, 50.00, true, NOW(), NOW()),
(2, 2, 'banner2.jpg', 'Painting Workshop', 'Learn to paint', 'Workshop for beginners', 'PHYSICAL', 'Art Center', NOW() + INTERVAL '10 days', NOW() + INTERVAL '10 days', NOW() + INTERVAL '8 days', 30, true, 0.00, true, NOW(), NOW()),
(6, 3, 'banner3.jpg', 'Tech Meetup', 'Networking Night', 'Meet tech enthusiasts', 'REMOTE', 'Tech Hub', NOW() + INTERVAL '7 days', NOW() + INTERVAL '7 days', NOW() + INTERVAL '5 days', 50, true, 0.00, true, NOW(), NOW()),
(6, 4, 'banner4.jpg', 'Business Seminar', 'Grow your Startup', 'Seminar with industry leaders', 'PHYSICAL', 'Convention Center', NOW() + INTERVAL '12 days', NOW() + INTERVAL '12 days', NOW() + INTERVAL '10 days', 80, false, 20.00, true, NOW(), NOW()),
(2, 5, 'banner5.jpg', 'Webinar on AI', 'Learn AI', 'Online webinar', 'REMOTE', 'Zoom', NOW() + INTERVAL '3 days', NOW() + INTERVAL '3 days', NOW() + INTERVAL '2 days', 200, true, 0.00, true, NOW(), NOW()),
(2, 1, 'banner6.jpg', 'Jazz Night', 'Smooth Jazz', 'Evening of jazz music', 'PHYSICAL', 'Jazz Club', NOW() + INTERVAL '8 days', NOW() + INTERVAL '8 days', NOW() + INTERVAL '7 days', 60, false, 35.00, true, NOW(), NOW()),
(6, 2, 'banner7.jpg', 'Cooking Workshop', 'Culinary Skills', 'Learn new recipes', 'PHYSICAL', 'Culinary Center', NOW() + INTERVAL '9 days', NOW() + INTERVAL '9 days', NOW() + INTERVAL '8 days', 40, false, 15.00, true, NOW(), NOW()),
(2, 3, 'banner8.jpg', 'Tech Talk', 'Latest in Tech', 'Discussion with experts', 'REMOTE', 'Tech Hub', NOW() + INTERVAL '11 days', NOW() + INTERVAL '11 days', NOW() + INTERVAL '9 days', 70, true, 0.00, true, NOW(), NOW()),
(6, 4, 'banner9.jpg', 'Startup Seminar', 'Funding & Growth', 'Seminar for entrepreneurs', 'PHYSICAL', 'Convention Center', NOW() + INTERVAL '15 days', NOW() + INTERVAL '15 days', NOW() + INTERVAL '13 days', 50, false, 25.00, true, NOW(), NOW()),
(2, 5, 'banner10.jpg', 'AI Webinar Part 2', 'Deep Learning', 'Advanced webinar', 'REMOTE', 'Zoom', NOW() + INTERVAL '18 days', NOW() + INTERVAL '18 days', NOW() + INTERVAL '16 days', 150, true, 0.00, true, NOW(), NOW());

-- SAVED EVENTS (7)
INSERT INTO saved_events (user_id, event_id, saved_at)
VALUES
(1, 1, NOW()),
(1, 2, NOW()),
(3, 1, NOW()),
(4, 3, NOW()),
(5, 4, NOW()),
(1, 5, NOW()),
(7, 6, NOW());

-- RSVPS (7)
INSERT INTO rsvps (user_id, event_id, status, created_at, updated_at)
VALUES
(1,1,'GOING',NOW(),NOW()),
(3,1,'INTERESTED',NOW(),NOW()),
(1,2,'GOING',NOW(),NOW()),
(4,3,'GOING',NOW(),NOW()),
(5,4,'NOT_INTERESTED',NOW(),NOW()),
(6,5,'GOING',NOW(),NOW()),
(7,6,'GOING',NOW(),NOW());

-- TICKETS (7)
INSERT INTO tickets (ticket_code, event_id, user_id, purchase_date, quantity, unit_price, total_price, status)
VALUES
('TICKET001',1,1,NOW(),2,50.00,100.00,'RESERVED'),
('TICKET002',1,3,NOW(),1,50.00,50.00,'PAID'),
('TICKET003',2,1,NOW(),1,0.00,0.00,'CANCELLED'),
('TICKET004',3,4,NOW(),3,0.00,0.00,'RESERVED'),
('TICKET005',4,5,NOW(),1,20.00,20.00,'PAID'),
('TICKET006',5,6,NOW(),1,0.00,0.00,'CANCELLED'),
('TICKET007',6,7,NOW(),2,35.00,70.00,'PAID');

-- BOOKED TICKETS (5)
INSERT INTO booked_tickets (ticket_id, qr_code_data, qr_code_image, is_checked_in, checked_in_time)
VALUES
(1,'QR001','qr1.png',false,NULL),
(2,'QR002','qr2.png',true,NOW()),
(3,'QR003','qr3.png',false,NULL),
(4,'QR004','qr4.png',false,NULL),
(5,'QR005','qr5.png',true,NOW());

-- PAYMENTS (5)
INSERT INTO payments (user_id, ticket_id, amount, transaction_id, status, created_at, updated_at)
VALUES
(3,2,50.00,'TXN123','COMPLETED',NOW(),NOW()),
(5,5,20.00,'TXN124','FAILED',NOW(),NOW()),
(7,7,70.00,'TXN125','CANCELLED',NOW(),NOW()),
(6,6,0.00,'TXN126','PENDING',NOW(),NOW()),
(1,1,100.00,'TXN127','PENDING',NOW(),NOW());

-- FEEDBACKS (5)
INSERT INTO feedbacks (user_id, event_id, message, created_at)
VALUES
(1,1,'Amazing event, loved it!',NOW()),
(3,1,'Looking forward to next time!',NOW()),
(4,3,'Very informative.',NOW()),
(5,4,'Great session!',NOW()),
(7,6,'Enjoyed it!',NOW());

-- NOTIFICATIONS (7)
INSERT INTO notifications (user_id, event_id, message, is_read, created_at)
VALUES
(1,1,'Your RSVP has been confirmed!',false,NOW()),
(3,1,'Event starts in 3 days!',false,NOW()),
(4,3,'Ticket purchased successfully',false,NOW()),
(5,4,'Reminder: Event tomorrow',false,NOW()),
(6,5,'Your booking is confirmed',false,NOW()),
(7,6,'RSVP accepted',false,NOW()),
(1,2,'Saved event reminder',false,NOW());
