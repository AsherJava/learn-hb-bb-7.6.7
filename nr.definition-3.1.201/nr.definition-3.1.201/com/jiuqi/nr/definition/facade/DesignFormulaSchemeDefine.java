/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import java.util.Date;

public interface DesignFormulaSchemeDefine
extends FormulaSchemeDefine {
    public void setUpdateTime(Date var1);

    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setFormSchemeKey(String var1);

    public void setDescription(String var1);

    public void setFormulaSchemeType(FormulaSchemeType var1);

    public void setDisplayMode(FormulaSchemeDisplayMode var1);

    @Deprecated
    public void setReserveAllZeroRow(boolean var1);

    public boolean setDefault(boolean var1);

    public boolean setShow(boolean var1);

    public void setEFDCPeriodSettingDefineImpl(EFDCPeriodSettingDefineImpl var1);

    public void setFormulaSchemeMenuApply(int var1);
}

