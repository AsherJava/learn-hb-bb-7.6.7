/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.utils.VersionUtils;

public class ZbSchemeVersionVO {
    private String key;
    private String schemeKey;
    private String code;
    private String order;
    private String title;
    private String startPeriod;
    private String endPeriod;
    private VersionStatus status;
    private String currentPeriod;

    public String getCurrentPeriod() {
        return this.currentPeriod;
    }

    public void setCurrentPeriod(String currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public VersionStatus getStatus() {
        return this.status;
    }

    public void setStatus(VersionStatus status) {
        this.status = status;
    }

    public String getYear() {
        return VersionUtils.getYearByPeriod(this.startPeriod);
    }
}

