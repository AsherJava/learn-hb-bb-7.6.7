/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.RoleGroup
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.authz.bean;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.RoleGroup;
import com.jiuqi.nr.common.itree.INode;
import java.io.Serializable;

public class RoleTreeNode
implements INode,
Serializable {
    private static final long serialVersionUID = -9009351368031644699L;
    private String key;
    private String code;
    private String title;
    private String parentId;
    private Boolean groupFlag;
    private RoleTreeNode parent;

    public RoleTreeNode() {
    }

    public RoleTreeNode(String key, String code, String title, String parentId, Boolean groupFlag) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.parentId = parentId;
        this.groupFlag = groupFlag;
    }

    public RoleTreeNode(RoleGroup roleGroup) {
        this(roleGroup.getId(), roleGroup.getName(), roleGroup.getTitle(), roleGroup.getParentId(), true);
    }

    public RoleTreeNode(Role role) {
        this(role.getId(), role.getName(), role.getTitle(), role.getGroupId(), false);
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getGroupFlag() {
        return this.groupFlag;
    }

    public void setGroupFlag(Boolean groupFlag) {
        this.groupFlag = groupFlag;
    }

    public RoleTreeNode getParent() {
        return this.parent;
    }

    public void setParent(RoleTreeNode parent) {
        this.parent = parent;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleTreeNode)) {
            return false;
        }
        RoleTreeNode that = (RoleTreeNode)o;
        return this.getKey() != null ? this.getKey().equals(that.getKey()) : that.getKey() == null;
    }

    public int hashCode() {
        return this.getKey() != null ? this.getKey().hashCode() : 0;
    }

    public String toString() {
        return "RoleTreeNode{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", parentId='" + this.parentId + '\'' + ", groupFlag=" + this.groupFlag + '}';
    }
}

