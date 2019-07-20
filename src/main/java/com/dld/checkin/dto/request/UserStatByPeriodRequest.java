package com.dld.checkin.dto.request;

import lombok.Data;

@Data
public class UserStatByPeriodRequest {

    private Long id;

    private String begin;

    private String end;

}
