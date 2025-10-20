/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.analysisreport.internal;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="sys_analytemplategroup")
public class AnalysisReportGroupDefineImpl
implements AnalysisReportGroupDefine {
    private static final long serialVersionUID = 536461854042388038L;
    @DBAnno.DBField(dbField="ag_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ag_title")
    private String title;
    @DBAnno.DBField(dbField="ag_description")
    private String description;
    @DBAnno.DBField(dbField="ag_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="ag_parent")
    private String parentgroup;
    @DBAnno.DBField(dbField="ag_version")
    private String version;
    @DBAnno.DBField(dbField="ag_ownerlevelandid")
    private String ownerlevelandid;
    @DBAnno.DBField(dbField="ag_updatetime", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updatetime;

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
    public void setParentgroup(String parentgroup) {
        this.parentgroup = parentgroup;
    }

    @Override
    public String getParentgroup() {
        return this.parentgroup;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getOrder() {
        return this.order;
    }
}

