/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.snapshot.dataset.datasource;

import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class NrSnapshotDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.publicparam.datasource.snapshot";
    public static final String TITLE = "\u62a5\u8868\u5feb\u7167";
    private String taskKey;

    public NrSnapshotDataSourceModel() {
    }

    public NrSnapshotDataSourceModel(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getType() {
        return TYPE;
    }

    public ParameterDataSourceHierarchyType getHierarchyType() {
        return ParameterDataSourceHierarchyType.PARENTMODE;
    }

    protected void fromExtJson(JSONObject json) throws JSONException {
        this.taskKey = json.optString("taskKey");
    }

    protected void toExtJson(JSONObject json) throws JSONException {
        json.put("taskKey", (Object)this.taskKey);
    }
}

