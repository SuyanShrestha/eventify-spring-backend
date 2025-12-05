package com.eventify.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventify.payment.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{
    
}