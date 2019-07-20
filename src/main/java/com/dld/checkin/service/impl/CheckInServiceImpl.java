package com.dld.checkin.service.impl;

import com.dld.checkin.dto.response.StatByPeriodResponse;
import com.dld.checkin.dto.response.UserStatByPeriodResponse;
import com.dld.checkin.service.CheckInService;
import com.dld.checkin.util.CheckInUtil;
import com.dld.checkin.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class CheckInServiceImpl implements CheckInService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean isCheckedIn(Long id, String date) {
        Boolean isCheckIn =
                stringRedisTemplate.opsForValue().getBit(
                        CheckInUtil.getCheckInKey(date), id);
        return Optional.ofNullable(isCheckIn).orElse(false);
    }

    @Override
    public long updateContinuousCheckIn(Long id) {
        String key = CheckInUtil.getContinuousCheckInKey(id);
        Long continuousCheckInDays = stringRedisTemplate.opsForValue().increment(key);
        // 设置n+2天的零点过期
        LocalDateTime dateTime = LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).withSecond(0);
        stringRedisTemplate.expireAt(key, Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
        return continuousCheckInDays;
    }

    @Override
    public long getContinuousCheckIn(Long userId) {
        String key = CheckInUtil.getContinuousCheckInKey(userId);
        String continuousCheckInDays = stringRedisTemplate.opsForValue().get(key);
        if (continuousCheckInDays == null) {
            return 0L;
        }
        return Long.parseLong(continuousCheckInDays);
    }

    @Override
    public StatByPeriodResponse statByPeriod(String begin, String end) {
        List<Date> dates;
        try {
            dates = DateUtil.computeAllDateWithPeriod(begin, end);
        } catch (ParseException e) {
            return null;
        }
        List<Map<String, Long>> stats = new ArrayList<>();
        for (Date date : dates) {
            String dateStr = DateUtil.format(date, "yyyy-MM-dd");
            byte[] dateBytes = CheckInUtil.getCheckInKey(dateStr).getBytes();
            Long count = stringRedisTemplate.execute(new RedisCallback<Long>() {
                @Nullable
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.bitCount(dateBytes);
                }
            });
            Map<String, Long> statMap = new HashMap<>();
            statMap.put(dateStr, count);
            stats.add(statMap);
        }
        StatByPeriodResponse statByPeriodResponse = new StatByPeriodResponse();
        statByPeriodResponse.setStats(stats);
        return statByPeriodResponse;
    }

    @Override
    public UserStatByPeriodResponse userStatByPeriod(Long id, String begin, String end) {
        List<Date> dates;
        try {
            dates = DateUtil.computeAllDateWithPeriod(begin, end);
        } catch (ParseException e) {
            return null;
        }
        List<Map<String, Boolean>> stats = new ArrayList<>();
        for (Date date : dates) {
            String dateStr = DateUtil.format(date, "yyyy-MM-dd");
            boolean checkedIn = isCheckedIn(id, dateStr);
            Map<String, Boolean> statMap = new HashMap<>();
            statMap.put(dateStr, checkedIn);
            stats.add(statMap);
        }
        UserStatByPeriodResponse userStatByPeriodResponse = new UserStatByPeriodResponse();
        userStatByPeriodResponse.setStats(stats);
        return userStatByPeriodResponse;
    }
}
