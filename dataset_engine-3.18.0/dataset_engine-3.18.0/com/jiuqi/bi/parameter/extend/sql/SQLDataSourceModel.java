/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.extend.sql;

import com.jiuqi.bi.parameter.extend.sql.SQLDataSourceHierarchyType;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class SQLDataSourceModel
extends DataSourceModel
implements Cloneable {
    public static final String TYPE = "com.jiuqi.bi.datasource.sql";
    private String expression;
    private SQLDataSourceHierarchyType sqlHierType = SQLDataSourceHierarchyType.NONE;
    private String dataSouce;
    private String structureCode;
    private static final String TAG_EXPR = "expresion";
    private static final String TAG_HIERTYPE = "sqlHierType";
    private static final String TAG_DATASOURCE = "dataSouce";
    private static final String TAG_STRUCTURECODE = "structureCode";

    public SQLDataSourceModel() {
        this.setType(TYPE);
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public SQLDataSourceHierarchyType getSQLHierarchyType() {
        return this.sqlHierType;
    }

    public void setSQLHierarchyType(SQLDataSourceHierarchyType hierarchyType) {
        this.sqlHierType = hierarchyType;
        ParameterHierarchyType paraHierarchyType = ParameterHierarchyType.NONE;
        if (hierarchyType == SQLDataSourceHierarchyType.PARENTMODE) {
            paraHierarchyType = ParameterHierarchyType.PARENT_SON;
        } else if (hierarchyType == SQLDataSourceHierarchyType.STRUCTURECODE) {
            paraHierarchyType = ParameterHierarchyType.STRUCTURECODE;
        }
        this.setHierarchyType(paraHierarchyType);
    }

    public String getDataSouce() {
        return this.dataSouce;
    }

    public void setDataSouce(String dataSouce) {
        this.dataSouce = dataSouce;
    }

    public String getStructureCode() {
        return this.structureCode;
    }

    public void setStructureCode(String structureCode) {
        this.structureCode = structureCode;
    }

    @Override
    protected void loadExt(JSONObject value) throws JSONException {
        if (!value.isNull(TAG_EXPR)) {
            this.expression = value.getString(TAG_EXPR);
        }
        int hierarchyTypeValue = value.optInt(TAG_HIERTYPE, 0);
        this.setSQLHierarchyType(SQLDataSourceHierarchyType.valueOf(hierarchyTypeValue));
        if (!value.isNull(TAG_DATASOURCE)) {
            this.dataSouce = value.getString(TAG_DATASOURCE);
        }
        if (!value.isNull(TAG_STRUCTURECODE)) {
            this.structureCode = value.getString(TAG_STRUCTURECODE);
        }
    }

    @Override
    protected void saveExt(JSONObject value) throws JSONException {
        if (this.expression != null) {
            value.put(TAG_EXPR, (Object)this.expression);
        }
        value.put(TAG_HIERTYPE, this.sqlHierType.value());
        if (this.dataSouce != null) {
            value.put(TAG_DATASOURCE, (Object)this.dataSouce);
        }
        if (this.structureCode != null) {
            value.put(TAG_STRUCTURECODE, (Object)this.structureCode);
        }
    }

    @Override
    public SQLDataSourceModel clone() {
        try {
            return (SQLDataSourceModel)super.clone();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

