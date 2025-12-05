package com.eventify.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.feedback.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long>{
    
}
