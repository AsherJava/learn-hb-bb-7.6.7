/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.consolidatedsystem.api.Formula;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.consolidatedsystem.api.Formula.ConsolidatedFormulaClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ConsolidatedFormulaClient {
    public static final String API_BASE_PATH = "/api/gcreport/v1/consolidatedFormula/";

    @GetMapping(value={"/api/gcreport/v1/consolidatedFormula/list/{systemId}"})
    public BusinessResponseEntity<List<ConsolidatedFormulaVO>> listConsFormulas(@PathVariable(value="systemId") String var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedFormula/save"})
    public BusinessResponseEntity<String> addConsFormula(@RequestBody List<ConsolidatedFormulaVO> var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedFormula//batchDelete"})
    public BusinessResponseEntity<String> batchDeleteConsFormula(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedFormula//exchangeSort/{opNodeId}/{step}"})
    public BusinessResponseEntity<String> exchangeSort(@PathVariable(value="opNodeId") String var1, @PathVariable(value="step") int var2);
}

