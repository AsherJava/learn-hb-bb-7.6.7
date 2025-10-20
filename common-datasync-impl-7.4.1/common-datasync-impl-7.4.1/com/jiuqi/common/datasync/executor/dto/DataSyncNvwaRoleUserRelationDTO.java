/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.executor.dto;

import java.util.List;

public class DataSyncNvwaRoleUserRelationDTO {
    private String roleName;
    private List<String> userNames;

    public DataSyncNvwaRoleUserRelationDTO() {
    }

    public DataSyncNvwaRoleUserRelationDTO(String roleName, List<String> userNames) {
        this.roleName = roleName;
        this.userNames = userNames;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getUserNames() {
        return this.userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }
}

