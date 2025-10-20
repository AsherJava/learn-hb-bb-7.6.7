/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather;

import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import java.util.List;

public interface IRuleTypeGather {
    public IRuleType getRuleTypeByCode(String var1);

    public List<IRuleType> listAll();
}

