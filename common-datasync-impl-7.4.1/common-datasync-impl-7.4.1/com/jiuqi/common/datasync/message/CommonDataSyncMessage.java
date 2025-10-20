/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.message;

import com.jiuqi.common.datasync.dto.CommonDataSyncSettingItemDTO;

public class CommonDataSyncMessage {
    private CommonDataSyncSettingItemDTO itemDTO;
    private String username;

    public CommonDataSyncMessage() {
    }

    public CommonDataSyncMessage(CommonDataSyncSettingItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    public CommonDataSyncSettingItemDTO getItemDTO() {
        return this.itemDTO;
    }

    public void setItemDTO(CommonDataSyncSettingItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

