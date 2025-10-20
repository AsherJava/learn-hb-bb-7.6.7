/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.database.metadata;

import org.json.JSONException;
import org.json.JSONObject;

public class LogicIndexField
implements Cloneable {
    private String fieldName;
    private int sortType;
    private static final String FIELDNAME = "fieldName";
    private static final String SORTTYPE = "sortType";
    public static final int DESC = -1;
    public static final int ASC = 1;

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getSortType() {
        return this.sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public void toJson(JSONObject json) throws JSONException {
        json.put(FIELDNAME, (Object)this.fieldName);
        json.put(SORTTYPE, this.sortType);
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.fieldName = json.getString(FIELDNAME);
        this.sortType = json.getInt(SORTTYPE);
    }

    public LogicIndexField clone() {
        try {
            return (LogicIndexField)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }
}

