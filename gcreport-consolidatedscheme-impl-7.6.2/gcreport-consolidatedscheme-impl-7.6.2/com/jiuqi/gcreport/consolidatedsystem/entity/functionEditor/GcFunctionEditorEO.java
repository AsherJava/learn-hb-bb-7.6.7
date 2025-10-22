/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.entity.functionEditor;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_FUNCTIONEDITOR", title="\u516c\u5f0f\u7f16\u8f91\u5668\u516c\u5f0f", inStorage=true)
public class GcFunctionEditorEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_FUNCTIONEDITOR";
    @DBColumn(nameInDB="code", dbType=DBColumn.DBType.NVarchar, length=500)
    private String code;
    @DBColumn(nameInDB="detail", dbType=DBColumn.DBType.NVarchar, length=500)
    private String detail;
    @DBColumn(nameInDB="userId", dbType=DBColumn.DBType.Varchar, length=36)
    private String userId;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        return "GcFunctionEditorEO{code='" + this.code + '\'' + ", detail='" + this.detail + '\'' + ", userId=" + this.userId + '}';
    }
}

