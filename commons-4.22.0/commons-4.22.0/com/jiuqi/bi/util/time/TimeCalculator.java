/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.TimeGranularityTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.types.TimeGranularityTypes;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.time.ExecutorByHalfYear;
import com.jiuqi.bi.util.time.ExecutorByMonth;
import com.jiuqi.bi.util.time.ExecutorByMonthDay;
import com.jiuqi.bi.util.time.ExecutorByQuarter;
import com.jiuqi.bi.util.time.ExecutorByTimeKey;
import com.jiuqi.bi.util.time.ExecutorByWeek;
import com.jiuqi.bi.util.time.ExecutorByWeekDay;
import com.jiuqi.bi.util.time.ExecutorByXun;
import com.jiuqi.bi.util.time.ExecutorByYear;
import com.jiuqi.bi.util.time.ITimeExecutor;
import com.jiuqi.bi.util.time.ITimeReader;
import com.jiuqi.bi.util.time.TimeCalcError;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.bi.util.time.TimeHelper;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TimeCalculator
implements Cloneable {
    private List<TimeFieldInfo> fields = new ArrayList<TimeFieldInfo>();
    private Map<String, TimeFieldInfo> finder = new HashMap<String, TimeFieldInfo>();
    private TimeFieldInfo timeKey;
    private ITimeExecutor executor;
    private Calendar timeKeyVal;
    private boolean defaultMode;

    public static TimeCalculator createCalculator(TimeFieldInfo ... timeFields) throws TimeCalcException {
        return TimeCalculator.createCalculator(Arrays.asList(timeFields));
    }

    public static TimeCalculator createCalculator(List<TimeFieldInfo> timeFields) throws TimeCalcException {
        TimeCalculator calculator = new TimeCalculator();
        calculator.init(timeFields);
        return calculator;
    }

    public List<TimeFieldInfo> getFields() {
        return this.fields;
    }

    public int indexOfField(String fieldName) {
        for (int i = 0; i < this.fields.size(); ++i) {
            if (!this.fields.get(i).getName().equalsIgnoreCase(fieldName)) continue;
            return i;
        }
        return -1;
    }

    public void init(List<TimeFieldInfo> timeFields) throws TimeCalcException {
        this.setFields(timeFields);
        this.executor = this.createExecutor();
    }

    public void init(TimeFieldInfo ... timeFields) throws TimeCalcException {
        this.init(Arrays.asList(timeFields));
    }

    private void setFields(List<TimeFieldInfo> timeFields) {
        this.fields.clear();
        this.finder.clear();
        this.timeKey = null;
        this.fields.addAll(timeFields);
        for (TimeFieldInfo field : this.fields) {
            this.finder.put(field.getName(), field);
            if (!field.isTimeKey()) continue;
            this.timeKey = field;
        }
    }

    private ITimeExecutor createExecutor() throws TimeCalcException {
        if (this.timeKey != null) {
            return new ExecutorByTimeKey(this.timeKey);
        }
        TimeFieldInfo yearField = this.findField(0);
        if (yearField == null) {
            throw new TimeCalcException("\u6ca1\u6709\u627e\u5230TimeKey\u5b57\u6bb5\u548c\u5e74\u7c92\u5ea6\u5b57\u6bb5\uff0c\u65e0\u6cd5\u8fdb\u884c\u5468\u671f\u8ba1\u7b97\u3002");
        }
        TimeFieldInfo minField = this.findMinField();
        ITimeExecutor runner = this.createNonKeyRunner(yearField, minField);
        this.timeKey = new TimeFieldInfo(null, minField.getGranularity(), "yyyyMMdd");
        return runner;
    }

    private ITimeExecutor createNonKeyRunner(TimeFieldInfo yearField, TimeFieldInfo minField) throws TimeCalcException {
        switch (minField.getGranularity()) {
            case 0: {
                return new ExecutorByYear(yearField);
            }
            case 1: {
                return new ExecutorByHalfYear(yearField, minField);
            }
            case 2: {
                return new ExecutorByQuarter(yearField, minField);
            }
            case 3: {
                return new ExecutorByMonth(yearField, minField);
            }
            case 4: {
                TimeFieldInfo monthField = this.findField(3);
                if (monthField == null) {
                    throw new TimeCalcException("\u6ca1\u6709\u627e\u5230\u6708\u7c92\u5ea6\u7684\u5b57\u6bb5\uff0c\u65e0\u6cd5\u8fdb\u884c\u5468\u671f\u8ba1\u7b97\u3002");
                }
                return new ExecutorByXun(yearField, monthField, minField);
            }
            case 5: {
                TimeFieldInfo monthField = this.findField(3);
                if (monthField != null) {
                    return new ExecutorByMonthDay(yearField, monthField, minField);
                }
                TimeFieldInfo weekField = this.findField(6);
                if (weekField != null) {
                    return new ExecutorByWeekDay(yearField, weekField, minField);
                }
                throw new TimeCalcException("\u6ca1\u6709\u627e\u5230\u6708\u7c92\u5ea6\u7684\u5b57\u6bb5\uff0c\u65e0\u6cd5\u8fdb\u884c\u5468\u671f\u8ba1\u7b97\u3002");
            }
            case 6: {
                return new ExecutorByWeek(yearField, minField);
            }
        }
        throw new TimeCalcException("\u8ba1\u7b97\u5468\u671f\u65f6\u9047\u5230\u672a\u77e5\u7684\u65f6\u671f\u7c92\u5ea6\uff1a" + minField.getGranularity());
    }

    private TimeFieldInfo findField(int granularity) {
        for (TimeFieldInfo field : this.fields) {
            if (field.getGranularity() != granularity) continue;
            return field;
        }
        return null;
    }

    private TimeFieldInfo findMinField() {
        TimeFieldInfo minField = null;
        for (TimeFieldInfo field : this.fields) {
            if (minField == null) {
                minField = field;
                continue;
            }
            if (TimeGranularityTypes.daysOf((int)field.getGranularity()) >= TimeGranularityTypes.daysOf((int)minField.getGranularity())) continue;
            minField = field;
        }
        return minField;
    }

    public int getFirstDayOfWeek() {
        return this.executor == null ? 2 : this.executor.getFirstDayOfWeek();
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) throws TimeCalcError {
        if (this.executor == null) {
            throw new TimeCalcError("\u672a\u521d\u59cb\u5316\u8ba1\u7b97\u5668\uff0c\u65e0\u6cd5\u6267\u884c\u5f53\u524d\u64cd\u4f5c");
        }
        this.executor.setFirstDayOfWeek(firstDayOfWeek);
    }

    public boolean isDefaultMode() {
        return this.defaultMode;
    }

    public void setDefaultMode(boolean defaultMode) {
        this.defaultMode = defaultMode;
    }

    public int getFiscalMinMonth() {
        return this.executor == null ? -1 : this.executor.getFiscalMinMonth();
    }

    public int getFiscalMaxMonth() {
        return this.executor == null ? -1 : this.executor.getFiscalMaxMonth();
    }

    public void setFiscalMonth(int minMonth, int maxMonth) {
        if (this.executor == null) {
            throw new IllegalStateException("\u672a\u521d\u59cb\u5316\u8ba1\u7b97\u5668\uff0c\u65e0\u6cd5\u8bbe\u7f6e\u8d22\u52a1\u671f\u95f4");
        }
        this.executor.setFiscalMonth(minMonth, maxMonth);
    }

    public void setValue(String fieldName, Object value) throws TimeCalcException {
        TimeFieldInfo field = this.finder.get(fieldName);
        if (field == null) {
            throw new TimeCalcException("\u67e5\u627e\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        this.executor.set(field, value);
        this.timeKeyVal = null;
    }

    public void setValue(int fieldIndex, Object value) throws TimeCalcException {
        this.executor.set(this.fields.get(fieldIndex), value);
        this.timeKeyVal = null;
    }

    public void reset(String fieldName, int value) throws TimeCalcException {
        TimeFieldInfo field = this.finder.get(fieldName);
        if (field == null) {
            throw new TimeCalcException("\u67e5\u627e\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        this.executor.set(field, value);
    }

    public void reset(int fieldIndex, int value) throws TimeCalcException {
        this.executor.set(this.fields.get(fieldIndex), value);
    }

    public boolean isNull() {
        return this.executor.isNull(this.defaultMode);
    }

    public boolean isNull(int granularity) {
        return this.executor.isNull(granularity);
    }

    public ITimeReader calculate() throws TimeCalcException {
        this.timeKeyVal = this.execTime();
        return new Reader(this.timeKeyVal);
    }

    private Calendar execTime() throws TimeCalcException {
        return this.executor.exec(this.defaultMode);
    }

    public ITimeReader offset(int offset) throws TimeCalcException {
        return this.offset(this.timeKey.getGranularity(), offset);
    }

    public ITimeReader offset(int granularity, int offset) throws TimeCalcException {
        return this.offset(granularity, offset, null);
    }

    public ITimeReader offset(int granularity, int offset, Map<Integer, Integer> fixedVals) throws TimeCalcException {
        if (this.timeKeyVal == null) {
            this.timeKeyVal = this.execTime();
        }
        if (this.timeKeyVal == null) {
            return new Reader(null);
        }
        Calendar newDate = this.offset(this.timeKeyVal, granularity, offset);
        if (fixedVals != null) {
            for (Map.Entry<Integer, Integer> fixedVal : fixedVals.entrySet()) {
                int granu = fixedVal.getKey();
                int value = fixedVal.getValue();
                if (TimeGranularityTypes.daysOf((int)granu) < TimeGranularityTypes.daysOf((int)this.timeKey.getGranularity()) && (granu != 3 || granularity != 2 && granularity != 1) && (granu != 5 || granularity != 4)) continue;
                newDate = TimeHelper.setTimeValue(newDate, granu, value);
            }
        }
        return new Reader(newDate);
    }

    private Calendar offset(Calendar timeKeyVal, int granularity, int offset) throws TimeCalcException {
        if (this.timeKey.getGranularity() == 6) {
            return TimeHelper.offsetWeekDate(timeKeyVal, granularity, offset);
        }
        return TimeHelper.offsetDate(timeKeyVal, granularity, offset);
    }

    public Iterator<ITimeReader> expand(String fieldName) throws TimeCalcException {
        TimeFieldInfo field = this.finder.get(fieldName);
        if (field == null) {
            throw new TimeCalcException("\u67e5\u627e\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
        }
        return this.createExpander(field);
    }

    public Iterator<ITimeReader> expand(int fieldIndex) throws TimeCalcException {
        TimeFieldInfo field = this.fields.get(fieldIndex);
        return this.createExpander(field);
    }

    private Iterator<ITimeReader> createExpander(TimeFieldInfo field) throws TimeCalcException {
        if (field.isTimeKey()) {
            throw new TimeCalcException("\u65e0\u6cd5\u5bf9TIMEKEY\u8fdb\u884c\u5c55\u5f00");
        }
        this.timeKeyVal = this.execTime();
        if (this.timeKeyVal == null) {
            return new Expander(field);
        }
        int terms = TimeHelper.countOf(this.timeKeyVal, field.getGranularity());
        return new Expander(field, terms);
    }

    public ITimeReader read(Calendar value) throws TimeCalcException {
        return new Reader(value);
    }

    public Iterator<ITimeReader> range(Calendar startDate, Calendar endDate) throws TimeCalcException {
        return new Ranger(startDate, endDate);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("TimeKey: ").append(this.timeKey).append(StringUtils.LINE_SEPARATOR).append("fields: ").append(this.fields).append(StringUtils.LINE_SEPARATOR).append("value: ");
        if (this.timeKeyVal == null) {
            buffer.append("null");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            buffer.append(format.format(this.timeKeyVal.getTime()));
        }
        return buffer.toString();
    }

    public TimeCalculator clone() {
        TimeCalculator timeCalc = new TimeCalculator();
        if (!this.fields.isEmpty()) {
            ArrayList<TimeFieldInfo> timeFields = new ArrayList<TimeFieldInfo>(this.fields.size());
            for (TimeFieldInfo field : this.fields) {
                timeFields.add(field.clone());
            }
            try {
                timeCalc.init(timeFields);
            }
            catch (TimeCalcException e) {
                throw new TimeCalcError(e);
            }
        }
        if (this.executor != null && timeCalc.executor != null) {
            timeCalc.executor.setFirstDayOfWeek(this.executor.getFirstDayOfWeek());
        }
        timeCalc.defaultMode = this.defaultMode;
        return timeCalc;
    }

    private final class Ranger
    implements Iterator<ITimeReader> {
        private Calendar current;
        private Calendar guard;

        public Ranger(Calendar startDate, Calendar endDate) {
            if (TimeCalculator.this.timeKey.getGranularity() == 6) {
                startDate.setFirstDayOfWeek(TimeCalculator.this.getFirstDayOfWeek());
                TimeHelper.alignWeekForward(startDate);
            }
            this.current = startDate;
            this.guard = endDate;
        }

        @Override
        public boolean hasNext() {
            return this.current.compareTo(this.guard) <= 0;
        }

        @Override
        public ITimeReader next() {
            Reader reader = new Reader(this.current);
            try {
                this.current = TimeCalculator.this.offset(this.current, TimeCalculator.this.timeKey.getGranularity(), 1);
            }
            catch (TimeCalcException e) {
                throw new TimeCalcError(e);
            }
            return reader;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private final class Expander
    implements Iterator<ITimeReader> {
        private TimeFieldInfo field;
        private int current;
        private int maxTerm;

        public Expander(TimeFieldInfo field, int maxTerm) {
            this.field = field;
            this.maxTerm = maxTerm;
        }

        public Expander(TimeFieldInfo field) {
            this(field, 0);
        }

        @Override
        public boolean hasNext() {
            return this.current < this.maxTerm;
        }

        @Override
        public ITimeReader next() {
            ++this.current;
            try {
                TimeCalculator.this.executor.set(this.field, this.current);
                return TimeCalculator.this.calculate();
            }
            catch (TimeCalcException e) {
                throw new TimeCalcError(e);
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private final class Reader
    implements ITimeReader {
        private Calendar value;
        private Format dayFormat;

        public Reader(Calendar value) {
            this.value = value;
        }

        @Override
        public Calendar getTimeKeyDate() {
            return this.value;
        }

        @Override
        public String getTimeKey() throws TimeCalcException {
            if (this.value == null) {
                return null;
            }
            return (String)TimeCalculator.this.timeKey.formatValue(this.value);
        }

        @Override
        public Object getValue(String fieldName) throws TimeCalcException {
            if (this.value == null) {
                return null;
            }
            TimeFieldInfo field = (TimeFieldInfo)TimeCalculator.this.finder.get(fieldName);
            if (field == null) {
                throw new TimeCalcException("\u67e5\u627e\u5b57\u6bb5\u4e0d\u5b58\u5728\uff1a" + fieldName);
            }
            return field.formatValue(this.value);
        }

        @Override
        public Object getValue(int fieldIndex) throws TimeCalcException {
            if (this.value == null) {
                return null;
            }
            return ((TimeFieldInfo)TimeCalculator.this.fields.get(fieldIndex)).formatValue(this.value);
        }

        @Override
        public Calendar getLastDate() throws TimeCalcException {
            return TimeCalculator.this.timeKey.getGranularity() == 6 ? this.value : TimeHelper.getLastDay(this.value, TimeCalculator.this.timeKey.getGranularity());
        }

        @Override
        public String getLastDay() throws TimeCalcException {
            Calendar lastDate = this.getLastDate();
            if (lastDate == null) {
                return null;
            }
            if (this.dayFormat == null) {
                this.dayFormat = new SimpleDateFormat("yyyyMMdd");
            }
            return this.dayFormat.format(lastDate.getTime());
        }

        @Override
        public int getDays() throws TimeCalcException {
            return TimeHelper.getDays(this.value, TimeCalculator.this.timeKey.getGranularity());
        }

        public String toString() {
            if (this.value == null) {
                return "null";
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            return format.format(this.value.getTime());
        }
    }
}

