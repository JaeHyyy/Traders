package com.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String>{
	

}
