/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Trigger
 */
package com.jiuqi.bi.core.jobs.model;

import com.jiuqi.bi.core.jobs.model.IScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.CronScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.DailyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.HolidayScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.MonthlyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.NoneScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.QuarterlyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.WeeklyScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.YearlyScheduleMethod;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;

public abstract class AbstractScheduleMethod
implements IScheduleMethod,
Cloneable {
    private static final Map<String, AbstractScheduleMethod> map = new HashMap<String, AbstractScheduleMethod>();
    private static final String TAG_NAME = "name";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TEXT = "text";
    private static final String TAG_CONTENT = "content";
    public static final int LAST_DAY = -1;

    @Override
    public AbstractScheduleMethod clone() {
        try {
            return (AbstractScheduleMethod)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public final void toJson(JSONObject json) throws JSONException {
        json.putOpt(TAG_NAME, (Object)this.getName());
        json.putOpt(TAG_TITLE, (Object)this.getTitle());
        json.putOpt(TAG_TEXT, (Object)this.generateText());
        JSONObject content = new JSONObject();
        json.putOpt(TAG_CONTENT, (Object)content);
        this.saveToJson(content);
    }

    @Override
    public final void fromJson(JSONObject json) throws JSONException {
        JSONObject content = json.getJSONObject(TAG_CONTENT);
        this.loadFromJson(content);
    }

    public abstract ScheduleBuilder<? extends Trigger> createQuartzScheduleBuilder();

    public void afterScheduleBuilder(Trigger trigger) {
    }

    protected abstract void loadFromJson(JSONObject var1) throws JSONException;

    protected abstract void saveToJson(JSONObject var1) throws JSONException;

    protected static void regMethod(AbstractScheduleMethod sampleInstance) {
        if (map.get(sampleInstance.getName()) != null) {
            return;
        }
        map.put(sampleInstance.getName(), sampleInstance);
    }

    public static AbstractScheduleMethod createMethod(String name) {
        AbstractScheduleMethod method = map.get(name);
        return method.clone();
    }

    public static AbstractScheduleMethod loadMethod(JSONObject json) throws JSONException {
        String name = json.optString(TAG_NAME);
        AbstractScheduleMethod method = AbstractScheduleMethod.createMethod(name);
        method.fromJson(json);
        return method;
    }

    static {
        AbstractScheduleMethod.regMethod(new NoneScheduleMethod());
        AbstractScheduleMethod.regMethod(new SimpleScheduleMethod());
        AbstractScheduleMethod.regMethod(new DailyScheduleMethod());
        AbstractScheduleMethod.regMethod(new WeeklyScheduleMethod());
        AbstractScheduleMethod.regMethod(new MonthlyScheduleMethod());
        AbstractScheduleMethod.regMethod(new CronScheduleMethod());
        AbstractScheduleMethod.regMethod(new QuarterlyScheduleMethod());
        AbstractScheduleMethod.regMethod(new YearlyScheduleMethod());
        AbstractScheduleMethod.regMethod(new HolidayScheduleMethod());
    }
}

