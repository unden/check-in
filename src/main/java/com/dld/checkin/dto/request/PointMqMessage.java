package com.dld.checkin.dto.request;

import lombok.Data;

@Data
public class PointMqMessage {

    private long id;

    private long continuousCheckInDays;

}
