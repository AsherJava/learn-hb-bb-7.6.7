/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.gcreport.intermediatelibrary.vo.OptionVO;
import java.util.ArrayList;
import java.util.List;

public class Scheme {
    private OptionVO scheme;
    private String schemeTitle;
    private Integer periodType;
    private String periodTypeChar;
    private Integer dflYear;
    private Integer dflPeriod;
    private List<OptionVO> acctYear;
    private List<OptionVO> acctPeriod;
    private String unitDefine;
    private String unitTitle;
    private String currencyDefine;
    private String gcorgtypeDefine;
    private String gcadjtypeDefine;
    private List defines = new ArrayList();
    private String fromPeriod;
    private String toPeriod;

    public OptionVO getScheme() {
        return this.scheme;
    }

    public void setScheme(OptionVO scheme) {
        this.scheme = scheme;
    }

    public String getSchemeTitle() {
        return this.schemeTitle;
    }

    public void setSchemeTitle(String schemeTitle) {
        this.schemeTitle = schemeTitle;
    }

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getPeriodTypeChar() {
        return this.periodTypeChar;
    }

    public void setPeriodTypeChar(String periodTypeChar) {
        this.periodTypeChar = periodTypeChar;
    }

    public List<OptionVO> getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(List<OptionVO> acctYear) {
        this.acctYear = acctYear;
    }

    public List<OptionVO> getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(List<OptionVO> acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getUnitDefine() {
        return this.unitDefine;
    }

    public void setUnitDefine(String unitDefine) {
        this.unitDefine = unitDefine;
    }

    public List getDefines() {
        return this.defines;
    }

    public void setDefines(List defines) {
        this.defines = defines;
    }

    public Integer getDflYear() {
        return this.dflYear;
    }

    public void setDflYear(Integer dflYear) {
        this.dflYear = dflYear;
    }

    public Integer getDflPeriod() {
        return this.dflPeriod;
    }

    public void setDflPeriod(Integer dflPeriod) {
        this.dflPeriod = dflPeriod;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getCurrencyDefine() {
        return this.currencyDefine;
    }

    public void setCurrencyDefine(String currencyDefine) {
        this.currencyDefine = currencyDefine;
    }

    public String getGcorgtypeDefine() {
        return this.gcorgtypeDefine;
    }

    public void setGcorgtypeDefine(String gcorgtypeDefine) {
        this.gcorgtypeDefine = gcorgtypeDefine;
    }

    public String getGcadjtypeDefine() {
        return this.gcadjtypeDefine;
    }

    public void setGcadjtypeDefine(String gcadjtypeDefine) {
        this.gcadjtypeDefine = gcadjtypeDefine;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }
}

