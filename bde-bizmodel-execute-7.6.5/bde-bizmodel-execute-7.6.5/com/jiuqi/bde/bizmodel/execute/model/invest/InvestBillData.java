/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.model.invest;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceData;
import java.math.BigDecimal;
import java.util.Map;

public class InvestBillData
extends BalanceData {
    private String vchrUniqueCode;
    private String investedUnit;
    private String vchrTypeCode;
    private String vchrNum;
    private String vchrDate;
    private String changeScenario;
    private String changeDate;
    private String currencyCode;
    private BigDecimal nc;
    private BigDecimal c;
    private BigDecimal jf;
    private BigDecimal df;
    private BigDecimal bqnum;
    private BigDecimal jl;
    private BigDecimal dl;
    private BigDecimal ljnum;
    private BigDecimal ye;
    private BigDecimal zsc;
    private BigDecimal wnc;
    private BigDecimal wc;
    private BigDecimal wjf;
    private BigDecimal wdf;
    private BigDecimal wbqnum;
    private BigDecimal wjl;
    private BigDecimal wdl;
    private BigDecimal wljnum;
    private BigDecimal wye;
    private Map<String, String> assistMap;

    public Map<String, String> getAssistMap() {
        return this.assistMap;
    }

    public void setAssistMap(Map<String, String> assistMap) {
        this.assistMap = assistMap;
    }

    public String getVchrUniqueCode() {
        return this.vchrUniqueCode;
    }

    public void setVchrUniqueCode(String vchrUniqueCode) {
        this.vchrUniqueCode = vchrUniqueCode;
    }

    public String getInvestedUnit() {
        return this.investedUnit;
    }

    public void setInvestedUnit(String investedUnit) {
        this.investedUnit = investedUnit;
    }

    public String getVchrTypeCode() {
        return this.vchrTypeCode;
    }

    public void setVchrTypeCode(String vchrTypeCode) {
        this.vchrTypeCode = vchrTypeCode;
    }

    public String getVchrNum() {
        return this.vchrNum;
    }

    public void setVchrNum(String vchrNum) {
        this.vchrNum = vchrNum;
    }

    public String getVchrDate() {
        return this.vchrDate;
    }

    public void setVchrDate(String vchrDate) {
        this.vchrDate = vchrDate;
    }

    public String getChangeScenario() {
        return this.changeScenario;
    }

    public void setChangeScenario(String changeScenario) {
        this.changeScenario = changeScenario;
    }

    public String getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    @Override
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public BigDecimal getNc() {
        return this.nc;
    }

    @Override
    public void setNc(BigDecimal nc) {
        this.nc = nc;
    }

    @Override
    public BigDecimal getC() {
        return this.c;
    }

    @Override
    public void setC(BigDecimal c) {
        this.c = c;
    }

    @Override
    public BigDecimal getJf() {
        return this.jf;
    }

    @Override
    public void setJf(BigDecimal jf) {
        this.jf = jf;
    }

    @Override
    public BigDecimal getDf() {
        return this.df;
    }

    @Override
    public void setDf(BigDecimal df) {
        this.df = df;
    }

    @Override
    public BigDecimal getBqnum() {
        return this.bqnum;
    }

    @Override
    public void setBqnum(BigDecimal bqnum) {
        this.bqnum = bqnum;
    }

    @Override
    public BigDecimal getJl() {
        return this.jl;
    }

    @Override
    public void setJl(BigDecimal jl) {
        this.jl = jl;
    }

    @Override
    public BigDecimal getDl() {
        return this.dl;
    }

    @Override
    public void setDl(BigDecimal dl) {
        this.dl = dl;
    }

    @Override
    public BigDecimal getLjnum() {
        return this.ljnum;
    }

    @Override
    public void setLjnum(BigDecimal ljnum) {
        this.ljnum = ljnum;
    }

    @Override
    public BigDecimal getYe() {
        return this.ye;
    }

    @Override
    public void setYe(BigDecimal ye) {
        this.ye = ye;
    }

    @Override
    public BigDecimal getZsc() {
        return this.zsc;
    }

    @Override
    public void setZsc(BigDecimal zsc) {
        this.zsc = zsc;
    }

    @Override
    public BigDecimal getWnc() {
        return this.wnc;
    }

    @Override
    public void setWnc(BigDecimal wnc) {
        this.wnc = wnc;
    }

    @Override
    public BigDecimal getWc() {
        return this.wc;
    }

    @Override
    public void setWc(BigDecimal wc) {
        this.wc = wc;
    }

    @Override
    public BigDecimal getWjf() {
        return this.wjf;
    }

    @Override
    public void setWjf(BigDecimal wjf) {
        this.wjf = wjf;
    }

    @Override
    public BigDecimal getWdf() {
        return this.wdf;
    }

    @Override
    public void setWdf(BigDecimal wdf) {
        this.wdf = wdf;
    }

    @Override
    public BigDecimal getWbqnum() {
        return this.wbqnum;
    }

    @Override
    public void setWbqnum(BigDecimal wbqnum) {
        this.wbqnum = wbqnum;
    }

    @Override
    public BigDecimal getWjl() {
        return this.wjl;
    }

    @Override
    public void setWjl(BigDecimal wjl) {
        this.wjl = wjl;
    }

    @Override
    public BigDecimal getWdl() {
        return this.wdl;
    }

    @Override
    public void setWdl(BigDecimal wdl) {
        this.wdl = wdl;
    }

    @Override
    public BigDecimal getWljnum() {
        return this.wljnum;
    }

    @Override
    public void setWljnum(BigDecimal wljnum) {
        this.wljnum = wljnum;
    }

    @Override
    public BigDecimal getWye() {
        return this.wye;
    }

    @Override
    public void setWye(BigDecimal wye) {
        this.wye = wye;
    }
}

