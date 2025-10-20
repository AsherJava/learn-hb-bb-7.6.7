/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.model.dao;

import com.jiuqi.bde.bizmodel.impl.model.entity.BaseDataBizModelEO;
import java.util.List;

public interface BaseDataBizModelDao {
    public List<BaseDataBizModelEO> loadAll();

    public void save(BaseDataBizModelEO var1);

    public void update(BaseDataBizModelEO var1);
}

