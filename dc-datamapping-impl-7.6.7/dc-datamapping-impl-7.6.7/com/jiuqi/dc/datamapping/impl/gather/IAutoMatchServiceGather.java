/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.impl.gather;

import com.jiuqi.dc.datamapping.impl.service.AutoMatchService;
import java.util.List;

public interface IAutoMatchServiceGather {
    public AutoMatchService getServiceByCode(String var1);

    public List<AutoMatchService> listAll();
}

