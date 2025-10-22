/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class QueryDSField
extends DSField {
    private String expresion;
    private ColumnModelDefine columnModel;
    private String entityTableName;

    public QueryDSField() {
    }

    public QueryDSField(String expresion) {
        this.expresion = expresion;
    }

    public QueryDSField(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }

    public String getExpresion() {
        return this.expresion;
    }

    public String getEntityTableName() {
        return this.entityTableName;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public void setEntityTableName(String entityTableName) {
        this.entityTableName = entityTableName;
    }

    public String getKeyFieldName() {
        if (this.entityTableName != null) {
            return this.entityTableName + "_CODE";
        }
        return null;
    }

    public String getNameFieldName() {
        if (this.entityTableName != null) {
            return this.entityTableName + "_TITLE";
        }
        return null;
    }

    public String getParentFieldName() {
        if (this.entityTableName != null) {
            return this.entityTableName + "_PARENT";
        }
        return null;
    }

    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }

    public void setColumnModel(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }
}

