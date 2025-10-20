/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.basedata.service;

import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import java.util.List;

public interface BaseDataVersionService {
    public BaseDataVersionDO get(BaseDataVersionDTO var1);

    public PageVO<BaseDataVersionDO> list(BaseDataVersionDTO var1);

    public R add(BaseDataVersionDO var1);

    public R split(BaseDataVersionDO var1);

    public R update(BaseDataVersionDO var1);

    public R remove(BaseDataVersionDO var1);

    public R changeStatus(List<BaseDataVersionDO> var1);

    public R syncCache(BaseDataVersionDO var1);

    public List<BaseDataVersionDO> listCache(BaseDataVersionDTO var1);
}

