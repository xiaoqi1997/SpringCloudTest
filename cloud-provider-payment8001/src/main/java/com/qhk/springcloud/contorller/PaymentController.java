package com.qhk.springcloud.contorller;

import ch.qos.logback.core.util.TimeUtil;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.converters.Auto;
import com.qhk.springcloud.entities.CommonResult;
import com.qhk.springcloud.entities.Payment;
import com.qhk.springcloud.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "payment")
@Log4j2
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;


    @PostMapping
    public CommonResult<Integer> create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        if (result > 0) {
            return new CommonResult<>(200, "插入数据库成功+serverPort" + serverPort, result);
        }
        return new CommonResult<>(400, "插入数据库失败");
    }

    @GetMapping(value = "/{id}")
    public CommonResult<Payment> getById(@PathVariable Long id) {
        Payment payment = paymentService.getById(id);
        if (payment == null) {
            return new CommonResult<>(500, "无此数据");
        }

        return new CommonResult<>(200, "查询成功 + serverPort" + serverPort, payment);
    }

    @GetMapping("/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for(String element: services) {
            log.info("element: " + element);
        }
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance info: serviceInstances) {
            log.info(info.getServiceId() + "\t" + info.getHost() + "\t" + info.getPort());
        }
        return this.discoveryClient;
    }

    @GetMapping("/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
