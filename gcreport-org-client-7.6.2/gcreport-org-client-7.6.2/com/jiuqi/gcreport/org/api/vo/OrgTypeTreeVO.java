/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrgTypeTreeVO {
    private String id;
    private String title;
    private String categoryName;
    private Date validTime;
    private Date invalidTime;
    private Boolean groupFlag;
    private List<OrgTypeTreeVO> children = new ArrayList<OrgTypeTreeVO>();

    public OrgTypeTreeVO() {
    }

    public OrgTypeTreeVO(String id, String title, Boolean groupFlag) {
        this.id = id;
        this.title = title;
        this.groupFlag = groupFlag;
    }

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

    public List<OrgTypeTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<OrgTypeTreeVO> children) {
        this.children = children;
    }

    public Boolean getGroupFlag() {
        return this.groupFlag;
    }

    public void setGroupFlag(Boolean groupFlag) {
        this.groupFlag = groupFlag;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getValidTime() {
        return this.validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Date getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }
}

