package com.eventify.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.event.model.EventCategory;

@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory,Integer>{
    
    boolean existsByNameIgnoreCase(String name);

}
