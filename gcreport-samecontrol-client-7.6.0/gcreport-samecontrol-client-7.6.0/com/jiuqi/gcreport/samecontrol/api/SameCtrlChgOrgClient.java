/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.samecontrol.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrlChgOrgClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrlChgOrgClient {
    public static final String CHANGE_ORG_PATH = "/api/gcreport/v1/samecontrol/changedOrg";

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/queryChangedOrgs"})
    public BusinessResponseEntity<List<SameCtrlChgOrgVO>> queryChangedOrgs(@RequestBody ChangeOrgCondition var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/addChangedOrg"})
    public BusinessResponseEntity<String> addChangedOrg(@RequestBody ChangeOrgCondition var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/getDisposeAndAcquisitionOrg"})
    public BusinessResponseEntity<List<GcOrgCacheVO>> getDisposeAndAcquisitionOrg(@RequestBody ChangeOrgCondition var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/changedCtrlOrgsState"})
    public BusinessResponseEntity<String> changedCtrlOrgsState(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/autoCreateSameCtrlChgOrg/{orgType}/{periodStr}"})
    public BusinessResponseEntity<String> autoCreateSameCtrlChgOrg(@PathVariable(value="orgType") String var1, @PathVariable(value="periodStr") String var2);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/getOrgversionByChangeDate"})
    public BusinessResponseEntity<String> getOrgversionByChangeDate(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/listVirtualOrgTypeBaseData"})
    public BusinessResponseEntity<List<GcBaseData>> listVirtualOrgTypeBaseData();

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/addManageChangedOrg"})
    public BusinessResponseEntity<String> addManageChangedOrg(@RequestBody ChangeOrgCondition var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/updateManageChangedOrg"})
    public BusinessResponseEntity<String> updateManageChangedOrg(@RequestBody ChangeOrgCondition var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/deleteManageChangedOrg/{id}"})
    public BusinessResponseEntity<String> deleteManageChangedOrg(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/deleteManageChangedOrgByMRecid/{ids}"})
    public BusinessResponseEntity<String> deleteManageChangedOrgByMRecid(@PathVariable(value="ids") List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/samecontrol/changedOrg/listManageChangedOrg"})
    public BusinessResponseEntity<List<SameCtrlChgOrgVO>> listManageChangedOrg(@RequestBody ChangeOrgCondition var1);
}

