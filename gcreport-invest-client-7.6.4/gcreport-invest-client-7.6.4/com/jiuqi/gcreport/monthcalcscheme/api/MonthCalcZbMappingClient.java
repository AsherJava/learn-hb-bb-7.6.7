/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.monthcalcscheme.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.billcore.monthcalcscheme.api.MonthCalcZbMappingClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface MonthCalcZbMappingClient {
    public static final String API_PATH = "/api/gcreport/v1/monthCalcZbMapping/";

    @PostMapping(value={"/api/gcreport/v1/monthCalcZbMapping/listZbMappings"})
    public BusinessResponseEntity<PageInfo<MonthCalcZbMappingVO>> listZbMappings(@RequestBody MonthCalcZbMappingCond var1);

    @PostMapping(value={"/api/gcreport/v1/monthCalcZbMapping/saveZbMappings"})
    public BusinessResponseEntity<String> saveZbMappings(@RequestParam(value="monthCalcSchemeId") String var1, @RequestBody List<MonthCalcZbMappingVO> var2);

    @PostMapping(value={"/api/gcreport/v1/monthCalcZbMapping/deleteZbMappings"})
    public BusinessResponseEntity<List<MonthCalcZbMappingVO>> deleteZbMappings(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/monthCalcZbMapping//exchangeSort/{opNodeId}/{step}"})
    public BusinessResponseEntity<String> exchangeSort(@PathVariable(value="opNodeId") String var1, @PathVariable(value="step") int var2);
}

