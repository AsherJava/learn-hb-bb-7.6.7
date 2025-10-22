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
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskGroupLink;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_TASKGROUP")
@DBAnno.DBLink(linkWith=RunTimeTaskGroupLink.class, linkField="groupKey", field="key")
public class RunTimeTaskGroupDefineImpl
implements DesignTaskGroupDefine {
    @DBAnno.DBField(dbField="fl_parent_key", isPk=false)
    private String parentKey;
    @DBAnno.DBField(dbField="fl_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fl_title")
    private String title;
    @DBAnno.DBField(dbField="fl_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="fl_version")
    private String version;
    @DBAnno.DBField(dbField="fl_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="fl_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, notUpdate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fl_code")
    private String code;
    @DBAnno.DBField(dbField="fl_desc")
    private String description;

    public String getParentKey() {
        return this.parentKey;
    }

    @Override
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}

