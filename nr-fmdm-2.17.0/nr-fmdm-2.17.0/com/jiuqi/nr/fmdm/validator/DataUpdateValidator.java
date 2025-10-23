/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.validator;

import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import java.util.List;

public interface DataUpdateValidator {
    public List<FMDMCheckFailNodeInfo> check(FMDMDataDTO var1);
}

