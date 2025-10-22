/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.entity.engine.definitions;

import com.jiuqi.nr.entity.engine.setting.FieldsInfoImpl;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class TableRunInfo {
    private String tableName;
    private String dimensionName;
    private TableModelDefine tableModelDefine;
    private IEntityModel entityModel;
    private FieldsInfoImpl fieldsInfo;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TableModelDefine getTableModelDefine() {
        return this.tableModelDefine;
    }

    public void setTableModelDefine(TableModelDefine tableModelDefine) {
        this.tableModelDefine = tableModelDefine;
    }

    public IEntityModel getEntityModel() {
        return this.entityModel;
    }

    public void setEntityModel(IEntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public FieldsInfoImpl getFieldsInfo() {
        return this.fieldsInfo;
    }

    public void setFieldsInfo(FieldsInfoImpl fieldsInfo) {
        this.fieldsInfo = fieldsInfo;
    }
}

