/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.io.Serializable;

public class VaParamSyncMetaRelevanceDO
extends TenantDO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String metaType;
    private String moduleName;
    private String defineName;
    private String defineTitle;
    private String groupName;
    private String modelName;
    private String bizType;

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

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}

