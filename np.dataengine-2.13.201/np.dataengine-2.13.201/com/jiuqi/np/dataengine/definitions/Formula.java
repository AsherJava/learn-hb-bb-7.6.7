/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.definitions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Formula
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 9110824817899638130L;
    private String id;
    private String code;
    private String formula;
    private String Order;
    private String reportName;
    private String formKey;
    private String meanning;
    private Integer checktype;
    private boolean isAutoCalc;
    private String balanceZBExp;
    private List<Formula> conditions;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getOrder() {
        return this.Order;
    }

    public void setOrder(String order) {
        this.Order = order;
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getMeanning() {
        return this.meanning;
    }

    public void setMeanning(String meanning) {
        this.meanning = meanning;
    }

    public Integer getChecktype() {
        return this.checktype;
    }

    public void setChecktype(Integer checktype) {
        this.checktype = checktype;
    }

    public boolean isAutoCalc() {
        return this.isAutoCalc;
    }

    public void setAutoCalc(boolean isAutoCalc) {
        this.isAutoCalc = isAutoCalc;
    }

    public String getBalanceZBExp() {
        return this.balanceZBExp;
    }

    public void setBalanceZBExp(String balanceZBExp) {
        this.balanceZBExp = balanceZBExp;
    }

    public List<Formula> getConditions() {
        if (this.conditions == null) {
            this.conditions = new ArrayList<Formula>();
        }
        return this.conditions;
    }

    public boolean hasConditions() {
        return this.conditions != null && this.conditions.size() > 0;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Formula other = (Formula)obj;
        return !(this.id == null ? other.id != null : !this.id.equals(other.id));
    }

    public String toString() {
        return this.reportName + "[" + this.code + "]:" + this.formula;
    }

    public Object clone() throws CloneNotSupportedException {
        Formula o = new Formula();
        o.checktype = this.checktype;
        o.code = this.code;
        o.formKey = this.formKey;
        o.formula = this.formula;
        o.id = this.id;
        o.isAutoCalc = this.isAutoCalc;
        o.meanning = this.meanning;
        o.reportName = this.reportName;
        return o;
    }
}

