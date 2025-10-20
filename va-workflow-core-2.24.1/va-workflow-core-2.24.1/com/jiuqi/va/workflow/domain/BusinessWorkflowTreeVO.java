/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.List;

public class BusinessWorkflowTreeVO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String id;
    private String moduleName;
    private String groupName;
    private String name;
    private String title;
    private String parentName;
    private String type;
    private String uniqueCode;
    private String version;
    private String simpleVersion;
    private List<BusinessWorkflowTreeVO> children = new ArrayList<BusinessWorkflowTreeVO>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSimpleVersion() {
        return this.simpleVersion;
    }

    public void setSimpleVersion(String simpleVersion) {
        this.simpleVersion = simpleVersion;
    }

    public List<BusinessWorkflowTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<BusinessWorkflowTreeVO> children) {
        this.children = children;
    }
}

