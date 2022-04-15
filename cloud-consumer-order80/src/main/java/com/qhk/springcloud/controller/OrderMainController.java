package com.qhk.springcloud.controller;

import com.qhk.springcloud.entities.CommonResult;
import com.qhk.springcloud.entities.Payment;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RequestMapping("/consumer")
@RestController
@Log4j2
public class OrderMainController {

//    private static final String PAYMENT_URL = "http://localhost:8001/payment/";
    // CLOUD-PAYMENT-SERVICE 为微服务注册名称，即下方定义的名称
    // spring:
        //  application:
        //    name: cloud-order-service
    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE/payment/";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL, payment, CommonResult.class);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getById(@PathVariable Long id) {
        return restTemplate.getForObject(PAYMENT_URL + id,  CommonResult.class);
    }
}
