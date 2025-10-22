/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.publicparam.datasource.caliberdim;

import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class NrCaliberDimDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.publicparam.datasource.caliber";
    public static final String TITLE = "\u62a5\u8868\u53e3\u5f84\u7ef4\u5ea6";
    private static final String TAG_ENTITY_VIEW_ID = "entityViewId";
    private String entityViewId;

    public String getEntityViewId() {
        return this.entityViewId;
    }

    public void setEntityViewId(String entityViewId) {
        this.entityViewId = entityViewId;
    }

    public String getType() {
        return TYPE;
    }

    public ParameterDataSourceHierarchyType getHierarchyType() {
        return ParameterDataSourceHierarchyType.PARENTMODE;
    }

    protected void fromExtJson(JSONObject json) throws JSONException {
        this.entityViewId = json.optString(TAG_ENTITY_VIEW_ID);
    }

    protected void toExtJson(JSONObject json) throws JSONException {
        json.put(TAG_ENTITY_VIEW_ID, (Object)this.entityViewId);
    }
}

