/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.analysisreport.common.AnalysisReportTemplateConsts
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 */
package com.jiuqi.gcreport.analysisreport.authority;

import com.jiuqi.gcreport.analysisreport.common.AnalysisReportTemplateConsts;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.service.AnalysisReportTemplateService;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalysisReportTemplateResourceCategory
extends DefaultResourceCategory {
    public static final String ANALYSIS_REPORT_RESOURCE_CATEGORY_ID = "AnalysisReportTemplateResourcesCategory-5201314";
    @Autowired
    private AnalysisReportTemplateService templateService;

    public String getId() {
        return ANALYSIS_REPORT_RESOURCE_CATEGORY_ID;
    }

    public String getTitle() {
        return "\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a";
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition(GranteeInfo granteeInfo) {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem superItem = new PrivilegeDefinitionItem();
        superItem.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        superItem.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        list.add((PrivilegeDefinition)superItem);
        PrivilegeDefinitionItem read = new PrivilegeDefinitionItem();
        read.setPrivilegeId("analysisreport_template_resource_read");
        read.setPrivilegeTitle("\u8bbf\u95ee");
        list.add((PrivilegeDefinition)read);
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        return this.getChildResources(AnalysisReportTemplateConsts.ROOT_PARENT_ID, granteeInfo);
    }

    public boolean isSupportReject() {
        return true;
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo) {
        ArrayList<Resource> list = new ArrayList<Resource>();
        List<AnalysisReportDTO> analysisReportDTOs = this.templateService.queryItemsByParentId(resourceGroupId);
        for (AnalysisReportDTO analysisReportDTO : analysisReportDTOs) {
            if ("group".equals(analysisReportDTO.getNodeType())) {
                ResourceGroupItem group = ResourceGroupItem.createResourceGroupItem((String)analysisReportDTO.getId(), (String)analysisReportDTO.getTitle(), (boolean)true, (boolean)false);
                list.add((Resource)group);
                continue;
            }
            ResourceItem resource = ResourceItem.createResourceItem((String)analysisReportDTO.getId(), (String)analysisReportDTO.getTitle());
            list.add((Resource)resource);
        }
        return list;
    }
}

