/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dto;

import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import java.time.Instant;

public class ZbSchemeVersionDTO
implements ZbSchemeVersion {
    private String key;
    private String schemeKey;
    private String code;
    private String order;
    private String title;
    private String startPeriod;
    private String endPeriod;
    private String level;
    private Instant updateTime;
    private VersionStatus status;
    private boolean copyLast;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    @Override
    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getStartPeriod() {
        return this.startPeriod;
    }

    @Override
    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    @Override
    public String getEndPeriod() {
        return this.endPeriod;
    }

    @Override
    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    @Override
    public String getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public VersionStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(VersionStatus status) {
        this.status = status;
    }

    public boolean isCopyLast() {
        return this.copyLast;
    }

    public void setCopyLast(boolean copyLast) {
        this.copyLast = copyLast;
    }
}

