/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDO
 *  com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO
 */
package com.jiuqi.dc.integration.missmapping.impl.dao;

import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDO;
import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO;
import java.util.List;

public interface MissMappingDao {
    public List<String> queryDim(MissMappingQueryVO var1);

    public List<MissMappingDO> getDetail(MissMappingQueryVO var1);

    public Integer getDetailTotal(MissMappingQueryVO var1);

    public Integer getGatherTotal(MissMappingQueryVO var1);

    public List<MissMappingDO> getGather(MissMappingQueryVO var1);

    public List<String> getDcOrg(String var1);
}

