/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.integration.missmapping.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDTO;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingGatherDTO;
import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MissMappingQueryClient {
    public static final String MISS_MAPPING_API_BASE_PATH = "/api/datacenter/v1/ref/missmapping";

    @PostMapping(value={"/api/datacenter/v1/ref/missmapping/dim"})
    public BusinessResponseEntity<List<BaseDataShowVO>> queryMissMappingDim(@RequestBody MissMappingQueryVO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/missmapping/detail"})
    public BusinessResponseEntity<PageVO<MissMappingDTO>> queryMissMappingDetail(@RequestBody MissMappingQueryVO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/missmapping/gather"})
    public BusinessResponseEntity<PageVO<MissMappingGatherDTO>> gatherMissMapping(@RequestBody MissMappingQueryVO var1);
}

