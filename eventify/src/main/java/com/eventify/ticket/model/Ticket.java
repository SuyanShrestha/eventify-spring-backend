package com.eventify.ticket.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventify.event.model.Event;
import com.eventify.payment.model.Payment;
import com.eventify.ticket.enums.TicketStatus;
import com.eventify.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String ticketCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime purchaseDate;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private TicketStatus status = TicketStatus.RESERVED;

    @OneToOne(mappedBy = "ticket")
    private BookedTicket bookedTicket;

    @OneToOne(mappedBy = "ticket")
    private Payment payment;



    // ===========================
    // Bidirectional Relationships
    // ===========================

    // @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private Payment payment;

    // @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private BookedTicket bookedTicket;
}
