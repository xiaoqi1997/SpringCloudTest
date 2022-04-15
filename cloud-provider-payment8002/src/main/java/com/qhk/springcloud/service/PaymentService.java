package com.qhk.springcloud.service;

import com.qhk.springcloud.entities.Payment;

public interface PaymentService {
    int create(Payment payment);

    Payment getById(Long id);
}
