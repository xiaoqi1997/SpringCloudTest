package com.qhk.springcloud.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Log4j2
@RestController
@RequestMapping("/consul")
public class OrderController {
    @Resource
    private RestTemplate restTemplate;

    private static final String SERVICE_URL = "http://consul-provider-payment/";

    @GetMapping
    public Object getConsul() {
        return restTemplate.getForObject(SERVICE_URL + "/consul/payment/consul", String.class);
    }
}
