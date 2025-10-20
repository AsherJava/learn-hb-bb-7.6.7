/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.offsetitem.dto.PentrationDataJsonSerializer
 */
package com.jiuqi.gcreport.workingpaper.querytask.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.offsetitem.dto.PentrationDataJsonSerializer;
import java.math.BigDecimal;
import java.util.Map;

public class WorkingPaperUnDxsItemDTO {
    private String orgCode;
    private String subjectCode;
    private OrientEnum subjectOrient;
    private String ywlxCode;
    private String ruleId;
    private Integer elmMode;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal unOffSetDebit;
    private BigDecimal unOffSetCredit;
    private BigDecimal diffd;
    private BigDecimal diffc;
    private String fetchSetGroupId;
    private String fetchConfigTitle;
    @JsonSerialize(using=PentrationDataJsonSerializer.class)
    private Map<String, String> otherColumnsValueMap;

    public static WorkingPaperUnDxsItemDTO empty() {
        WorkingPaperUnDxsItemDTO emptyWorkingPaperUnDxsItemDTO = new WorkingPaperUnDxsItemDTO();
        emptyWorkingPaperUnDxsItemDTO.unOffSetCredit = BigDecimal.ZERO;
        emptyWorkingPaperUnDxsItemDTO.unOffSetDebit = BigDecimal.ZERO;
        emptyWorkingPaperUnDxsItemDTO.diffc = BigDecimal.ZERO;
        emptyWorkingPaperUnDxsItemDTO.diffd = BigDecimal.ZERO;
        return emptyWorkingPaperUnDxsItemDTO;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getYwlxCode() {
        return this.ywlxCode;
    }

    public void setYwlxCode(String ywlxCode) {
        this.ywlxCode = ywlxCode;
    }

    public Integer getElmMode() {
        return this.elmMode;
    }

    public void setElmMode(Integer elmMode) {
        this.elmMode = elmMode;
    }

    public BigDecimal getDebit() {
        return this.debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return this.credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getUnOffSetDebit() {
        return this.unOffSetDebit;
    }

    public void setUnOffSetDebit(BigDecimal offSetDebit) {
        this.unOffSetDebit = offSetDebit;
    }

    public BigDecimal getUnOffSetCredit() {
        return this.unOffSetCredit;
    }

    public void setUnOffSetCredit(BigDecimal offSetCredit) {
        this.unOffSetCredit = offSetCredit;
    }

    public BigDecimal getDiffd() {
        return this.diffd;
    }

    public void setDiffd(BigDecimal diffd) {
        this.diffd = diffd;
    }

    public BigDecimal getDiffc() {
        return this.diffc;
    }

    public void setDiffc(BigDecimal diffc) {
        this.diffc = diffc;
    }

    public Map<String, String> getOtherColumnsValueMap() {
        return this.otherColumnsValueMap;
    }

    public void setOtherColumnsValueMap(Map<String, String> otherColumnsValueMap) {
        this.otherColumnsValueMap = otherColumnsValueMap;
    }

    public void setSubjectOrient(OrientEnum subjectOrient) {
        this.subjectOrient = subjectOrient;
    }

    public OrientEnum getSubjectOrient() {
        return this.subjectOrient;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getFetchSetGroupId() {
        return this.fetchSetGroupId;
    }

    public void setFetchSetGroupId(String fetchSetGroupId) {
        this.fetchSetGroupId = fetchSetGroupId;
    }

    public String getFetchConfigTitle() {
        return this.fetchConfigTitle;
    }

    public void setFetchConfigTitle(String fetchConfigTitle) {
        this.fetchConfigTitle = fetchConfigTitle;
    }
}

