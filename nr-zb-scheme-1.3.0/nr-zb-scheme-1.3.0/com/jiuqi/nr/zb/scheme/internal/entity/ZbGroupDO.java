/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.entity;

import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_ZB_GROUP")
public class ZbGroupDO
implements ZbGroup {
    @DBAnno.DBField(dbField="ZG_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ZG_SCHEME_KEY")
    private String schemeKey;
    @DBAnno.DBField(dbField="ZG_VERSION")
    private String versionKey;
    @DBAnno.DBField(dbField="ZG_TITLE")
    private String title;
    @DBAnno.DBField(dbField="ZG_PARENT")
    private String parentKey;
    @DBAnno.DBField(dbField="ZG_UPDATE_TIME", tranWith="transTimeStampByInstant", dbType=Timestamp.class, appType=Instant.class)
    private Instant updateTime;
    @DBAnno.DBField(dbField="ZG_LEVEL")
    private String level;
    @DBAnno.DBField(dbField="ZG_ORDER", isOrder=true)
    private String order;

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
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    @Override
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
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
    public String getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(String level) {
        this.level = level;
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
    public String getVersionKey() {
        return this.versionKey;
    }

    @Override
    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }
}

