package com.eventify.core.email;

import com.eventify.core.email.dto.EmailDTO;

public class EmailTemplates {

    private EmailTemplates() {}

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
}