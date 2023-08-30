package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService extends BaseService<Payment,Long>{
    public PaymentService(JpaRepository<Payment, Long> repository) {
        super(repository);
    }
}
