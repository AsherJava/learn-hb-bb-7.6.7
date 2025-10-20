/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 */
package com.jiuqi.gcreport.rate.impl.entity;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@DBTable(name="DC_SCHEME_CONVRATE", title="\u6c47\u7387\u8f6c\u6362\u65b9\u6848\u8868")
public class ConvertRateSchemeEO
extends BaseEntity {
    private static final long serialVersionUID = 665692085887241415L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    @DBColumn(title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, isRecid=true, isRequired=true, order=0)
    private String id;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true)
    private Long ver;
    @DBColumn(nameInDB="ROWDATAID", title="\u884c\u6570\u636eID", dbType=DBColumn.DBType.NVarchar, length=36, isRequired=true)
    private String rowDataId;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=300, isRequired=true)
    private String subjectCode;
    @DBColumn(nameInDB="BFRATETYPE", title="\u5e74\u521d\u4f59\u989d\u6c47\u7387\u53d6\u503c\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String bfRateType;
    @DBColumn(nameInDB="QCRATETYPE", title="\u671f\u521d\u4f59\u989d\u6c47\u7387\u53d6\u503c\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String qcRateType;
    @DBColumn(nameInDB="BQRATETYPE", title="\u672c\u671f\u53d1\u751f\u6c47\u7387\u53d6\u503c\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String bqRateType;
    @DBColumn(nameInDB="SUMRATETYPE", title="\u672c\u5e74\u7d2f\u8ba1\u6c47\u7387\u53d6\u503c\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String sumRateType;
    @DBColumn(nameInDB="CFRATETYPE", title="\u671f\u672b\u4f59\u989d\u6c47\u7387\u53d6\u503c\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String cfRateType;
    @DBColumn(nameInDB="NEXTYEARADJUSTRATETYPE", title="\u4e0b\u5e74\u8c03\u6574\u6c47\u7387\u53d6\u503c\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String nextYearAdjustRateType;
    private String tableName;

    public String getTableName() {
        if (!StringUtils.isEmpty((String)this.tableName)) {
            return this.tableName;
        }
        DBTable dbTable = ((Object)((Object)this)).getClass().getAnnotation(DBTable.class);
        if (dbTable == null) {
            throw new UnsupportedOperationException(((Object)((Object)this)).getClass() + "\u6ca1\u6709DBTable\u6ce8\u89e3,\u8bf7\u68c0\u67e5");
        }
        this.tableName = dbTable.name().toUpperCase();
        return this.tableName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getRowDataId() {
        return this.rowDataId;
    }

    public void setRowDataId(String rowDataId) {
        this.rowDataId = rowDataId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getBfRateType() {
        return this.bfRateType;
    }

    public void setBfRateType(String bfRateType) {
        this.bfRateType = bfRateType;
    }

    public String getQcRateType() {
        return this.qcRateType;
    }

    public void setQcRateType(String qcRateType) {
        this.qcRateType = qcRateType;
    }

    public String getBqRateType() {
        return this.bqRateType;
    }

    public void setBqRateType(String bqRateType) {
        this.bqRateType = bqRateType;
    }

    public String getSumRateType() {
        return this.sumRateType;
    }

    public void setSumRateType(String sumRateType) {
        this.sumRateType = sumRateType;
    }

    public String getCfRateType() {
        return this.cfRateType;
    }

    public void setCfRateType(String cfRateType) {
        this.cfRateType = cfRateType;
    }

    public String getNextYearAdjustRateType() {
        return this.nextYearAdjustRateType;
    }

    public void setNextYearAdjustRateType(String nextYearAdjustRateType) {
        this.nextYearAdjustRateType = nextYearAdjustRateType;
    }
}

