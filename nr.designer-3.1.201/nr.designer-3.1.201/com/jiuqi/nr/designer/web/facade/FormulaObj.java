/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.web.facade.FormulaConditionLinkObj;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormulaObj {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="IsNew")
    private boolean isNew;
    @JsonProperty(value="IsDirty")
    private boolean isDirty;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="Expression")
    private String expression;
    @JsonProperty(value="Description")
    private String description;
    @JsonProperty(value="CheckType")
    private int checkType;
    @JsonProperty(value="UseCalculate")
    private boolean useCalculate;
    @JsonProperty(value="UseCheck")
    private boolean useCheck;
    @JsonProperty(value="UseBalance")
    private boolean useBalance;
    @JsonProperty(value="SchemeKey")
    private String schemeKey;
    @JsonProperty(value="FormKey")
    private String formKey;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="AdjustItems")
    private String adjustItems;
    @JsonProperty(value="FormulaLevelCode")
    private String formulaLevelCode;
    @JsonProperty(value="AutoExecute")
    private boolean autoExecute;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="PosX")
    private int posX;
    @JsonProperty(value="PosY")
    private int posY;
    @JsonProperty(value="BalanceZBExp")
    private String balanceZBExp;
    @JsonProperty(value="Unit")
    private String unit;
    @JsonProperty(value="IsPrivate")
    private String IsPrivate;
    private String language;
    @JsonProperty(value="FormulaConditions")
    private List<FormulaConditionLinkObj> formulaConditions = new ArrayList<FormulaConditionLinkObj>();

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIsPrivate() {
        return this.IsPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.IsPrivate = isPrivate;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsNew() {
        return this.isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isIsDirty() {
        return this.isDirty;
    }

    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    public boolean isIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCheckType() {
        return this.checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
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

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAdjustItems() {
        return this.adjustItems;
    }

    public void setAdjustItems(String adjustItems) {
        this.adjustItems = adjustItems;
    }

    public String getFormulaLevelCode() {
        return this.formulaLevelCode;
    }

    public void setFormulaLevelCode(String formulaLevelCode) {
        this.formulaLevelCode = formulaLevelCode;
    }

    public boolean isAutoExecute() {
        return this.autoExecute;
    }

    public void setAutoExecute(boolean autoExecute) {
        this.autoExecute = autoExecute;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }

    public String getBalanceZBExp() {
        return this.balanceZBExp;
    }

    public void setBalanceZBExp(String balanceZBExp) {
        this.balanceZBExp = balanceZBExp;
    }

    public List<FormulaConditionLinkObj> getFormulaConditions() {
        return this.formulaConditions;
    }

    public void setFormulaConditions(List<FormulaConditionLinkObj> formulaConditions) {
        this.formulaConditions = formulaConditions;
    }
}

