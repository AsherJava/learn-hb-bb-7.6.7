/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonProperty$Access
 */
package com.jiuqi.nr.common.resource.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.resource.NrPrivilegeAuthority;
import com.jiuqi.nr.common.resource.NrResource;
import com.jiuqi.nr.common.resource.NrResourceCategory;
import com.jiuqi.nr.common.resource.NrResourceGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourceWebImpl
implements INode,
Comparable<ResourceWebImpl> {
    private String key;
    private String code;
    private String title;
    private Boolean isRole;
    private String categoryId;
    private ResourceType resourceType;
    private List<String> readKinds;
    private List<String> privilegeIds;
    private Object param;
    private int authRightAreaPlan;
    private Integer privilegeType;
    public Integer seq = 1;
    private String ownerId;
    private boolean fromAudit;
    @JsonProperty(access=JsonProperty.Access.READ_ONLY)
    private List<NrPrivilegeAuthority> privilegeAuthorities;

    public ResourceWebImpl() {
    }

    public ResourceWebImpl(NrResourceCategory nrResourceCategory) {
        this.resourceType = ResourceType.CATEGORY;
        this.key = nrResourceCategory.getId();
        this.title = nrResourceCategory.getTitle();
        this.categoryId = nrResourceCategory.getId();
        this.privilegeIds = nrResourceCategory.getPrivilegeIds();
        this.privilegeType = nrResourceCategory.getPrivilegeType();
        this.seq = nrResourceCategory.getSeq();
    }

    public ResourceWebImpl(NrResourceGroup resourceGroup) {
        this.resourceType = ResourceType.GROUP;
        this.title = resourceGroup.getTitle();
        this.privilegeType = resourceGroup.getPrivilegeType();
        this.authRightAreaPlan = resourceGroup.getAuthRightAreaPlan();
        if (resourceGroup.isAuthorisable()) {
            this.privilegeIds = resourceGroup.getPrivilegeIds();
            this.key = resourceGroup.getId();
        } else {
            this.key = UUID.randomUUID().toString();
        }
    }

    public ResourceWebImpl(NrResource nrResource) {
        this.resourceType = ResourceType.RESOURCE;
        this.key = nrResource.getId();
        this.privilegeIds = nrResource.getPrivilegeIds();
        this.privilegeType = nrResource.getPrivilegeType();
        this.title = nrResource.getTitle();
        this.param = nrResource.getParam();
    }

    public Object getParam() {
        return this.param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsRole() {
        return this.isRole;
    }

    public void setIsRole(Boolean isRole) {
        this.isRole = isRole;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public List<String> getReadKinds() {
        return this.readKinds;
    }

    public void setReadKinds(List<String> readKinds) {
        this.readKinds = readKinds;
    }

    public Integer getPrivilegeType() {
        return this.privilegeType;
    }

    public void setPrivilegeType(Integer privilegeType) {
        this.privilegeType = privilegeType;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public int getAuthRightAreaPlan() {
        return this.authRightAreaPlan;
    }

    public void setAuthRightAreaPlan(int authRightAreaPlan) {
        this.authRightAreaPlan = authRightAreaPlan;
    }

    public List<String> getPrivilegeIds() {
        return this.privilegeIds;
    }

    public void setPrivilegeIds(List<String> privilegeIds) {
        this.privilegeIds = privilegeIds;
    }

    public void addPrivilegeIds(String privilegeId) {
        if (this.privilegeIds == null) {
            this.privilegeIds = new ArrayList<String>();
        }
        this.privilegeIds.add(privilegeId);
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<NrPrivilegeAuthority> getPrivilegeAuthorities() {
        return this.privilegeAuthorities;
    }

    public void setPrivilegeAuthorities(List<NrPrivilegeAuthority> privilegeAuthorities) {
        this.privilegeAuthorities = privilegeAuthorities;
    }

    public boolean getFromAudit() {
        return this.fromAudit;
    }

    public void setFromAudit(boolean fromAudit) {
        this.fromAudit = fromAudit;
    }

    @Override
    public int compareTo(ResourceWebImpl o) {
        if (o != null) {
            return this.getSeq().compareTo(o.getSeq());
        }
        return 0;
    }

    public static enum ResourceType {
        ROOT,
        CATEGORY,
        GROUP,
        RESOURCE;

    }
}

