/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.time.ITimeReader
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeCalculator
 *  com.jiuqi.bi.util.time.TimeFieldInfo
 *  com.jiuqi.bi.util.time.TimeHelper
 */
package com.jiuqi.bi.dataset.manager;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.manager.timekey.AbstractTimeKeyField;
import com.jiuqi.bi.dataset.manager.timekey.TimeKeyField;
import com.jiuqi.bi.dataset.manager.timekey.YearHalfyearTimeKey;
import com.jiuqi.bi.dataset.manager.timekey.YearMonthDayTimeKey;
import com.jiuqi.bi.dataset.manager.timekey.YearMonthTimeKey;
import com.jiuqi.bi.dataset.manager.timekey.YearMonthXunTimeKey;
import com.jiuqi.bi.dataset.manager.timekey.YearQuarterTimeKey;
import com.jiuqi.bi.dataset.manager.timekey.YearTimeKey;
import com.jiuqi.bi.dataset.manager.timekey.YearWeekTimeKey;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.time.ITimeReader;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeCalculator;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.bi.util.time.TimeHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeKeyBuilder {
    private static Logger logger = LoggerFactory.getLogger(TimeKeyBuilder.class);
    private int yearFieldIndex = -1;
    private int halfYearFieldIndex = -1;
    private int quarterFieldIndex = -1;
    private int monthFieldIndex = -1;
    private int xunFieldIndex = -1;
    private int dayFieldIndex = -1;
    private int weekFieldIndex = -1;
    private int timekeyIndex = -1;
    private int sys_timekeyIndex = -1;
    private TimeGranularity sys_timekeyGranularity = TimeGranularity.YEAR;
    private MemoryDataSet<BIDataSetFieldInfo> dataset;
    private Metadata<BIDataSetFieldInfo> metadata;
    private AbstractTimeKeyField timeKeyField;

    public static void buildTimeKey(MemoryDataSet<BIDataSetFieldInfo> dataset) throws BIDataSetException {
        TimeKeyBuilder builder = new TimeKeyBuilder(dataset);
        builder.build();
    }

    public static int parse(String timekey, TimeGranularity granularity) {
        Date date;
        DateFormatEx dateFormat = new DateFormatEx("yyyyMMdd");
        try {
            date = dateFormat.parse(timekey);
        }
        catch (ParseException e) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        try {
            return TimeHelper.getTimeValue((Calendar)calendar, (int)granularity.value());
        }
        catch (TimeCalcException e) {
            logger.error(e.getMessage(), e);
            return -1;
        }
    }

    public static String prev(String timeKey, int granularity, int n) {
        return TimeKeyBuilder.prev(timeKey, TimeGranularity.valueOf(granularity), n);
    }

    public static String prev(String timekey, TimeGranularity granularity, int N) {
        return TimeKeyBuilder.prev(timekey, granularity, N, -1, -1);
    }

    public static String prev(String timekey, TimeGranularity granularity, int N, int minMonth, int maxMonth) {
        if (N == 0) {
            return timekey;
        }
        TimeFieldInfo timeField = new TimeFieldInfo("name", granularity.value(), "yyyyMMdd", true);
        try {
            TimeCalculator calculator = TimeCalculator.createCalculator((TimeFieldInfo[])new TimeFieldInfo[]{timeField});
            calculator.setValue("name", (Object)timekey);
            calculator.setFiscalMonth(minMonth, maxMonth);
            ITimeReader reader = calculator.offset(granularity.value(), -N);
            return reader.getTimeKey();
        }
        catch (TimeCalcException e) {
            logger.error(e.getMessage(), e);
            return timekey;
        }
    }

    public static String prevWeekByYear(String timekey, int N) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = format.parse(timekey);
            calendar.setFirstDayOfWeek(2);
            calendar.setTime(date);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return timekey;
        }
        try {
            int timeValue = TimeHelper.getTimeValue((Calendar)calendar, (int)TimeGranularity.WEEK.value());
            if (timeValue == 0) {
                return timekey;
            }
            calendar.add(1, -N);
            Calendar newCalendar = TimeHelper.setTimeValue((Calendar)calendar, (int)TimeGranularity.WEEK.value(), (int)timeValue);
            return format.format(newCalendar.getTime());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return timekey;
        }
    }

    public TimeKeyBuilder(MemoryDataSet<BIDataSetFieldInfo> dataset) {
        this.dataset = dataset;
        this.metadata = this.dataset.getMetadata();
    }

    public void build() {
        this.initPeriodField();
        if (this.timekeyIndex != -1) {
            this.timeKeyField = new TimeKeyField(this.timekeyIndex, this.getFieldInfo(this.timekeyIndex));
        } else if (this.yearFieldIndex != -1) {
            if (this.dayFieldIndex != -1) {
                if (this.monthFieldIndex != -1) {
                    this.timeKeyField = new YearMonthDayTimeKey(this.yearFieldIndex, this.monthFieldIndex, this.dayFieldIndex, this.getFieldInfo(this.yearFieldIndex), this.getFieldInfo(this.monthFieldIndex), this.getFieldInfo(this.dayFieldIndex));
                }
            } else if (this.weekFieldIndex != -1) {
                this.timeKeyField = new YearWeekTimeKey(this.yearFieldIndex, this.weekFieldIndex, this.getFieldInfo(this.yearFieldIndex), this.getFieldInfo(this.weekFieldIndex));
            } else if (this.xunFieldIndex != -1) {
                if (this.monthFieldIndex != -1) {
                    this.timeKeyField = new YearMonthXunTimeKey(this.yearFieldIndex, this.monthFieldIndex, this.xunFieldIndex, this.getFieldInfo(this.yearFieldIndex), this.getFieldInfo(this.monthFieldIndex), this.getFieldInfo(this.xunFieldIndex));
                }
            } else if (this.monthFieldIndex != -1) {
                this.timeKeyField = new YearMonthTimeKey(this.yearFieldIndex, this.monthFieldIndex, this.getFieldInfo(this.yearFieldIndex), this.getFieldInfo(this.monthFieldIndex));
            } else if (this.quarterFieldIndex != -1) {
                this.timeKeyField = new YearQuarterTimeKey(this.yearFieldIndex, this.quarterFieldIndex, this.getFieldInfo(this.yearFieldIndex), this.getFieldInfo(this.quarterFieldIndex));
            } else if (this.halfYearFieldIndex != -1) {
                this.timeKeyField = new YearHalfyearTimeKey(this.yearFieldIndex, this.halfYearFieldIndex, this.getFieldInfo(this.yearFieldIndex), this.getFieldInfo(this.halfYearFieldIndex));
            } else if (this.dayFieldIndex == -1 && this.xunFieldIndex == -1 && this.monthFieldIndex == -1 && this.quarterFieldIndex == -1 && this.halfYearFieldIndex == -1) {
                this.timeKeyField = new YearTimeKey(this.yearFieldIndex, this.getFieldInfo(this.yearFieldIndex));
            }
        }
        if (this.timeKeyField != null) {
            this.addSysPeriodColumn(this.sys_timekeyGranularity);
        }
        this.generateTimeKey();
    }

    private void initPeriodField() {
        boolean hasTimekey = false;
        for (Column column : this.metadata.getColumns()) {
            BIDataSetFieldInfo field = (BIDataSetFieldInfo)column.getInfo();
            if (field.getFieldType() != FieldType.TIME_DIM) continue;
            if (field.isTimekey()) {
                hasTimekey = true;
                this.timekeyIndex = column.getIndex();
                this.sys_timekeyGranularity = field.getTimegranularity();
            }
            switch (field.getTimegranularity()) {
                case YEAR: {
                    this.yearFieldIndex = column.getIndex();
                    break;
                }
                case HALFYEAR: {
                    this.halfYearFieldIndex = column.getIndex();
                    break;
                }
                case QUARTER: {
                    this.quarterFieldIndex = column.getIndex();
                    break;
                }
                case MONTH: {
                    this.monthFieldIndex = column.getIndex();
                    break;
                }
                case XUN: {
                    this.xunFieldIndex = column.getIndex();
                    break;
                }
                case DAY: {
                    this.dayFieldIndex = column.getIndex();
                    break;
                }
                case WEEK: {
                    this.weekFieldIndex = column.getIndex();
                }
            }
            if (hasTimekey || field.getTimegranularity().days() >= this.sys_timekeyGranularity.days()) continue;
            this.sys_timekeyGranularity = field.getTimegranularity();
        }
    }

    private void addSysPeriodColumn(TimeGranularity timegranularity) {
        Column column = new Column("SYS_TIMEKEY", DataType.STRING.value(), "SYS_TIMEKEY", null);
        BIDataSetFieldInfo info = new BIDataSetFieldInfo();
        info.setDataPattern("yyyyMMdd");
        info.setFieldType(null);
        info.setTimegranularity(timegranularity);
        info.setValType(DataType.STRING.value());
        info.setKeyField("SYS_TIMEKEY");
        info.setName("SYS_TIMEKEY");
        info.setTitle("SYS_TIMEKEY");
        column.setInfo((Object)info);
        this.metadata.addColumn(column);
        this.sys_timekeyIndex = column.getIndex();
        this.metadata.getProperties().put("TIMEKEY_INDEX", this.sys_timekeyIndex);
        this.metadata.getProperties().put("TIMEKEY_GRANULARITY", timegranularity);
    }

    private BIDataSetFieldInfo getFieldInfo(int index) {
        Column column = this.metadata.getColumn(index);
        return (BIDataSetFieldInfo)column.getInfo();
    }

    private void generateTimeKey() {
        if (this.timeKeyField == null) {
            return;
        }
        for (DataRow row : this.dataset) {
            String timekey = this.timeKeyField.generateTimekey(row.getBuffer());
            row.setString(this.sys_timekeyIndex, timekey);
        }
    }
}

