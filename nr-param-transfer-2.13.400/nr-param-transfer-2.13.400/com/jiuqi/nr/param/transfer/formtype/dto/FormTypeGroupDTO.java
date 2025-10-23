/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.formtype.facade.FormTypeGroupDefine
 *  com.jiuqi.nr.formtype.internal.bean.FormTypeGroupDefineImpl
 */
package com.jiuqi.nr.param.transfer.formtype.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.internal.bean.FormTypeGroupDefineImpl;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormTypeGroupDTO {
    private String id;
    private String code;
    private String title;
    private String groupId;
    private String desc;
    private String order;
    private Date updateTime;

    public FormTypeGroupDTO() {
    }

    public FormTypeGroupDTO(FormTypeGroupDefine groupDefine) {
        if (groupDefine != null) {
            this.setCode(groupDefine.getCode());
            this.setDesc(groupDefine.getDesc());
            this.setGroupId(groupDefine.getGroupId());
            this.setId(groupDefine.getId());
            this.setTitle(groupDefine.getTitle());
            this.setUpdateTime(groupDefine.getUpdateTime());
            this.setOrder(groupDefine.getOrder());
        }
    }

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

    public FormTypeGroupDefine convertDefine() {
        FormTypeGroupDefineImpl formTypeGroupDefine = new FormTypeGroupDefineImpl();
        formTypeGroupDefine.setId(this.getId());
        formTypeGroupDefine.setCode(this.getCode());
        formTypeGroupDefine.setTitle(this.getTitle());
        formTypeGroupDefine.setGroupId(this.getGroupId());
        formTypeGroupDefine.setDesc(this.getDesc());
        formTypeGroupDefine.setOrder(this.getOrder());
        formTypeGroupDefine.setUpdateTime(this.getUpdateTime());
        return formTypeGroupDefine;
    }
}

