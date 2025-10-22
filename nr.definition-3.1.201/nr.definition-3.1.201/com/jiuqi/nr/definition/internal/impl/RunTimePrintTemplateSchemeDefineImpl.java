/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_PRINTSCHEME")
public class RunTimePrintTemplateSchemeDefineImpl
implements PrintTemplateSchemeDefine {
    private static final long serialVersionUID = -6254312597681790044L;
    @DBAnno.DBField(dbField="ps_form_scheme_key", isPk=false)
    private String formSchemeKey;
    @DBAnno.DBField(dbField="ps_task_key", isPk=false)
    private String taskKey;
    @DBAnno.DBField(dbField="ps_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ps_title")
    private String title;
    @DBAnno.DBField(dbField="ps_order")
    private String order;
    @DBAnno.DBField(dbField="ps_version")
    private String version;
    @DBAnno.DBField(dbField="ps_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="ps_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
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

    public void setGatherCoverData(byte[] printGatherData) {
        this.gatherCoverData = printGatherData;
    }

    public void setCommonAttribute(byte[] printAttrData) {
        this.commonAttribute = printAttrData;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

