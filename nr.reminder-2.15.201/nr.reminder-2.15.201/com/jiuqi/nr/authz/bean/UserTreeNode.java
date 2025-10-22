/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.authz.bean;

import com.jiuqi.nr.authz.IUserEntity;
import com.jiuqi.nr.common.itree.INode;
import java.io.Serializable;

public class UserTreeNode
implements INode,
Serializable {
    private static final long serialVersionUID = -6849731617049191970L;
    private String key;
    private String name;
    private String nickName;
    private String orgCode;
    private String orgName;

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.name;
    }

    public String getTitle() {
        return this.nickName;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public UserTreeNode() {
    }

    public UserTreeNode(String key, String name, String nickName) {
        this.key = key;
        this.name = name;
        this.nickName = nickName;
    }

    public UserTreeNode(String key, String name, String nickName, String orgCode) {
        this.key = key;
        this.name = name;
        this.nickName = nickName;
        this.orgCode = orgCode;
    }

    public UserTreeNode(IUserEntity iUserEntity) {
        this(iUserEntity.getId(), iUserEntity.getName(), iUserEntity.getNickname(), iUserEntity.getOrgCode());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTreeNode)) {
            return false;
        }
        UserTreeNode that = (UserTreeNode)o;
        return this.getName() != null ? this.getName().equals(that.getName()) : that.getName() == null;
    }

    public int hashCode() {
        return this.getName() != null ? this.getName().hashCode() : 0;
    }

    public String toString() {
        return "UserTreeNode{key='" + this.key + '\'' + ", name='" + this.name + '\'' + ", nickName='" + this.nickName + '\'' + '}';
    }
}

