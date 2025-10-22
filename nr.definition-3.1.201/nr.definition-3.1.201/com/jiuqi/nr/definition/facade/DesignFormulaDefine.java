/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.FormulaDefine;
import java.math.BigDecimal;
import java.util.Date;

public interface DesignFormulaDefine
extends FormulaDefine {
    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setFormulaSchemeKey(String var1);

    public void setFormKey(String var1);

    public void setCode(String var1);

    public void setExpression(String var1);

    public void setDataItems(String var1);

    @Deprecated
    public void setAdjustItems(String var1);

    public void setDescription(String var1);

    public void setIsAutoExecute(boolean var1);

    public void setUseCalculate(boolean var1);

    public void setUseCheck(boolean var1);

    public void setUseBalance(boolean var1);

    public void setCheckType(int var1);

    public void setBalanceZBExp(String var1);

    public void setIsPrivate(boolean var1);

    public void setUnit(String var1);

    public void setSyntax(int var1);

    public void setOrdinal(BigDecimal var1);
}

