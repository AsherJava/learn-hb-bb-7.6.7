/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.journalsingle.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_Journal_RelateScheme", title="\u65e5\u8bb0\u8d26\u5173\u8054\u65b9\u6848")
@DBIndex(name="INDEX_JOURNAL_SCHEME_1", type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE, columnsFields={"SCHEMEID", "ADJUSTTYPE", "TASKID"})
public class JournalRelateSchemeEO
extends DefaultTableEntity {
    public static final String TABLE_NAME = "GC_Journal_RelateScheme";
    @DBColumn(length=36, nameInDB="TASKID", title="\u4efb\u52a1", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(length=36, nameInDB="SCHEMEID", title="\u62a5\u8868\u65b9\u6848", isRequired=true, dbType=DBColumn.DBType.Varchar)
    private String schemeId;
    @DBColumn(length=36, nameInDB="ADJUSTTYPE", title="\u8c03\u6574\u7c7b\u578b\u4ee3\u7801", isRequired=true, dbType=DBColumn.DBType.NVarchar)
    private String adjustType;
    @DBColumn(nameInDB="CREATETIME", title="\u65f6\u95f4\u6233", isRequired=true, dbType=DBColumn.DBType.Date)
    private Date createTime;

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

    public String getAdjustType() {
        return this.adjustType;
    }

    public void setAdjustType(String adjustType) {
        this.adjustType = adjustType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

