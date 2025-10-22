/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.samecontrol.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlEnvContextResultVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrlExtractDataClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrlExtractDataClient {
    public static final String EXTRACT_DATA = "/api/gcreport/v1/samecontrol/extractData";

    @PostMapping(value={"/api/gcreport/v1/samecontrol/extractData/extractOffSetReportData/start"})
    public BusinessResponseEntity<SameCtrlEnvContextResultVO> sameCtrlExtractData(@RequestBody SameCtrlExtractDataVO var1);

    @GetMapping(value={"/api/gcreport/v1/samecontrol/extractData/start/progress/{sn}"})
    public BusinessResponseEntity<ProgressData<List<String>>> querySnStartProgress(@PathVariable(value="sn") String var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/extractData/extractReportData"})
    public BusinessResponseEntity<Object> extractReportData(@RequestBody SameCtrlExtractReportCond var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/extractData/extractLogInfo"})
    public BusinessResponseEntity<Object> querySameCtrlExtractLog(@RequestBody SameCtrlExtractDataVO var1);
}

