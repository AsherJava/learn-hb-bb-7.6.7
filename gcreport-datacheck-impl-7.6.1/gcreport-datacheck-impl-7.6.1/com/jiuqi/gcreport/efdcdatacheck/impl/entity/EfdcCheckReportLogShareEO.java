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
package com.jiuqi.gcreport.efdcdatacheck.impl.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_EFDCCHECKREPORTLOG_SHARE", title="\u6570\u636e\u7a3d\u6838\u62a5\u544a\u65e5\u5fd7\u5171\u4eab\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_EFDCCHECKREPORTLOG_SHARE", columnsFields={"FILE_KEY", "SHARED_USER"})})
public class EfdcCheckReportLogShareEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_EFDCCHECKREPORTLOG_SHARE";
    @DBColumn(nameInDB="FILE_KEY", title="\u6587\u4ef6key", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String fileKey;
    @DBColumn(nameInDB="SHARED_USER", title="\u88ab\u5171\u4eab\u8005", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String sharedUser;
    @DBColumn(nameInDB="SHARED_DATE", dbType=DBColumn.DBType.Date)
    private Date sharedDate;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getSharedUser() {
        return this.sharedUser;
    }

    public void setSharedUser(String sharedUser) {
        this.sharedUser = sharedUser;
    }

    public Date getSharedDate() {
        return this.sharedDate;
    }

    public void setSharedDate(Date sharedDate) {
        this.sharedDate = sharedDate;
    }
}

