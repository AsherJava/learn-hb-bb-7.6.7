/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.formtype.facade.FormTypeDefine
 *  com.jiuqi.nr.formtype.internal.bean.FormTypeDefineImpl
 */
package com.jiuqi.nr.param.transfer.formtype.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.internal.bean.FormTypeDefineImpl;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormTypeDTO {
    private String id;
    private String code;
    private String title;
    private String groupId;
    private String desc;
    private String order;
    private Date updateTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public FormTypeDefine convertDefine() {
        FormTypeDefineImpl formTypeDefine = new FormTypeDefineImpl();
        formTypeDefine.setId(this.getId());
        formTypeDefine.setCode(this.getCode());
        formTypeDefine.setTitle(this.getTitle());
        formTypeDefine.setGroupId(this.getGroupId());
        formTypeDefine.setDesc(this.getDesc());
        formTypeDefine.setOrder(this.getOrder());
        formTypeDefine.setUpdateTime(this.getUpdateTime());
        return formTypeDefine;
    }
}

