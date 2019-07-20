package com.dld.checkin.service;

import com.dld.checkin.dto.response.StatByPeriodResponse;
import com.dld.checkin.dto.response.UserStatByPeriodResponse;

public interface CheckInService {

    boolean isCheckedIn(Long id, String date);

    long updateContinuousCheckIn(Long id);

    long getContinuousCheckIn(Long userId);

    StatByPeriodResponse statByPeriod(String begin, String end);

    UserStatByPeriodResponse userStatByPeriod(Long id, String begin, String end);
}
