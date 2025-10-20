/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.dc.taskscheduling.log.impl.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.nvwa.definition.common.TableModelKind;

@DBTable(name="GC_TEMP_ARCHIVE", title="\u4e34\u65f6\u65e5\u5fd7\u5b58\u6863\u8868", primaryRequired=false, kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), dataSource="jiuqi.gcreport.mdd.datasource")
public class DcArchiveTempEO
extends BaseEntity {
    private static final long serialVersionUID = -70056654820918447L;
    @DBColumn(nameInDB="RUNNERID", title="\u9884\u5904\u7406\u4efb\u52a1\u5168\u5c40ID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String runnerId;
    @DBColumn(nameInDB="LOGID", title="\u660e\u7ec6\u4efb\u52a1ID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String logId;
    @DBColumn(nameInDB="SQLINFOID", title="sql\u4fe1\u606fID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String sqlInfoId;
    @DBColumn(nameInDB="SQLEXCUTEID", title="sql\u6267\u884c\u4fe1\u606fID", dbType=DBColumn.DBType.NVarchar, length=60)
    private String sqlExcuteId;

    public String getId() {
        return null;
    }

    public void setId(String id) {
    }

    public String getTableName() {
        return "GC_TEMP_ARCHIVE";
    }

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

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

    public String getSqlExcuteId() {
        return this.sqlExcuteId;
    }

    public void setSqlExcuteId(String sqlExcuteId) {
        this.sqlExcuteId = sqlExcuteId;
    }
}

