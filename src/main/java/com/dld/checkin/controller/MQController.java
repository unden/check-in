package com.dld.checkin.controller;

import com.alibaba.fastjson.JSON;
import com.dld.checkin.dto.request.IsCheckedInRequest;
import com.dld.checkin.util.ResponseUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mq")
public class MQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseUtil send() {
        IsCheckedInRequest isCheckedInRequest = new IsCheckedInRequest();
        isCheckedInRequest.setId(1L);
        isCheckedInRequest.setDate("2019-07-01");
        rabbitTemplate.convertAndSend("unden", JSON.toJSONString(isCheckedInRequest));
        return ResponseUtil.success();
    }

}
