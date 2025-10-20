/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.database.metadata;

import com.jiuqi.bi.database.metadata.LogicIndexField;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogicIndex
implements Cloneable {
    private String tableName;
    private String indexName;
    private boolean unique;
    private List<LogicIndexField> IndexFields = new ArrayList<LogicIndexField>();
    private static final String TABLENAME = "tableName";
    private static final String INDEXNAME = "indexName";
    private static final String UNIQUE = "unique";
    private static final String INDEXFIELDS = "indexFields";

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public List<LogicIndexField> getIndexFields() {
        return this.IndexFields;
    }

    public void addIndexField(LogicIndexField field) {
        this.IndexFields.add(field);
    }

    public void setIndexFields(List<LogicIndexField> indexFields) {
        this.IndexFields = indexFields;
    }

    public boolean isUnique() {
        return this.unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public void toJson(JSONObject json) throws JSONException {
        json.put(TABLENAME, (Object)this.tableName);
        json.put(INDEXNAME, (Object)this.indexName);
        json.put(UNIQUE, this.unique);
        JSONArray fields = new JSONArray();
        for (int i = 0; i < this.IndexFields.size(); ++i) {
            JSONObject field = new JSONObject();
            this.IndexFields.get(i).toJson(field);
            fields.put((Object)field);
        }
        json.put(INDEXFIELDS, (Object)fields);
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.tableName = json.getString(TABLENAME);
        this.indexName = json.getString(INDEXNAME);
        this.unique = json.optBoolean(UNIQUE);
        this.IndexFields.clear();
        JSONArray fields = json.getJSONArray(INDEXFIELDS);
        for (int i = 0; i < fields.length(); ++i) {
            LogicIndexField field = new LogicIndexField();
            field.fromJson(fields.getJSONObject(i));
            this.IndexFields.add(field);
        }
    }

    public LogicIndex clone() {
        try {
            LogicIndex logicIndex = (LogicIndex)super.clone();
            logicIndex.IndexFields = new ArrayList<LogicIndexField>();
            for (int i = 0; i < this.IndexFields.size(); ++i) {
                LogicIndexField field = this.IndexFields.get(i);
                logicIndex.IndexFields.add(field.clone());
            }
            return logicIndex;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }
}

