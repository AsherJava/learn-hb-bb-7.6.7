/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.examine.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.examine.common.ExamineStatus;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAMEXAMINE_INFO")
public class ParamExamineInfo {
    @DBAnno.DBField(dbField="PEI_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="PEI_USER")
    private String userId;
    @DBAnno.DBField(dbField="PEI_DATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, isOrder=true)
    private Date checkDate;
    @DBAnno.DBField(dbField="PEI_STATUS", tranWith="transExamineStatus", dbType=Integer.class, appType=ExamineStatus.class)
    private ExamineStatus status;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCheckDate() {
        return this.checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public ExamineStatus getStatus() {
        return this.status;
    }

    public void setStatus(ExamineStatus status) {
        this.status = status;
    }
}

