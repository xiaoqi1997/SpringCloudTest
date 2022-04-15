package com.qhk.springcloud.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/zk80")
@Log4j2
public class OrderZkController {
    public String INVOKE_URL = "http://cloud-provider-payment/";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping
    public String paymentInfo() {
        return restTemplate.getForObject(INVOKE_URL + "zookeeper/payment/zk", String.class);
    }
}
