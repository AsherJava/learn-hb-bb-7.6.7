/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.inputdata.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.ManalOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.inputdata.api.OffsetItemInputDataClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface OffsetItemInputDataClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/adjustingEntry";

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/manualOffsetQuery"})
    public BusinessResponseEntity<String> queryUnOffsetRecords(@RequestBody GcCalcArgmentsDTO var1, @RequestParam boolean var2);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/unOffsetRecord"})
    public BusinessResponseEntity<Object> queryUnOffsetRecords(@Valid @RequestBody QueryParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/unOffset/parent/Record"})
    public BusinessResponseEntity<Object> queryParentUnOffsetRecords(@Valid @RequestBody QueryParamsVO var1);

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/unOffsetColumnSelect/{systemId}/{dataSource}"})
    public BusinessResponseEntity<List<DesignFieldDefineVO>> queryUnOffsetColumnSelect(@PathVariable(value="systemId") String var1, @PathVariable(value="dataSource") String var2);

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/queryRecordOffsetState/{recordId}/{taskId}"})
    public BusinessResponseEntity<String> queryRecordOffsetState(@PathVariable(value="recordId") String var1, @PathVariable(value="taskId") String var2);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/canOffset"})
    public BusinessResponseEntity<String> canOffset(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/efdcQuery/{recordId}/{fieldCode}"})
    public BusinessResponseEntity<String> efdcPenetrableSearch(@RequestBody JtableContext var1, @PathVariable(value="recordId") List<String> var2, @PathVariable(value="fieldCode") String var3);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/manualOffset"})
    public BusinessResponseEntity<Object> manualoffset(@RequestBody ManalOffsetParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/manualBatchOffset"})
    public BusinessResponseEntity<Object> manualBatchOffset(@RequestBody ManualBatchOffsetParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/calc"})
    public BusinessResponseEntity<String> calc(@Valid @RequestBody QueryParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/queryEntryDetails/{recordId}/{taskId}"})
    public BusinessResponseEntity<String> queryEntryDetails(@PathVariable(value="recordId") String var1, @PathVariable(value="taskId") String var2, @RequestBody List<String> var3);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/queryInputDataOtherShowColumns/{taskId}"})
    public BusinessResponseEntity<List<String>> queryInputDataOtherShowColumns(@PathVariable(value="taskId") String var1, @RequestBody List<String> var2);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/relateRecords/{recordId}/{taskId}"})
    public BusinessResponseEntity<String> relateRecords(@PathVariable(value="recordId") String var1, @PathVariable(value="taskId") String var2, @RequestBody List<String> var3);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/queryOffsetByRecords"})
    public BusinessResponseEntity<Object> queryOffsetByRecords(@RequestBody GcCalcArgmentsDTO var1);

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/relateRecords/isSupportPentration"})
    public BusinessResponseEntity<Object> isSupportPentration();

    @GetMapping(value={"/api/gcreport/v1/adjustingEntry/initPenetrateData/{recordId}/{taskId}"})
    public BusinessResponseEntity<Object> initPenetrateData(@PathVariable(value="recordId") String var1, @PathVariable(value="taskId") String var2);
}

