package com.qhk.springcloud.service;

public interface PaymentService {
    String paymentInfo_OK(Integer id);
    String paymentInfo_Timeout(Integer id);
}
