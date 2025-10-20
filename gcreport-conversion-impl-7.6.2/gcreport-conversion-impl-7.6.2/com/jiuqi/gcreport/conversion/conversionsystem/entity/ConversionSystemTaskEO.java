/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.conversion.conversionsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CONV_SYSTEM_TS", title="\u6298\u7b97\u4f53\u7cfb\u5173\u8054\u4efb\u52a1", inStorage=true, indexs={@DBIndex(name="IDX_GC_CONVSYSTEMTS_COM1", columnsFields={"TASKID", "SCHEMEID"})})
public class ConversionSystemTaskEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CONV_SYSTEM_TS";
    @DBColumn(nameInDB="SYSTEMID", title="\u6298\u7b97\u4f53\u7cfbID", dbType=DBColumn.DBType.Varchar, length=36)
    private String systemId;
    @DBColumn(nameInDB="RATESCHEMECODE", title="\u6c47\u7387\u65b9\u6848CODE", dbType=DBColumn.DBType.Varchar, length=36)
    private String rateSchemeCode;
    @DBColumn(nameInDB="TASKID", title="\u5173\u8054\u4efb\u52a1id", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId;
    @DBColumn(nameInDB="SCHEMEID", title="\u62a5\u8868\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeId;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createtime;
    @DBColumn(nameInDB="CREATEUSER", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.Varchar, length=36)
    private String createuser;
    @DBColumn(nameInDB="MODIFIEDTIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modifiedtime;
    @DBColumn(nameInDB="MODIFIEDUSER", title="\u4fee\u6539\u4eba", dbType=DBColumn.DBType.Varchar, length=36)
    private String modifieduser;

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getRateSchemeCode() {
        return this.rateSchemeCode;
    }

    public void setRateSchemeCode(String rateSchemeCode) {
        this.rateSchemeCode = rateSchemeCode;
    }

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

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return this.createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Date getModifiedtime() {
        return this.modifiedtime;
    }

    public void setModifiedtime(Date modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public String getModifieduser() {
        return this.modifieduser;
    }

    public void setModifieduser(String modifieduser) {
        this.modifieduser = modifieduser;
    }
}

