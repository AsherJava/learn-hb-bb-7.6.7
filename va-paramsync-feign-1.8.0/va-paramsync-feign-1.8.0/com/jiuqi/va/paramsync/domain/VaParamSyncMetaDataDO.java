/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.paramsync.domain.VaParamSyncMetaRelevanceDO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VaParamSyncMetaDataDO
implements Serializable {
    private static final long serialVersionUID = 2405172041950251809L;
    private String metaType;
    private String moduleName;
    private String defineName;
    private String defineTitle;
    private String groupName;
    private String defineVer;
    private String modelName;
    private String bizType;
    private List<VaParamSyncMetaRelevanceDO> relevance;

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

    public String getDefineName() {
        return this.defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public String getDefineTitle() {
        return this.defineTitle;
    }

    public void setDefineTitle(String defineTitle) {
        this.defineTitle = defineTitle;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDefineVer() {
        return this.defineVer;
    }

    public void setDefineVer(String defineVer) {
        this.defineVer = defineVer;
    }

    public List<VaParamSyncMetaRelevanceDO> getRelevance() {
        return this.relevance;
    }

    public boolean addRelevanceItem(VaParamSyncMetaRelevanceDO relevanceItem) {
        if (relevanceItem == null) {
            return false;
        }
        if (this.relevance == null || this.relevance.isEmpty()) {
            this.relevance = new ArrayList<VaParamSyncMetaRelevanceDO>();
        }
        return this.relevance.add(relevanceItem);
    }

    public boolean addRelevance(List<VaParamSyncMetaRelevanceDO> relevance) {
        if (relevance == null || relevance.isEmpty()) {
            return false;
        }
        if (this.relevance == null || this.relevance.isEmpty()) {
            this.relevance = new ArrayList<VaParamSyncMetaRelevanceDO>();
        }
        return this.relevance.addAll(relevance);
    }

    public void setRelevance(List<VaParamSyncMetaRelevanceDO> relevance) {
        this.relevance = relevance;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public int hashCode() {
        return Objects.hash(this.metaType, this.moduleName, this.defineName);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        VaParamSyncMetaDataDO dataDO = (VaParamSyncMetaDataDO)obj;
        return Objects.equals(this.metaType, dataDO.metaType) && Objects.equals(this.moduleName, dataDO.moduleName) && Objects.equals(this.defineName, dataDO.defineName) && Objects.equals(this.bizType, dataDO.bizType);
    }
}

