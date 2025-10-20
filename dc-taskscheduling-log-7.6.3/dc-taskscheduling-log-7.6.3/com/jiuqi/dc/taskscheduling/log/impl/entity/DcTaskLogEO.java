/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.dc.taskscheduling.log.impl.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import java.util.Date;

@DBTable(name="GC_LOG_TASKINFO", title="\u4efb\u52a1\u4fe1\u606f\u4e3b\u8868", indexs={@DBIndex(name="IDX_GC_LOG_TASK_TASKTYPE", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"TASKTYPE"}), @DBIndex(name="IDX_GC_LOG_TASK_EXT_1", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"EXT_1"}), @DBIndex(name="IDX_GC_LOG_TASK_EXT_2", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"EXT_2"})}, kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), dataSource="jiuqi.gcreport.mdd.datasource")
public class DcTaskLogEO
extends DcDefaultTableEntity {
    public static final String TABLENAME = "GC_LOG_TASKINFO";
    private static final long serialVersionUID = -8682220519633626118L;
    @DBColumn(nameInDB="TASKTYPE", title="\u4efb\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=60, order=1)
    private String taskType;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, order=2)
    private Date createTime;
    @DBColumn(nameInDB="MESSAGE", title="\u6d88\u606f\u4fe1\u606f", dbType=DBColumn.DBType.Text, order=3)
    private String message;
    @DBColumn(nameInDB="MESSAGEDIGEST", title="\u6d88\u606f\u6458\u8981", dbType=DBColumn.DBType.NVarchar, length=200, order=4)
    private String messageDigest;
    @DBColumn(nameInDB="ENDTIME", title="\u622a\u6b62\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, order=5)
    private Date endTime;
    @DBColumn(nameInDB="RESULTLOG", title="\u7ed3\u679c\u65e5\u5fd7", dbType=DBColumn.DBType.NVarchar, length=2000, order=6)
    private String resultLog;
    @DBColumn(nameInDB="SOURCETYPE", title="\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=1, defaultValue="0", order=7)
    private String sourceType;
    @DBColumn(nameInDB="EXT_1", title="\u62d3\u5c55\u5b57\u6bb51", dbType=DBColumn.DBType.NVarchar, length=200, order=8)
    private String ext_1;
    @DBColumn(nameInDB="EXT_2", title="\u62d3\u5c55\u5b57\u6bb52", dbType=DBColumn.DBType.NVarchar, length=200, order=9)
    private String ext_2;
    @DBColumn(nameInDB="EXT_3", title="\u62d3\u5c55\u5b57\u6bb53", dbType=DBColumn.DBType.NVarchar, length=200, order=10)
    private String ext_3;
    @DBColumn(nameInDB="EXT_4", title="\u62d3\u5c55\u5b57\u6bb54", dbType=DBColumn.DBType.NVarchar, length=200, order=11)
    private String ext_4;
    @DBColumn(nameInDB="EXT_5", title="\u62d3\u5c55\u5b57\u6bb55", dbType=DBColumn.DBType.NVarchar, length=200, order=12)
    private String ext_5;

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDigest() {
        return this.messageDigest;
    }

    public void setMessageDigest(String messageDigest) {
        this.messageDigest = messageDigest;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getResultLog() {
        return this.resultLog;
    }

    public void setResultLog(String resultLog) {
        this.resultLog = resultLog;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getExt_1() {
        return this.ext_1;
    }

    public void setExt_1(String ext_1) {
        this.ext_1 = ext_1;
    }

    public String getExt_2() {
        return this.ext_2;
    }

    public void setExt_2(String ext_2) {
        this.ext_2 = ext_2;
    }

    public String getExt_3() {
        return this.ext_3;
    }

    public void setExt_3(String ext_3) {
        this.ext_3 = ext_3;
    }

    public String getExt_4() {
        return this.ext_4;
    }

    public void setExt_4(String ext_4) {
        this.ext_4 = ext_4;
    }

    public String getExt_5() {
        return this.ext_5;
    }

    public void setExt_5(String ext_5) {
        this.ext_5 = ext_5;
    }
}

