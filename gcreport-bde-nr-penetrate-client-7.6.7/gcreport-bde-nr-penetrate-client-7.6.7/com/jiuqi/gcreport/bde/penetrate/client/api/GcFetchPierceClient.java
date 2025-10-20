/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.bde.penetrate.client.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchBatchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.BdeFormulaResultVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.BdeQueryFormulaParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.EfdcFormulaResultVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceDataRegionParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.GcFetchPierceParamVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.NrLinkInfoVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.QueryBblxParam;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GcFetchPierceClient {
    @PostMapping(value={"/api/gcreport/v1/fetch/api/queryPierceParams"})
    @NRContextBuild
    public BusinessResponseEntity<GcFetchPierceParamVO> queryPierceParams(@RequestBody GcFetchPierceDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/api/queryPierceEnable"})
    @NRContextBuild
    public BusinessResponseEntity<Boolean> queryPierceEnable(@RequestBody GcFetchPierceDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/api/queryBatchPierceParams"})
    @NRContextBuild
    public BusinessResponseEntity<Map<String, Map<String, Boolean>>> queryBatchPierceParams(@RequestBody GcFetchBatchPierceDTO var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/api/queryFormByRegionKey/{regionKey}"})
    public BusinessResponseEntity<GcFetchPierceDataRegionParamVO> queryFormByRegionKey(@PathVariable(value="regionKey") String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/api/queryEfdcFormulaParams"})
    @NRContextBuild
    public BusinessResponseEntity<EfdcFormulaResultVO> efdcFormulaAnalysis(@RequestBody GcFetchPierceDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/api/queryBdeFormulaParams"})
    @NRContextBuild
    public BusinessResponseEntity<BdeFormulaResultVO> analysisBDEFetchSetting(@RequestBody BdeQueryFormulaParamVO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/api/queryBblx"})
    public BusinessResponseEntity<String> queryBblx(@RequestBody QueryBblxParam var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/api/queryLinkInfo"})
    public BusinessResponseEntity<List<NrLinkInfoVO>> queryLinkInfo(@RequestBody List<String> var1);
}

