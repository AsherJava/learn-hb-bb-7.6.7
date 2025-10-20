/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_PRINTSCHEME_DES")
public class DesignPrintTemplateSchemeDefineImpl
implements DesignPrintTemplateSchemeDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="ps_form_scheme_key", isPk=false)
    private String formSchemeKey;
    @DBAnno.DBField(dbField="ps_task_key", isPk=false)
    private String taskKey;
    @DBAnno.DBField(dbField="ps_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ps_title")
    private String title;
    @DBAnno.DBField(dbField="ps_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="ps_version")
    private String version;
    @DBAnno.DBField(dbField="ps_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="ps_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="ps_desc")
    private String description;
    private byte[] commonAttribute;
    private byte[] gatherCoverData;

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
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

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public byte[] getCommonAttribute() {
        return this.commonAttribute;
    }

    @Override
    public byte[] getGatherCoverData() {
        return this.gatherCoverData;
    }

    @Override
    public void setDescription(String desc) {
        this.description = desc;
    }

    @Override
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public void setCommonAttribute(byte[] commonAttribute) {
        this.commonAttribute = commonAttribute;
    }

    @Override
    public void setGatherCoverData(byte[] gatherCoverData) {
        this.gatherCoverData = gatherCoverData;
    }
}

