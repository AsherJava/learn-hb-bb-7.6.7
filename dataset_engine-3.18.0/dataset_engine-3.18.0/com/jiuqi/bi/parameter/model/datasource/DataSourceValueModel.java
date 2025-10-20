/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.model.datasource;

import org.json.JSONException;
import org.json.JSONObject;

public class DataSourceValueModel
implements Cloneable {
    private String attrName;
    private String code;
    private String name;
    private static final String TAG_ATTRNAME = "attrName";
    private static final String TAG_NAME = "name";
    private static final String TAG_CODE = "code";

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttrName() {
        return this.attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public DataSourceValueModel clone() {
        try {
            return (DataSourceValueModel)super.clone();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void toJson(JSONObject value) throws JSONException {
        value.put(TAG_ATTRNAME, (Object)this.attrName);
        value.put(TAG_NAME, (Object)this.name);
        value.put(TAG_CODE, (Object)this.code);
    }

    public void fromJson(JSONObject value) throws JSONException {
        if (!value.isNull(TAG_ATTRNAME)) {
            this.attrName = value.getString(TAG_ATTRNAME);
        }
        if (!value.isNull(TAG_NAME)) {
            this.name = value.getString(TAG_NAME);
        }
        if (!value.isNull(TAG_CODE)) {
            this.code = value.getString(TAG_CODE);
        }
    }
}

