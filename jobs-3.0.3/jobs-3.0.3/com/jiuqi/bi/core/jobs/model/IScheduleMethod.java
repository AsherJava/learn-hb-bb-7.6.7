/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.model;

import org.json.JSONException;
import org.json.JSONObject;

public interface IScheduleMethod
extends Cloneable {
    public String getName();

    public String getTitle();

    public String generateText();

    public IScheduleMethod clone();

    public void toJson(JSONObject var1) throws JSONException;

    public void fromJson(JSONObject var1) throws JSONException;
}

