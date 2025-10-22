/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.np.authz2.privilege.Authority
 */
package com.jiuqi.nr.common.resource.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.nr.common.resource.bean.PrivilegeVOJsonDeserializer;
import java.util.Map;

@JsonDeserialize(using=PrivilegeVOJsonDeserializer.class)
public class PrivilegeVO {
    private String ownerId;
    private Map<String, Map<String, Authority>> authority;
    private Boolean isRole;
    private String resCategoryId;
    private Boolean isDuty;
    private Map<String, Integer> resourceMapPrivilegeType;

    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Map<String, Map<String, Authority>> getAuthority() {
        return this.authority;
    }

    public void setAuthority(Map<String, Map<String, Authority>> authority) {
        this.authority = authority;
    }

    public Boolean getRole() {
        return this.isRole;
    }

    public void setRole(Boolean role) {
        this.isRole = role;
    }

    public String getResCategoryId() {
        return this.resCategoryId;
    }

    public void setResCategoryId(String resCategoryId) {
        this.resCategoryId = resCategoryId;
    }

    public Map<String, Integer> getResourceMapPrivilegeType() {
        return this.resourceMapPrivilegeType;
    }

    public void setResourceMapPrivilegeType(Map<String, Integer> resourceMapPrivilegeType) {
        this.resourceMapPrivilegeType = resourceMapPrivilegeType;
    }

    public Boolean getDuty() {
        return this.isDuty;
    }

    public void setDuty(Boolean duty) {
        this.isDuty = duty;
    }
}

