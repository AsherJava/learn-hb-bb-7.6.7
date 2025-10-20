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
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMGROUP_DES")
@DBAnno.DBLink(linkWith=DesignFormGroupLink.class, linkField="groupKey", field="key")
public class DesignFormGroupDefineImpl
implements DesignFormGroupDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="fg_form_scheme_key", isPk=false)
    private String formSchemeKey;
    @DBAnno.DBField(dbField="fg_parent_key", isPk=false)
    private String parentKey;
    @DBAnno.DBField(dbField="fg_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fg_title")
    private String title;
    @DBAnno.DBField(dbField="fg_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="fg_version")
    private String version;
    @DBAnno.DBField(dbField="fg_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="fg_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fg_code")
    private String code;
    @DBAnno.DBField(dbField="fg_desc")
    private String description;
    @DBAnno.DBField(dbField="fg_condition")
    private String condition;
    @DBAnno.DBField(dbField="fg_unit")
    private String measureUnit;

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getParentKey() {
        return this.parentKey;
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
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
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

    @Override
    public void setFormSchemeKey(String fromSchemeKey) {
        this.formSchemeKey = fromSchemeKey;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getCondition() {
        return this.condition;
    }

    @Override
    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @Override
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}

