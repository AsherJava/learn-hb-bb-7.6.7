/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VaParamSyncMetaGroupDO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String metaType;
    private String moduleName;
    private String groupName;
    private String groupTitle;
    private String parentName;
    private String defineName;
    private Integer groupType;
    private String modelName;
    private Integer hasRelevance;
    private List<VaParamSyncMetaGroupDO> children;

    public Integer getHasRelevance() {
        return this.hasRelevance;
    }

    public void setHasRelevance(Integer hasRelevance) {
        this.hasRelevance = hasRelevance;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupTitle() {
        return this.groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<VaParamSyncMetaGroupDO> getChildren() {
        return this.children;
    }

    public void setChildren(List<VaParamSyncMetaGroupDO> children) {
        this.children = children;
    }

    public boolean addChild(VaParamSyncMetaGroupDO child) {
        if (child == null) {
            return false;
        }
        if (this.children == null) {
            this.children = new ArrayList<VaParamSyncMetaGroupDO>();
        }
        return this.children.add(child);
    }

    public boolean addChildren(List<VaParamSyncMetaGroupDO> children) {
        if (children == null || children.isEmpty()) {
            return false;
        }
        if (this.children == null) {
            this.children = new ArrayList<VaParamSyncMetaGroupDO>();
        }
        return this.children.addAll(children);
    }

    public String getDefineName() {
        return this.defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public Integer getGroupType() {
        return this.groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public int hashCode() {
        return this.groupName.hashCode() + this.metaType.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        VaParamSyncMetaGroupDO groupDO = (VaParamSyncMetaGroupDO)obj;
        return groupDO.getGroupName() != null && this.groupName != null && groupDO.getGroupName().equals(this.groupName) && groupDO.getMetaType().equals(this.metaType);
    }
}

