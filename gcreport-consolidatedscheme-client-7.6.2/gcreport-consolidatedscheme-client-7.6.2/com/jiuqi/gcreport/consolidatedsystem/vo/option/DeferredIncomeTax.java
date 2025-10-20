/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.option;

import java.util.ArrayList;
import java.util.List;

public class DeferredIncomeTax {
    private List<String> assetSubjects = new ArrayList<String>();
    private String zbCode;
    private String diTaxFilterFormula;
    private String positiveDebitSubject;
    private String positiveCreditSubject;
    private String negativeDebitSubject;
    private String negativeCreditSubject;
    private String initPositiveDebitSubject;
    private String initPositiveCreditSubject;
    private String initNegativeDebitSubject;
    private String initNegativeCreditSubject;
    private List<String> offsetInitRules = new ArrayList<String>();
    private String minorityLossGainSubject;
    private String minorityEquitySubject;
    private List<String> LossGainRecoverySubjects = new ArrayList<String>();
    private String lgRecoveryFilterFormula;

    public List<String> getAssetSubjects() {
        return this.assetSubjects;
    }

    public void setAssetSubjects(List<String> assetSubjects) {
        this.assetSubjects = assetSubjects;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getPositiveDebitSubject() {
        return this.positiveDebitSubject;
    }

    public void setPositiveDebitSubject(String positiveDebitSubject) {
        this.positiveDebitSubject = positiveDebitSubject;
    }

    public String getPositiveCreditSubject() {
        return this.positiveCreditSubject;
    }

    public void setPositiveCreditSubject(String positiveCreditSubject) {
        this.positiveCreditSubject = positiveCreditSubject;
    }

    public String getNegativeDebitSubject() {
        return this.negativeDebitSubject;
    }

    public void setNegativeDebitSubject(String negativeDebitSubject) {
        this.negativeDebitSubject = negativeDebitSubject;
    }

    public String getNegativeCreditSubject() {
        return this.negativeCreditSubject;
    }

    public void setNegativeCreditSubject(String negativeCreditSubject) {
        this.negativeCreditSubject = negativeCreditSubject;
    }

    public String getInitPositiveDebitSubject() {
        return this.initPositiveDebitSubject;
    }

    public void setInitPositiveDebitSubject(String initPositiveDebitSubject) {
        this.initPositiveDebitSubject = initPositiveDebitSubject;
    }

    public String getInitPositiveCreditSubject() {
        return this.initPositiveCreditSubject;
    }

    public void setInitPositiveCreditSubject(String initPositiveCreditSubject) {
        this.initPositiveCreditSubject = initPositiveCreditSubject;
    }

    public String getInitNegativeDebitSubject() {
        return this.initNegativeDebitSubject;
    }

    public void setInitNegativeDebitSubject(String initNegativeDebitSubject) {
        this.initNegativeDebitSubject = initNegativeDebitSubject;
    }

    public String getInitNegativeCreditSubject() {
        return this.initNegativeCreditSubject;
    }

    public void setInitNegativeCreditSubject(String initNegativeCreditSubject) {
        this.initNegativeCreditSubject = initNegativeCreditSubject;
    }

    public List<String> getOffsetInitRules() {
        return this.offsetInitRules;
    }

    public void setOffsetInitRules(List<String> offsetInitRules) {
        this.offsetInitRules = offsetInitRules;
    }

    public String getMinorityLossGainSubject() {
        return this.minorityLossGainSubject;
    }

    public void setMinorityLossGainSubject(String minorityLossGainSubject) {
        this.minorityLossGainSubject = minorityLossGainSubject;
    }

    public String getMinorityEquitySubject() {
        return this.minorityEquitySubject;
    }

    public void setMinorityEquitySubject(String minorityEquitySubject) {
        this.minorityEquitySubject = minorityEquitySubject;
    }

    public List<String> getLossGainRecoverySubjects() {
        return this.LossGainRecoverySubjects;
    }

    public void setLossGainRecoverySubjects(List<String> lossGainRecoverySubjects) {
        this.LossGainRecoverySubjects = lossGainRecoverySubjects;
    }

    public String getDiTaxFilterFormula() {
        return this.diTaxFilterFormula;
    }

    public void setDiTaxFilterFormula(String diTaxFilterFormula) {
        this.diTaxFilterFormula = diTaxFilterFormula;
    }

    public String getLgRecoveryFilterFormula() {
        return this.lgRecoveryFilterFormula;
    }

    public void setLgRecoveryFilterFormula(String lgRecoveryFilterFormula) {
        this.lgRecoveryFilterFormula = lgRecoveryFilterFormula;
    }
}

