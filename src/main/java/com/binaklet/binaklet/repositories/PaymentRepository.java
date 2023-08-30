package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
