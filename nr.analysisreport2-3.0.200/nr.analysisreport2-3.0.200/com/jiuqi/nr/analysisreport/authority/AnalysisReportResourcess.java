/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.resource.ResourceSearchResult
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.analysisreport.authority;

import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.analysisreport.authority.AnalysisReportAuthorityProvider;
import com.jiuqi.nr.analysisreport.authority.bean.AnalysisReportResource;
import com.jiuqi.nr.analysisreport.authority.common.AnalysisReportResourceType;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportDefineImpl;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportGroupDefineImpl;
import com.jiuqi.nr.analysisreport.internal.service.AnalysisReportService;
import com.jiuqi.nr.analysisreport.service.SaveAnalysis;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.resource.ResourceSearchResult;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalysisReportResourcess
extends DefaultResourceCategory {
    private static final Logger log = LoggerFactory.getLogger(AnalysisReportResourcess.class);
    public static final String ANALYSISREPORT_ID = "AnalysisReportResourceCategory-f4c9dcedfc8a";
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    AnalysisHelper analysisHelper;
    @Autowired
    private AnalysisReportAuthorityProvider authProvider;
    @Autowired
    SaveAnalysis saveAnalysis;
    @Autowired
    private AnalysisReportService analysisReportService;

    public String getId() {
        return ANALYSISREPORT_ID;
    }

    public String getTitle() {
        return "\u5206\u6790\u62a5\u544a";
    }

    public int getSeq() {
        return 200;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public boolean isSupportReject() {
        return true;
    }

    public List<String> getBasePrivilegeIds() {
        return Collections.singletonList("analysisreport_resource_read");
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition() {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem superItem = new PrivilegeDefinitionItem();
        superItem.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        superItem.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        list.add((PrivilegeDefinition)superItem);
        PrivilegeDefinitionItem readItem = new PrivilegeDefinitionItem();
        readItem.setPrivilegeId("analysisreport_resource_read");
        readItem.setPrivilegeTitle("\u8bbf\u95ee");
        list.add((PrivilegeDefinition)readItem);
        PrivilegeDefinitionItem newbuildItem = new PrivilegeDefinitionItem();
        newbuildItem.setPrivilegeId("analysisreport_resource_create");
        newbuildItem.setPrivilegeTitle("\u65b0\u5efa");
        list.add((PrivilegeDefinition)newbuildItem);
        PrivilegeDefinitionItem writeItem = new PrivilegeDefinitionItem();
        writeItem.setPrivilegeId("analysisreport_resource_write");
        writeItem.setPrivilegeTitle("\u7f16\u8f91");
        list.add((PrivilegeDefinition)writeItem);
        PrivilegeDefinitionItem deleteItem = new PrivilegeDefinitionItem();
        deleteItem.setPrivilegeId("analysisreport_resource_delete");
        deleteItem.setPrivilegeTitle("\u5220\u9664");
        list.add((PrivilegeDefinition)deleteItem);
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        ArrayList<Resource> roots = new ArrayList<Resource>();
        ArrayList<AnalysisReportResource> allResourceId = new ArrayList<AnalysisReportResource>();
        try {
            List<AnalysisReportGroupDefine> analysisReportGroups = this.analysisHelper.getGroupByParent("0");
            if (analysisReportGroups != null) {
                for (AnalysisReportGroupDefine analysisReportGroup : analysisReportGroups) {
                    AnalysisReportResource temp = new AnalysisReportResource();
                    temp.analysisReportGroupToResoucce((AnalysisReportGroupDefineImpl)analysisReportGroup);
                    allResourceId.add(temp);
                }
            }
            for (AnalysisReportResource resource : allResourceId) {
                boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("analysisreport_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)resource);
                if (!hasDelegateAuth) continue;
                boolean isGroup = resource.getType().equals(AnalysisReportResourceType.GROUP);
                if (isGroup) {
                    roots.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resource.getId(), (String)resource.getTitle(), (boolean)true));
                    continue;
                }
                roots.add((Resource)ResourceItem.createResourceItem((String)resource.getId(), (String)resource.getTitle()));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return roots;
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo) {
        ArrayList<Resource> resources = new ArrayList<Resource>();
        ArrayList<AnalysisReportResource> allResourceId = new ArrayList<AnalysisReportResource>();
        try {
            List<AnalysisReportDefine> analysisReportsInGroup;
            List<AnalysisReportGroupDefine> analysisReportGroupsInGroup = this.analysisHelper.getGroupByParent(resourceGroupId);
            if (analysisReportGroupsInGroup != null) {
                for (AnalysisReportGroupDefine analysisGroup : analysisReportGroupsInGroup) {
                    AnalysisReportResource temp = new AnalysisReportResource();
                    temp.analysisReportGroupToResoucce((AnalysisReportGroupDefineImpl)analysisGroup);
                    allResourceId.add(temp);
                }
            }
            if ((analysisReportsInGroup = this.analysisHelper.getListByGroupKey(resourceGroupId)) != null) {
                for (AnalysisReportDefine analysisReport : analysisReportsInGroup) {
                    AnalysisReportResource temp = new AnalysisReportResource();
                    temp.analysisReportToResoucce((AnalysisReportDefineImpl)analysisReport);
                    allResourceId.add(temp);
                }
            }
            for (AnalysisReportResource resource : allResourceId) {
                boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("analysisreport_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)resource);
                if (!hasDelegateAuth) continue;
                boolean isGroup = resource.getType().equals(AnalysisReportResourceType.GROUP);
                if (isGroup) {
                    resources.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resource.getId(), (String)resource.getTitle(), (boolean)true));
                    continue;
                }
                resources.add((Resource)ResourceItem.createResourceItem((String)resource.getId(), (String)resource.getTitle()));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return resources;
    }

    public boolean enableSearch() {
        return true;
    }

    public List<ResourceSearchResult> searchResource(String fuzzyTitle, String key) {
        ArrayList<ResourceSearchResult> resourceList = new ArrayList<ResourceSearchResult>();
        try {
            if (StringUtils.isNotEmpty((CharSequence)key)) {
                AnalysisReportDefine reportDefine = this.analysisHelper.getListByKey(key);
                this.buildResouceResult(reportDefine, resourceList);
            } else if (StringUtils.isNotEmpty((CharSequence)fuzzyTitle)) {
                List<AnalysisReportDefine> reportDefines = this.analysisReportService.fuzzyQuery(fuzzyTitle);
                for (AnalysisReportDefine reportDefine : reportDefines) {
                    this.buildResouceResult(reportDefine, resourceList);
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return resourceList;
    }

    public void buildResouceResult(AnalysisReportDefine reportDefine, List<ResourceSearchResult> resourceList) {
        if (reportDefine == null) {
            return;
        }
        if (this.authProvider.canReadModal(reportDefine.getKey(), AnalysisReportResourceType.TEMPLATE)) {
            List<String> pathKeys = this.getPathKey(reportDefine.getGroupKey(), reportDefine.getKey());
            ResourceSearchResult resourceSearchResult = new ResourceSearchResult(reportDefine.getKey(), reportDefine.getTitle(), pathKeys);
            resourceList.add(resourceSearchResult);
        }
    }

    public List<String> getPathKey(String groupKey, String templateKey) {
        ArrayList<String> pathKeys = new ArrayList<String>();
        pathKeys.add(ANALYSISREPORT_ID);
        pathKeys.addAll(this.analysisHelper.getGroupPathKey(groupKey));
        if (StringUtils.isNotEmpty((CharSequence)templateKey)) {
            pathKeys.add(templateKey);
        }
        return pathKeys;
    }
}

