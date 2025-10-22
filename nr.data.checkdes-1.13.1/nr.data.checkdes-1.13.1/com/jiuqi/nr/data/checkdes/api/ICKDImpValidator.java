/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.api;

import com.jiuqi.nr.data.checkdes.facade.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;

public interface ICKDImpValidator {
    public CKDTransObj validate(CKDTransObj var1, CKDImpMes var2);

    public String name();
}

