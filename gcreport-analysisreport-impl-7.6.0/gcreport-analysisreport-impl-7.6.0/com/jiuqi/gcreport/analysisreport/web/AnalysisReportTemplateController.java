/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.analysisreport.api.AnalysisReportTemplateClient
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.analysisreport.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.analysisreport.api.AnalysisReportTemplateClient;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.service.AnalysisReportTemplateService;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class AnalysisReportTemplateController
implements AnalysisReportTemplateClient {
    @Autowired
    private AnalysisReportTemplateService templateService;
    @Autowired
    private ISecretLevelService secretLevelService;
    @Autowired
    private INvwaSystemOptionService systemOptionOperator;

    public BusinessResponseEntity<List<AnalysisReportRefOrgDTO>> queryRefOrgsByTemplateId(String templateId) {
        List<AnalysisReportRefOrgDTO> analysisReportRefOrgDTOs = this.templateService.queryRefOrgsByTemplateId(templateId);
        return BusinessResponseEntity.ok(analysisReportRefOrgDTOs);
    }

    public BusinessResponseEntity<List<SecretLevelItem>> querySecretLevelItems() {
        String securityLevelValue = this.systemOptionOperator.findValueById("SECURITYLEVEL_ENABLE");
        if (!"1".equals(securityLevelValue)) {
            return BusinessResponseEntity.ok(Collections.emptyList());
        }
        List secretLevelItems = this.secretLevelService.getSecretLevelItems();
        return BusinessResponseEntity.ok((Object)secretLevelItems);
    }

    public BusinessResponseEntity<AnalysisReportDTO> queryAnalysisReportTree() {
        AnalysisReportDTO analysisReportDTO = this.templateService.queryAnalysisReportTree(true);
        return BusinessResponseEntity.ok((Object)analysisReportDTO);
    }

    public BusinessResponseEntity<Boolean> saveAnalysisReportTemplate(AnalysisReportDTO analysisReportDTO) throws Exception {
        boolean isSuccess = this.templateService.saveAnalysisReport(analysisReportDTO);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> upAnalysisReportTemplate(String id) {
        boolean isSuccess = this.templateService.upAnalysisReport(id);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> downAnalysisReportTemplate(String id) {
        boolean isSuccess = this.templateService.downAnalysisReport(id);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> deleteAnalysisReportTemplate(Set<String> ids) {
        boolean isSuccess = this.templateService.deleteAnalysisReport(ids);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Object> queryRefAnalysisReportTree() {
        Object reportTree = this.templateService.queryRefAnalysisReportTree();
        return BusinessResponseEntity.ok((Object)reportTree);
    }

    public BusinessResponseEntity<List<AnalysisReportDTO>> queryItemsByParentId(String parentId) {
        List<AnalysisReportDTO> items = this.templateService.queryItemsByParentIdContainSelf(parentId);
        return BusinessResponseEntity.ok(items);
    }

    public List<Map<String, Object>> queryAnalysisReportLeafTemplates() {
        List<Map<String, Object>> maps = this.templateService.queryAnalysisReportLeafTemplates();
        return maps;
    }
}

