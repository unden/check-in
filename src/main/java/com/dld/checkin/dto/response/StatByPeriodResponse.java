package com.dld.checkin.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StatByPeriodResponse {

    private List<Map<String, Long>> stats;

}
