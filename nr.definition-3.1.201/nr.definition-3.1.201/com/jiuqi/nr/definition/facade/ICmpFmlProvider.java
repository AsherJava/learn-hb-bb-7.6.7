/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.util.List;

public interface ICmpFmlProvider {
    public FormulaSchemeDefine getFmlScheme();

    public List<FormulaDefine> getCmpFml();
}

