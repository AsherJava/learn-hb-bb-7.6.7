/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Trigger
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod;

import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;

public class NoneScheduleMethod
extends AbstractScheduleMethod {
    public static final String NAME = "noschedule";
    public static final String TITLE = "\u4e0d\u8c03\u5ea6";

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
        return "\u624b\u5de5\u8c03\u5ea6";
    }

    @Override
    public NoneScheduleMethod clone() {
        return (NoneScheduleMethod)super.clone();
    }

    @Override
    protected void loadFromJson(JSONObject json) throws JSONException {
    }

    @Override
    protected void saveToJson(JSONObject json) throws JSONException {
    }

    @Override
    public ScheduleBuilder<? extends Trigger> createQuartzScheduleBuilder() {
        return null;
    }
}

