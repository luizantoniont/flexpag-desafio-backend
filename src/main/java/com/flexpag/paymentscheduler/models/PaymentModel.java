package com.flexpag.paymentscheduler.models;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PAYMENT")
public class PaymentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Instant date;
    
    private Double payment;

    private Integer paymentStatus;
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }
    public void setDate(Instant date) {
        this.date = date;
    }
    public Double getPayment() {
        return payment;
    }
    public PaymentStatus getPaymentStatus() {
        return PaymentStatus.valueOf(paymentStatus);
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus.getCodeStatus();
    }
    public PaymentModel(){
        setPaymentStatus(PaymentStatus.PENDING);
    }
    public PaymentModel(Instant date, Double payment){
        this.date = date;
        this.payment = payment;
        setPaymentStatus(PaymentStatus.PENDING);
    }
    
}