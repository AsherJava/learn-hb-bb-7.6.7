/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.gcreport.conversion.conversionsystem.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskCommonVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskFormInfoVO;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="com.jiuqi.gcreport.conversion.conversionsystem.api.NrTaskClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface NrTaskClient {
    public static final String NRTASK_API_BASE_PATH = "/api/gcreport/v1/taskquery";

    @GetMapping(value={"/api/gcreport/v1/taskquery/tasks"})
    public BusinessResponseEntity<List<TaskCommonVO>> getTasksInfo();

    @GetMapping(value={"/api/gcreport/v1/taskquery/tasks/{taskid}/schemes"})
    public BusinessResponseEntity<List<TaskCommonVO>> getSchemesInfo(@PathVariable(value="taskid") String var1);

    @GetMapping(value={"/api/gcreport/v1/taskquery/schemes/{schemeId}/forms"})
    public BusinessResponseEntity<List<TaskFormInfoVO>> queryAllFormDefinesInFormScheme(@PathVariable(value="schemeId") String var1) throws Exception;

    @GetMapping(value={"/api/gcreport/v1/taskquery/formulas/{schemeId}"})
    public BusinessResponseEntity<List<FormulaSchemeObject>> getFormulaSchemes(@PathVariable(value="schemeId") String var1);
}

