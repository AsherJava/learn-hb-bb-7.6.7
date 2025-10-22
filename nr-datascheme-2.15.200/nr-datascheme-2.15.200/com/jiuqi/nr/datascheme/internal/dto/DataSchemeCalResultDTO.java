/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.nr.datascheme.api.core.CalResult
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeCalResult
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.datascheme.api.core.CalResult;
import com.jiuqi.nr.datascheme.api.core.DataSchemeCalResult;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeCalResultDO;
import java.time.Instant;

public class DataSchemeCalResultDTO
extends DataSchemeCalResultDO {
    private String key;
    private String dataSchemeKey;
    private CalResult calResult;
    private String message;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Instant updateTime;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    @Override
    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    @Override
    public CalResult getCalResult() {
        return this.calResult;
    }

    @Override
    public void setCalResult(CalResult calResult) {
        this.calResult = calResult;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public static DataSchemeCalResultDTO valueOf(DataSchemeCalResult o) {
        if (o == null) {
            return null;
        }
        DataSchemeCalResultDTO dataSchemeCalResultDTO = new DataSchemeCalResultDTO();
        dataSchemeCalResultDTO.setKey(o.getKey());
        dataSchemeCalResultDTO.setDataSchemeKey(o.getDataSchemeKey());
        dataSchemeCalResultDTO.setCalResult(o.getCalResult());
        dataSchemeCalResultDTO.setMessage(o.getMessage());
        dataSchemeCalResultDTO.setUpdateTime(o.getUpdateTime());
        return dataSchemeCalResultDTO;
    }
}

