/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.gcreport.rate.impl.domain;

import com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO;
import com.jiuqi.va.mapper.domain.TenantDO;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="DC_SCHEME_CONVRATE")
public class ConvertRateSchemeDO
extends TenantDO {
    private static final long serialVersionUID = -2945433379782475960L;
    public static final String TABLENAME = "DC_SCHEME_CONVRATE";
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="VER")
    private Long ver;
    @Column(name="ROWDATAID")
    private String rowDataId;
    @Column(name="SUBJECTCODE")
    private String subjectCode;
    @Column(name="BFRATETYPE")
    private String bfRateType;
    @Column(name="QCRATETYPE")
    private String qcRateType;
    @Column(name="BQRATETYPE")
    private String bqRateType;
    @Column(name="SUMRATETYPE")
    private String sumRateType;
    @Column(name="CFRATETYPE")
    private String cfRateType;
    @Column(name="NEXTYEARADJUSTRATETYPE")
    private String nextYearAdjustRateType;

    public ConvertRateSchemeDO() {
    }

    public ConvertRateSchemeDO(ConvertRateSchemeVO vo) {
        this.id = vo.getId();
        this.ver = vo.getVer();
        this.rowDataId = vo.getRowDataId();
        this.subjectCode = vo.getSubjectCode();
        this.bfRateType = vo.getBfRateType();
        this.qcRateType = vo.getQcRateType();
        this.bqRateType = vo.getBqRateType();
        this.sumRateType = vo.getSumRateType();
        this.cfRateType = vo.getCfRateType();
        this.nextYearAdjustRateType = vo.getNextYearAdjustRateType();
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

    public String toString() {
        return "ConvertRateSchemeDO{id='" + this.id + '\'' + ", ver=" + this.ver + ", rowDataId='" + this.rowDataId + '\'' + ", subjectCode='" + this.subjectCode + '\'' + ", bfRateType='" + this.bfRateType + '\'' + ", qcRateType='" + this.qcRateType + '\'' + ", bqRateType='" + this.bqRateType + '\'' + ", sumRateType='" + this.sumRateType + '\'' + ", cfRateType='" + this.cfRateType + '\'' + ", nextYearAdjustRateType='" + this.nextYearAdjustRateType + '\'' + '}';
    }
}

