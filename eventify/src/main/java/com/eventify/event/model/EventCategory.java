package com.eventify.event.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "event_categories")
public class EventCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;
}