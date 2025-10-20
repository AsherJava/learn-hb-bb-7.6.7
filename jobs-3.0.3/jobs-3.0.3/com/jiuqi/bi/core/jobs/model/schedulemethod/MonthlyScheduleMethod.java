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
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;

public class MonthlyScheduleMethod
extends AbstractScheduleMethod {
    public static final String NAME = "monthly";
    public static final String TITLE = "\u6bcf\u6708\u8c03\u5ea6";
    private static final String TAG_DAYS = "days";
    private static final String TAG_DAYHOUR = "hour";
    private SortedSet<Integer> daysInMonth = new TreeSet<Integer>();
    private DayHour dh = new DayHour();

    public void addDay(int day) {
        this.daysInMonth.add(day);
    }

    public void removeDay(int day) {
        this.daysInMonth.remove(day);
    }

    public Iterator<Integer> days() {
        return this.daysInMonth.iterator();
    }

    public void setDayHour(DayHour dh) {
        this.dh = dh;
    }

    public DayHour getDayHour() {
        return this.dh;
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
        if (this.daysInMonth.isEmpty()) {
            return "\u672a\u8bbe\u7f6e\u6267\u884c\u5468\u671f";
        }
        StringBuilder sb = new StringBuilder();
        int firstDay = this.daysInMonth.first();
        sb.append("\u6bcf\u6708");
        if (firstDay == -1) {
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
        this.daysInMonth.clear();
        JSONArray array = json.optJSONArray(TAG_DAYS);
        if (array != null) {
            for (int index = 0; index < array.length(); ++index) {
                int i = array.optInt(index);
                this.daysInMonth.add(i);
            }
        }
        JSONObject jo = json.optJSONObject(TAG_DAYHOUR);
        this.dh = new DayHour();
        this.dh.fromJson(jo);
    }

    @Override
    protected void saveToJson(JSONObject json) throws JSONException {
        JSONArray array = new JSONArray();
        for (Integer i : this.daysInMonth) {
            array.put((Object)i);
        }
        json.putOpt(TAG_DAYS, (Object)array);
        JSONObject jo = new JSONObject();
        this.dh.toJson(jo);
        json.putOpt(TAG_DAYHOUR, (Object)jo);
    }

    @Override
    public MonthlyScheduleMethod clone() {
        MonthlyScheduleMethod cloned = (MonthlyScheduleMethod)super.clone();
        cloned.dh = this.dh.clone();
        cloned.daysInMonth = new TreeSet<Integer>();
        for (Integer i : this.daysInMonth) {
            cloned.daysInMonth.add(i);
        }
        return cloned;
    }

    @Override
    public ScheduleBuilder<? extends Trigger> createQuartzScheduleBuilder() {
        StringBuilder cronExpr = new StringBuilder();
        cronExpr.append(this.dh.getSecond()).append(" ").append(this.dh.getMinute()).append(" ").append(this.dh.isPM() ? this.dh.getHour() + 12 : this.dh.getHour()).append(" ");
        Iterator daysIterator = this.daysInMonth.iterator();
        StringBuilder cronDayExpr = new StringBuilder();
        int countDay = 0;
        while (daysIterator.hasNext()) {
            int day = (Integer)daysIterator.next();
            if (day == -1) {
                cronDayExpr.append("L");
                break;
            }
            if (countDay > 0) {
                cronDayExpr.append(",");
            }
            cronDayExpr.append(day);
            ++countDay;
        }
        cronExpr.append((CharSequence)cronDayExpr).append(" ");
        cronExpr.append("1-12 ? *");
        CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule((String)cronExpr.toString());
        csb.withMisfireHandlingInstructionDoNothing();
        return csb;
    }
}

