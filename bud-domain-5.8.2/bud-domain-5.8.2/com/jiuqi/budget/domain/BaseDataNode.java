/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.budget.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.nr.common.itree.INode;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BaseDataNode
implements INode {
    private String key;
    private String code;
    private String p;
    private String name;
    private String title;
    private String color;
    private Integer status;
    private Integer childCount;
    private Integer allChildrenCount;
    private String workflowTitle;

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getP() {
        return this.p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkflowTitle() {
        return this.workflowTitle;
    }

    public void setWorkflowTitle(String workflowTitle) {
        this.workflowTitle = workflowTitle;
    }

    public Integer getChildCount() {
        return this.childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public Integer getAllChildrenCount() {
        return this.allChildrenCount;
    }

    public void setAllChildrenCount(Integer allChildrenCount) {
        this.allChildrenCount = allChildrenCount;
    }

    public static BaseDataNode newBaseDataNode(FBaseDataObj baseDataObj) {
        BaseDataNode dataNode = new BaseDataNode();
        dataNode.setCode(baseDataObj.getShowCode());
        dataNode.setKey(baseDataObj.getKey());
        dataNode.setName(baseDataObj.getName());
        dataNode.setTitle(baseDataObj.getName().concat("\uff08").concat(baseDataObj.getShowCode()).concat("\uff09"));
        return dataNode;
    }
}

