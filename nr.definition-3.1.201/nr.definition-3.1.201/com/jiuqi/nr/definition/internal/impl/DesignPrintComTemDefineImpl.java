/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_PRINTCOMTEM_DES")
public class DesignPrintComTemDefineImpl
implements DesignPrintComTemDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="PC_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="PC_CODE")
    private String code;
    @DBAnno.DBField(dbField="PC_TITLE")
    private String title;
    @DBAnno.DBField(dbField="PC_ORDER", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="PC_PRINT_SCHEME_KEY")
    private String printSchemeKey;
    @DBAnno.DBField(dbField="PC_DESC")
    private String description;
    @DBAnno.DBField(dbField="PC_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="PC_VERSION")
    private String version;
    @DBAnno.DBField(dbField="PC_LEVEL")
    private String ownerLevelAndId;
    private byte[] templateData;

    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
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
    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    @Override
    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    @Override
    public byte[] getTemplateData() {
        return this.templateData;
    }

    @Override
    public void setTemplateData(byte[] templateData) {
        this.templateData = templateData;
    }
}

