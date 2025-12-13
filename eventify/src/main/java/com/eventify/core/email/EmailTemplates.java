package com.eventify.core.email;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.eventify.core.email.dto.EmailDTO;
import com.eventify.event.enums.EventMode;
import com.eventify.event.model.Event;

public class EmailTemplates {

    private EmailTemplates() {}

    // when event gets approved
    public static EmailDTO eventApproval(
            String organizerEmail,
            String organizerName,
            String eventTitle,
            String dashboardUrl
    ) {
        String subject = "🎉 Your Event '" + eventTitle + "' Has Been Approved!";

        String text = """
                Dear %s,

                We are pleased to inform you that your event '%s' has been approved. 🎉

                You can now proceed with the necessary arrangements to make your event successful.

                Best Regards,
                Eventify Team
                """.formatted(organizerName, eventTitle);

        String html = """
                <html>
                    <h2 style="color: #2E86C1;">🎉 Event Approved!</h2>
                    <p>Dear <strong>%s</strong>,</p>

                    <p>Your event <strong>%s</strong> has been approved! 🚀</p>

                    <p style="margin-top: 20px;">
                        <a href="%s"
                           style="background-color: #2E86C1; color: white; padding: 10px 20px;
                           text-decoration: none; border-radius: 5px; font-weight: bold;">
                            Manage Your Event
                        </a>
                    </p>

                    <p style="margin-top: 30px;">Best Regards,</p>
                    <p><strong>Eventify Team</strong></p>
                </html>
                """.formatted(organizerName, eventTitle, dashboardUrl);

        return EmailDTO.builder()
                .to(organizerEmail)
                .subject(subject)
                .textContent(text)
                .htmlContent(html)
                .build();
    }

    // when user registers
    public static EmailDTO welcomeEmail(
        String email,
        String username,
        String baseUrl
    ) {
        String subject = "Welcome to Eventify! 🎉";

        String text = """
                Dear %s,

                Welcome to Eventify! 🎉
                We are thrilled to have you on board.

                Best Regards,
                Eventify Team
                """.formatted(username);

        String html = """
                <html>
                    <h2 style="color:#2E86C1;">Welcome to Eventify, %s! 🎉</h2>
                    <p>Dear <strong>%s</strong>,</p>

                    <p>We are thrilled to have you on board.</p>

                    <p style="margin-top:20px;">
                        <a href="%s"
                        style="background-color:#2E86C1;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;font-weight:bold;">
                            Explore Now
                        </a>
                    </p>

                    <p style="margin-top:30px;">Best Regards,</p>
                    <p><strong>The Eventify Team</strong></p>
                </html>
                """.formatted(username, username, baseUrl);

        return EmailDTO.builder()
                .to(email)
                .subject(subject)
                .textContent(text)
                .htmlContent(html)
                .build();
    }


    // remind users about booked events
    public static EmailDTO eventReminder(
        String email,
        String username,
        String eventTitle,
        String startDate,
        String endDate,
        String time,
        String location,
        String rsvpMessage
    ) {
        String subject = "Reminder: Your Upcoming Event '" + eventTitle + "' 📅";

        String text = """
                Dear %s,

                This is a reminder for your upcoming event "%s".

                Start Date: %s
                End Date: %s
                Time: %s
                Location: %s

                %s

                Best Regards,
                Eventify Team
                """.formatted(username, eventTitle, startDate, endDate, time, location, rsvpMessage);

        String html = """
                <html>
                    <p>Dear %s,</p>
                    <p>This is a friendly reminder about your upcoming event <strong>%s</strong>.</p>

                    <ul>
                        <li><strong>Start:</strong> %s</li>
                        <li><strong>End:</strong> %s</li>
                        <li><strong>Time:</strong> %s</li>
                        <li><strong>Location:</strong> %s</li>
                    </ul>

                    <p>%s</p>

                    <p>Best Regards,<br><strong>Eventify Team</strong></p>
                </html>
                """.formatted(username, eventTitle, startDate, endDate, time, location, rsvpMessage);

        return EmailDTO.builder()
                .to(email)
                .subject(subject)
                .textContent(text)
                .htmlContent(html)
                .build();
    }


