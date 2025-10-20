/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import com.jiuqi.np.user.dto.UserDTO;

public class UserAttributeTypeValuesDTO
extends UserDTO {
    private static final long serialVersionUID = 1L;
    private String defineKey;
    private String parentId;
    private String key;

    public String getDefineKey() {
        return this.defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

