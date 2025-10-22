/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.gcreport.financialcheckapi.offset;

import com.jiuqi.gcreport.financialcheckapi.offset.SubjectInfoVO;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(contextId="com.jiuqi.gcreport.api.dataentry.GcOffsetInputAdjustEntryClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcOffsetInputAdjustEntryClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/inputAdjust";

    @PostMapping(value={"/api/gcreport/v1/inputAdjust/listSubjectInfoBySystemId/{systemId}"})
    public List<SubjectInfoVO> listSubjectInfo(@PathVariable(value="systemId") String var1);
}

