/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.samecontrol.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlEnvContextResultVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrlExtractClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrlExtractClient {
    public static final String CHANGE_ORG_PATH = "/api/gcreport/v1/samecontrol/sameCtrlExtract";

    @PostMapping(value={"/api/gcreport/v1/samecontrol/sameCtrlExtract/querySameCtrlExtractDatas"})
    public BusinessResponseEntity<Pagination<SameCtrlOffSetItemVO>> querySameCtrlExtractDatas(@RequestBody SameCtrlOffsetCond var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/sameCtrlExtract/extractSameCtrlData"})
    public BusinessResponseEntity<SameCtrlEnvContextResultVO> extractSameCtrlData(@RequestBody SameCtrlExtractDataVO var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/sameCtrlExtract/disEnable/{ids}"})
    public BusinessResponseEntity<String> disEnable(@PathVariable(value="ids") List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/sameCtrlExtract/enable/{ids}"})
    public BusinessResponseEntity<String> enable(@PathVariable(value="ids") List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/sameCtrlExtract/writeBack"})
    public BusinessResponseEntity<String> writeBack(@RequestBody SameCtrlExtractDataVO var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/sameCtrlExtract/delOffset/{ids}"})
    public BusinessResponseEntity<String> delOffset(@PathVariable(value="ids") List<String> var1);
}

