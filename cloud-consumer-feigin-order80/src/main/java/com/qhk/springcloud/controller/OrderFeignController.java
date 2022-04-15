package com.qhk.springcloud.controller;

import com.qhk.springcloud.entities.CommonResult;
import com.qhk.springcloud.entities.Payment;
import com.qhk.springcloud.service.PaymentFeignService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Log4j2
@RequestMapping("/feign")
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable Long id) {
        return paymentFeignService.getById(id);
    }

    @GetMapping("/timeOut")
    public String timeOut() {
        // open-feign-ribbon 默认是1秒钟超时
        return paymentFeignService.paymentFeignTimeout();
    }
}
