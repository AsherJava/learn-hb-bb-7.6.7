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
package com.jiuqi.gcreport.financialcheckcore.sqlutils.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_RELTX_IDTEMPORARY", title="\u5173\u8054\u4ea4\u6613\u96c6\u5408\u53c2\u6570\u4e34\u65f6\u8868", indexs={@DBIndex(name="IDX_GC_RELTX_IDTEMPORARY_GROUPID", columnsFields={"GROUP_ID"})}, dataSource="jiuqi.gcreport.mdd.datasource")
public class ReltxIdTemporary
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_RELTX_IDTEMPORARY";
    @DBColumn(nameInDB="GROUP_ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String groupId;
    @DBColumn(nameInDB="TBCODE", dbType=DBColumn.DBType.NVarchar, length=100)
    private String tbCode;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTbCode() {
        return this.tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }
}

