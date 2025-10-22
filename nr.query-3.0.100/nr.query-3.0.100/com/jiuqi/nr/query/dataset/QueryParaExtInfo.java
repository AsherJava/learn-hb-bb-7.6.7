/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.nr.query.dataset.BaseParaExtInfo;
import org.json.JSONObject;

public class QueryParaExtInfo
extends BaseParaExtInfo {
    private static final String TAG_ENTITY_VIEW_ID = "entityViewId";
    private static final String TAG_COLUMN_INDEX = "columnIndex";
    private static final String TAG_PERIOD_TYPE = "periodType";
    private static final String TAG_BLOCKID = "blockId";
    private String entityViewId;
    private int columnIndex = -1;
    private int periodType;
    private String blockId;

    public String getEntityViewId() {
        return this.entityViewId;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setEntityViewId(String entityViewId) {
        this.entityViewId = entityViewId;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Override
    protected void loadExtFromJSON(JSONObject extJsonObject) {
        this.entityViewId = extJsonObject.getString(TAG_ENTITY_VIEW_ID);
        this.columnIndex = extJsonObject.getInt(TAG_COLUMN_INDEX);
        this.periodType = extJsonObject.getInt(TAG_PERIOD_TYPE);
        this.blockId = extJsonObject.getString(TAG_BLOCKID);
    }

    @Override
    protected void saveExtToJSON(JSONObject extJsonObject) {
        extJsonObject.put(TAG_ENTITY_VIEW_ID, (Object)this.entityViewId);
        extJsonObject.put(TAG_COLUMN_INDEX, this.columnIndex);
        extJsonObject.put(TAG_PERIOD_TYPE, this.periodType);
        extJsonObject.put(TAG_BLOCKID, (Object)this.blockId);
    }
}

