/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.quartz.TimeOfDay
 */
package com.jiuqi.bi.core.jobs.model.schedulemethod;

import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.TimeOfDay;

public class DayHour
implements Cloneable {
    private static final String TAG_ISPM = "ispm";
    private static final String TAG_HOUR = "hour";
    private static final String TAG_MINUTE = "minute";
    private static final String TAG_SECOND = "second";
    private boolean isPM = false;
    private int hour = 0;
    private int minute = 0;
    private int second = 0;

    public boolean isPM() {
        return this.isPM;
    }

    public void setPM(boolean isPM) {
        this.isPM = isPM;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void toJson(JSONObject json) throws JSONException {
        json.putOpt(TAG_ISPM, (Object)this.isPM());
        json.putOpt(TAG_HOUR, (Object)this.getHour());
        json.putOpt(TAG_MINUTE, (Object)this.getMinute());
        json.putOpt(TAG_SECOND, (Object)this.getSecond());
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.setPM(json.optBoolean(TAG_ISPM));
        this.setHour(json.optInt(TAG_HOUR));
        this.setMinute(json.optInt(TAG_MINUTE));
        this.setSecond(json.optInt(TAG_SECOND));
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int tempHour = this.hour;
        if (this.isPM) {
            tempHour += 12;
        }
        sb.append(tempHour >= 10 ? Integer.valueOf(tempHour) : "0" + tempHour).append(":").append(this.getMinute() >= 10 ? Integer.valueOf(this.getMinute()) : "0" + this.getMinute()).append(":").append(this.getSecond() >= 10 ? Integer.valueOf(this.getSecond()) : "0" + this.getSecond());
        return sb.toString();
    }

    public DayHour clone() {
        try {
            return (DayHour)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.hour;
        result = 31 * result + (this.isPM ? 1231 : 1237);
        result = 31 * result + this.minute;
        result = 31 * result + this.second;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DayHour other = (DayHour)obj;
        if (this.hour != other.hour) {
            return false;
        }
        if (this.isPM != other.isPM) {
            return false;
        }
        if (this.minute != other.minute) {
            return false;
        }
        return this.second == other.second;
    }

    public TimeOfDay toQuartzObject() {
        return new TimeOfDay(this.isPM ? this.hour + 12 : this.hour, this.minute, this.second);
    }
}

