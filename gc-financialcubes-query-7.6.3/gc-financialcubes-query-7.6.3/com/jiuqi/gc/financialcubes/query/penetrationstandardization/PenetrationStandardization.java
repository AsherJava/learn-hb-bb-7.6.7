/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.penetrationstandardization;

import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;

public interface PenetrationStandardization {
    public boolean match(String var1, String var2, PenetrationContextInfo var3);

    public void process(PenetrationParamDTO var1, String var2, String var3, PenetrationContextInfo var4);
}

