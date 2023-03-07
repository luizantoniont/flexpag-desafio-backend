package com.flexpag.paymentscheduler.controllers;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexpag.paymentscheduler.models.PaymentModel;
import com.flexpag.paymentscheduler.models.PaymentStatus;
import com.flexpag.paymentscheduler.services.PaymentService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/payment-schedule")
public class PaymentController {
    
    final PaymentService paymentService; 

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody PaymentModel paymentModel) {
        if (paymentModel.getDate().equals(Instant.now()) || paymentModel.getDate().isBefore(Instant.now())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A data do agendamento deve ser posterior a data atual.");
        }
        paymentModel.setPaymentStatus(PaymentStatus.PENDING);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.save(paymentModel));    
    }

    @GetMapping
    public ResponseEntity<Page<PaymentModel>> getAllPaymentSchedule(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePaymentSchedule(@PathVariable(value = "id") Long id){
        Optional<PaymentModel> paymentModelOptional = paymentService.findById(id);
        if (!paymentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado.");
        }
        else if(paymentModelOptional.get().getDate().equals(Instant.now()) || paymentModelOptional.get().getDate().isBefore(Instant.now())) {
            paymentModelOptional.get().setPaymentStatus(PaymentStatus.PAID);
            paymentService.save(paymentModelOptional.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(paymentModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatedPayment(@PathVariable(value = "id") Long id, @RequestBody PaymentModel paymentModel){
        Optional<PaymentModel> paymentModelOptional = paymentService.findById(id);

        if (!paymentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado.");
        }

        else if(paymentModelOptional.get().getDate().equals(Instant.now()) ||
                paymentModelOptional.get().getDate().isBefore(Instant.now())) {
            paymentModelOptional.get().setPaymentStatus(PaymentStatus.PAID);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Pagamento já foi efetuado.");
        }

        var updatedPaymentSchedule = new PaymentModel();
        BeanUtils.copyProperties(paymentModel, updatedPaymentSchedule);
        updatedPaymentSchedule.setId(paymentModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.save(updatedPaymentSchedule));
    } 
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePaymentSchedule(@PathVariable(value = "id") Long id){
        Optional<PaymentModel> paymentModelOptional = paymentService.findById(id);
        if (!paymentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado.");
        }
        paymentService.delete(paymentModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Agendamento cancelado com sucesso.");
    }
        
}
 



