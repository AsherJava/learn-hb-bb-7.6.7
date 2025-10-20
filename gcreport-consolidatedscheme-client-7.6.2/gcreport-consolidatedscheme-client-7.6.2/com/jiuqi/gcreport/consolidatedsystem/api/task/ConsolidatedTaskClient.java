/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.consolidatedsystem.api.task;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.consolidatedsystem.api.task.ConsolidatedTaskClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ConsolidatedTaskClient {
    public static final String CONSOLIDATED_TASK_API_BASE_PATH = "/api/gcreport/v1/consolidatedTasks";

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/tasks"})
    public BusinessResponseEntity<List<TaskDefine>> getConsolidatesdTasks();

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/listTask"})
    public BusinessResponseEntity<List<TaskInfoVO>> listTask();

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/getSystemBySchemeId/{schemeId}/{periodStr}"})
    public BusinessResponseEntity<ConsolidatedSystemVO> getSystemBySchemeId(@PathVariable(value="schemeId") String var1, @PathVariable(value="periodStr") String var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/getSystemByTaskId/{taskId}/{periodStr}"})
    public BusinessResponseEntity<ConsolidatedTaskVO> getSystemByTaskId(@PathVariable(value="taskId") String var1, @PathVariable(value="periodStr") String var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/getAllRelateSystemBySchemeId/{schemeId}"})
    public BusinessResponseEntity<List<ConsolidatedSystemVO>> getAllRelateSystemBySchemeId(@PathVariable(value="schemeId") String var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/boundedTasks"})
    public BusinessResponseEntity<List<String>> getAllBoundTasks();

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/boundedTaskVos"})
    public BusinessResponseEntity<List<TaskDefine>> listAllBoundTaskVos();

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/{taskId}/boundedSchemeVos"})
    public BusinessResponseEntity<List<FormSchemeDefine>> listBoundSchemeVos(@PathVariable(value="taskId") String var1) throws Exception;

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/systems/{systemId}"})
    public BusinessResponseEntity<List<ConsolidatedTaskVO>> getConsolidatesdTasks(@PathVariable(value="systemId") String var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/systems/list/{systemId}"})
    public BusinessResponseEntity<List<ConsolidatedTaskVO>> listConsolidatesdTasks(@PathVariable(value="systemId") String var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedTasks"})
    public BusinessResponseEntity<String> bindConsolidatesdTask(@Valid @RequestBody ConsolidatedTaskVO var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedTasks/batchSave"})
    public BusinessResponseEntity<String> bindConsolidatesdTask(@Valid @RequestBody List<ConsolidatedTaskVO> var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedTasks/{ids}"})
    public BusinessResponseEntity<String> unbindConsolidatesdTask(@PathVariable(value="ids") @RequestBody String[] var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/taskSchemeTree"})
    public BusinessResponseEntity<List<BaseDataVO>> getSchemeTree() throws Exception;

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/systemSchemeInfo/{schemeId}/{periodStr}"})
    public BusinessResponseEntity<ConsolidatedTaskVO> getTaskBySchemeId(@PathVariable(value="schemeId") String var1, @PathVariable(value="periodStr") String var2);

    @PostMapping(value={"/api/gcreport/v1/consolidatedTasks/getRelevancySystemsBySchemeIds"})
    public BusinessResponseEntity<List<String>> getRelevancySystemsBySchemeIds(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedTasks/getRelevancySystemsInputSchemeIds"})
    public BusinessResponseEntity<List<String>> getRelevancySystemsInputSchemeIds(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedTasks/exchangeSort/{opNodeId}/{step}"})
    public BusinessResponseEntity<String> exchangeSort(@PathVariable(value="opNodeId") String var1, @PathVariable(value="step") int var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/allDataCollectorScheme"})
    public BusinessResponseEntity<List<ConsolidatedTaskVO>> getAllDataCollectorScheme();

    @GetMapping(value={"/api/gcreport/v1/consolidatedTasks/allKey2TitleOfTask"})
    public String getAllKey2TitleOfTask();

    @PostMapping(value={"/api/gcreport/v1/consolidatedTasks/getRelevancySchemeKeys"})
    public BusinessResponseEntity<Set<String>> getRelevancySchemeKeys(@RequestBody List<String> var1);
}

