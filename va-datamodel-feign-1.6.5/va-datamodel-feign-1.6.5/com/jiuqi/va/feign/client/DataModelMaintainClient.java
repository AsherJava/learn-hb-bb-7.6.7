/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.feign.client;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import java.util.List;
import java.util.Map;

public interface DataModelMaintainClient {
    public DataModelDO get(DataModelDTO var1);

    public PageVO<DataModelDO> listAll(DataModelDTO var1);

    public R add(DataModelDTO var1);

    public R update(DataModelDTO var1);

    public R publish(List<DataModelDTO> var1);

    public List<Map<String, Object>> getBizTypes();
}

