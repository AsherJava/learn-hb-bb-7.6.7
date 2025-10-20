/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.offsetitem.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.adjustingentry.api.GcOffSetAppClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcOffSetAppClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/adjustingEntry";
    public static final String OFFSETITEM_API_BASE_PATH = "/api/gcreport/v1/offsetitems";

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/offsetEntry"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> getOffsetEntry(@Valid @RequestBody QueryParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/offsetEntry/delete"})
    public BusinessResponseEntity<String> deleteOffsetEntrysByMrecid(@RequestBody QueryParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/offsetEntry/condition"})
    public BusinessResponseEntity<String> deleteOffsetEntrys(@Valid @RequestBody QueryParamsVO var1);

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/offsetColumnSelect"})
    public BusinessResponseEntity<List<DesignFieldDefineVO>> queryOffsetColumnSelect();

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/schemes/rules"})
    public BusinessResponseEntity<String> cancelRuleData(@RequestParam(value="taskId") String var1, @RequestParam(value="systemId") String var2, @RequestParam(value="periodStr") String var3, @RequestParam(value="ruleId") String var4, @RequestBody List<String> var5, @RequestParam(value="orgType") String var6, @RequestParam(value="selectAdjustCode") String var7);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/offsetOrgData"})
    public BusinessResponseEntity<List<GcOrgCacheVO>> getOffsetOrgData(@RequestBody QueryParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/mergeOrgData"})
    public BusinessResponseEntity<List<GcOrgCacheVO>> getMergeUnitOrgData(@RequestBody QueryParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/writeOffShow/{mrecids}"})
    public BusinessResponseEntity<List<GcOffSetVchrItemVO>> writeOffShow(@PathVariable(value="mrecids") @RequestBody List<String> var1, @RequestBody QueryParamsVO var2);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/offsetEntry/writeOffSave/{mrecids}"})
    public BusinessResponseEntity<String> writeOffSave(@PathVariable(value="mrecids") @RequestBody List<String> var1, @RequestBody QueryParamsVO var2);

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/getHBOrgByCE/{orgType}/{yyyyMMdd}/{orgCode}"})
    public BusinessResponseEntity<GcOrgCacheVO> getHBOrgByCE(@PathVariable(value="orgType") String var1, @PathVariable(value="yyyyMMdd") String var2, @PathVariable(value="orgCode") String var3);

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/delAllBtn/getShow"})
    public List<Map<String, Object>> allowDelAll();

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/updateOffsetDisabledFlag/{isDisabled}"})
    public BusinessResponseEntity<String> updateOffsetDisabledFlag(@RequestBody List<String> var1, @PathVariable(value="isDisabled") boolean var2);

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/allowDisableOffset"})
    public List<Map<String, Object>> allowDisableOffset();

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/updateMemo"})
    public BusinessResponseEntity<Object> updateMemo(@RequestBody Map<String, Object> var1);

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/getMemoLength/{tabName}"})
    public BusinessResponseEntity<Object> getMemoLength(@PathVariable(value="tabName") String var1);

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/getPenetrateData/{mrecid}/{ruleId}/{ruleType}/{orgType}"})
    public BusinessResponseEntity<Object> getPenetrateData(@PathVariable(value="mrecid") String var1, @PathVariable(value="ruleId") String var2, @PathVariable(value="ruleType") String var3, @PathVariable(value="orgType") String var4);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/sumTabRecords"})
    public BusinessResponseEntity<Object> listSumTabRecords(@Valid @RequestBody QueryParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/queryOffsetButtons"})
    public BusinessResponseEntity<String> queryOffsetButtons(@RequestBody GcOffsetExecutorVO var1);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/adjust/deleteMrecids/{orgType}/{periodStr}"})
    public BusinessResponseEntity<Object> deleteAdjust(@RequestBody List<String> var1, @PathVariable(value="orgType") String var2, @PathVariable(value="periodStr") String var3);

    @PostMapping(value={"/api/gcreport/v1/offsetitems/writeableByOrgCodeAndDiffCode"})
    public BusinessResponseEntity<ReadWriteAccessDesc> writeableByOrgCodeAndDiffCode(@Valid @RequestBody DimensionParamsVO var1);
}

