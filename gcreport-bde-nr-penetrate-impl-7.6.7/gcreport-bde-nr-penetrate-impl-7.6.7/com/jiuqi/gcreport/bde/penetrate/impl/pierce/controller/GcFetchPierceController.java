/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.penetrate.client.api.GcFetchPierceClient
 *  com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchBatchPierceDTO
 *  com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.BdeFormulaResultVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.BdeQueryFormulaParamVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.EfdcFormulaResultVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceDataRegionParamVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceParamVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.NrLinkInfoVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.QueryBblxParam
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.penetrate.impl.pierce.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.penetrate.client.api.GcFetchPierceClient;
import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchBatchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.BdeFormulaResultVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.BdeQueryFormulaParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.EfdcFormulaResultVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceDataRegionParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.NrLinkInfoVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.QueryBblxParam;
import com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.GcBdeFetchPierceService;
import com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.GcEfdcFetchPierceService;
import com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.GcFetchPierceService;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcFetchPierceController
implements GcFetchPierceClient {
    @Autowired
    GcFetchPierceService gcFetchPierceService;
    @Autowired
    GcEfdcFetchPierceService efdcFetchPierceService;
    @Autowired
    GcBdeFetchPierceService bdeFetchPierceService;

    @NRContextBuild
    public BusinessResponseEntity<GcFetchPierceParamVO> queryPierceParams(GcFetchPierceDTO efdcRequestInfo) {
        return BusinessResponseEntity.ok((Object)this.gcFetchPierceService.queryPierceParams(efdcRequestInfo));
    }

    @NRContextBuild
    public BusinessResponseEntity<Boolean> queryPierceEnable(GcFetchPierceDTO efdcRequestInfo) {
        return BusinessResponseEntity.ok((Object)this.gcFetchPierceService.enable(efdcRequestInfo));
    }

    @NRContextBuild
    public BusinessResponseEntity<Map<String, Map<String, Boolean>>> queryBatchPierceParams(GcFetchBatchPierceDTO batchPierceDTO) {
        return BusinessResponseEntity.ok(this.gcFetchPierceService.queryBatchPierceParams(batchPierceDTO));
    }

    public BusinessResponseEntity<GcFetchPierceDataRegionParamVO> queryFormByRegionKey(String regionKey) {
        return BusinessResponseEntity.ok((Object)this.gcFetchPierceService.queryFormByRegionKey(regionKey));
    }

    @NRContextBuild
    public BusinessResponseEntity<EfdcFormulaResultVO> efdcFormulaAnalysis(GcFetchPierceDTO efdcNewRequestInfo) {
        return BusinessResponseEntity.ok((Object)this.efdcFetchPierceService.efdcFormulaAnalysis(efdcNewRequestInfo));
    }

    @NRContextBuild
    public BusinessResponseEntity<BdeFormulaResultVO> analysisBDEFetchSetting(BdeQueryFormulaParamVO bdeQueryFormulaParamVO) {
        return BusinessResponseEntity.ok((Object)this.bdeFetchPierceService.analysisBdeFetchSetting(bdeQueryFormulaParamVO));
    }

    @NRContextBuild
    public BusinessResponseEntity<String> queryBblx(QueryBblxParam bblxParam) {
        return BusinessResponseEntity.ok((Object)this.gcFetchPierceService.queryBblx(bblxParam));
    }

    public BusinessResponseEntity<List<NrLinkInfoVO>> queryLinkInfo(List<String> linkIdList) {
        return BusinessResponseEntity.ok(this.gcFetchPierceService.queryLinkInfo(linkIdList));
    }
}

