/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class SQLDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.bi.datasource.sql";
    private String datasourceId;
    private String sql;
    private String structureCode;

    public SQLDataSourceModel() {
    }

    public SQLDataSourceModel(String datasourceId, String sql) {
        this.datasourceId = datasourceId;
        this.sql = sql;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceId() {
        return this.datasourceId;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return this.sql;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public void setStructureCode(String structureCode) {
        this.structureCode = structureCode;
    }

    public String getStructureCode() {
        return this.structureCode;
    }

    @Override
    protected void fromExtJson(JSONObject json) throws JSONException {
        super.fromExtJson(json);
        this.datasourceId = json.optString("datasourceId", "_default");
        if ("\u6570\u636e\u6e90".equals(this.datasourceId)) {
            this.datasourceId = "_default";
        }
        this.sql = json.optString("sql");
        this.structureCode = json.optString("structureCode");
    }

    @Override
    protected void toExtJson(JSONObject json) throws JSONException {
        super.toExtJson(json);
        if ("\u6570\u636e\u6e90".equals(this.datasourceId)) {
            this.datasourceId = "_default";
        }
        json.put("datasourceId", (Object)this.datasourceId);
        json.put("sql", (Object)this.sql);
        json.put("structureCode", (Object)this.structureCode);
    }
}

