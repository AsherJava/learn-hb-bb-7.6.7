/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.resourceview.quantity.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class QuantityBaseDTO {
    private String id;
    private String name;
    private String title;
    private String order;
    private String group;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    private String icon;
    private String resourceType;
    private String subResourceType;
    private String resourceTypeTitle;
    private String subResourceTypeTitle;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setSubResourceType(String subResourceType) {
        this.subResourceType = subResourceType;
    }

    public void setResourceTypeTitle(String resourceTypeTitle) {
        this.resourceTypeTitle = resourceTypeTitle;
    }

    public void setSubResourceTypeTitle(String subResourceTypeTitle) {
        this.subResourceTypeTitle = subResourceTypeTitle;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getGroup() {
        return this.group;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public String getSubResourceType() {
        return this.subResourceType;
    }

    public String getResourceTypeTitle() {
        return this.resourceTypeTitle;
    }

    public String getSubResourceTypeTitle() {
        return this.subResourceTypeTitle;
    }
}

