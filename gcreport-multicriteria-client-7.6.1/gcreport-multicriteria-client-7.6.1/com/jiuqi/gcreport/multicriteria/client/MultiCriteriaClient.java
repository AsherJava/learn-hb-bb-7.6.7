/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.multicriteria.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMulCriAfterFormVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaVO;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaZbDataVO;
import com.jiuqi.nr.dataentry.tree.FormTree;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.multicriteria.client.MultiCriteriaClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface MultiCriteriaClient {
    public static final String API_PATH = "/api/gcreport/v1/multiCriteria";

    @PostMapping(value={"/api/gcreport/v1/multiCriteria/ReportTree/{schemeId}"})
    public BusinessResponseEntity<FormTree> queryFormTree(@PathVariable(value="schemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/multiCriteria/saveAfterForm"})
    public BusinessResponseEntity<String> saveAfterForm(@RequestBody GcMulCriAfterFormVO var1);

    @PostMapping(value={"/api/gcreport/v1/multiCriteria/queryAfterReport/{taskId}/{schemeId}"})
    public BusinessResponseEntity<List<String>> queryMulCriAfterForms(@PathVariable(value="taskId") String var1, @PathVariable(value="schemeId") String var2);

    @PostMapping(value={"/api/gcreport/v1/multiCriteria/saveSubjectMapping/{deleteIds}", "/api/gcreport/v1/multiCriteria/saveSubjectMapping/"})
    public BusinessResponseEntity<String> saveSubjectMapping(@PathVariable(value="deleteIds", required=false) List<String> var1, @RequestBody List<GcMultiCriteriaVO> var2);

    @PostMapping(value={"/api/gcreport/v1/multiCriteria/querySubjectMapping"})
    public BusinessResponseEntity<List<GcMultiCriteriaVO>> querySubjectMapping(@RequestBody GcMultiCriteriaConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/multiCriteria/queryZbTitlesByCode"})
    public BusinessResponseEntity<String> queryZbTitlesByCode(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/multiCriteria/queryZbData"})
    public BusinessResponseEntity<List<GcMultiCriteriaZbDataVO>> queryZbData(@RequestBody GcMultiCriteriaConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/multiCriteria/saveZbData"})
    public BusinessResponseEntity<String> saveZbData(@RequestBody GcMultiCriteriaConditionVO var1);

    @GetMapping(value={"/api/gcreport/v1/multiCriteria/queryFormData/{schemeId}/{formKey}"})
    public BusinessResponseEntity<String> queryFormData(@PathVariable(value="schemeId") String var1, @PathVariable(value="formKey") String var2);
}

