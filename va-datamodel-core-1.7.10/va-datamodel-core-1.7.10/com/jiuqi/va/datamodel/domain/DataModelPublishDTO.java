/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 */
package com.jiuqi.va.datamodel.domain;

import com.jiuqi.va.domain.datamodel.DataModelType;

public class DataModelPublishDTO {
    private String name;
    private String title;
    private DataModelType.BizType biztype;
    private String groupcode;
    private Integer state;
    private Boolean fullComplete;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataModelType.BizType getBiztype() {
        return this.biztype;
    }

    public void setBiztype(DataModelType.BizType biztype) {
        this.biztype = biztype;
    }

    public String getGroupcode() {
        return this.groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean getFullComplete() {
        return this.fullComplete;
    }

    public void setFullComplete(Boolean fullComplete) {
        this.fullComplete = fullComplete;
    }
}

