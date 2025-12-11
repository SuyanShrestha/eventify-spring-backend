package com.eventify.core.email;

import com.eventify.core.email.dto.EmailDTO;

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


}