/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.executor.dto;

import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaAttributeDTO;
import java.util.List;

public class DataSyncNvwaRoleDTO {
    private String id;
    private String name;
    private String title;
    private String description;
    private int dataStatus;
    private String lastModifyTime;
    private List<DataSyncNvwaAttributeDTO> attributes;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDataStatus() {
        return this.dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public List<DataSyncNvwaAttributeDTO> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<DataSyncNvwaAttributeDTO> attributes) {
        this.attributes = attributes;
    }
}

