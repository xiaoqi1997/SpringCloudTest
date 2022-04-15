package com.qhk.springcloud.service.impl;

import com.qhk.springcloud.dao.PaymentDao;
import com.qhk.springcloud.entities.Payment;
import com.qhk.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class PaymentServiceImpl  implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    public Payment getById(Long id) {
        return paymentDao.getById(id);
    }
}