    // to notify users about their checkin
    public static EmailDTO checkInConfirmation(
        String email,
        String username,
        String eventTitle,
        String venue,
        String checkInTime,
        String eventUrl,
        String organizerName
    ) {
        String subject = "✅ Check-in Confirmed: " + eventTitle;

        String text = """
                Dear %s,

                Your check-in for "%s" is confirmed.

                Time: %s
                Venue: %s

                Enjoy the event!

                Regards,
                %s
                """.formatted(username, eventTitle, checkInTime, venue, organizerName);

        String html = """
                <html>
                    <h2 style="color:#2E86C1;">🎉 Check-in Successful!</h2>
                    <p>Dear <strong>%s</strong>,</p>
                    <p>Your check-in for <strong>%s</strong> was successful!</p>

                    <p>🕒 <strong>Time:</strong> %s</p>
                    <p>📍 <strong>Venue:</strong> %s</p>

                    <p style="margin-top:20px;">
                        <a href="%s"
                        style="background-color:#2E86C1;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;font-weight:bold;">
                            View Event Details
                        </a>
                    </p>

                    <p style="margin-top:30px;">Best Regards,<br><strong>%s</strong></p>
                </html>
                """.formatted(username, eventTitle, checkInTime, venue, eventUrl, organizerName);

        return EmailDTO.builder()
                .to(email)
                .subject(subject)
                .textContent(text)
                .htmlContent(html)
                .build();
    }


    // when event is updated
    public static EmailDTO eventUpdated(
        String email,
        String username,
        String eventTitle,
        String eventUrl,
        String organizerName
    ) {
        String subject = "Your Event '" + eventTitle + "' Has Been Updated! ✨";

        String text = """
                Dear %s,

                The event '%s' has been updated.
                View it here: %s

                Regards,
                %s
                """.formatted(username, eventTitle, eventUrl, organizerName);

        String html = """
                <html>
                    <h2 style="color:#E74C3C;">Your Event '%s' Has Been Updated! ✨</h2>
                    <p>Dear <strong>%s</strong>,</p>

                    <p>Your event has new updates. Please review them.</p>

                    <p style="margin-top:20px;">
                        <a href="%s"
                        style="background-color:#E74C3C;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;font-weight:bold;">
                            View Updated Event
                        </a>
                    </p>

                    <p style="margin-top:30px;">Best Regards,<br><strong>%s</strong></p>
                </html>
                """.formatted(eventTitle, username, eventUrl, organizerName);

        return EmailDTO.builder()
                .to(email)
                .subject(subject)
                .textContent(text)
                .htmlContent(html)
                .build();
    }

