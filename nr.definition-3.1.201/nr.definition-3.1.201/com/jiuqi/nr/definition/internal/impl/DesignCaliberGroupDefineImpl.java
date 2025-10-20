/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.DesignCaliberGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignCaliberGroupLink;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="des_sys_calibergroupdefine")
@DBAnno.DBLink(linkWith=DesignCaliberGroupLink.class, linkField="groupKey", field="key")
public class DesignCaliberGroupDefineImpl
implements DesignCaliberGroupDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="ca_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ca_title")
    private String title;
    @DBAnno.DBField(dbField="ca_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="ca_version")
    private String version;
    @DBAnno.DBField(dbField="ca_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, notUpdate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="ca_code")
    private String code;
    @DBAnno.DBField(dbField="ca_desc")
    private String description;
    @DBAnno.DBField(dbField="ca_parent_key", isPk=false)
    private String parentKey;

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public String getCode() {
        return this.code;
    }

    public String getKey() {
        return this.key;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

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
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setParentKey(String parentkey) {
        this.parentKey = parentkey;
    }

    public String getOwnerLevelAndId() {
        return null;
    }
}

