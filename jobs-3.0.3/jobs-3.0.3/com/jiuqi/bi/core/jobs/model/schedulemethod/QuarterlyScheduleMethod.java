/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Trigger
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod;

import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour;
import com.jiuqi.bi.core.jobs.model.schedulemethod.YearlyScheduleMethod;
import java.util.SortedSet;
import java.util.TreeSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuarterlyScheduleMethod
extends AbstractScheduleMethod {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String NAME = "quarterly";
    public static final String TITLE = "\u6bcf\u5b63\u8c03\u5ea6";
    private static final String TAG_MONTHS = "months";
    private static final String TAG_DAYS = "days";
    private static final String TAG_DAYHOUR = "hour";
    private SortedSet<Integer> monthsInYear = new TreeSet<Integer>();
    private SortedSet<Integer> daysInMonth = new TreeSet<Integer>();
    private DayHour dh = new DayHour();

    public void addMonth(int month) {
        this.monthsInYear.add(month);
    }

    public void addDay(int day) {
        this.daysInMonth.add(day);
    }

    public void setDayHour(DayHour dayHour) {
        this.dh = dayHour;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public String generateText() {
        int firstDay;
        if (this.monthsInYear.isEmpty() || this.daysInMonth.isEmpty()) {
            return "\u672a\u8bbe\u7f6e\u6267\u884c\u5468\u671f";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\u6bcf\u5b63");
        int firstMonth = this.monthsInYear.first();
        sb.append("\u7b2c").append(firstMonth).append("\u6708");
        if (this.monthsInYear.size() > 1) {
            sb.append("\u4ee5\u53ca\u5f53\u5b63\u7684\u7b2c").append(this.monthsInYear.size() - 1).append("\u6708\u7684");
        }
        if ((firstDay = this.daysInMonth.first().intValue()) == -1) {
            sb.append("\u6700\u540e\u4e00\u5929");
        } else {
            sb.append(firstDay).append("\u53f7");
        }
        if (this.daysInMonth.size() > 1) {
            sb.append("\u4ee5\u53ca\u5f53\u6708\u7684\u5176\u4ed6").append(this.daysInMonth.size() - 1).append("\u5929");
        }
        sb.append(this.dh.toString()).append("\u6267\u884c");
        return sb.toString();
    }

    @Override
    protected void loadFromJson(JSONObject json) throws JSONException {
        JSONArray monthArray;
        this.daysInMonth.clear();
        this.monthsInYear.clear();
        JSONArray array = json.optJSONArray(TAG_DAYS);
        if (array != null) {
            for (int index = 0; index < array.length(); ++index) {
                int i = array.optInt(index);
                this.daysInMonth.add(i);
            }
        }
        if ((monthArray = json.optJSONArray(TAG_MONTHS)) != null) {
            for (int index = 0; index < monthArray.length(); ++index) {
                int i = monthArray.optInt(index);
                this.monthsInYear.add(i);
            }
        }
        JSONObject jo = json.optJSONObject(TAG_DAYHOUR);
        this.dh = new DayHour();
        this.dh.fromJson(jo);
    }

    @Override
    protected void saveToJson(JSONObject json) throws JSONException {
        JSONArray array = new JSONArray();
        for (Integer n : this.daysInMonth) {
            array.put((Object)n);
        }
        json.putOpt(TAG_DAYS, (Object)array);
        JSONArray monthsArray = new JSONArray();
        for (Integer i : this.monthsInYear) {
            monthsArray.put((Object)i);
        }
        json.putOpt(TAG_MONTHS, (Object)monthsArray);
        JSONObject jSONObject = new JSONObject();
        this.dh.toJson(jSONObject);
        json.putOpt(TAG_DAYHOUR, (Object)jSONObject);
    }

    @Override
    public QuarterlyScheduleMethod clone() {
        QuarterlyScheduleMethod cloned = (QuarterlyScheduleMethod)super.clone();
        cloned.dh = this.dh.clone();
        cloned.daysInMonth = new TreeSet<Integer>();
        for (Integer i : this.daysInMonth) {
            cloned.daysInMonth.add(i);
        }
        cloned.monthsInYear = new TreeSet<Integer>();
        for (Integer i : this.monthsInYear) {
            cloned.monthsInYear.add(i);
        }
        return cloned;
    }

    @Override
    public ScheduleBuilder<? extends Trigger> createQuartzScheduleBuilder() {
        YearlyScheduleMethod yearlyScheduleMethod = new YearlyScheduleMethod();
        for (int quarter = 0; quarter < 4; ++quarter) {
            for (Integer month : this.monthsInYear) {
                if (month > 3) {
                    this.logger.error("\u8c03\u5ea6\u4fe1\u606f\u4e2d\uff1a\u5b63\u5ea6\u6708\u975e\u6cd5\u3010{}\u3011\uff0c\u6bcf\u5b63\u5ea6\u6700\u5927\u6708\u6570\u4e3a3\u4e2a\u6708\u3002\u8be5\u6708\u4efd\u5c06\u88ab\u5ffd\u7565", (Object)month);
                    continue;
                }
                yearlyScheduleMethod.addMonth(quarter * 3 + month);
            }
        }
        for (Integer day : this.daysInMonth) {
            yearlyScheduleMethod.addDay(day);
        }
        yearlyScheduleMethod.setDayHour(this.dh);
        return yearlyScheduleMethod.createQuartzScheduleBuilder();
    }
}

