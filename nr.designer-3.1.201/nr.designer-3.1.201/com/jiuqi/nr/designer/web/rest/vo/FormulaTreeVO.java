/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.facade.FormulaConditionLinkObj;
import java.util.Date;
import java.util.List;

public class FormulaTreeVO {
    private String formulaSchemeKey;
    private String formKey;
    private String code;
    private String expression;
    private String dataItems;
    private String description;
    private boolean isAutoExecute;
    private boolean useCalculate;
    private boolean useCheck;
    private boolean useBalance;
    private int checkType;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    private Date updateTime;
    private String largeExpression;
    private String balanceZBExp;
    private boolean isPrivate;
    private String unit = "-";
    private List<FormulaConditionLinkObj> FormulaConditions;

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDataItems() {
        return this.dataItems;
    }

    public void setDataItems(String dataItems) {
        this.dataItems = dataItems;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAutoExecute() {
        return this.isAutoExecute;
    }

    public void setAutoExecute(boolean autoExecute) {
        this.isAutoExecute = autoExecute;
    }

    public boolean isUseCalculate() {
        return this.useCalculate;
    }

    public void setUseCalculate(boolean useCalculate) {
        this.useCalculate = useCalculate;
    }

    public boolean isUseCheck() {
        return this.useCheck;
    }

    public void setUseCheck(boolean useCheck) {
        this.useCheck = useCheck;
    }

    public boolean isUseBalance() {
        return this.useBalance;
    }

    public void setUseBalance(boolean useBalance) {
        this.useBalance = useBalance;
    }

    public int getCheckType() {
        return this.checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLargeExpression() {
        return this.largeExpression;
    }

    public void setLargeExpression(String largeExpression) {
        this.largeExpression = largeExpression;
    }

    public String getBalanceZBExp() {
        return this.balanceZBExp;
    }

    public void setBalanceZBExp(String balanceZBExp) {
        this.balanceZBExp = balanceZBExp;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        this.isPrivate = aPrivate;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<FormulaConditionLinkObj> getFormulaConditions() {
        return this.FormulaConditions;
    }

    public void setFormulaConditions(List<FormulaConditionLinkObj> formulaConditions) {
        this.FormulaConditions = formulaConditions;
    }
}

