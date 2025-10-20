/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.calculate.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.gcreport.calculate.vo.GcCalcArgmentsVO;
import com.jiuqi.gcreport.calculate.vo.GcCalcLogVO;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.calculate.api.GcCalcClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcCalcClient {
    public static final String CALC_API_BASE_PATH = "/api/gcreport/v1/calculate";

    @PostMapping(value={"/api/gcreport/v1/calculate/start"})
    public BusinessResponseEntity<Object> start(@Valid @RequestBody GcCalcArgmentsVO var1);

    @PostMapping(value={"/api/gcreport/v1/calculate/findcalcloginfo"})
    public BusinessResponseEntity<GcCalcLogVO> findCalcLogInfo(@RequestBody GcCalcArgmentsVO var1);

    @GetMapping(value={"/api/gcreport/v1/calculate/start/progress/{sn}"})
    public BusinessResponseEntity<ProgressData<List<String>>> querySnStartProgress(@PathVariable(value="sn") String var1);

    @PostMapping(value={"/api/gcreport/v1/calculate/start/progress/{sn}"})
    public BusinessResponseEntity<Object> deleteSnStartProgress(@PathVariable(value="sn") String var1);

    @PostMapping(value={"/api/gcreport/v1/calculate/ruleTreeExpandLevel"})
    public List<Map<String, Object>> ruleTreeExpandLevel(@PathVariable(value="sn") String var1);
}

