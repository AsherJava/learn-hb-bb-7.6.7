/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.domain.user;

import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.mapper.domain.PageDTO;

public class UserDTO
extends UserDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private boolean pagination;
    private int offset;
    private int limit;
    private String searchKey;
    private String roleName;
    private boolean checkPwd = true;
    private String[] userIds;

    public boolean isPagination() {
        return this.pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCheckPwd() {
        return this.checkPwd;
    }

    public void setCheckPwd(boolean checkPwd) {
        this.checkPwd = checkPwd;
    }

    public String[] getUserIds() {
        return this.userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }
}

