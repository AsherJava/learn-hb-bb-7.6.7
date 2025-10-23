/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.schedule.common;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class ProcessScheduleDateUtil {
    private ProcessScheduleDateUtil() {
    }

    public static Date[] fixTimeRange(Date startDate, Date endDate) {
        ZonedDateTime end;
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate \u548c endDate \u90fd\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime start = startDate.toInstant().atZone(zone);
        if (start.equals(end = endDate.toInstant().atZone(zone))) {
            end = start.plusHours(24L);
        }
        return new Date[]{Date.from(start.toInstant()), Date.from(end.toInstant())};
    }

    public static Date dateModifyWithTime(Date originalDate, LocalTime targetTime) {
        if (originalDate == null || targetTime == null) {
            throw new IllegalArgumentException("originalDate \u548c targetTime \u90fd\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = originalDate.toInstant();
        LocalDate originalLocalDate = instant.atZone(zone).toLocalDate();
        LocalDateTime combinedDateTime = LocalDateTime.of(originalLocalDate, targetTime);
        return Date.from(combinedDateTime.atZone(zone).toInstant());
    }

    public static Date dateOffsetNaturalDaysWithTime(Date originalDate, int offsetDays, LocalTime targetTime) {
        if (originalDate == null || targetTime == null) {
            throw new IllegalArgumentException("originalDate \u548c targetTime \u90fd\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = originalDate.toInstant();
        LocalDate originalLocalDate = instant.atZone(zone).toLocalDate();
        LocalDate adjustedDate = originalLocalDate.plusDays(offsetDays);
        LocalDateTime combinedDateTime = LocalDateTime.of(adjustedDate, targetTime);
        return Date.from(combinedDateTime.atZone(zone).toInstant());
    }

    public static Date dateOffsetBusinessDaysWithTime(Date originalDate, int offsetDays, LocalTime targetTime) {
        if (originalDate == null || targetTime == null) {
            throw new IllegalArgumentException("originalDate \u548c targetTime \u90fd\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = originalDate.toInstant();
        LocalDate originalLocalDate = instant.atZone(zone).toLocalDate();
        LocalDate adjustedDate = ProcessScheduleDateUtil.dateOffsetBusinessDays(originalLocalDate, offsetDays);
        LocalDateTime combinedDateTime = LocalDateTime.of(adjustedDate, targetTime);
        return Date.from(combinedDateTime.atZone(zone).toInstant());
    }

    public static LocalDate dateOffsetBusinessDays(LocalDate date, int offsetDays) {
        int absDays = Math.abs(offsetDays);
        int direction = offsetDays >= 0 ? 1 : -1;
        LocalDate result = date;
        while (absDays > 0) {
            DayOfWeek day = (result = result.plusDays(direction)).getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) continue;
            --absDays;
        }
        return result;
    }

    public static boolean isNowInRange(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate \u548c endDate \u90fd\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        ZonedDateTime start = ZonedDateTime.ofInstant(startDate.toInstant(), zoneId);
        ZonedDateTime end = ZonedDateTime.ofInstant(endDate.toInstant(), zoneId);
        if (now.isEqual(start) || now.isEqual(end)) {
            return true;
        }
        return !now.isBefore(start) && !now.isAfter(end);
    }

    public static boolean isNowAfter(Date targetDate) {
        if (targetDate == null) {
            throw new IllegalArgumentException("targetDate \u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        ZonedDateTime target = ZonedDateTime.ofInstant(targetDate.toInstant(), zoneId);
        return now.isAfter(target);
    }

    public static boolean isNowBefore(Date targetDate) {
        if (targetDate == null) {
            throw new IllegalArgumentException("targetDate \u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        ZonedDateTime target = ZonedDateTime.ofInstant(targetDate.toInstant(), zoneId);
        return now.isBefore(target);
    }

    public static Date[] getDateRangeFromNow(int offsetYears) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate now = LocalDate.now(zoneId);
        LocalDate target = now.plusYears(offsetYears);
        ZonedDateTime startZoned = now.atStartOfDay(zoneId);
        ZonedDateTime endZoned = target.atStartOfDay(zoneId);
        Date startDate = Date.from(startZoned.toInstant());
        Date endDate = Date.from(endZoned.toInstant());
        return new Date[]{startDate, endDate};
    }
}

