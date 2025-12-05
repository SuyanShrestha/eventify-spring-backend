package com.eventify.ticket.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "booked_tickets")
public class BookedTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "ticket_id", unique = true)
    private Ticket ticket;

    @Column(length = 255, unique = true, nullable = false)
    private String qrCodeData;

    private String qrCodeImage;

    @Builder.Default
    private Boolean isCheckedIn = false;

    private LocalDateTime checkedInTime;

    // if checkedInTime wasnt manually set, it will be initialized here before Hibernate inserts it
    @PrePersist
    @PreUpdate
    public void handleCheckInTimestamp() {
        if (isCheckedIn && checkedInTime == null) {
            checkedInTime = LocalDateTime.now();
        }
    }
}
