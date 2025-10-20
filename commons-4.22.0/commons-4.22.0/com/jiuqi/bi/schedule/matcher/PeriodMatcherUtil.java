/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule.matcher;

import java.util.Calendar;

class PeriodMatcherUtil {
    PeriodMatcherUtil() {
    }

    static boolean enableInDay(Calendar now, int startHour, int endHour) {
        if (startHour < 0 || startHour > 23) {
            startHour = 0;
        }
        if (endHour < 0 || endHour > 23) {
            endHour = 23;
        }
        if (startHour == endHour) {
            return true;
        }
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(now.getTimeInMillis());
        start.set(11, startHour);
        start.set(12, 0);
        start.set(13, 0);
        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(now.getTimeInMillis());
        if (endHour < startHour) {
            end.add(5, 1);
        }
        end.set(11, endHour);
        end.set(12, 0);
        end.set(13, 0);
        return now.after(start) && now.before(end);
    }

    static boolean enableInWeek(Calendar now, int weekDays) {
        int dayOfWeek = now.get(7);
        switch (dayOfWeek) {
            case 1: {
                return (weekDays & 1) == 1;
            }
            case 2: {
                return (weekDays & 2) == 2;
            }
            case 3: {
                return (weekDays & 4) == 4;
            }
            case 4: {
                return (weekDays & 8) == 8;
            }
            case 5: {
                return (weekDays & 0x10) == 16;
            }
            case 6: {
                return (weekDays & 0x20) == 32;
            }
            case 7: {
                return (weekDays & 0x40) == 64;
            }
        }
        return false;
    }
}

