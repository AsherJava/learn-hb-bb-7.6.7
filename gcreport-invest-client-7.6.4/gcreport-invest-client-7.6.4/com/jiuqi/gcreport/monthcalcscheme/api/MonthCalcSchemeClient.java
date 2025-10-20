/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.monthcalcscheme.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcSchemeVO;
import com.jiuqi.gcreport.monthcalcscheme.vo.TaskVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.billcore.monthcalcscheme.api.MonthCalcSchemeClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface MonthCalcSchemeClient {
    public static final String API_PATH = "/api/gcreport/v1/monthCalcScheme/";

    @GetMapping(value={"/api/gcreport/v1/monthCalcScheme/getMonthCalcSchemeTree"})
    public BusinessResponseEntity<List<MonthCalcSchemeVO>> listMonthCalcSchemes();

    @PostMapping(value={"/api/gcreport/v1/monthCalcScheme/addMonthCalcScheme"})
    public BusinessResponseEntity<Object> addMonthCalcScheme(@RequestBody MonthCalcSchemeVO var1);

    @GetMapping(value={"/api/gcreport/v1/monthCalcScheme/getMonthCalcScheme"})
    public BusinessResponseEntity<MonthCalcSchemeVO> getMonthCalcScheme(@RequestParam(value="monthCalcSchemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/monthCalcScheme/delMonthCalcScheme"})
    public BusinessResponseEntity<Object> deleteMonthCalcScheme(@RequestBody String var1);

    @GetMapping(value={"/api/gcreport/v1/monthCalcScheme/getTasksOfType"})
    public BusinessResponseEntity<Map<String, List<TaskVO>>> getTasksOfType();
}

