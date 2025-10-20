/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.samecontrol.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrlOffSetItemClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrlOffSetItemClient {
    public static final String OFFSET_PATH = "/api/gcreport/v1/samecontrol/offset";

    @PostMapping(value={"/api/gcreport/v1/samecontrol/offset/listToSameParentOrgs"})
    public BusinessResponseEntity<List<GcOrgCacheVO>> listUnitPaths(@RequestBody SameCtrlOffsetCond var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/offset/queryOffsets"})
    public BusinessResponseEntity<Pagination<SameCtrlOffSetItemVO>> listOffsets(@RequestBody SameCtrlOffsetCond var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/offset/deleteOffsets"})
    public BusinessResponseEntity<String> deleteOffsetEntrysByMrecid(@RequestBody SameCtrlOffsetCond var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/offset/queryInputAdjustment/{mRecid}"})
    public BusinessResponseEntity<List<SameCtrlOffSetItemVO>> queryInputAdjustment(@PathVariable(value="mRecid") String var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/offset/saveInputAdjustment"})
    public BusinessResponseEntity<String> saveInputAdjustment(@RequestBody List<List<SameCtrlOffSetItemVO>> var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/offset/deleteInputAdjustment"})
    public BusinessResponseEntity<String> deleteInputAdjustment(@RequestBody SameCtrlOffsetCond var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/offset/extractData"})
    public void extractData(@RequestBody SameCtrlOffsetCond var1);
}

