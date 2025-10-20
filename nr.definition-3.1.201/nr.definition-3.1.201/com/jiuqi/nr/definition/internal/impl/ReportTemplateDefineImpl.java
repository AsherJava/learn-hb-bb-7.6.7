/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_REPORT_TEMPLATE")
public class ReportTemplateDefineImpl
implements ReportTemplateDefine {
    @DBAnno.DBField(dbField="RT_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="RT_TASK_KEY")
    private String taskKey;
    @DBAnno.DBField(dbField="RT_FORMSCHEME_KEY")
    private String formSchemeKey;
    @DBAnno.DBField(dbField="RT_CONDITION")
    private String condition;
    @DBAnno.DBField(dbField="RT_FILE_NAME_EXP")
    private String fileNameExp;
    @DBAnno.DBField(dbField="RT_FILE_NAME")
    private String fileName;
    @DBAnno.DBField(dbField="RT_FILE_KEY")
    private String fileKey;
    @DBAnno.DBField(dbField="RT_ORDER", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="RT_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ReportTemplateDefineImpl other = (ReportTemplateDefineImpl)obj;
        return !(this.key == null ? other.key != null : !this.key.equals(other.key));
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String getFileNameExp() {
        return this.fileNameExp;
    }

    public void setFileNameExp(String fileNameExp) {
        this.fileNameExp = fileNameExp;
    }

    @Override
    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

