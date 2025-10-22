/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO
 */
package com.jiuqi.gcreport.billcore.offsetcheck.dto;

import com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO;
import java.util.ArrayList;
import java.util.List;

public class BillOffsetCheckInfoDTO
extends DataTraceCheckInfoDTO {
    private String ruleType;
    private String ruleTypeTitle;
    private String ruleApplyConditon;
    private String checkDebitDiffInfo;
    private String checkCreditDiffInfo;
    private String checkStatus;
    private boolean rowAmtAllNull;
    private String originOffsetMrecid;
    private int parentLevel;
    private List<BillOffsetCheckInfoDTO> children = new ArrayList<BillOffsetCheckInfoDTO>();

    public BillOffsetCheckInfoDTO(String ruleId, String subjectCode, String formula) {
        super(ruleId, subjectCode, formula);
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleTypeTitle() {
        return this.ruleTypeTitle;
    }

    public void setRuleTypeTitle(String ruleTypeTitle) {
        this.ruleTypeTitle = ruleTypeTitle;
    }

    public String getRuleApplyConditon() {
        return this.ruleApplyConditon;
    }

    public void setRuleApplyConditon(String ruleApplyConditon) {
        this.ruleApplyConditon = ruleApplyConditon;
    }

    public String getCheckDebitDiffInfo() {
        return this.checkDebitDiffInfo;
    }

    public void setCheckDebitDiffInfo(String checkDebitDiffInfo) {
        this.checkDebitDiffInfo = checkDebitDiffInfo;
    }

    public String getCheckCreditDiffInfo() {
        return this.checkCreditDiffInfo;
    }

    public void setCheckCreditDiffInfo(String checkCreditDiffInfo) {
        this.checkCreditDiffInfo = checkCreditDiffInfo;
    }

    public String getCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public boolean isRowAmtAllNull() {
        return this.rowAmtAllNull;
    }

    public void setRowAmtAllNull(boolean rowAmtAllNull) {
        this.rowAmtAllNull = rowAmtAllNull;
    }

    public List<BillOffsetCheckInfoDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<BillOffsetCheckInfoDTO> children) {
        this.children = children;
    }

    public int getParentLevel() {
        return this.parentLevel;
    }

    public void setParentLevel(int parentLevel) {
        this.parentLevel = parentLevel;
    }

    public String getOriginOffsetMrecid() {
        return this.originOffsetMrecid;
    }

    public void setOriginOffsetMrecid(String originOffsetMrecid) {
        this.originOffsetMrecid = originOffsetMrecid;
    }
}

