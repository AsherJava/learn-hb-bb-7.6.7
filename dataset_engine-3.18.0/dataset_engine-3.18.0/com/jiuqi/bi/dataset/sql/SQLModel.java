/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.model.DSModel;
import org.json.JSONObject;

public class SQLModel
extends DSModel {
    @Deprecated
    public static final String TYPE = "com.jiuqi.bi.dataset.sql";
    public static final String SQL_TYPE = "com.jiuqi.bi.dataset.sq";
    private String dataSourceId;
    private String dataSourceTitle;
    private String sql;
    private String preSqlScript;
    private String afterSqlScript;
    private static final String TAG_DSID = "dsId";
    private static final String TAG_DSTITLE = "dsTitle";
    private static final String TAG_SQL = "sql";

    @Override
    public String getType() {
        return SQL_TYPE;
    }

    public static boolean isSQLModelType(String type) {
        return type != null && (type.equals(TYPE) || type.equals(SQL_TYPE));
    }

    @Override
    protected void loadExtFromJSON(JSONObject json) throws Exception {
        if (!json.isNull(TAG_DSID)) {
            this.setDataSourceId(json.getString(TAG_DSID));
        }
        if (!json.isNull(TAG_DSTITLE)) {
            this.setDataSourceTitle(json.getString(TAG_DSTITLE));
        }
        if (!json.isNull(TAG_SQL)) {
            this.setSql(json.getString(TAG_SQL));
        }
    }

    @Override
    protected void saveExtToJSON(JSONObject json) throws Exception {
        this.putValue(json, TAG_DSID, this.getDataSourceId());
        this.putValue(json, TAG_DSTITLE, this.getDataSourceTitle());
        this.putValue(json, TAG_SQL, this.getSql());
    }

    public String getDataSourceId() {
        return this.dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataSourceTitle() {
        return this.dataSourceTitle;
    }

    public void setDataSourceTitle(String dataSourceTitle) {
        this.dataSourceTitle = dataSourceTitle;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return this.sql;
    }

    public String getPreSqlScript() {
        return this.preSqlScript;
    }

    public void setPreSqlScript(String preSqlScript) {
        this.preSqlScript = preSqlScript;
    }

    public String getAfterSqlScript() {
        return this.afterSqlScript;
    }

    public void setAfterSqlScript(String afterSqlScript) {
        this.afterSqlScript = afterSqlScript;
    }

    @Override
    public SQLModel clone() {
        return (SQLModel)super.clone();
    }
}

