/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface FormulaSchemeDefine
extends IMetaItem {
    public String getFormSchemeKey();

    public String getDescription();

    public FormulaSchemeType getFormulaSchemeType();

    public FormulaSchemeDisplayMode getDisplayMode();

    @Deprecated
    public boolean getReserveAllZeroRow();

    public boolean isDefault();

    public boolean isShow();

    public EFDCPeriodSettingDefineImpl getEfdcPeriodSettingDefineImpl();

    public int getFormulaSchemeMenuApply();
}

