/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.bean;

import java.io.Serializable;
import java.util.Date;

public class WorkflowSettingDefine
implements Serializable {
    private static final long serialVersionUID = 8795682642673780800L;
    private String id;
    private String title;
    private String dataType;
    private String dataId;
    private String[] dataObj;
    private String workflowId;
    private String desc;
    private String createUser;
    private Date updateTime;
    private String version;
    private boolean chooseAll;
    private String order;
    private Date effectiveTime;
    private int type;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getWorkflowId() {
        return this.workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String[] getDataObj() {
        return this.dataObj;
    }

    public void setDataObj(String[] dataObj) {
        this.dataObj = dataObj;
    }

    public boolean isChooseAll() {
        return this.chooseAll;
    }

    public void setChooseAll(boolean chooseAll) {
        this.chooseAll = chooseAll;
    }

    public Date getEffectiveTime() {
        return this.effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