    // sending email invitations
    public static EmailDTO eventInvitation(
        String recipientEmail,
        Event event,
        String frontendBaseUrl
    ) {
        String subject = "📅 You're Invited: " + event.getTitle() + " by " + event.getOrganizer().getUsername();

        String eventUrl = frontendBaseUrl + "/events/" + event.getId();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy - hh:mm a");

        String formattedDate = event.getStartDate().format(formatter);

        String venueText = event.getEventType() == EventMode.PHYSICAL
                ? event.getVenue()
                : "Online Event";

        String detailsText =
                event.getDetails() != null && !event.getDetails().isBlank()
                        ? event.getDetails()
                        : "No additional details provided.";

        // ---- TEXT VERSION ----
        String textContent = """
                You're Invited: %s

                Hello,

                You have been invited to an event hosted by %s.

                📍 Venue: %s
                📅 Date & Time: %s
                ℹ️ Event Details: %s

                View event details here: %s
                """.formatted(
                event.getTitle(),
                event.getOrganizer().getUsername(),
                venueText,
                formattedDate,
                detailsText,
                eventUrl
        );

        // ---- HTML VERSION ----
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Event Invitation</title>
                    <style>
                        @media only screen and (max-width: 600px) {
                            .container {
                                width: 100%% !important;
                                padding: 20px !important;
                            }
                            .content {
                                padding: 20px !important;
                            }
                            .details-box {
                                padding: 20px !important;
                            }
                            h2 {
                                font-size: 20px !important;
                            }
                        }
                    </style>
                </head>
                <body style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: #333; background-color: #f9f9f9; padding: 20px; margin: 0;">
                    <div class="container" style="background-color: #ffffff; border-radius: 10px; padding: 30px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto;">
                        <h2 style="text-align: center; color: #1E90FF; font-size: 24px; font-weight: bold; margin-bottom: 25px;">You're Invited to %s 🎉</h2>

                        <p style="font-size: 16px; color: #555; line-height: 1.6;">
                            We are excited to invite you to the event <strong style="color: #1E90FF;">%s</strong> hosted by %s.
                            Please find the event details below.
                        </p>

                        <div class="details-box" style="margin: 25px 0; background-color: #f8fbff; padding: 25px; border-radius: 12px; border-left: 4px solid #1E90FF;">
                            <h3 style="font-size: 20px; color: #333; font-weight: bold; margin-top: 0; margin-bottom: 20px;">Event Details</h3>

                            <div style="margin-bottom: 20px; display: block;">
                                <p style="font-size: 16px; color: #1E90FF; margin: 10px 0; font-weight: bold;">📅 Event Date</p>
                                <p style="font-size: 16px; color: #555; margin: 5px 0; padding-left: 10px; border-left: 2px solid #e0e0e0;">%s</p>
                            </div>

                            <div style="margin-bottom: 20px; display: block;">
                                <p style="font-size: 16px; color: #1E90FF; margin: 10px 0; font-weight: bold;">📍 Venue</p>
                                <p style="font-size: 16px; color: #555; margin: 5px 0; padding-left: 10px; border-left: 2px solid #e0e0e0;">%s</p>
                            </div>

                            <div style="margin-bottom: 10px; display: block;">
                                <p style="font-size: 16px; color: #1E90FF; margin: 10px 0; font-weight: bold;">ℹ️ Event Details</p>
                                <p style="font-size: 16px; color: #555; margin: 5px 0; padding-left: 10px; border-left: 2px solid #e0e0e0;">%s</p>
                            </div>
                        </div>

                        <p style="font-size: 16px; color: #555; line-height: 1.6;">
                            We're excited to have you join us! For more details, click the button below.
                        </p>

                        <p style="font-size: 16px; color: #555; line-height: 1.6;">
                            See you at the event! 😊
                        </p>

                        <div style="text-align: center; margin-top: 30px;">
                            <a href="%s"
                            style="background-color: #1E90FF; color: #ffffff; text-decoration: none;
                                    display: inline-block; padding: 12px 30px; font-weight: 500;
                                    border-radius: 4px; font-size: 16px;">
                                View Event Details
                            </a>
                        </div>

                        <footer style="text-align: center; margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; font-size: 12px; color: #aaa;">
                            <p style="margin: 5px 0;">&copy; %d Eventify. All rights reserved.</p>
                        </footer>
                    </div>
                </body>
                </html>
                """.formatted(
                event.getTitle(),                    // 1st %s - header "You're Invited to..."
                event.getTitle(),                    // 2nd %s - "event <strong>..."
                event.getOrganizer().getUsername(),  // 3rd %s - "hosted by..."
                formattedDate,                       // 4th %s - Event Date
                venueText,                           // 5th %s - Venue
                detailsText,                         // 6th %s - Event Details
                eventUrl,                            // 7th %s - href in <a> tag
                LocalDate.now().getYear()            // 8th %d - copyright year
        );

        return EmailDTO.builder()
                .to(recipientEmail)
                .subject(subject)
                .textContent(textContent)
                .htmlContent(htmlContent)
                .build();
    }




}