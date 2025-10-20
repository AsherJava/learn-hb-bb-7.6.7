/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;
import com.jiuqi.va.biz.utils.Utils;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GregorianCalendarConvert {
    public static final Converter toString2 = new Converter(){

        @Override
        public Object cast(Object data) {
            return Utils.formatDateTime(((GregorianCalendar)data).getTime());
        }

        @Override
        public Class<?> getTargetType() {
            return String.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Calendar.class;
        }
    };
    public static final Converter fromString = new Converter(){

        @Override
        public Object cast(Object data) {
            Date value = Utils.parseDateTime((String)data);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(value);
            return calendar;
        }

        @Override
        public Class<?> getTargetType() {
            return GregorianCalendar.class;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toDate = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((GregorianCalendar)data).getTime();
        }

        @Override
        public Class<?> getTargetType() {
            return Date.class;
        }

        @Override
        public Class<?> getSourceType() {
            return GregorianCalendar.class;
        }
    };
    public static final Converter fromDate = new Converter(){

        @Override
        public Object cast(Object data) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime((Date)data);
            return calendar;
        }

        @Override
        public Class<?> getTargetType() {
            return GregorianCalendar.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Date.class;
        }
    };
    public static final Converter toLong = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((GregorianCalendar)data).getTimeInMillis();
        }

        @Override
        public Class<?> getTargetType() {
            return Long.class;
        }

        @Override
        public Class<?> getSourceType() {
            return GregorianCalendar.class;
        }
    };
    public static final Converter fromLong = new Converter(){

        @Override
        public Object cast(Object data) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis((Long)data);
            return calendar;
        }

        @Override
        public Class<?> getTargetType() {
            return GregorianCalendar.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Long.class;
        }
    };
}

