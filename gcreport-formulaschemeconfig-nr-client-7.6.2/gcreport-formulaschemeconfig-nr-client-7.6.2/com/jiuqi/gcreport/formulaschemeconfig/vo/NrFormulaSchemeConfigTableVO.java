/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.formulaschemeconfig.vo;

import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.formulaschemeconfig.vo.FormulaSchemeConfigTableVO;
import com.jiuqi.va.domain.org.OrgDO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NrFormulaSchemeConfigTableVO
extends FormulaSchemeConfigTableVO
implements Serializable {
    private String taskId;
    private String schemeId;
    private String entityId;
    private String currencyCode;
    private String currencyTitle;
    private BaseDataVO currency;
    private Boolean isBaseCurrency;
    private String assistDim;
    private String bblx;
    private List<String> fetchAfterSchemeId;
    private List<String> completeMergeId;
    private String convertSystemSchemeId;
    private List<String> convertAfterSchemeId;
    private List<String> unSaCtDeExtLaYeNumSaPerId;
    private List<String> sameCtrlExtAfterSchemeId;
    private String postingSchemeId;
    private String fetchAfterScheme;
    private String completeMerge;
    private String convertSystemScheme;
    private String convertAfterScheme;
    private String unSaCtDeExtLaYeNumSaPer;
    private String sameCtrlExtAfterScheme;
    private String postingScheme;

    public NrFormulaSchemeConfigTableVO(OrgDO org, BaseDataVO currency, Boolean isBaseCurrency, String bblx) {
        this.setOrgId(org.getCode());
        this.setOrgUnit(org);
        this.setOrgTitle(org.getName());
        if (currency != null) {
            this.setCurrency(currency);
            this.setCurrencyCode(currency.getCode());
            this.setCurrencyTitle(currency.getTitle());
        }
        if (isBaseCurrency != null) {
            this.setIsBaseCurrency(isBaseCurrency);
        }
        if (bblx != null) {
            this.setBblx(bblx);
        }
        this.setFetchSchemeId(new String());
        this.setFetchAfterSchemeId(new ArrayList<String>());
        this.setConvertAfterSchemeId(new ArrayList<String>());
        this.setConvertSystemSchemeId(new String());
        this.setPostingSchemeId(new String());
        this.setCompleteMergeId(new ArrayList<String>());
        this.setSameCtrlExtAfterSchemeId(new ArrayList<String>());
        this.setUnSaCtDeExtLaYeNumSaPerId(new ArrayList<String>());
        this.setFetchScheme(new String());
        this.setFetchAfterScheme(new String());
        this.setConvertAfterScheme(new String());
        this.setConvertSystemScheme(new String());
        this.setPostingScheme(new String());
        this.setCompleteMerge(new String());
        this.setSameCtrlExtAfterScheme(new String());
        this.setUnSaCtDeExtLaYeNumSaPer(new String());
    }

    public NrFormulaSchemeConfigTableVO() {
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyTitle() {
        return this.currencyTitle;
    }

    public void setCurrencyTitle(String currencyTitle) {
        this.currencyTitle = currencyTitle;
    }

    public BaseDataVO getCurrency() {
        return this.currency;
    }

    public void setCurrency(BaseDataVO currency) {
        this.currency = currency;
    }

    public Boolean getIsBaseCurrency() {
        return this.isBaseCurrency;
    }

    public void setIsBaseCurrency(Boolean baseCurrency) {
        this.isBaseCurrency = baseCurrency;
    }

    public String getAssistDim() {
        return this.assistDim;
    }

    public void setAssistDim(String assistDim) {
        this.assistDim = assistDim;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public List<String> getFetchAfterSchemeId() {
        return this.fetchAfterSchemeId;
    }

    public void setFetchAfterSchemeId(List<String> fetchAfterSchemeId) {
        this.fetchAfterSchemeId = fetchAfterSchemeId;
    }

    public List<String> getConvertAfterSchemeId() {
        return this.convertAfterSchemeId;
    }

    public void setConvertAfterSchemeId(List<String> convertAfterSchemeId) {
        this.convertAfterSchemeId = convertAfterSchemeId;
    }

    public String getConvertSystemSchemeId() {
        return this.convertSystemSchemeId;
    }

    public void setConvertSystemSchemeId(String convertSystemSchemeId) {
        this.convertSystemSchemeId = convertSystemSchemeId;
    }

    public String getPostingSchemeId() {
        return this.postingSchemeId;
    }

    public void setPostingSchemeId(String postingSchemeId) {
        this.postingSchemeId = postingSchemeId;
    }

    public List<String> getCompleteMergeId() {
        return this.completeMergeId;
    }

    public void setCompleteMergeId(List<String> completeMergeId) {
        this.completeMergeId = completeMergeId;
    }

    public List<String> getUnSaCtDeExtLaYeNumSaPerId() {
        return this.unSaCtDeExtLaYeNumSaPerId;
    }

    public void setUnSaCtDeExtLaYeNumSaPerId(List<String> unSaCtDeExtLaYeNumSaPerId) {
        this.unSaCtDeExtLaYeNumSaPerId = unSaCtDeExtLaYeNumSaPerId;
    }

    public List<String> getSameCtrlExtAfterSchemeId() {
        return this.sameCtrlExtAfterSchemeId;
    }

    public void setSameCtrlExtAfterSchemeId(List<String> sameCtrlExtAfterSchemeId) {
        this.sameCtrlExtAfterSchemeId = sameCtrlExtAfterSchemeId;
    }

    public String getFetchAfterScheme() {
        return this.fetchAfterScheme;
    }

    public void setFetchAfterScheme(String fetchAfterScheme) {
        this.fetchAfterScheme = fetchAfterScheme;
    }

    public String getConvertAfterScheme() {
        return this.convertAfterScheme;
    }

    public void setConvertAfterScheme(String convertAfterScheme) {
        this.convertAfterScheme = convertAfterScheme;
    }

    public String getConvertSystemScheme() {
        return this.convertSystemScheme;
    }

    public void setConvertSystemScheme(String convertSystemScheme) {
        this.convertSystemScheme = convertSystemScheme;
    }

    public String getPostingScheme() {
        return this.postingScheme;
    }

    public void setPostingScheme(String postingScheme) {
        this.postingScheme = postingScheme;
    }

    public String getCompleteMerge() {
        return this.completeMerge;
    }

    public void setCompleteMerge(String completeMerge) {
        this.completeMerge = completeMerge;
    }

    public String getUnSaCtDeExtLaYeNumSaPer() {
        return this.unSaCtDeExtLaYeNumSaPer;
    }

    public void setUnSaCtDeExtLaYeNumSaPer(String unSaCtDeExtLaYeNumSaPer) {
        this.unSaCtDeExtLaYeNumSaPer = unSaCtDeExtLaYeNumSaPer;
    }

    public String getSameCtrlExtAfterScheme() {
        return this.sameCtrlExtAfterScheme;
    }

    public void setSameCtrlExtAfterScheme(String sameCtrlExtAfterScheme) {
        this.sameCtrlExtAfterScheme = sameCtrlExtAfterScheme;
    }
}

