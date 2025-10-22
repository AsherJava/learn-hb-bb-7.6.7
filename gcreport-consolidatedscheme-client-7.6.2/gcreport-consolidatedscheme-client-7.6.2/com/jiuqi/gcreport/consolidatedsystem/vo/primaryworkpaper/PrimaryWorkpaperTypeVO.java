/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotEmpty
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Size
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PrimaryWorkpaperTypeVO {
    private String id;
    @NotEmpty(message="\u8bf7\u586b\u5199\u540d\u79f0")
    @Size(max=20, message="\u5de5\u4f5c\u5e95\u7a3f\u7c7b\u578b\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc720\u4e2a\u5b57\u7b26")
    private @NotEmpty(message="\u8bf7\u586b\u5199\u540d\u79f0") @Size(max=20, message="\u5de5\u4f5c\u5e95\u7a3f\u7c7b\u578b\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc720\u4e2a\u5b57\u7b26") String title;
    @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a") String parentId;
    private Integer sortOrder;
    private Boolean leafFlag;
    @NotNull(message="\u5408\u5e76\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5408\u5e76\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a") String reportSystem;
    private String dataType;
    private List<PrimaryWorkpaperTypeVO> children;

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

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public String getReportSystem() {
        return this.reportSystem;
    }

    public void setReportSystem(String reportSystem) {
        this.reportSystem = reportSystem;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<PrimaryWorkpaperTypeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<PrimaryWorkpaperTypeVO> children) {
        this.children = children;
    }
}

