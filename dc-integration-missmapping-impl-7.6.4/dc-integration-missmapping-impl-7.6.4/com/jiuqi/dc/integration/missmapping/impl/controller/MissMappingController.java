/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  com.jiuqi.dc.integration.missmapping.client.MissMappingQueryClient
 *  com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDTO
 *  com.jiuqi.dc.integration.missmapping.client.dto.MissMappingGatherDTO
 *  com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.integration.missmapping.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.integration.missmapping.client.MissMappingQueryClient;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDTO;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingGatherDTO;
import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO;
import com.jiuqi.dc.integration.missmapping.impl.service.MissMappingService;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissMappingController
implements MissMappingQueryClient {
    @Autowired
    private MissMappingService missMappingService;

    public BusinessResponseEntity<List<BaseDataShowVO>> queryMissMappingDim(MissMappingQueryVO missMappingQueryVO) {
        return BusinessResponseEntity.ok(this.missMappingService.getMissMappingDim(missMappingQueryVO));
    }

    public BusinessResponseEntity<PageVO<MissMappingDTO>> queryMissMappingDetail(MissMappingQueryVO missMappingQueryVO) {
        return BusinessResponseEntity.ok(this.missMappingService.getMissMappingDetail(missMappingQueryVO));
    }

    public BusinessResponseEntity<PageVO<MissMappingGatherDTO>> gatherMissMapping(MissMappingQueryVO missMappingQueryVO) {
        return BusinessResponseEntity.ok(this.missMappingService.getMissMappingGather(missMappingQueryVO));
    }
}

