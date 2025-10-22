/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.RoleGroup
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.authz.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.RoleGroup;
import com.jiuqi.nr.authz.bean.RoleJsonDeserializer;
import com.jiuqi.nr.authz.bean.RoleJsonSerializer;
import com.jiuqi.nr.common.itree.INode;

@JsonSerialize(using=RoleJsonSerializer.class)
@JsonDeserialize(using=RoleJsonDeserializer.class)
public class RoleWebImpl
implements INode {
    public static final String WEB_ROLE_ID = "id";
    public static final String WEB_ROLE_PARENTID = "parentId";
    public static final String WEB_ROLE_CODE = "code";
    public static final String WEB_ROLE_TITLE = "title";
    public static final String WEB_ROLE_GROUPFLAG = "groupFlag";
    public static final String WEB_ROLE_DESCRIPTION = "description";
    public static final String WEB_ROLE_INDEX = "index";
    public static final String WEB_ROLE_KEY = "key";
    private String id;
    private String parentId;
    private String code;
    private String title;
    private Boolean groupFlag;
    private String description;
    private Integer index;
    private String key;

    public RoleWebImpl() {
    }

    public RoleWebImpl(RoleGroup roleGroup) {
        this.groupFlag = true;
        this.id = roleGroup.getId();
        this.parentId = roleGroup.getParentId();
        this.title = roleGroup.getTitle();
        this.description = roleGroup.getDescription();
        this.key = roleGroup.getId().toString();
    }

    public RoleWebImpl(Role role) {
        this.groupFlag = false;
        this.id = role.getId();
        this.parentId = role.getGroupId();
        this.title = role.getTitle();
        this.code = role.getName();
        this.description = role.getDescription();
        this.key = role.getId().toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getGroupFlag() {
        return this.groupFlag;
    }

    public void setGroupFlag(Boolean groupFlag) {
        this.groupFlag = groupFlag;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

