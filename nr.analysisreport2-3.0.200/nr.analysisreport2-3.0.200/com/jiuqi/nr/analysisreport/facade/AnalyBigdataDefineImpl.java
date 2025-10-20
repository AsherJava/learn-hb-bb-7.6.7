/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.analysisreport.facade;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.analysisreport.facade.AnalyBigdataDefine;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="sys_analyversion_bigdata")
public class AnalyBigdataDefineImpl
implements AnalyBigdataDefine {
    @DBAnno.DBField(dbField="av_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="av_big_data", dbType=Clob.class)
    private String bigDate;
    @DBAnno.DBField(dbField="AV_ARC_KEY")
    private String arcKey;
    @DBAnno.DBField(dbField="AV_CATALOG", dbType=Clob.class)
    private String catalog;
    @DBAnno.DBField(dbField="AV_UPDATETIME", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="AV_CATALOG_UPDATETIME", dbType=Timestamp.class, appType=Date.class)
    private Date catalogUpdatetime;

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setBigData(String bigData) {
        this.bigDate = bigData;
    }

    @Override
    public String getBigData() {
        return this.bigDate;
    }

    @Override
    public void setArcKey(String arcKey) {
        this.arcKey = arcKey;
    }

    @Override
    public String getArcKey() {
        return this.arcKey;
    }

    @Override
    public String getCatalog() {
        return this.catalog;
    }

    @Override
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    @Override
    public Date getCatalogUpdatetime() {
        return this.catalogUpdatetime;
    }

    @Override
    public void setCatalogUpdatetime(Date catalogUpdatetime) {
        this.catalogUpdatetime = catalogUpdatetime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public String getBigDate() {
        return this.bigDate;
    }

    @Override
    public void setBigDate(String bigDate) {
        this.bigDate = bigDate;
    }
}

