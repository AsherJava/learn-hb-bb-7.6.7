/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.rate.client.vo;

import com.jiuqi.common.rate.client.vo.SubjectShowVO;
import java.util.List;

public class ConvertRateSchemeVO {
    private String id;
    private Long ver;
    private String rowDataId;
    private List<SubjectShowVO> subject;
    private String subjectCode;
    private String bfRateType;
    private String qcRateType;
    private String bqRateType;
    private String sumRateType;
    private String cfRateType;
    private String nextYearAdjustRateType;

    public ConvertRateSchemeVO() {
    }

    public ConvertRateSchemeVO(String id, Long ver, String rowDataId, String subjectCode, String bfRateType, String qcRateType, String bqRateType, String sumRateType, String cfRateType, String nextYearAdjustRateType) {
        this.id = id;
        this.ver = ver;
        this.rowDataId = rowDataId;
        this.subjectCode = subjectCode;
        this.bfRateType = bfRateType;
        this.qcRateType = qcRateType;
        this.bqRateType = bqRateType;
        this.sumRateType = sumRateType;
        this.cfRateType = cfRateType;
        this.nextYearAdjustRateType = nextYearAdjustRateType;
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

    public List<SubjectShowVO> getSubject() {
        return this.subject;
    }

    public void setSubject(List<SubjectShowVO> subject) {
        this.subject = subject;
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
        return "ConvertRateSchemeVO{id='" + this.id + '\'' + ", ver=" + this.ver + ", rowDataId='" + this.rowDataId + '\'' + ", subject=" + this.subject + ", subjectCode='" + this.subjectCode + '\'' + ", bfRateType='" + this.bfRateType + '\'' + ", qcRateType='" + this.qcRateType + '\'' + ", bqRateType='" + this.bqRateType + '\'' + ", sumRateType='" + this.sumRateType + '\'' + ", cfRateType='" + this.cfRateType + '\'' + ", nextYearAdjustRateType='" + this.nextYearAdjustRateType + '\'' + '}';
    }
}

