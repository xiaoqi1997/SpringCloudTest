package com.qhk.springcloud;

import com.qhk.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
public class CloudConsumerOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(CloudConsumerOrder80.class, args);
    }
}
