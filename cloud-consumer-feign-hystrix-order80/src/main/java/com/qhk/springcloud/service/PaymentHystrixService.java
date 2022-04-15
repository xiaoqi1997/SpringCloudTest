package com.qhk.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CLOUD-PROVIDER-HYSTRIX-PAYMENT\t")
@Service
public interface PaymentHystrixService {
    @GetMapping("/getInfo/{id}")
    public String getInfo (@PathVariable("id") Integer id);
}
