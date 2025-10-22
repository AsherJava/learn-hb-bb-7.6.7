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
import com.jiuqi.nr.common.resource.bean.PrivilegeJsonDeserializer;
import com.jiuqi.nr.common.resource.bean.UserAppContext;
import java.util.List;
import java.util.Map;

@JsonDeserialize(using=PrivilegeJsonDeserializer.class)
public class PrivilegeWebImpl
extends UserAppContext {
    public static final String WEB_PRIVILEGE_OWNERID = "ownerId";
    public static final String WEB_PRIVILEGE_ISROLE = "isRole";
    public static final String WEB_PRIVILEGE_ISDUTY = "isDuty";
    public static final String WEB_PRIVILEGE_RESCATEGORYID = "resCategoryId";
    public static final String WEB_PRIVILEGE_RESOURCEIDMAPPRIVILEGEIDS = "resourceIdMapPrivilegeIds";
    public static final String WEB_PRIVILEGE_AUTHORITY = "authority";
    public static final String WEB_PRIVILEGE_RESOURCEMAPPRIVILEGETYPE = "resourceMapPrivilegeType";
    private String ownerId;
    private Boolean isRole;
    private String resCategoryId;
    private Boolean isDuty;
    private Map<String, List<String>> resourceIdMapPrivilegeIds;
    Map<String, Object> authTypes;
    private Map<String, Integer> resourceMapPrivilegeType;
    private Map<String, Map<String, Authority>> authority;

    public Map<String, Object> getAuthTypes() {
        return this.authTypes;
    }

    public void setAuthTypes(Map<String, Object> authTypes) {
        this.authTypes = authTypes;
    }

    public Map<String, Integer> getResourceMapPrivilegeType() {
        return this.resourceMapPrivilegeType;
    }

    public void setResourceMapPrivilegeType(Map<String, Integer> resourceMapPrivilegeType) {
        this.resourceMapPrivilegeType = resourceMapPrivilegeType;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getIsRole() {
        return this.isRole;
    }

    public void setIsRole(Boolean isRole) {
        this.isRole = isRole;
    }

    public String getResCategoryId() {
        return this.resCategoryId;
    }

    public void setResCategoryId(String resCategoryId) {
        this.resCategoryId = resCategoryId;
    }

    public Map<String, List<String>> getResourceIdMapPrivilegeIds() {
        return this.resourceIdMapPrivilegeIds;
    }

    public void setResourceIdMapPrivilegeIds(Map<String, List<String>> resourceIdMapPrivilegeIds) {
        this.resourceIdMapPrivilegeIds = resourceIdMapPrivilegeIds;
    }

    public Map<String, Map<String, Authority>> getAuthority() {
        return this.authority;
    }

    public void setAuthority(Map<String, Map<String, Authority>> authority) {
        this.authority = authority;
    }

    public Boolean getDuty() {
        return this.isDuty;
    }

    public void setDuty(Boolean duty) {
        this.isDuty = duty;
    }
}

