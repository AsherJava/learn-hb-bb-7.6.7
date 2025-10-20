/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.analysisreport.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="sys_analytemplate")
@JsonIgnoreProperties(ignoreUnknown=true)
public class AnalysisReportDefineImpl
implements AnalysisReportDefine {
    private static final long serialVersionUID = -502551398793458648L;
    @DBAnno.DBField(dbField="at_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ag_key")
    private String groupKey;
    @DBAnno.DBField(dbField="at_title", isOrder=true)
    private String title;
    @DBAnno.DBField(dbField="at_description")
    private String description;
    @DBAnno.DBField(dbField="at_data", dbType=Clob.class)
    private String data;
    @DBAnno.DBField(dbField="at_createuser")
    private String createuser;
    @DBAnno.DBField(dbField="at_modifyuser")
    private String modifyuser;
    @DBAnno.DBField(dbField="at_order")
    private String order;
    @DBAnno.DBField(dbField="at_version")
    private String version;
    @DBAnno.DBField(dbField="at_ownerlevelandid")
    private String ownerlevelandid;
    @DBAnno.DBField(dbField="at_updatetime", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updatetime;
    @DBAnno.DBField(dbField="at_printdata", dbType=Clob.class)
    private String printdata;
    @DBAnno.DBField(dbField="at_dimension", dbType=Clob.class)
    private String dimension;
    @DBAnno.DBField(dbField="at_securitylevel")
    private String securityLevel;
    @DBAnno.DBField(dbField="at_periodoffset")
    private int periodOffset;
    @DBAnno.DBField(dbField="AT_CATALOG_UPDATETIME", dbType=Timestamp.class, appType=Date.class)
    private Date atCatalogUpdatetime;
    @DBAnno.DBField(dbField="AT_CATALOG", dbType=Clob.class, appType=String.class)
    private String atCatalog;

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setOwnerLevelAndId(String ownerlevelandid) {
        this.ownerlevelandid = ownerlevelandid;
    }

    @Override
    public void setUpdateTime(Date date) {
        this.updatetime = date;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerlevelandid;
    }

    public Date getUpdateTime() {
        return this.updatetime;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getGroupKey() {
        return this.groupKey;
    }

    @Override
    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
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
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getCreateuser() {
        return this.createuser;
    }

    @Override
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    @Override
    public String getModifyuser() {
        return this.modifyuser;
    }

    @Override
    public void setModifyuser(String modifyuser) {
        this.modifyuser = modifyuser;
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
    public void setPrintData(String data) {
        this.printdata = data;
    }

    @Override
    public String getPrintData() {
        return this.printdata;
    }

    @Override
    public String getDimension() {
        return this.dimension;
    }

    @Override
    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    @Override
    public String getSecurityLevel() {
        return this.securityLevel;
    }

    @Override
    public void setSecurityLevel(String securitylevel) {
        this.securityLevel = securitylevel;
    }

    @Override
    public int getPeriodOffset() {
        return this.periodOffset;
    }

    @Override
    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    @Override
    public String getAtCatalog() {
        return this.atCatalog;
    }

    @Override
    public void setAtCatalog(String atCatalog) {
        this.atCatalog = atCatalog;
    }

    @Override
    public Date getAtCatalogUpdatetime() {
        return this.atCatalogUpdatetime;
    }

    @Override
    public void setAtCatalogUpdatetime(Date atCatalogUpdatetime) {
        this.atCatalogUpdatetime = atCatalogUpdatetime;
    }
}

