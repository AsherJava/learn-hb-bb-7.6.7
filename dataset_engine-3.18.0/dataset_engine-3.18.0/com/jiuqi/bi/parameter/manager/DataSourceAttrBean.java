/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.manager;

import org.json.JSONException;
import org.json.JSONObject;

public class DataSourceAttrBean {
    private String currAttrName;
    private String keyColName;
    private String nameColName;
    private String title;
    private static final String TAG_CURRATRR = "currAttrName";
    private static final String TAG_KEYATTR = "keyAttr";
    private static final String TAG_NAMEATTR = "nameAttr";
    private static final String TAG_TITLE = "title";

    public void toJson(JSONObject value) throws JSONException {
        value.put(TAG_CURRATRR, (Object)this.currAttrName);
        value.put(TAG_KEYATTR, (Object)this.keyColName);
        value.put(TAG_NAMEATTR, (Object)this.nameColName);
        value.put(TAG_TITLE, (Object)this.title);
    }

    public void fromJson(JSONObject value) throws JSONException {
        this.currAttrName = value.getString(TAG_CURRATRR);
        this.keyColName = value.getString(TAG_KEYATTR);
        this.nameColName = value.getString(TAG_NAMEATTR);
        if (!value.isNull(TAG_TITLE)) {
            this.title = value.getString(TAG_TITLE);
        }
    }

    public String getCurrAttrName() {
        return this.currAttrName;
    }

    public void setCurrAttrName(String currAttrName) {
        this.currAttrName = currAttrName;
    }

    public String getKeyColName() {
        return this.keyColName;
    }

    public void setKeyColName(String keyColName) {
        this.keyColName = keyColName;
    }

    public String getNameColName() {
        return this.nameColName;
    }

    public void setNameColName(String nameColName) {
        this.nameColName = nameColName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean nameEqualsKey() {
        return this.keyColName.equals(this.nameColName);
    }
}

