/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import java.util.List;

public interface IRuntimeFormulaSchemeService {
    public FormulaSchemeDefine queryFormulaScheme(String var1);

    public FormulaSchemeDefine getDefaultFormulaSchemeInFormScheme(String var1);

    public List<FormulaSchemeDefine> getFormulaSchemesByFormScheme(String var1);

    public List<FormulaSchemeDefine> getFormulaSchemesByFormScheme(String var1, FormulaSchemeType var2);

    public void refreshObjCache(String var1, EFDCPeriodSettingDefineImpl var2);
}

