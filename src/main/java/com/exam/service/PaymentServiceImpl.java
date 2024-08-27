package com.exam.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.entity.Payment;
import com.exam.repository.PaymentRepository;
import com.exam.repository.StockRepository;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{
	
	PaymentRepository paymentRepository;
    private final ModelMapper mapper;  // ModelMapper를 클래스 레벨 필드로 선언

	public PaymentServiceImpl(PaymentRepository paymentRepository, ModelMapper mapper) {
		this.paymentRepository = paymentRepository;
		this.mapper = mapper;
	}

	@Override
	public Payment savePayment(Payment payment) {
		return paymentRepository.save(payment);
	}

}//end

