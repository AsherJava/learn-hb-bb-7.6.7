/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.analysisreport.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.analysisreport.api.AnalysisReportTemplateManageClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface AnalysisReportTemplateClient {
    public static final String API_PATH = "/api/gcreport/v1/analysisreport/templatemanage";

    @GetMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/queryRefOrgsByTemplateId"})
    public BusinessResponseEntity<List<AnalysisReportRefOrgDTO>> queryRefOrgsByTemplateId(@RequestParam(value="templateId") String var1);

    @GetMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/querySecretLevelItems"})
    public BusinessResponseEntity<List<SecretLevelItem>> querySecretLevelItems();

    @GetMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/queryTree"})
    public BusinessResponseEntity<AnalysisReportDTO> queryAnalysisReportTree();

    @PostMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/save"})
    public BusinessResponseEntity<Boolean> saveAnalysisReportTemplate(@RequestBody AnalysisReportDTO var1) throws Exception;

    @PostMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/up"})
    public BusinessResponseEntity<Boolean> upAnalysisReportTemplate(@RequestParam(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/down"})
    public BusinessResponseEntity<Boolean> downAnalysisReportTemplate(@RequestParam(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/delete"})
    public BusinessResponseEntity<Boolean> deleteAnalysisReportTemplate(@RequestBody Set<String> var1);

    @GetMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/queryRefAnalysisReportTree"})
    public BusinessResponseEntity<Object> queryRefAnalysisReportTree();

    @GetMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/queryAnalysisReportLeafTemplates"})
    public List<Map<String, Object>> queryAnalysisReportLeafTemplates();

    @GetMapping(value={"/api/gcreport/v1/analysisreport/templatemanage/queryItemsByParentId"})
    public BusinessResponseEntity<List<AnalysisReportDTO>> queryItemsByParentId(@RequestParam(value="parentId") String var1);
}

