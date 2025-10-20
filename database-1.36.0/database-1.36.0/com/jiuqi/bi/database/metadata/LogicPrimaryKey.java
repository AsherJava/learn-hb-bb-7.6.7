/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.database.metadata;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogicPrimaryKey
implements Cloneable {
    private String tableName;
    private String pkName;
    private List<String> fieldNames = new ArrayList<String>();
    private static final String TABLENAME = "tableName";
    private static final String PKNAME = "pkName";
    private static final String FIELDNAMES = "fieldNames";
    private static final String FIELDANME = "fieldName";

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPkName() {
        return this.pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public List<String> getFieldNames() {
        return this.fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public void toJson(JSONObject json) throws JSONException {
        json.put(TABLENAME, (Object)this.tableName);
        json.put(PKNAME, (Object)this.pkName);
        JSONArray array = new JSONArray();
        for (int i = 0; i < this.fieldNames.size(); ++i) {
            JSONObject field = new JSONObject();
            field.put(FIELDANME, (Object)this.fieldNames.get(i));
            array.put((Object)field);
        }
        json.put(FIELDNAMES, (Object)array);
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.tableName = json.getString(FIELDANME);
        this.pkName = json.getString(PKNAME);
        this.fieldNames.clear();
        JSONArray array = json.getJSONArray(FIELDNAMES);
        for (int i = 0; i < array.length(); ++i) {
            JSONObject fieldName = array.getJSONObject(i);
            this.fieldNames.add(fieldName.getString(FIELDANME));
        }
    }

    public LogicPrimaryKey clone() {
        try {
            LogicPrimaryKey primaryKey = (LogicPrimaryKey)super.clone();
            ArrayList<String> fieldNames = new ArrayList<String>();
            for (int i = 0; i < this.fieldNames.size(); ++i) {
                fieldNames.add(this.fieldNames.get(i));
            }
            primaryKey.fieldNames = fieldNames;
            return primaryKey;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }
}

