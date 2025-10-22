/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.carryover.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.carryover.vo.CarryOverTaskProcessVO;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.carryover.api.GcCarryOverClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcCarryOverClient {
    public static final String CARRYOVER_API_BASE_PATH = "/api/gcreport/v1/carryover";

    @PostMapping(value={"/api/gcreport/v1/carryover/doCarryOver"})
    public BusinessResponseEntity<Object> doCarryOver(@RequestBody QueryParamsVO var1);

    @GetMapping(value={"/api/gcreport/v1/carryover/taskLog/{taskLogId}"})
    public BusinessResponseEntity<TaskLog> getTaskLog(@PathVariable(value="taskLogId") String var1);

    @PostMapping(value={"/api/gcreport/v1/carryover/listCarryOverLogTableData"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> listCarryOverLogTableData(@RequestBody QueryParamsVO var1);

    @GetMapping(value={"/api/gcreport/v1/carryover/getTaskProcess/{taskLogId}"})
    public BusinessResponseEntity<Map<String, Object>> getTaskProcess(@PathVariable(value="taskLogId") String var1);

    @GetMapping(value={"/api/gcreport/v1/carryover/queryTaskProcess/{taskId}"})
    public BusinessResponseEntity<CarryOverTaskProcessVO> queryTaskProcess(@PathVariable(value="taskId") String var1);

    @GetMapping(value={"/api/gcreport/v1/carryover/downloadLog/{taskLogId}"})
    public ResponseEntity<Resource> downloadLog(@PathVariable(value="taskLogId") String var1, HttpServletRequest var2);

    @PostMapping(value={"/api/gcreport/v1/carryover/getAcctYearRange"})
    public String getAcctYearRange(@RequestBody Map<String, String> var1);

    @GetMapping(value={"/api/gcreport/v1/carryover/getConsSystem"})
    public String getConsSystem();

    @PostMapping(value={"/api/gcreport/v1/carryover/getOrgVerAndType/{schemeId}/{defaultAcctYear}"})
    public BusinessResponseEntity<Map<String, String>> getOrgVerAndType(@PathVariable(value="schemeId") String var1, @PathVariable(value="defaultAcctYear") String var2);

    @GetMapping(value={"/api/gcreport/v1/carryover/getSchemeByTaskKey/{taskKey}/{acctYear}"})
    public BusinessResponseEntity<Scheme> getSchemeByTaskKeyAndAcctYear(@PathVariable(value="taskKey") String var1, @PathVariable(value="acctYear") Integer var2);

    @GetMapping(value={"/api/gcreport/v1/carryover/listCarryOverSumColumns"})
    public BusinessResponseEntity<List<DesignFieldDefineVO>> listCarryOverSumColumns();

    @PostMapping(value={"/api/gcreport/v1/carryover/checkAdjust"})
    public BusinessResponseEntity<Boolean> checkAdjust(@RequestBody QueryParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/carryover/getOrgTypeByTaskIdAndPeriod"})
    public BusinessResponseEntity<String> getOrgTypeByTaskIdAndPeriod(@RequestBody QueryParamsVO var1);
}

