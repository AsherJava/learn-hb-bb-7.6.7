/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import com.jiuqi.util.StringUtils;
import java.util.Date;

public class I18nRunTimeFormulaSchemeDefine
implements FormulaSchemeDefine {
    private final FormulaSchemeDefine formulaSchemeDefine;
    private String title;

    public I18nRunTimeFormulaSchemeDefine(FormulaSchemeDefine formulaSchemeDefine) {
        this.formulaSchemeDefine = formulaSchemeDefine;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formulaSchemeDefine.getFormSchemeKey();
    }

    @Override
    public String getDescription() {
        return this.formulaSchemeDefine.getDescription();
    }

    @Override
    public FormulaSchemeType getFormulaSchemeType() {
        return this.formulaSchemeDefine.getFormulaSchemeType();
    }

    @Override
    public FormulaSchemeDisplayMode getDisplayMode() {
        return this.formulaSchemeDefine.getDisplayMode();
    }

    @Override
    public boolean getReserveAllZeroRow() {
        return this.formulaSchemeDefine.getReserveAllZeroRow();
    }

    @Override
    public boolean isDefault() {
        return this.formulaSchemeDefine.isDefault();
    }

    @Override
    public boolean isShow() {
        return this.formulaSchemeDefine.isShow();
    }

    @Override
    public EFDCPeriodSettingDefineImpl getEfdcPeriodSettingDefineImpl() {
        return this.formulaSchemeDefine.getEfdcPeriodSettingDefineImpl();
    }

    public String getKey() {
        return this.formulaSchemeDefine.getKey();
    }

    public String getOrder() {
        return this.formulaSchemeDefine.getOrder();
    }

    public String getVersion() {
        return this.formulaSchemeDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.formulaSchemeDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.formulaSchemeDefine.getUpdateTime();
    }

    public String getTitle() {
        return StringUtils.isEmpty((String)this.title) ? this.formulaSchemeDefine.getTitle() : this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getFormulaSchemeMenuApply() {
        return this.formulaSchemeDefine.getFormulaSchemeMenuApply();
    }
}

