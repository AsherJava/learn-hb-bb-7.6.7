/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.io.record.bean;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;

@DBAnno.DBTable(dbTable="NR_DX_IMPORT_HISTORY")
public class ImportHistory {
    @DBAnno.DBField(dbField="REC_KEY", isPk=true, notUpdate=true)
    private String recKey;
    @DBAnno.DBField(dbField="TASK_KEY", notUpdate=true)
    private String taskKey;
    @DBAnno.DBField(dbField="CALIBER_ENTITY", notUpdate=true)
    private String caliberEntity;
    @DBAnno.DBField(dbField="FORM_SCHEME_KEY", notUpdate=true)
    private String formSchemeKey;
    @DBAnno.DBField(dbField="DATA_TIME", notUpdate=true)
    private String dataTime;
    @DBAnno.DBField(dbField="MAPPING_KEY", notUpdate=true)
    private String mappingKey;
    @DBAnno.DBField(dbField="PARAM", tranWith="transClob", dbType=Clob.class, appType=String.class, notUpdate=true)
    private String param;
    @DBAnno.DBField(dbField="STATE")
    private Integer state;
    @DBAnno.DBField(dbField="CREATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, notUpdate=true)
    private Date createTime;
    @DBAnno.DBField(dbField="END_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date endTime;
    @DBAnno.DBField(dbField="CREATE_USER", notUpdate=true)
    private String createUser;
    @DBAnno.DBField(dbField="PARAM_TYPE", notUpdate=true)
    private String paramType;

    public String getRecKey() {
        return this.recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getCaliberEntity() {
        return this.caliberEntity;
    }

    public void setCaliberEntity(String caliberEntity) {
        this.caliberEntity = caliberEntity;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getMappingKey() {
        return this.mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }
}

