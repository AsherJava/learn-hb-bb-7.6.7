/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 */
package com.jiuqi.va.datamodel.service;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import java.util.List;

public interface VaDataModelPublishedService {
    public DataModelDO get(DataModelDTO var1);

    public PageVO<DataModelDO> list(DataModelDTO var1);

    public R push(DataModelDO var1);

    public R updateBaseInfo(DataModelDO var1);

    public R pushComplete(DataModelDO var1);

    public R pushIncrement(DataModelDO var1);

    public R remove(DataModelDO var1);

    public List<String> listGroup(DataModelDTO var1);

    public R syncCache(DataModelDTO var1);
}

