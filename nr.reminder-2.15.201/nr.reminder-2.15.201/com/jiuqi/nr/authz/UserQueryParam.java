/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.authz;

import java.util.List;

public class UserQueryParam {
    private String roleGroupId;
    private String roleId;
    private List<String> entityKey;
    private List<String> roleIds;
    private String keyword;
    private Boolean locked;
    private Boolean enabled;
    private Integer maxResult;
    private Integer page = 0;

    public String getRoleGroupId() {
        return this.roleGroupId;
    }

    public void setRoleGroupId(String roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(List<String> entityKey) {
        this.entityKey = entityKey;
    }

    public List<String> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getMaxResult() {
        return this.maxResult;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}

