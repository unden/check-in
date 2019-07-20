package com.dld.checkin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static List<Date> computeAllDateWithPeriod(String begin, String end) throws ParseException {
        List<Date> dates = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date beginDate = simpleDateFormat.parse(begin);
        Date endDate = simpleDateFormat.parse(end);
        calendar.setTime(beginDate);
        do {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } while (calendar.getTimeInMillis() <= endDate.getTime());
        return dates;
    }

    public static void main(String[] args) throws ParseException {
        List<Date> dates = computeAllDateWithPeriod("2019-07-01", "2019-08-05");
        for (Date date : dates) {
            System.out.println(date);
        }
    }

}
