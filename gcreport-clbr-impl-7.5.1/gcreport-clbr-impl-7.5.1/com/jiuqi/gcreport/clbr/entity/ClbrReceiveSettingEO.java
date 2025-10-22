/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.clbr.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_CLBR_RECEIVE_SETTING", title="\u534f\u540c\u63a5\u6536\u65b9\u4ee3\u529e\u6d88\u606f\u914d\u7f6e")
public class ClbrReceiveSettingEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CLBR_RECEIVE_SETTING";
    private static final long serialVersionUID = 8579287484193018275L;
    @DBColumn(title="\u63a5\u6536\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=500)
    private String oppClbrTypes;
    @DBColumn(title="\u7ec4\u7ec7\u673a\u6784", dbType=DBColumn.DBType.Varchar, length=1000)
    private String oppRelation;
    @DBColumn(title="\u89d2\u8272", dbType=DBColumn.DBType.Varchar, length=500)
    private String roleCodes;
    @DBColumn(title="\u7528\u6237", dbType=DBColumn.DBType.Varchar, length=500)
    private String userNames;
    @DBColumn(title="\u90e8\u95e8", dbType=DBColumn.DBType.Varchar, length=100)
    private String department;

    public String getOppClbrTypes() {
        return this.oppClbrTypes;
    }

    public void setOppClbrTypes(String oppClbrTypes) {
        this.oppClbrTypes = oppClbrTypes;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public String getRoleCodes() {
        return this.roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getUserNames() {
        return this.userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

