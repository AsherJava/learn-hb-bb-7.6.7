/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AysncTaskArgsInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 *  com.jiuqi.nr.jtable.service.IJtableBase
 */
package com.jiuqi.nr.efdc.pojo;

import com.jiuqi.nr.common.asynctask.entity.AysncTaskArgsInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.nr.jtable.service.IJtableBase;
import java.util.Date;
import java.util.Map;

public class EfdcInfo
extends AysncTaskArgsInfo
implements IJtableBase,
INRContext {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private Map<String, Object> paras;
    private Map<String, DimensionValue> dimensionSet;
    private boolean containsUnbVou;
    private boolean appUsePayment;
    private boolean overwrite;
    private boolean account;
    private String commencementPeriod;
    private String closingPeriod;
    private boolean accountingPeriod;
    private String startPay;
    private String endPay;
    private Date dateCommenced;
    private Date dateClosing;
    private Map<String, Object> variableMap;
    private String periodRange;
    public String contextEntityId;
    public String contextFilterExpression;
    public boolean curForm;

    public boolean getCurForm() {
        return this.curForm;
    }

    public void setCurForm(boolean curForm) {
        this.curForm = curForm;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Map<String, Object> getParas() {
        return this.paras;
    }

    public void setParas(Map<String, Object> paras) {
        this.paras = paras;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public boolean isContainsUnbVou() {
        return this.containsUnbVou;
    }

    public void setContainsUnbVou(boolean containsUnbVou) {
        this.containsUnbVou = containsUnbVou;
    }

    public boolean isOverwrite() {
        return this.overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public boolean isAccount() {
        return this.account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    public String getCommencementPeriod() {
        return this.commencementPeriod;
    }

    public void setCommencementPeriod(String commencementPeriod) {
        this.commencementPeriod = commencementPeriod;
    }

    public String getClosingPeriod() {
        return this.closingPeriod;
    }

    public void setClosingPeriod(String closingPeriod) {
        this.closingPeriod = closingPeriod;
    }

    public boolean isAccountingPeriod() {
        return this.accountingPeriod;
    }

    public void setAccountingPeriod(boolean accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    public Date getDateCommenced() {
        return this.dateCommenced;
    }

    public void setDateCommenced(Date dateCommenced) {
        this.dateCommenced = dateCommenced;
    }

    public Date getDateClosing() {
        return this.dateClosing;
    }

    public void setDateClosing(Date dateClosing) {
        this.dateClosing = dateClosing;
    }

    public String getPeriodRange() {
        return this.periodRange;
    }

    public void setPeriodRange(String periodRange) {
        this.periodRange = periodRange;
    }

    public boolean isAppUsePayment() {
        return this.appUsePayment;
    }

    public void setAppUsePayment(boolean appUsePayment) {
        this.appUsePayment = appUsePayment;
    }

    public JtableContext getContext() {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(this.dimensionSet);
        jtableContext.setFormKey(this.formKey);
        jtableContext.setFormSchemeKey(this.formSchemeKey);
        jtableContext.setTaskKey(this.taskKey);
        jtableContext.setVariableMap(this.variableMap);
        return jtableContext;
    }

    public LogParam getLogParam() {
        return null;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public String getStartPay() {
        return this.startPay;
    }

    public void setStartPay(String startPay) {
        this.startPay = startPay;
    }

    public String getEndPay() {
        return this.endPay;
    }

    public void setEndPay(String endPay) {
        this.endPay = endPay;
    }

    public String toString() {
        return "EfdcInfo{taskKey='" + this.taskKey + '\'' + ", formSchemeKey='" + this.formSchemeKey + '\'' + ", formKey='" + this.formKey + '\'' + ", paras=" + this.paras + ", dimensionSet=" + this.dimensionSet + ", containsUnbVou=" + this.containsUnbVou + ", overwrite=" + this.overwrite + ", account=" + this.account + ", commencementPeriod='" + this.commencementPeriod + '\'' + ", closingPeriod='" + this.closingPeriod + '\'' + ", accountingPeriod=" + this.accountingPeriod + ", startPay='" + this.startPay + '\'' + ", endPay='" + this.endPay + '\'' + ", dateCommenced=" + this.dateCommenced + ", dateClosing=" + this.dateClosing + ", periodRange='" + this.periodRange + '\'' + '}';
    }
}

