/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.publicparam.datasource.entity;

import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class NrEntityDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.publicparam.datasource.dimension";
    public static final String TITLE = "\u62a5\u8868\u5b9e\u4f53";
    private static final String TAG_ENTITY_VIEW_ID = "entityViewId";
    private static final String TAG_FORM_SCHEME_KEY = "formSchemeKey";
    private static final String TAG_PROPERTIES = "properties";
    private String entityViewId;
    private String formSchemeKey;
    private String properties;

    public String getEntityViewId() {
        return this.entityViewId;
    }

    public void setEntityViewId(String entityViewId) {
        this.entityViewId = entityViewId;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getType() {
        return TYPE;
    }

    public ParameterDataSourceHierarchyType getHierarchyType() {
        return ParameterDataSourceHierarchyType.PARENTMODE;
    }

    protected void fromExtJson(JSONObject json) throws JSONException {
        this.entityViewId = json.optString(TAG_ENTITY_VIEW_ID);
        this.formSchemeKey = json.optString(TAG_FORM_SCHEME_KEY);
        this.properties = json.optString(TAG_PROPERTIES);
    }

    protected void toExtJson(JSONObject json) throws JSONException {
        json.put(TAG_ENTITY_VIEW_ID, (Object)this.entityViewId);
        json.put(TAG_FORM_SCHEME_KEY, (Object)this.formSchemeKey);
        json.put(TAG_PROPERTIES, (Object)this.properties);
    }

    public String getProperties() {
        return this.properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}

