/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class FormulaDTO
extends Formula
implements FormulaDefine {
    private final String formulaSchemeKey;
    private final String dataItems;
    private final String adjustItems;
    private final boolean isPrivate;
    private final String unit;
    private byte type;
    private final int syntax;
    private final BigDecimal ordinal;
    private List<FormulaParsedExp> expressions;
    private String effectiveForm;

    public FormulaDTO(FormulaDefine formulaDefine) {
        this(null, formulaDefine);
    }

    public FormulaDTO(FormDefine form, FormulaDefine formulaDefine) {
        super.setId(formulaDefine.getKey());
        super.setCode(formulaDefine.getCode());
        super.setFormula(formulaDefine.getExpression());
        super.setMeanning(formulaDefine.getDescription());
        super.setChecktype(Integer.valueOf(formulaDefine.getCheckType()));
        super.setAutoCalc(formulaDefine.getIsAutoExecute());
        super.setOrder(formulaDefine.getOrder());
        if (formulaDefine.getUseCalculate()) {
            this.type = (byte)(this.type | 1);
        }
        if (formulaDefine.getUseCheck()) {
            this.type = (byte)(this.type | 2);
        }
        if (formulaDefine.getUseBalance()) {
            super.setBalanceZBExp(formulaDefine.getBalanceZBExp());
            this.type = (byte)(this.type | 4);
        }
        if (null != form) {
            super.setFormKey(form.getKey());
            super.setReportName(form.getFormCode());
        }
        this.formulaSchemeKey = formulaDefine.getFormulaSchemeKey();
        this.dataItems = formulaDefine.getDataItems();
        this.adjustItems = formulaDefine.getAdjustItems();
        this.isPrivate = formulaDefine.getIsPrivate();
        this.unit = formulaDefine.getUnit();
        this.syntax = formulaDefine.getSyntax();
        this.ordinal = formulaDefine.getOrdinal();
    }

    public void addConditions(Collection<Formula> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return;
        }
        super.getConditions().addAll(conditions);
    }

    public void addExpression(FormulaParsedExp expression) {
        if (null == this.expressions) {
            this.expressions = new ArrayList<FormulaParsedExp>();
        }
        this.expressions.add(expression);
    }

    public void setEffectiveForm(String effectiveForm) {
        this.effectiveForm = effectiveForm;
    }

    public String getFormulaKey() {
        return super.getId();
    }

    public String getFormulaCode() {
        return super.getCode();
    }

    public FormulaDefine getFormulaDefine() {
        return this;
    }

    public List<FormulaParsedExp> getExpressions() {
        if (null == this.expressions) {
            return Collections.emptyList();
        }
        return this.expressions;
    }

    public String getEffectiveForm() {
        return this.effectiveForm;
    }

    public int getType() {
        return this.type;
    }

    public String getKey() {
        return super.getId();
    }

    @Override
    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    @Override
    public String getExpression() {
        return super.getFormula();
    }

    @Override
    public String getDataItems() {
        return this.dataItems;
    }

    @Override
    public String getAdjustItems() {
        return this.adjustItems;
    }

    @Override
    public String getDescription() {
        return super.getMeanning();
    }

    @Override
    public boolean getIsAutoExecute() {
        return super.isAutoCalc();
    }

    @Override
    public boolean getUseCalculate() {
        return (this.type & 1) > 0;
    }

    @Override
    public boolean getUseCheck() {
        return (this.type & 2) > 0;
    }

    @Override
    public boolean getUseBalance() {
        return (this.type & 4) > 0;
    }

    @Override
    public int getCheckType() {
        return null == super.getChecktype() ? 0 : super.getChecktype();
    }

    @Override
    public boolean getIsPrivate() {
        return this.isPrivate;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    public Date getUpdateTime() {
        return null;
    }

    public String getTitle() {
        return "";
    }

    public String getVersion() {
        return "";
    }

    public String getOwnerLevelAndId() {
        return "";
    }

    @Override
    public int getSyntax() {
        return this.syntax;
    }

    @Override
    public BigDecimal getOrdinal() {
        return this.ordinal;
    }
}

