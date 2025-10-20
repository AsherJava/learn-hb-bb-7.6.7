/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.domain.metagroup;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.UUID;

public class MetaGroupDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String name;
    private String title;
    private long versionNo;
    private Long rowVersion;
    private String moduleName;
    private String metaType;
    private String parentName;
    private int state;
    private long orgVersion;
    private String uniqueCode;
    private String userName;
    private List<String> nameList;

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

    public long getVersionNo() {
        return this.versionNo;
    }

    public void setVersionNo(long versionNo) {
        this.versionNo = versionNo;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getOrgVersion() {
        return this.orgVersion;
    }

    public void setOrgVersion(long orgVersion) {
        this.orgVersion = orgVersion;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getNameList() {
        return this.nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public Long getRowVersion() {
        return this.rowVersion;
    }

    public void setRowVersion(Long rowVersion) {
        this.rowVersion = rowVersion;
    }
}

