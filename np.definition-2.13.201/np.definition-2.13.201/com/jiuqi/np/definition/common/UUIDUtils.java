/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.uuid.EthernetAddress
 *  com.fasterxml.uuid.Generators
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.np.definition.common;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.exception.NpDefinitionExceptionEnum;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class UUIDUtils {
    public static UUID fromString(String id) {
        UUID uuid = null;
        if (!StringUtils.isEmpty(id)) {
            try {
                uuid = UUID.fromString(id);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return uuid;
    }

    public static UUID convertFromString(String id) throws JQException {
        UUID uuid = null;
        if (!StringUtils.isEmpty(id)) {
            try {
                uuid = UUID.fromString(id);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)NpDefinitionExceptionEnum.NPDEFINITION_EXCEPTION_201);
            }
        }
        return uuid;
    }

    public static String getKey() {
        return UUID.randomUUID().toString();
    }

    public static String toString(UUID id) {
        return id.toString();
    }

    public static String[] toString(UUID[] ids) {
        if (ids == null) {
            return null;
        }
        String[] result = new String[ids.length];
        for (int i = 0; i < ids.length; ++i) {
            result[i] = ids[i].toString();
        }
        return result;
    }

    public static boolean isUUID(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        try {
            UUID.fromString(id);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public static UUID getTimeBaseUUID() {
        return Generators.timeBasedGenerator((EthernetAddress)EthernetAddress.fromInterface()).generate();
    }

    public static boolean isTimeBaseUUID(UUID uuid) {
        return uuid.version() == 1;
    }

    private static long getTimeMillis(UUID uuid) {
        long kClockOffset = 122192928000000000L;
        long kClockMultiplierL = 10000L;
        return (uuid.timestamp() - kClockOffset) / kClockMultiplierL;
    }

    public static Calendar getCalendar(UUID uuid) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(UUIDUtils.getTimeMillis(uuid));
        return cal;
    }

    public static Date getDate(UUID uuid) {
        return UUIDUtils.getCalendar(uuid).getTime();
    }

    public static Date getDate(UUID uuid, DatePrecision precision) {
        Calendar calendar = Calendar.getInstance();
        long timeMillis = UUIDUtils.getTimeMillis(uuid);
        switch (precision) {
            case YEAR: {
                calendar.setTimeInMillis(timeMillis - timeMillis % 86400000L);
                calendar.set(2, 0);
                calendar.set(5, 1);
                break;
            }
            case MONTH: {
                calendar.setTimeInMillis(timeMillis - timeMillis % 86400000L);
                calendar.set(5, 1);
                break;
            }
            case DATE: {
                calendar.setTimeInMillis(timeMillis - timeMillis % 86400000L);
                break;
            }
            case HOUR: {
                calendar.setTimeInMillis(timeMillis - timeMillis % 3600000L);
                break;
            }
            case MINUTE: {
                calendar.setTimeInMillis(timeMillis - timeMillis % 60000L);
                break;
            }
            case SECOND: {
                calendar.setTimeInMillis(timeMillis - timeMillis % 1000L);
                break;
            }
            default: {
                calendar.setTimeInMillis(timeMillis);
            }
        }
        return calendar.getTime();
    }

    public static Date getCeilDate(UUID uuid, DatePrecision precision) {
        Calendar calendar = Calendar.getInstance();
        long timeMillis = UUIDUtils.getTimeMillis(uuid);
        switch (precision) {
            case YEAR: {
                calendar.setTimeInMillis(timeMillis - timeMillis % 86400000L);
                calendar.set(2, 0);
                calendar.set(5, 1);
                calendar.add(1, 1);
                break;
            }
            case MONTH: {
                calendar.setTimeInMillis(timeMillis - timeMillis % 86400000L);
                calendar.set(5, 1);
                calendar.add(2, 1);
                break;
            }
            case DATE: {
                calendar.setTimeInMillis(timeMillis - timeMillis % 86400000L);
                calendar.add(5, 1);
                break;
            }
            case HOUR: {
                long l = timeMillis % 3600000L;
                calendar.setTimeInMillis(timeMillis - l);
                if (l <= 0L) break;
                calendar.add(10, 1);
                break;
            }
            case MINUTE: {
                long l = timeMillis % 60000L;
                calendar.setTimeInMillis(timeMillis - l);
                if (l <= 0L) break;
                calendar.add(12, 1);
                break;
            }
            case SECOND: {
                long l = timeMillis % 1000L;
                calendar.setTimeInMillis(timeMillis - l);
                if (l <= 0L) break;
                calendar.add(13, 1);
                break;
            }
            default: {
                calendar.setTimeInMillis(timeMillis);
            }
        }
        return calendar.getTime();
    }

    public static enum DatePrecision {
        YEAR,
        MONTH,
        DATE,
        HOUR,
        MINUTE,
        SECOND,
        NONE;

    }
}

