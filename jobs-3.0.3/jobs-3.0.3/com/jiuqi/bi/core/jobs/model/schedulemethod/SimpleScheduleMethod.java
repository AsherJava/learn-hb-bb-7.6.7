/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.ScheduleBuilder
 *  org.quartz.SimpleScheduleBuilder
 *  org.quartz.Trigger
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod;

import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SchedulePeroid;
import com.jiuqi.bi.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;

public class SimpleScheduleMethod
extends AbstractScheduleMethod {
    public static final String NAME = "simple";
    public static final String TITLE = "\u7b80\u5355\u8c03\u5ea6";
    private static final String TAG_EXETIME = "exetime";
    private static final String TAG_ISREPEAT = "isRepeat";
    private static final String TAG_PERIOD = "periodType";
    private static final String TAG_INTERVAL = "interval";
    private static final String TAG_REPEAT_COUNT = "repeatCount";
    private long executeTime = System.currentTimeMillis();
    private boolean isRepeat = false;
    private SchedulePeroid repeatPeriodType = SchedulePeroid.SECOND;
    private int repeatInterval = 1;
    private int repeatCount = -1;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(this.executeTime);
        String dateString = sdf.format(d);
        StringBuilder sb = new StringBuilder();
        if (System.currentTimeMillis() > this.executeTime) {
            sb.append("\u5df2\u4e8e");
        } else {
            sb.append("\u5c06\u4e8e");
        }
        sb.append(dateString);
        if (this.isRepeat) {
            sb.append("\u5f00\u59cb\uff0c\u6bcf\u9694").append(this.getRepeatInterval()).append(this.getRepeatPeriodType().title()).append("\u6267\u884c\u4e00\u6b21");
        } else {
            sb.append("\u6267\u884c\u4e00\u6b21");
        }
        return sb.toString();
    }

    @Override
    public ScheduleBuilder<? extends Trigger> createQuartzScheduleBuilder() {
        SimpleScheduleBuilder ssb = SimpleScheduleBuilder.simpleSchedule();
        if (this.isRepeat()) {
            if (this.repeatCount > 0) {
                ssb.withRepeatCount(this.repeatCount);
            } else {
                ssb.repeatForever();
            }
            if (this.repeatPeriodType == SchedulePeroid.SECOND) {
                ssb.withIntervalInSeconds(this.repeatInterval);
            } else if (this.repeatPeriodType == SchedulePeroid.MINUTE) {
                ssb.withIntervalInMinutes(this.repeatInterval);
            } else if (this.repeatPeriodType == SchedulePeroid.HOUR) {
                ssb.withIntervalInHours(this.repeatInterval);
            }
        }
        return ssb;
    }

    public long getExecuteTime() {
        return this.executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public boolean isRepeat() {
        return this.isRepeat;
    }

    public void setRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public SchedulePeroid getRepeatPeriodType() {
        return this.repeatPeriodType;
    }

    public void setRepeatPeriodType(SchedulePeroid repeatPeriodType) {
        this.repeatPeriodType = repeatPeriodType;
    }

    public int getRepeatInterval() {
        return this.repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public int getRepeatCount() {
        return this.repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    @Override
    protected void loadFromJson(JSONObject json) throws JSONException {
        this.executeTime = json.optLong(TAG_EXETIME);
        this.isRepeat = json.optBoolean(TAG_ISREPEAT);
        this.repeatInterval = json.optInt(TAG_INTERVAL);
        this.repeatPeriodType = StringUtils.isNotEmpty((String)json.optString(TAG_PERIOD)) ? SchedulePeroid.valueOf(json.optString(TAG_PERIOD)) : SchedulePeroid.SECOND;
        this.repeatCount = json.optInt(TAG_REPEAT_COUNT);
    }

    @Override
    protected void saveToJson(JSONObject json) throws JSONException {
        json.putOpt(TAG_EXETIME, (Object)this.executeTime);
        json.putOpt(TAG_ISREPEAT, (Object)this.isRepeat);
        json.putOpt(TAG_INTERVAL, (Object)this.repeatInterval);
        json.put(TAG_PERIOD, (Object)(this.repeatPeriodType == null ? SchedulePeroid.SECOND : this.repeatPeriodType));
        json.put(TAG_REPEAT_COUNT, this.repeatCount);
    }
}

