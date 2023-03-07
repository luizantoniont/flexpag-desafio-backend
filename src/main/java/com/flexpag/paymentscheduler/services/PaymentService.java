package com.flexpag.paymentscheduler.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flexpag.paymentscheduler.models.PaymentModel;
import com.flexpag.paymentscheduler.repositories.PaymentRepository;

@Service
public class PaymentService {

    final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentModel save(PaymentModel paymentModel) {
        return paymentRepository.save(paymentModel);
    }

    public Optional<PaymentModel> findById(Long id) {
        return paymentRepository.findById(id);
    }
    public Page<PaymentModel> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }
    @Transactional
    public void delete(PaymentModel paymentScheduleModel) {
        paymentRepository.delete(paymentScheduleModel);
    }
}