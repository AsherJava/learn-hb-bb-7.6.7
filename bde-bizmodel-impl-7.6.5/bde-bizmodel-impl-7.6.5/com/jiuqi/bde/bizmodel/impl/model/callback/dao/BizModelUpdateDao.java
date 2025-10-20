/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.model.callback.dao;

import com.jiuqi.bde.bizmodel.impl.model.callback.FetchSourceUpdateEO;
import com.jiuqi.bde.bizmodel.impl.model.entity.BaseDataBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.entity.CustomBizModelEO;
import com.jiuqi.bde.bizmodel.impl.model.entity.FinBizModelEO;
import java.util.List;

public interface BizModelUpdateDao {
    public List<FetchSourceUpdateEO> loadAll();

    public void insertFinBizModel(List<FinBizModelEO> var1);

    public void insertCustomBizModel(List<CustomBizModelEO> var1);

    public void insertBaseDataBizModel(List<BaseDataBizModelEO> var1);
}

