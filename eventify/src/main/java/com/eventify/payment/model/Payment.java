package com.eventify.payment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.eventify.payment.enums.PaymentStatus;
import com.eventify.ticket.model.Ticket;
import com.eventify.user.model.User;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(optional = false)
    @JoinColumn(name = "ticket_id", unique = true)
    private Ticket ticket;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(length = 100)
    private String transactionId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
