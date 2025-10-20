/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Trigger
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod;

import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;

public class CronScheduleMethod
extends AbstractScheduleMethod {
    public static final String NAME = "cron";
    public static final String TITLE = "Cron\u8868\u8fbe\u5f0f";
    private static final String TAG_CRON = "cron";
    private String cronExpression = null;

    @Override
    public String getName() {
        return "cron";
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCronExpression() {
        return this.cronExpression;
    }

    @Override
    public String generateText() {
        return "cron\u8868\u8fbe\u5f0f\uff1a" + this.cronExpression;
    }

    @Override
    public CronScheduleMethod clone() {
        return (CronScheduleMethod)super.clone();
    }

    @Override
    protected void loadFromJson(JSONObject json) throws JSONException {
        String cron = json.optString("cron");
        this.setCronExpression(cron);
    }

    @Override
    protected void saveToJson(JSONObject json) throws JSONException {
        json.putOpt("cron", (Object)this.getCronExpression());
    }

    @Override
    public ScheduleBuilder<? extends Trigger> createQuartzScheduleBuilder() {
        CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule((String)this.cronExpression);
        csb.withMisfireHandlingInstructionDoNothing();
        return csb;
    }
}

