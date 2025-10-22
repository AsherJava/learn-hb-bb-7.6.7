/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.bap.parameter.extend.compatible.remote.RemoteDataSourceModel
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.publicparam.compatible;

import com.jiuqi.nvwa.bap.parameter.extend.compatible.remote.RemoteDataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class NvWaDimDataSourceModel
extends RemoteDataSourceModel {
    private static final String TAG_ENTITY_ID = "entityId";
    private static final String TAG_PARANAME = "paraName";
    private String paraName;
    private String entityId;

    public NvWaDimDataSourceModel() {
        super("com.jiuqi.publicparam.datasource.dimension");
        this.setType("com.jiuqi.publicparam.datasource.dimension");
    }

    public String getParaName() {
        return this.paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    protected void saveExt(JSONObject value) throws JSONException {
        super.saveExt(value);
        value.put(TAG_ENTITY_ID, (Object)this.entityId);
        value.put(TAG_PARANAME, (Object)this.paraName);
    }

    protected void loadExt(JSONObject value) throws JSONException {
        super.loadExt(value);
        if (value.has(TAG_ENTITY_ID)) {
            this.entityId = value.getString(TAG_ENTITY_ID);
        }
        if (value.has(TAG_PARANAME)) {
            this.paraName = value.getString(TAG_PARANAME);
        }
    }
}

