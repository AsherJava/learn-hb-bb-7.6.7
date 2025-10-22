/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.DataFdInfo;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class FormFieldWrapper {
    private boolean isEntityField;
    private DataLinkDefine dataLinkDefine;
    private Object fieldObj;
    private IEntityDefine refEntity;
    private String refTableName;
    private String tableName;
    private boolean isEntityTable;

    public String getRefTableName() {
        return this.refTableName;
    }

    public FormFieldWrapper(DataLinkDefine dl, IFMDMAttribute fmAttr) {
        this.dataLinkDefine = dl;
        this.fieldObj = fmAttr;
        this.isEntityField = true;
    }

    public FormFieldWrapper(DataLinkDefine dl, DataField dataAttr) {
        this.dataLinkDefine = dl;
        this.fieldObj = dataAttr;
        this.isEntityField = true;
    }

    public boolean isEntityField() {
        return this.isEntityField;
    }

    public DataLinkDefine getDataLinkDefine() {
        return this.dataLinkDefine;
    }

    public boolean isFormulaField() {
        return this.dataLinkDefine == null ? false : this.dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA;
    }

    public <T> T getFieldObjAs() {
        return (T)this.fieldObj;
    }

    public Object getFieldObj() {
        return this.fieldObj;
    }

    public IEntityDefine getRefEntity() {
        return this.refEntity;
    }

    public void setRefEntity(IEntityDefine refEntity) {
        this.refEntity = refEntity;
    }

    public void setRefTableName(String tableName) {
        this.refTableName = tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setIsEntityTable(boolean b) {
        this.isEntityTable = b;
    }

    public boolean isEntityTable() {
        return this.isEntityTable;
    }

    public DataFdInfo createFdInfo(EntityQueryHelper entityQueryHelper) {
        DataFdInfo result = new DataFdInfo();
        if (this.getFieldObj() instanceof ColumnModelDefine) {
            ColumnModelDefine colDefine = (ColumnModelDefine)this.getFieldObjAs();
            result.setCode(colDefine.getCode());
            result.setName(colDefine.getName());
            result.setFieldName(colDefine.getCode());
            result.setNullAble(colDefine.isNullAble());
        } else {
            FieldDefine fd = (FieldDefine)this.getFieldObjAs();
            result.setCode(fd.getCode());
            result.setName(fd.getTitle());
            result.setFieldName(entityQueryHelper.getFieldNameByFieldKey(fd.getKey()));
            result.setNullAble(fd.getNullable());
        }
        result.setIsFixedSize(this.refEntity != null && this.refEntity.getTreeStruct() != null && this.refEntity.getTreeStruct().isFixedSize() ? this.refEntity.getTreeStruct().getCodeSize() : -1);
        return result;
    }
}

