/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.journalsingle.vo;

import java.util.Date;

public class JournalSubjectVO {
    private String id;
    private String code;
    private String title;
    private String parentId;
    private String jRelateSchemeId;
    private Integer orient;
    private String beforeZbCode;
    private String afterZbCode;
    private Integer needShow;
    private String sortOrder;
    private Date createTime;

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

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getjRelateSchemeId() {
        return this.jRelateSchemeId;
    }

    public void setjRelateSchemeId(String jRelateSchemeId) {
        this.jRelateSchemeId = jRelateSchemeId;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public String getBeforeZbCode() {
        return this.beforeZbCode;
    }

    public void setBeforeZbCode(String beforeZbCode) {
        this.beforeZbCode = beforeZbCode;
    }

    public String getAfterZbCode() {
        return this.afterZbCode;
    }

    public void setAfterZbCode(String afterZbCode) {
        this.afterZbCode = afterZbCode;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getNeedShow() {
        return this.needShow;
    }

    public void setNeedShow(Integer needShow) {
        this.needShow = needShow;
    }
}

