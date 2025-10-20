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
 *  javax.persistence.Column
 */
package com.jiuqi.dc.taskscheduling.lockmgr.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import javax.persistence.Column;

@DBTable(name="DC_TASKMANAGE", title="\u4efb\u52a1\u4fe1\u606f\u8868", indexs={@DBIndex(name="UK_TASKMANAGE_TASKNAME_UNIT", type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE, columnsFields={"TASKNAME", "UNITCODE"})}, kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), dataSource="jiuqi.gcreport.mdd.datasource")
public class TaskMgrEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = -5936063697335549191L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true)
    private Long ver;
    @DBColumn(nameInDB="TASKNAME", title="\u4efb\u52a1\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=60)
    private String taskName;
    @DBColumn(nameInDB="UNITCODE", title="\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60)
    private String unitCode;
    @DBColumn(nameInDB="BATCHNUM", title="\u6279\u6b21\u53f7", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private String batchNum;
    @DBColumn(nameInDB="BEGINTIME", title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private String beginTime;

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
}

