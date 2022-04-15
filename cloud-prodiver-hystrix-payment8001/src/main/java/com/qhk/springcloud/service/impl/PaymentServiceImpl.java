package com.qhk.springcloud.service.impl;

import com.qhk.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_OK, id：" + id;
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        int timeout = 3;
        try{
            TimeUnit.SECONDS.sleep(timeout);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() +
                "paymentInfo_Timeout," +
                " id：" + id +
                " 耗时(秒)" + timeout;
    }
}
