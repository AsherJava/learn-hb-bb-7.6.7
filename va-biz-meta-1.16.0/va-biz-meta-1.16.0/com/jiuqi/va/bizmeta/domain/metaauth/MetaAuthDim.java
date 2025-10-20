/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.bizmeta.domain.metaauth;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MetaAuthDim {
    private UUID id;
    private String name;
    private String title;
    private String moduleName;
    private String moduleTitle;
    private String metaType;
    private String groupName;
    private String modelName;
    private String modelTitle;
    private UUID orgId;
    private String uniqueCode;
    private Boolean group;
    private Integer authflag;
    private Integer atauthorize;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleTitle() {
        return this.moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public UUID getOrgId() {
        return this.orgId;
    }

    public void setOrgId(UUID orgId) {
        this.orgId = orgId;
    }

    public String getModelTitle() {
        return this.modelTitle;
    }

    public void setModelTitle(String modelTitle) {
        this.modelTitle = modelTitle;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Boolean getGroup() {
        return this.group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public Integer getAuthflag() {
        return this.authflag;
    }

    public void setAuthflag(Integer authflag) {
        this.authflag = authflag;
    }

    public Integer getAtauthorize() {
        return this.atauthorize;
    }

    public void setAtauthorize(Integer atauthorize) {
        this.atauthorize = atauthorize;
    }
}

