/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Trigger
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod;

import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DayHour;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;

public class DailyScheduleMethod
extends AbstractScheduleMethod {
    public static final String NAME = "daily";
    public static final String TITLE = "\u6bcf\u65e5\u8c03\u5ea6";
    private static final String TAG_EXETIMES = "times";
    private List<DayHour> executeTimesInDay = new ArrayList<DayHour>();

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    public void setExecuteTimeInDay(DayHour executeTime) {
        if (this.executeTimesInDay == null) {
            this.executeTimesInDay = new ArrayList<DayHour>();
        }
        this.executeTimesInDay.add(executeTime);
    }

    @Override
    public String generateText() {
        if (this.executeTimesInDay.isEmpty()) {
            return "\u672a\u8bbe\u7f6e\u6267\u884c\u5468\u671f";
        }
        StringBuilder sb = new StringBuilder();
        DayHour firstExeTime = this.executeTimesInDay.get(0);
        sb.append("\u6bcf\u5929").append(firstExeTime);
        int size = this.executeTimesInDay.size();
        if (size > 1) {
            sb.append("\u4ee5\u53ca\u5176\u4ed6").append(size - 1).append("\u4e2a\u65f6\u95f4\u70b9\u5206\u522b");
        }
        sb.append("\u6267\u884c\u4e00\u6b21");
        return sb.toString();
    }

    @Override
    protected void loadFromJson(JSONObject json) throws JSONException {
        this.executeTimesInDay.clear();
        JSONArray array = json.optJSONArray(TAG_EXETIMES);
        for (int i = 0; i < array.length(); ++i) {
            JSONObject jo = array.getJSONObject(i);
            DayHour dh = new DayHour();
            dh.fromJson(jo);
            this.executeTimesInDay.add(dh);
        }
    }

    @Override
    protected void saveToJson(JSONObject json) throws JSONException {
        JSONArray array = new JSONArray();
        for (DayHour dh : this.executeTimesInDay) {
            JSONObject jo = new JSONObject();
            dh.toJson(jo);
            array.put((Object)jo);
        }
        json.putOpt(TAG_EXETIMES, (Object)array);
    }

    @Override
    public DailyScheduleMethod clone() {
        DailyScheduleMethod cloned = (DailyScheduleMethod)super.clone();
        cloned.executeTimesInDay = new ArrayList<DayHour>();
        for (DayHour dh : this.executeTimesInDay) {
            cloned.executeTimesInDay.add(dh.clone());
        }
        return cloned;
    }

    @Override
    public ScheduleBuilder<? extends Trigger> createQuartzScheduleBuilder() {
        if (this.executeTimesInDay.size() > 1) {
            throw new IllegalArgumentException("\u6682\u4e0d\u652f\u6301\u8bbe\u7f6e\u591a\u4e2a\u65f6\u95f4\u70b9");
        }
        DayHour dh = this.executeTimesInDay.get(0);
        StringBuilder cronExpr = new StringBuilder();
        cronExpr.append(dh.getSecond()).append(" ").append(dh.getMinute()).append(" ").append(dh.isPM() ? dh.getHour() + 12 : dh.getHour()).append(" ");
        cronExpr.append("* * ?");
        CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule((String)cronExpr.toString());
        csb.withMisfireHandlingInstructionDoNothing();
        return csb;
    }
}

