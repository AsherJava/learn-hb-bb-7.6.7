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
import java.math.BigDecimal;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface FormulaDefine
extends IMetaItem {
    public String getFormulaSchemeKey();

    public String getFormKey();

    public String getCode();

    public String getExpression();

    public String getDataItems();

    @Deprecated
    public String getAdjustItems();

    public String getDescription();

    public boolean getIsAutoExecute();

    public boolean getUseCalculate();

    public boolean getUseCheck();

    public boolean getUseBalance();

    public int getCheckType();

    public String getBalanceZBExp();

    public boolean getIsPrivate();

    public String getUnit();

    public int getSyntax();

    public BigDecimal getOrdinal();
}

