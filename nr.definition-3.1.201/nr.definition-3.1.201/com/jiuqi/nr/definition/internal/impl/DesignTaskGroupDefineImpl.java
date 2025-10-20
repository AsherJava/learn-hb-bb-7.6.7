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
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_TASKGROUP")
@DBAnno.DBLink(linkWith=DesignTaskGroupLink.class, linkField="groupKey", field="key")
@Deprecated
public class DesignTaskGroupDefineImpl
implements DesignTaskGroupDefine {
    private static final long serialVersionUID = 1L;
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
    @DBAnno.DBField(dbField="fl_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="fl_code")
    private String code;
    @DBAnno.DBField(dbField="fl_desc")
    private String description;

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
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
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
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
}

