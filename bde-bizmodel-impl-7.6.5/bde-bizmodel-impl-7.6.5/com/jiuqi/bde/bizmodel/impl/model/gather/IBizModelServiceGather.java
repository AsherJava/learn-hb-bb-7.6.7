/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.model.gather;

import com.jiuqi.bde.bizmodel.impl.model.service.BizModelManageService;
import java.util.List;

public interface IBizModelServiceGather {
    public BizModelManageService getByCode(String var1);

    public List<BizModelManageService> list();
}

