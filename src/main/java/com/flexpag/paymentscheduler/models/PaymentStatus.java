package com.flexpag.paymentscheduler.models;

public enum PaymentStatus {

    PENDING(1),
    PAID(2);

    private int codeStatus;

    private PaymentStatus(int codeStatus){
        this.codeStatus = codeStatus;
    }
    public int getCodeStatus() {
        return codeStatus;
    }

    public static PaymentStatus valueOf(int codeStatus){
        for(PaymentStatus value : PaymentStatus.values()){
            if(value.getCodeStatus() == codeStatus){
                return value;
            }
        }
        throw new IllegalArgumentException("Código inválido.");
    }

}
