/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.dc.base.common.jdbc.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;

@DBTable(name="GC_LOG_SQLEXECUTE", title="SQL\u6267\u884c\u8bb0\u5f55\u65e5\u5fd7\u8868", kind=TableModelKind.SYSTEM_EXTEND, dataSource="jiuqi.gcreport.mdd.datasource", ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), indexs={@DBIndex(name="IDX_GC_LOG_SQLEXECUTE_LOGID", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"LOGID"})})
public class DcSqlExecuteLogEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = 284802013647982940L;
    @DBColumn(nameInDB="LOGID", title="\u65e5\u5fd7ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true, order=1)
    private String logId;
    @DBColumn(nameInDB="SQLINFOID", title="SQL\u4fe1\u606fID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true, order=2)
    private String sqlInfoId;
    @DBColumn(nameInDB="EXECUTEPARAM", title="\u6267\u884c\u53c2\u6570", dbType=DBColumn.DBType.Varchar, length=1000, order=3)
    private String excuteParam;
    @DBColumn(nameInDB="STARTTIME", title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.Long, isRequired=true, order=4)
    private Long startTime;
    @DBColumn(nameInDB="ENDTIME", title="\u7ed3\u675f\u65f6\u95f4", dbType=DBColumn.DBType.Long, isRequired=false, order=5)
    private Long endTime;

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getSqlInfoId() {
        return this.sqlInfoId;
    }

    public void setSqlInfoId(String sqlInfoId) {
        this.sqlInfoId = sqlInfoId;
    }

    public String getExcuteParam() {
        return this.excuteParam;
    }

    public void setExcuteParam(String excuteParam) {
        this.excuteParam = excuteParam;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}

