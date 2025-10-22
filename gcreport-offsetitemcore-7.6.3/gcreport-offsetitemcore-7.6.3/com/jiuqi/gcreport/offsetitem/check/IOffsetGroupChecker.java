/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.check;

import com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckResult;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;

public interface IOffsetGroupChecker {
    public String validatorName();

    public OffsetItemCheckResult saveCheck(GcOffSetVchrDTO var1);
}

