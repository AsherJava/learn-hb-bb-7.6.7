/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.entity;

import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_ZB_VERSION")
public class ZbSchemeVersionDO
implements ZbSchemeVersion {
    @DBAnno.DBField(dbField="ZV_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ZV_SCHEME_KEY")
    private String schemeKey;
    @DBAnno.DBField(dbField="ZV_CODE")
    private String code;
    @DBAnno.DBField(dbField="ZV_ORDER", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="ZV_TITLE")
    private String title;
    @DBAnno.DBField(dbField="ZV_START_PERIOD")
    private String startPeriod;
    @DBAnno.DBField(dbField="ZV_END_PERIOD")
    private String endPeriod;
    @DBAnno.DBField(dbField="ZV_LEVEL")
    private String level;
    @DBAnno.DBField(dbField="ZV_UPDATE_TIME", tranWith="transTimeStampByInstant", dbType=Timestamp.class, appType=Instant.class)
    private Instant updateTime;
    @DBAnno.DBField(dbField="ZV_STATUS", tranWith="transVersionStatus", dbType=Integer.class, appType=VersionStatus.class)
    private VersionStatus status;

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
}

