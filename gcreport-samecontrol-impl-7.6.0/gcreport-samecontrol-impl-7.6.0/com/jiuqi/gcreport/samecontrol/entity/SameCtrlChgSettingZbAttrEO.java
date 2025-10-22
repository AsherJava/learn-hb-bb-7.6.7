/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.samecontrol.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_SAMECTRL_ZBATTR", title="\u6307\u6807\u5c5e\u6027", indexs={@DBIndex(name="IDX_SAMECTRL_ZBATTR_COM1", columnsFields={"taskId", "schemeId"}), @DBIndex(name="IDX_SAMECTRLCHGORG_FORMKEY", columnsFields={"formKey"})})
public class SameCtrlChgSettingZbAttrEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_SAMECTRL_ZBATTR";
    @DBColumn(title="\u4efb\u52a1Id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848Id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String schemeId;
    @DBColumn(title="\u8868\u5355Id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String formKey;
    @DBColumn(title="\u533a\u57df\u7c7b\u578b", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer dataRegionKind;
    @DBColumn(title="\u6307\u6807Code", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String zbCode;
    @DBColumn(title="\u6307\u6807\u5c5e\u6027", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String zbAttribure;
    private String fieldName;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getDataRegionKind() {
        return this.dataRegionKind;
    }

    public void setDataRegionKind(Integer dataRegionKind) {
        this.dataRegionKind = dataRegionKind;
    }

    public String getZbAttribure() {
        return this.zbAttribure;
    }

    public void setZbAttribure(String zbAttribure) {
        this.zbAttribure = zbAttribure;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}

