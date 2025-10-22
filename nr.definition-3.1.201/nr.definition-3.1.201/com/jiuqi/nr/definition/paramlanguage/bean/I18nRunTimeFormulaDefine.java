/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.util.StringUtils;
import java.math.BigDecimal;
import java.util.Date;

public class I18nRunTimeFormulaDefine
implements FormulaDefine {
    private final FormulaDefine formulaDefine;
    private String description;

    public I18nRunTimeFormulaDefine(FormulaDefine formulaDefine) {
        this.formulaDefine = formulaDefine;
    }

    @Override
    public String getFormulaSchemeKey() {
        return this.formulaDefine.getFormulaSchemeKey();
    }

    @Override
    public String getFormKey() {
        return this.formulaDefine.getFormKey();
    }

    @Override
    public String getCode() {
        return this.formulaDefine.getCode();
    }

    @Override
    public String getExpression() {
        return this.formulaDefine.getExpression();
    }

    @Override
    public String getDataItems() {
        return this.formulaDefine.getDataItems();
    }

    @Override
    @Deprecated
    public String getAdjustItems() {
        return this.formulaDefine.getAdjustItems();
    }

    @Override
    public boolean getIsAutoExecute() {
        return this.formulaDefine.getIsAutoExecute();
    }

    @Override
    public boolean getUseCalculate() {
        return this.formulaDefine.getUseCalculate();
    }

    @Override
    public boolean getUseCheck() {
        return this.formulaDefine.getUseCheck();
    }

    @Override
    public boolean getUseBalance() {
        return this.formulaDefine.getUseBalance();
    }

    @Override
    public int getCheckType() {
        return this.formulaDefine.getCheckType();
    }

    public String getKey() {
        return this.formulaDefine.getKey();
    }

    public String getTitle() {
        return this.formulaDefine.getTitle();
    }

    public String getOrder() {
        return this.formulaDefine.getOrder();
    }

    public String getVersion() {
        return this.formulaDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.formulaDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.formulaDefine.getUpdateTime();
    }

    @Override
    public String getDescription() {
        return StringUtils.isEmpty((String)this.description) ? this.formulaDefine.getDescription() : this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getBalanceZBExp() {
        return this.formulaDefine.getBalanceZBExp();
    }

    @Override
    public boolean getIsPrivate() {
        return this.formulaDefine.getIsPrivate();
    }

    @Override
    public String getUnit() {
        return this.formulaDefine.getUnit();
    }

    @Override
    public int getSyntax() {
        return this.formulaDefine.getSyntax();
    }

    @Override
    public BigDecimal getOrdinal() {
        return this.formulaDefine.getOrdinal();
    }
}

