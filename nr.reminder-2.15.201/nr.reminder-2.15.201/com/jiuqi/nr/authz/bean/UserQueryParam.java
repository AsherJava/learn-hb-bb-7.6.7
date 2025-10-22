/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.authz.bean;

import com.jiuqi.nr.authz.bean.QueryParam;
import java.util.List;

public class UserQueryParam
extends QueryParam {
    private List<String> roleIds;
    private Integer pageCount;
    private Integer page;
    private String entityDataKey;
    private boolean entity;
    private List<String> units;

    public List<String> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getEntityDataKey() {
        return this.entityDataKey;
    }

    public void setEntityDataKey(String entityDataKey) {
        this.entityDataKey = entityDataKey;
    }

    public boolean isEntity() {
        return this.entity;
    }

    public void setEntity(boolean entity) {
        this.entity = entity;
    }

    public List<String> getUnits() {
        return this.units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }
}

