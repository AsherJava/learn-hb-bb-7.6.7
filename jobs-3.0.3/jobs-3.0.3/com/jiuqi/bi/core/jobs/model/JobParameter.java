/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.model;

import com.jiuqi.bi.core.jobs.model.ParameterType;
import org.json.JSONObject;

public class JobParameter
implements Cloneable {
    private String name;
    private String title;
    private String desc;
    private ParameterType type = ParameterType.STRING;
    private String defaultValue;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public ParameterType getType() {
        return this.type;
    }

    public JobParameter setType(ParameterType type) {
        this.type = type;
        return this;
    }

    public JobParameter clone() {
        try {
            return (JobParameter)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.putOpt("name", (Object)this.name);
        object.putOpt("title", (Object)this.title);
        object.putOpt("desc", (Object)this.desc);
        object.putOpt("type", (Object)this.type.name());
        object.putOpt("defaultValue", (Object)this.defaultValue);
        return object;
    }

    public void fromJson(JSONObject object) {
        this.name = object.optString("name");
        this.title = object.optString("title");
        this.desc = object.optString("desc");
        this.type = ParameterType.typeOf(object.optString("type"));
        this.defaultValue = object.optString("defaultValue");
    }
}

