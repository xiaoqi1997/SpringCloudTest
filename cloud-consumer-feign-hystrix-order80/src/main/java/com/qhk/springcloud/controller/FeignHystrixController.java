package com.qhk.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.qhk.springcloud.service.PaymentHystrixService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Log4j2
@RequestMapping("feignHystrix")
@DefaultProperties(defaultFallback = "getTimeout")
public class FeignHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/{id}")
    public String getInfo(@PathVariable Integer id) {
        return paymentHystrixService.getInfo(id);
    }
    @GetMapping("/getTimeout/{id}")
    @HystrixCommand(fallbackMethod = "", commandProperties = {
            @HystrixProperty(name="", value = "1500")
    })
    public String getTimeout(@PathVariable Integer id) {
        return "错误";
    }

}
