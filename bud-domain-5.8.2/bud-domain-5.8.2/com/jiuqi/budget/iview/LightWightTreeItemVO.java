/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.budget.iview;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.budget.domain.BaseTreeItemVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class LightWightTreeItemVO
extends BaseTreeItemVO {
    private String dataType;
    private String id;
    private String bizId;
    private String bizCode;
    private Map<String, Object> attributes;
    private List<LightWightTreeItemVO> children;
    @JsonIgnore
    private Object data;

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<LightWightTreeItemVO> getChildren() {
        if (this.children == null) {
            return Collections.emptyList();
        }
        return this.children;
    }

    public void setChildren(List<LightWightTreeItemVO> children) {
        this.children = children;
    }

    public void addChild(LightWightTreeItemVO child) {
        if (this.children == null) {
            this.children = new ArrayList<LightWightTreeItemVO>();
        }
        this.children.add(child);
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean hasChild(String childId) {
        if (this.children == null || this.children.isEmpty()) {
            return false;
        }
        for (LightWightTreeItemVO child : this.children) {
            if (!child.getId().equals(childId)) continue;
            return true;
        }
        return false;
    }

    public String getBizId() {
        return this.bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LightWightTreeItemVO{dataType='" + this.dataType + '\'' + ", id=" + this.id + ", bizId='" + this.bizId + '\'' + ", bizCode='" + this.bizCode + '\'' + ", attributes=" + this.attributes + ", children=" + this.children + ", data=" + this.data + '}';
    }
}

