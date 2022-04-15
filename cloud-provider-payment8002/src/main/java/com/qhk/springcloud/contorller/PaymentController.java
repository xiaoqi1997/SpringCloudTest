package com.qhk.springcloud.contorller;

import com.qhk.springcloud.entities.CommonResult;
import com.qhk.springcloud.entities.Payment;
import com.qhk.springcloud.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "payment")
@Log4j2
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

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
}
