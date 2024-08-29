package com.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.exam.entity.Payment;
import com.exam.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping("/{branchId}")
	public Payment createPayment(@RequestBody Payment payment) {
		return paymentService.savePayment(payment);
	}


}
