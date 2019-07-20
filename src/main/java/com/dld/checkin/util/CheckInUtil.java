package com.dld.checkin.util;

public class CheckInUtil {

    private static final String CHECK_IN_PRE_KEY = "USER_CHECK_IN::DAY::";

    private static final String CONTINUOUS_CHECK_IN_COUNT_PRE_KEY = "USER_CHECK_IN::CONTINUOUS_COUNT::";

    public static String getCheckInKey(String date) {
        return CHECK_IN_PRE_KEY + date;
    }

    public static String getContinuousCheckInKey(Long userId) {
        return CONTINUOUS_CHECK_IN_COUNT_PRE_KEY + userId;
    }

}
