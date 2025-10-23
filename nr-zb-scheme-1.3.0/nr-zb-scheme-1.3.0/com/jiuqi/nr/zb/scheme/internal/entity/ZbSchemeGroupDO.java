/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.entity;

import com.jiuqi.nr.zb.scheme.core.ZbSchemeGroup;
import com.jiuqi.nr.zb.scheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_ZB_SCHEME_GROUP")
public class ZbSchemeGroupDO
implements ZbSchemeGroup {
    @DBAnno.DBField(dbField="ZSG_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ZSG_TITLE")
    private String title;
    @DBAnno.DBField(dbField="ZSG_DESC")
    private String desc;
    @DBAnno.DBField(dbField="ZSG_PARENT")
    private String parentKey;
    @DBAnno.DBField(dbField="ZSG_UPDATE_TIME", tranWith="transTimeStampByInstant", dbType=Timestamp.class, appType=Instant.class)
    private Instant updateTime;
    @DBAnno.DBField(dbField="ZSG_LEVEL")
    private String level;
    @DBAnno.DBField(dbField="ZSG_ORDER", isOrder=true)
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
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
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
}

