/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.basedata.service;

import com.jiuqi.va.domain.basedata.BaseDataCacheDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import java.util.Map;

public interface BaseDataService {
    public R exist(BaseDataDTO var1);

    public int count(BaseDataDTO var1);

    public PageVO<BaseDataDO> list(BaseDataDTO var1);

    public Map<String, Object[]> columnValueList(BaseDataColumnValueDTO var1);

    public R add(BaseDataDTO var1);

    public R batchAdd(BaseDataBatchOptDTO var1);

    public R update(BaseDataDTO var1);

    public R batchUpdate(BaseDataBatchOptDTO var1);

    public R remove(BaseDataDTO var1);

    public R batchRemove(BaseDataBatchOptDTO var1);

    public R stop(BaseDataDTO var1);

    public R batchStop(BaseDataBatchOptDTO var1);

    public R recover(BaseDataDTO var1);

    public R batchRecover(BaseDataBatchOptDTO var1);

    public R move(BaseDataMoveDTO var1);

    public R clearRecovery(BaseDataDTO var1);

    public R changeShare(BaseDataBatchOptDTO var1);

    public R getNextCode(BaseDataDTO var1);

    public R sync(BaseDataBatchOptDTO var1);

    public R fastUpDown(BaseDataDTO var1);

    public List<BaseDataDO> verDiffList(BaseDataDTO var1);

    public R initCache(BaseDataCacheDTO var1);

    public R syncCache(BaseDataCacheDTO var1);

    public R cleanCache(BaseDataCacheDTO var1);
}

