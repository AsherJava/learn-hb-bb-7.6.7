/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 */
package com.jiuqi.nr.param.transfer.definition.dto.formgroup;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormGroupLinkDTO {
    private String groupKey;
    private String formKey;
    private String level;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String formOrder;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFormOrder() {
        return this.formOrder;
    }

    public void setFormOrder(String formOrder) {
        this.formOrder = formOrder;
    }

    public static FormGroupLinkDTO valueOf(DesignFormGroupLink groupLink) {
        if (groupLink == null) {
            return null;
        }
        FormGroupLinkDTO link = new FormGroupLinkDTO();
        link.setFormKey(groupLink.getFormKey());
        link.setFormOrder(groupLink.getFormOrder());
        link.setGroupKey(groupLink.getGroupKey());
        link.setLevel(groupLink.getLevel());
        link.setUpdateTime(groupLink.getUpdateTime());
        return link;
    }

    public DesignFormGroupLink value2Define() {
        DesignFormGroupLink link = new DesignFormGroupLink();
        link.setFormKey(this.getFormKey());
        link.setFormOrder(this.getFormOrder());
        link.setGroupKey(this.getGroupKey());
        link.setLevel(this.getLevel());
        link.setUpdateTime(this.getUpdateTime());
        return link;
    }
}

