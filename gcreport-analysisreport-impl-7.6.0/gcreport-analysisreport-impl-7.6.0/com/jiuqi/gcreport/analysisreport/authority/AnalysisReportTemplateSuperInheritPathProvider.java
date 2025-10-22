/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.analysisreport.common.AnalysisReportTemplateConsts
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 */
package com.jiuqi.gcreport.analysisreport.authority;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.analysisreport.common.AnalysisReportTemplateConsts;
import com.jiuqi.gcreport.analysisreport.dao.AnalysisReportTemplateDao;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportEO;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalysisReportTemplateSuperInheritPathProvider
implements SuperInheritPathProvider {
    @Autowired
    private AnalysisReportTemplateDao templateDao;

    public String getResourceCategoryId() {
        return "AnalysisReportTemplateResourcesCategory-5201314";
    }

    public Object getParentResource(Object resource) {
        String resourceId;
        if (resource instanceof Resource) {
            resourceId = ((Resource)resource).getId();
        } else if (resource instanceof String) {
            resourceId = (String)resource;
        } else {
            throw new RuntimeException("\u67e5\u8be2\u4e0a\u7ea7\u8d44\u6e90\u9519\u8bef");
        }
        if (AnalysisReportTemplateConsts.ROOT_PARENT_ID.equals(resourceId)) {
            return null;
        }
        AnalysisReportEO analysisReportEO = (AnalysisReportEO)this.templateDao.get((Serializable)((Object)resourceId));
        String parentId = analysisReportEO.getParentId();
        if (analysisReportEO == null || StringUtils.isEmpty((String)parentId)) {
            return null;
        }
        AnalysisReportEO parentEO = (AnalysisReportEO)this.templateDao.get((Serializable)((Object)parentId));
        if (parentEO == null) {
            return null;
        }
        ResourceGroupItem resourceGroupItem = new ResourceGroupItem();
        resourceGroupItem.setId(parentEO.getId());
        resourceGroupItem.setTitle(parentEO.getTitle());
        return resourceGroupItem;
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        return Collections.emptySet();
    }
}

