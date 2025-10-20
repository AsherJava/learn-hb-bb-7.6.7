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
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMGROUP")
@DBAnno.DBLink(linkWith=RunTimeFormGroupLink.class, linkField="groupKey", field="key")
public class RunTimeFormGroupDefineImpl
implements FormGroupDefine {
    private static final long serialVersionUID = 4067628311914806981L;
    public static final String TABLE_NAME = "NR_PARAM_FORMGROUP";
    public static final String FIELD_NAME_KEY = "FG_KEY";
    public static final String FIELD_NAME_FORMSCHEME_KEY = "FG_FORM_SCHEME_KEY";
    @DBAnno.DBField(dbField="FG_FORM_SCHEME_KEY", isPk=false)
    private String formSchemeKey;
    @DBAnno.DBField(dbField="fg_parent_key", isPk=false)
    private String parentKey;
    @DBAnno.DBField(dbField="FG_KEY", isPk=true)
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

    public void setKey(String key) {
        this.key = key;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setFormSchemeKey(String fromSchemeKey) {
        this.formSchemeKey = fromSchemeKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}

