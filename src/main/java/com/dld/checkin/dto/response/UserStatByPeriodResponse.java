package com.dld.checkin.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserStatByPeriodResponse {

    private List<Map<String, Boolean>> stats;

}
