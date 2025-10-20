/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDTO
 *  com.jiuqi.dc.integration.missmapping.client.dto.MissMappingGatherDTO
 *  com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.dc.integration.missmapping.impl.service;

import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDTO;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingGatherDTO;
import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;

public interface MissMappingService {
    public List<BaseDataShowVO> getMissMappingDim(MissMappingQueryVO var1);

    public PageVO<MissMappingDTO> getMissMappingDetail(MissMappingQueryVO var1);

    public PageVO<MissMappingGatherDTO> getMissMappingGather(MissMappingQueryVO var1);
}

