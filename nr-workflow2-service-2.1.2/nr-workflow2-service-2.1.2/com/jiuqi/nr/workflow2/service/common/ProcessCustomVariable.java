/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.service.common;

import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.service.common.IProcessCustomVariable;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProcessCustomVariable
implements IProcessCustomVariable,
Cloneable {
    private final JSONObject value;

    public ProcessCustomVariable(JSONObject value) {
        this.value = value;
    }

    public ProcessCustomVariable(String value) {
        this.value = new JSONObject(value);
    }

    @Override
    public JSONObject getValue() {
        return this.value;
    }

    public String toJsonString() {
        return JavaBeanUtils.toJSONStr((Object)this.value.toMap());
    }

    @Override
    public IProcessCustomVariable getVariable(String key) {
        return new ProcessCustomVariable(this.value.getJSONObject(key));
    }

    @Override
    public <T> T toJavaBean(JSONObject jsonObject, Class<T> clazz) throws Exception {
        return (T)JavaBeanUtils.toJavaBean((String)jsonObject.toString(), clazz);
    }

    @Override
    public ProcessCustomVariable clone() {
        try {
            return (ProcessCustomVariable)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public boolean getBoolean(String key) {
        if (this.value.has(key)) {
            return this.value.getBoolean(key);
        }
        return false;
    }

    public String getString(String key) {
        if (this.value.has(key)) {
            return this.value.getString(key);
        }
        return null;
    }

    public double getDouble(String key) {
        if (this.value.has(key)) {
            return this.value.getDouble(key);
        }
        return 0.0;
    }

    public float getFloat(String key) {
        if (this.value.has(key)) {
            return this.value.getFloat(key);
        }
        return 0.0f;
    }

    public int getInt(String key) {
        if (this.value.has(key)) {
            return this.value.getInt(key);
        }
        return 0;
    }

    public long getLong(String key) {
        if (this.value.has(key)) {
            return this.value.getLong(key);
        }
        return 0L;
    }

    public JSONArray getJSONArray(String key) {
        if (this.value.has(key)) {
            return this.value.getJSONArray(key);
        }
        return null;
    }

    public JSONObject getJSONObject(String key) {
        if (this.value.has(key)) {
            return this.value.getJSONObject(key);
        }
        return null;
    }

    public Object get(String key) {
        if (this.value.has(key)) {
            return this.value.get(key);
        }
        return null;
    }
}

