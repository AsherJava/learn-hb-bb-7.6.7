/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 */
package com.jiuqi.nr.data.checkdes.obj;

import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import java.io.Serializable;

public class ImpAsyncPar
implements Serializable {
    private static final long serialVersionUID = -207889680901326891L;
    private CKDImpPar ckdImpPar;
    private ParamsMapping paramsMapping;

    public CKDImpPar getCkdImpPar() {
        return this.ckdImpPar;
    }

    public void setCkdImpPar(CKDImpPar ckdImpPar) {
        this.ckdImpPar = ckdImpPar;
    }

    public ParamsMapping getParamsMapping() {
        return this.paramsMapping;
    }

    public void setParamsMapping(ParamsMapping paramsMapping) {
        this.paramsMapping = paramsMapping;
    }
}

