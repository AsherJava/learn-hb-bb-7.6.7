/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDetailDO
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.basedata.service;

import com.jiuqi.va.domain.basedata.BaseDataCacheDO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDetailDO;
import com.jiuqi.va.domain.common.R;
import java.util.List;

public interface BaseDataDetailService {
    public R add(BaseDataDetailDO var1);

    public R delete(BaseDataDetailDO var1);

    public List<BaseDataDetailDO> get(BaseDataDetailDO var1);

    public List<BaseDataDetailDO> list(BaseDataDetailDO var1);

    public void loadDetailData(BaseDataDO var1, List<BaseDataCacheDO> var2);
}

