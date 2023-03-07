package com.flexpag.paymentscheduler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexpag.paymentscheduler.models.PaymentModel;

public interface PaymentRepository extends JpaRepository<PaymentModel, Long>{
    
}
