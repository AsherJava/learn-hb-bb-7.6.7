/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.reportdatasync.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_PARAMSYNC_RECEIVE_LOGITEM", title="\u56fd\u8d44\u59d4\u53c2\u6570\u540c\u6b65\u63a5\u6536\u4efb\u52a1\u6267\u884c\u65e5\u5fd7\u660e\u7ec6\u8868")
public class ReportDataSyncReceiveLogItemEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_PARAMSYNC_RECEIVE_LOGITEM";
    @DBColumn(title="\u63a5\u6536\u4efb\u52a1\u65e5\u5fd7Id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String receiveTaskLogId;
    @DBColumn(title="\u540c\u6b65\u66f4\u65b0\u6267\u884c\u65e5\u5fd7\u8be6\u60c5", dbType=DBColumn.DBType.Varchar, length=2000)
    private String syncLogInfo;

    public String getReceiveTaskLogId() {
        return this.receiveTaskLogId;
    }

    public void setReceiveTaskLogId(String receiveTaskLogId) {
        this.receiveTaskLogId = receiveTaskLogId;
    }

    public String getSyncLogInfo() {
        return this.syncLogInfo;
    }

    public void setSyncLogInfo(String syncLogInfo) {
        if (syncLogInfo != null && syncLogInfo.length() > 2000) {
            syncLogInfo = syncLogInfo.substring(0, 2000);
        }
        this.syncLogInfo = syncLogInfo;
    }
}

