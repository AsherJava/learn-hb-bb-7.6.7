/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.quickreport.writeback.TableField;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TableModel {
    private String name;
    private String title;
    private List<TableField> defaultFields = new ArrayList<TableField>();
    private List<String> keyFields = new ArrayList<String>();
    private static final String TAG_NAME = "name";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DEFFIELDS = "defaultFields";
    private static final String TAG_KEYFIELDS = "keyFields";

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

    public List<TableField> getDefaultFields() {
        return this.defaultFields;
    }

    public List<String> getKeyFields() {
        return this.keyFields;
    }

    public void save(JSONObject value) throws JSONException {
        value.put(TAG_NAME, (Object)this.name);
        value.put(TAG_TITLE, (Object)this.title);
        JSONArray defaultFieldsArray = new JSONArray();
        for (TableField field : this.defaultFields) {
            JSONObject fieldObj = new JSONObject();
            field.save(fieldObj);
            defaultFieldsArray.put((Object)fieldObj);
        }
        value.put(TAG_DEFFIELDS, (Object)defaultFieldsArray);
        JSONArray keyFieldsArray = new JSONArray();
        for (String keyField : this.keyFields) {
            keyFieldsArray.put((Object)keyField);
        }
        value.put(TAG_KEYFIELDS, (Object)keyFieldsArray);
    }

    public void load(JSONObject value) throws JSONException {
        this.name = value.getString(TAG_NAME);
        this.title = value.getString(TAG_TITLE);
        JSONArray deaultFieldsArray = value.getJSONArray(TAG_DEFFIELDS);
        for (int i = 0; i < deaultFieldsArray.length(); ++i) {
            JSONObject fieldObj = deaultFieldsArray.getJSONObject(i);
            TableField field = new TableField();
            field.load(fieldObj);
            this.defaultFields.add(field);
        }
        JSONArray keyFieldArray = value.getJSONArray(TAG_KEYFIELDS);
        for (int i = 0; i < keyFieldArray.length(); ++i) {
            this.keyFields.add(keyFieldArray.getString(i));
        }
    }
}

