/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.model.dao;

import com.jiuqi.bde.bizmodel.impl.model.entity.FinBizModelEO;
import java.util.List;

public interface FinBizModelDao {
    public List<FinBizModelEO> loadAll();

    public void save(FinBizModelEO var1);

    public void update(FinBizModelEO var1);
}

