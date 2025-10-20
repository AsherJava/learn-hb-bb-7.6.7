/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.analysisreport.chapter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
@DBAnno.DBTable(dbTable="NR_B_AR_CHAPTER")
public class ReportChapterDefine
implements Serializable {
    private static final long serialVersionUID = 536461854042388038L;
    @JsonProperty(value="key")
    @DBAnno.DBField(dbField="ARC_KEY", isPk=true)
    private String arcKey;
    @JsonProperty(value="modelId")
    @DBAnno.DBField(dbField="ARC_AT_KEY")
    private String arcAtKey;
    @JsonProperty(value="title")
    @DBAnno.DBField(dbField="ARC_NAME")
    private String arcName;
    @JsonProperty(value="parentId")
    @DBAnno.DBField(dbField="ARC_PARENT")
    private String arcParent;
    @DBAnno.DBField(dbField="ARC_DATA", dbType=Clob.class, appType=String.class)
    private String arcData;
    @DBAnno.DBField(dbField="ARC_ORDER", isOrder=true)
    private String arcOrder;
    @DBAnno.DBField(dbField="ARC_UPDATETIME", dbType=Timestamp.class, appType=Date.class)
    private Date arcUpdatetime;
    @DBAnno.DBField(dbField="ARC_CATALOG", dbType=Clob.class, appType=String.class)
    private String catalog;
    @DBAnno.DBField(dbField="ARC_CATALOG_UPDATETIME", dbType=Timestamp.class, appType=Date.class)
    private Date catalogUpdatetime;
    @DBAnno.DBField(dbField="ARC_TYPE_SPEED")
    private Integer typeSpeed;

    public void setArcKey(String arcKey) {
        this.arcKey = arcKey;
    }

    public String getArcKey() {
        return this.arcKey;
    }

    public void setArcAtKey(String arcAtKey) {
        this.arcAtKey = arcAtKey;
    }

    public String getArcAtKey() {
        return this.arcAtKey;
    }

    public void setArcName(String arcName) {
        this.arcName = arcName;
    }

    public String getArcName() {
        return this.arcName;
    }

    public void setArcParent(String arcParent) {
        this.arcParent = arcParent;
    }

    public String getArcParent() {
        return this.arcParent;
    }

    public void setArcData(String arcData) {
        this.arcData = arcData;
    }

    public String getArcData() {
        return this.arcData;
    }

    public void setArcOrder(String arcOrder) {
        this.arcOrder = arcOrder;
    }

    public String getArcOrder() {
        return this.arcOrder;
    }

    public void setArcUpdatetime(Date arcUpdatetime) {
        this.arcUpdatetime = arcUpdatetime;
    }

    public Date getArcUpdatetime() {
        return this.arcUpdatetime;
    }

    public String getCatalog() {
        return this.catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Date getCatalogUpdatetime() {
        return this.catalogUpdatetime;
    }

    public void setCatalogUpdatetime(Date catalogUpdatetime) {
        this.catalogUpdatetime = catalogUpdatetime;
    }

    public Integer getTypeSpeed() {
        return this.typeSpeed;
    }

    public void setTypeSpeed(Integer typeSpeed) {
        this.typeSpeed = typeSpeed;
    }
}

