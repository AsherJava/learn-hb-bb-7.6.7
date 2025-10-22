/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClbrSchemeTreeVO {
    private String id;
    private String parentId;
    private Boolean leafFlag;
    private String title;
    private String schemeDesc;
    private String clbrInfo;
    private String flowControlType;
    private String vchrControlType;
    private Date createTime;
    private List<ClbrSchemeTreeVO> children = new ArrayList<ClbrSchemeTreeVO>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClbrInfo() {
        return this.clbrInfo;
    }

    public void setClbrInfo(String clbrInfo) {
        this.clbrInfo = clbrInfo;
    }

    public String getFlowControlType() {
        return this.flowControlType;
    }

    public void setFlowControlType(String flowControlType) {
        this.flowControlType = flowControlType;
    }

    public String getVchrControlType() {
        return this.vchrControlType;
    }

    public void setVchrControlType(String vchrControlType) {
        this.vchrControlType = vchrControlType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setChildren(List<ClbrSchemeTreeVO> children) {
        this.children = children;
    }

    public List<ClbrSchemeTreeVO> getChildren() {
        return this.children;
    }

    public void addChildren(ClbrSchemeTreeVO clbrSchemeTreeVO) {
        this.children.add(clbrSchemeTreeVO);
    }

    public String getSchemeDesc() {
        return this.schemeDesc;
    }

    public void setSchemeDesc(String schemeDesc) {
        this.schemeDesc = schemeDesc;
    }
}

