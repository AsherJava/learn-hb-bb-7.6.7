/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.executor.dto;

import com.jiuqi.common.datasync.executor.dto.DataSyncNvwaAttributeDTO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DataSyncNvwaOrganizationDTO {
    private String id;
    private String code;
    private String name;
    private String type;
    private String shortName;
    private Date validTime;
    private Date invalidTime;
    private String parentCode;
    private boolean stop;
    private boolean recovery;
    private BigDecimal ordinal;
    private String createTime;
    private String createUser;
    private int dataStatus;
    private String lastModifyTime;
    private List<DataSyncNvwaAttributeDTO> attributes;

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Date getValidTime() {
        return this.validTime;
    }

    public Date getInvalidTime() {
        return this.invalidTime;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public boolean isStop() {
        return this.stop;
    }

    public boolean isRecovery() {
        return this.recovery;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setRecovery(boolean recovery) {
        this.recovery = recovery;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

