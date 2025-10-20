/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.model.dao;

import com.jiuqi.bde.bizmodel.impl.model.entity.CustomBizModelEO;
import java.util.List;

public interface CustomBizModelDao {
    public List<CustomBizModelEO> loadAll();

    public void save(CustomBizModelEO var1);

    public void update(CustomBizModelEO var1);
}

