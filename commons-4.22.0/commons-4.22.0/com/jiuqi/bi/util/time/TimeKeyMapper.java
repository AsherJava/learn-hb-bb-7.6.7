/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.TimeGranularityTypes
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.types.TimeGranularityTypes;
import com.jiuqi.bi.util.time.ITimeReader;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeCalculator;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.bi.util.time.TimeHelper;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public class TimeKeyMapper {
    private int srcGranularity = -1;
    private String srcFormat = "yyyyMMdd";
    private int destGranularity = -1;
    private String destFormat = "yyyyMMdd";
    private int firstDayOfWeek = 2;
    private int fiscalMinMonth = -1;
    private int fiscalMaxMonth = -1;

    public int getSrcGranularity() {
        return this.srcGranularity;
    }

    public TimeKeyMapper setSrcGranularity(int srcGranularity) {
        this.srcGranularity = srcGranularity;
        return this;
    }

    public String getSrcFormat() {
        return this.srcFormat;
    }

    public TimeKeyMapper setSrcFormat(String srcFormat) {
        this.srcFormat = srcFormat;
        return this;
    }

    public int getDestGranularity() {
        return this.destGranularity;
    }

    public TimeKeyMapper setDestGranularity(int destGranularity) {
        this.destGranularity = destGranularity;
        return this;
    }

    public String getDestFormat() {
        return this.destFormat;
    }

    public TimeKeyMapper setDestFormat(String destFormat) {
        this.destFormat = destFormat;
        return this;
    }

    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public TimeKeyMapper setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
        return this;
    }

    public int getFiscalMinMonth() {
        return this.fiscalMinMonth;
    }

    public TimeKeyMapper setFiscalMinMonth(int fiscalMinMonth) {
        this.fiscalMinMonth = fiscalMinMonth;
        return this;
    }

    public int getFiscalMaxMonth() {
        return this.fiscalMaxMonth;
    }

    public TimeKeyMapper setFiscalMaxMonth(int fiscalMaxMonth) {
        this.fiscalMaxMonth = fiscalMaxMonth;
        return this;
    }

    public List<String> map(Collection<String> timeKeys) throws TimeCalcException {
        if (timeKeys.isEmpty()) {
            return Collections.emptyList();
        }
        if (this.srcGranularity == this.destGranularity && Objects.equals(this.srcFormat, this.destFormat)) {
            return new ArrayList<String>(timeKeys);
        }
        if (TimeGranularityTypes.compare((int)this.srcGranularity, (int)this.destGranularity) > 0) {
            return this.expandTimeKeys(timeKeys);
        }
        if (TimeGranularityTypes.compare((int)this.srcGranularity, (int)this.destGranularity) < 0) {
            return this.alignTimeKeys(timeKeys);
        }
        return this.convertTimeKeys(timeKeys);
    }

    private List<String> expandTimeKeys(Collection<String> timeKeys) throws TimeCalcException {
        TreeSet<String> mapKeys = new TreeSet<String>();
        DateFormatEx formatter = this.createFormat(this.srcGranularity, this.srcFormat);
        TimeCalculator timeCalc = TimeCalculator.createCalculator(new TimeFieldInfo("TIMEKEY", this.destGranularity, this.destFormat, true));
        if (this.firstDayOfWeek > 0) {
            timeCalc.setFirstDayOfWeek(this.firstDayOfWeek);
        }
        if (this.isFiscalMode()) {
            timeCalc.setFiscalMonth(this.fiscalMinMonth, this.fiscalMaxMonth);
        }
        for (String timeKey : timeKeys) {
            Calendar firstDay;
            try {
                firstDay = formatter.parseCalendar(timeKey);
            }
            catch (ParseException e) {
                throw new TimeCalcException("\u89e3\u6790\u65f6\u671f\u683c\u5f0f\u9519\u8bef\uff1a" + timeKey, e);
            }
            if (this.destGranularity == 3 && this.fiscalMinMonth == 0 && (this.srcGranularity == 0 || firstDay.get(2) == 0)) {
                firstDay.set(2, -1);
            }
            Calendar lastDay = TimeHelper.getLastDay(firstDay, this.srcGranularity);
            Iterator<ITimeReader> i = timeCalc.range(firstDay, lastDay);
            while (i.hasNext()) {
                ITimeReader reader = i.next();
                mapKeys.add(reader.getTimeKey());
            }
        }
        return new ArrayList<String>(mapKeys);
    }

    private List<String> alignTimeKeys(Collection<String> timeKeys) throws TimeCalcException {
        TreeSet<String> mapKeys = new TreeSet<String>();
        DateFormatEx srcFormatter = this.createFormat(this.srcGranularity, this.srcFormat);
        DateFormatEx destFormatter = this.createFormat(this.destGranularity, this.destFormat);
        for (String timeKey : timeKeys) {
            Calendar srcDate;
            try {
                srcDate = srcFormatter.parseCalendar(timeKey);
            }
            catch (ParseException e) {
                throw new TimeCalcException("\u89e3\u6790\u65f6\u671f\u683c\u5f0f\u9519\u8bef\uff1a" + timeKey, e);
            }
            if (this.firstDayOfWeek > 0 && this.destGranularity == 6) {
                srcDate.setFirstDayOfWeek(this.firstDayOfWeek);
            }
            Calendar destDate = TimeHelper.alignDate(srcDate, this.destGranularity);
            mapKeys.add(destFormatter.format(destDate));
        }
        return new ArrayList<String>(mapKeys);
    }

    private List<String> convertTimeKeys(Collection<String> timeKeys) throws TimeCalcException {
        ArrayList<String> mapKeys = new ArrayList<String>(timeKeys.size());
        DateFormatEx srcFormatter = this.createFormat(this.srcGranularity, this.srcFormat);
        DateFormatEx destFormatter = this.createFormat(this.destGranularity, this.destFormat);
        for (String timeKey : timeKeys) {
            Calendar date;
            try {
                date = srcFormatter.parseCalendar(timeKey);
            }
            catch (ParseException e) {
                throw new TimeCalcException("\u89e3\u6790\u65f6\u671f\u683c\u5f0f\u9519\u8bef\uff1a" + timeKey, e);
            }
            mapKeys.add(destFormatter.format(date));
        }
        return mapKeys;
    }

    private DateFormatEx createFormat(int granularity, String pattern) {
        DateFormatEx format = new DateFormatEx(pattern, granularity);
        if (this.isFiscalMode()) {
            Calendar fiscal = TimeHelper.newCalendar(this.fiscalMinMonth, this.fiscalMaxMonth);
            format.setCalendar(fiscal);
        }
        return format;
    }

    private boolean isFiscalMode() {
        return !(this.fiscalMinMonth != 0 && this.fiscalMaxMonth <= 12 || this.srcGranularity != 3 && this.destGranularity != 3);
    }
}

