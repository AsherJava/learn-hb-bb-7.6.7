/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.gcreport.nr.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="com.jiuqi.gcreport.nr.api.ReportInfoClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ReportInfoClient {
    public static final String baseUrl = "/api/gcreport/v1/report";

    @GetMapping(value={"/api/gcreport/v1/report/schemes/{schemeId}/periodType"})
    public BusinessResponseEntity<Integer> querySchemePeriodType(@PathVariable(value="schemeId") String var1);

    @GetMapping(value={"/api/gcreport/v1/report/{schemeId}/cwFormulaScheme"})
    public BusinessResponseEntity<List<FormulaSchemeDefine>> cwFormulaScheme(@PathVariable(value="schemeId") String var1);
}

