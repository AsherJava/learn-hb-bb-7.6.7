/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.org.impl.fieldManager.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_ORGFIELDMANAGER", title="\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5\u7ba1\u7406", inStorage=true)
public class GcOrgFieldEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_ORGFIELDMANAGER";
    @DBColumn(nameInDB="initName", title="\u5b57\u6bb5\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=60)
    private String initName;
    @DBColumn(nameInDB="name_1", title="\u5b57\u6bb5\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=60)
    private String name;
    @DBColumn(nameInDB="code", title="\u5b57\u6bb5code", dbType=DBColumn.DBType.NVarchar, length=60)
    private String code;
    @DBColumn(nameInDB="enableVersion", title="\u542f\u7528\u7248\u672c", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer enableVersion;
    @DBColumn(nameInDB="allowMultiple", title="\u5141\u8bb8\u591a\u9009", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer allowMultiple;
    @DBColumn(nameInDB="enablenull", title="\u5141\u8bb8\u4e3a\u7a7a", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer enableNull;
    @DBColumn(nameInDB="readOnly", title="\u53ea\u8bfb", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer readOnly;
    @DBColumn(nameInDB="sortorder", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Double)
    private Double sortOrder;
    @DBColumn(nameInDB="boxchecked", title="\u662f\u5426\u9009\u4e2d", dbType=DBColumn.DBType.Int, precision=1, scale=0)
    private Integer boxChecked;
    @DBColumn(nameInDB="refTableName", title="\u5bf9\u5e94\u5b58\u50a8\u8868", dbType=DBColumn.DBType.NVarchar, length=60)
    private String refTableName;
    @DBColumn(nameInDB="editable", title="\u662f\u5426\u53ef\u7f16\u8f91", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    @Deprecated
    private Integer editable;

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEnableVersion() {
        return this.enableVersion;
    }

    public void setEnableVersion(Integer enableVersion) {
        this.enableVersion = enableVersion;
    }

    public Integer getAllowMultiple() {
        return this.allowMultiple;
    }

    public void setAllowMultiple(Integer allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    public Integer getEnableNull() {
        return this.enableNull;
    }

    public void setEnableNull(Integer enableNull) {
        this.enableNull = enableNull;
    }

    public Integer getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Integer readOnly) {
        this.readOnly = readOnly;
    }

    public String getInitName() {
        return this.initName;
    }

    public void setInitName(String initName) {
        this.initName = initName;
    }

    public String getRefTableName() {
        return this.refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    public Integer getBoxChecked() {
        return this.boxChecked;
    }

    public void setBoxChecked(Integer boxChecked) {
        this.boxChecked = boxChecked;
    }

    public Integer getEditable() {
        return this.editable;
    }

    public void setEditable(Integer editable) {
        this.editable = editable;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

