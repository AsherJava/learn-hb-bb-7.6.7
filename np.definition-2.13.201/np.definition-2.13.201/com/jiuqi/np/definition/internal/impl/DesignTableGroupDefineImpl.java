/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.impl;

import com.jiuqi.np.definition.facade.DesignTableGroupDefine;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="des_sys_tablegroup")
public class DesignTableGroupDefineImpl
implements DesignTableGroupDefine {
    private static final long serialVersionUID = 8625750130639554741L;
    @DBAnno.DBField(dbField="tg_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="tg_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="tg_version")
    private String version;
    @DBAnno.DBField(dbField="tg_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="tg_title")
    private String title;
    @DBAnno.DBField(dbField="tg_desc")
    private String description;
    @DBAnno.DBField(dbField="tg_parent_key")
    private String parentKey;
    @DBAnno.DBField(dbField="tg_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="tg_code")
    private String code;

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}

