package com.dld.checkin.controller;

import com.alibaba.fastjson.JSON;
import com.dld.checkin.constant.DateConstant;
import com.dld.checkin.dto.request.*;
import com.dld.checkin.dto.response.StatByPeriodResponse;
import com.dld.checkin.dto.response.UserStatByPeriodResponse;
import com.dld.checkin.service.CheckInService;
import com.dld.checkin.util.CheckInUtil;
import com.dld.checkin.util.ResponseEnum;
import com.dld.checkin.util.ResponseUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("check-in")
public class CheckInController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseUtil checkIn(@RequestBody CheckInRequest checkInRequest) {
        String today = LocalDate.now().format(DateConstant.DATE_TIME_FORMATTER);
        if (checkInService.isCheckedIn(checkInRequest.getId(), today)) {
            return ResponseUtil.fail(ResponseEnum.HAS_CHECKED_IN, null);
        }
        stringRedisTemplate.opsForValue().setBit(CheckInUtil.getCheckInKey(today), checkInRequest.getId(), true);
        long continuousCheckInDays = checkInService.updateContinuousCheckIn(checkInRequest.getId());

        // 发送MQ消息
        PointMqMessage pointMqMessage = new PointMqMessage();
        pointMqMessage.setId(checkInRequest.getId());
        pointMqMessage.setContinuousCheckInDays(continuousCheckInDays);
        rabbitTemplate.convertAndSend("check-in-topic", "", JSON.toJSONString(pointMqMessage));
        return ResponseUtil.success(continuousCheckInDays);
    }

    @PostMapping("is-checked")
    public ResponseUtil isCheckedIn(@RequestBody IsCheckedInRequest isCheckedInRequest) {
        return ResponseUtil.success(checkInService.isCheckedIn(isCheckedInRequest.getId(),
                isCheckedInRequest.getDate()));
    }

    @PostMapping("stat")
    public ResponseUtil statByPeriod(@RequestBody StatByPeriodRequest statByPeriodRequest) {
        StatByPeriodResponse statByPeriodResponse = checkInService.statByPeriod(statByPeriodRequest.getBegin(),
                statByPeriodRequest.getEnd());
        return ResponseUtil.success(statByPeriodResponse);
    }

    @PostMapping("stat/by-user")
    public ResponseUtil statByPeriod(@RequestBody UserStatByPeriodRequest userStatByPeriodRequest) {
        UserStatByPeriodResponse userStatByPeriodResponse =
                checkInService.userStatByPeriod(userStatByPeriodRequest.getId(),
                        userStatByPeriodRequest.getBegin(),
                        userStatByPeriodRequest.getEnd());
        return ResponseUtil.success(userStatByPeriodResponse);
    }

}
