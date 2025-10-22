/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.service;

import com.jiuqi.nr.data.checkdes.obj.CKDImpDetails;
import com.jiuqi.nr.data.checkdes.obj.CKDTransObj;
import java.util.List;

public interface ICKDImpValidator {
    public CKDTransObj validate(CKDTransObj var1, List<CKDImpDetails> var2);

    public String name();
}

