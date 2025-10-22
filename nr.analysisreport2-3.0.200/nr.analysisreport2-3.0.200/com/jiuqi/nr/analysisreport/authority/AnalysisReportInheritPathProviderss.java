/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.nr.analysisreport.authority;

import com.jiuqi.nr.analysisreport.authority.bean.AnalysisReportResource;
import com.jiuqi.nr.analysisreport.dao.AnalysisReportDefineDao;
import com.jiuqi.nr.analysisreport.dao.AnalysisReportGroupDefineDao;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import java.util.Collections;
import java.util.Set;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalysisReportInheritPathProviderss
implements SuperInheritPathProvider {
    private static final Logger log = LoggerFactory.getLogger(AnalysisReportInheritPathProviderss.class);
    private String virtualNode = "0";
    @Autowired
    private AnalysisReportDefineDao templateDao;
    @Autowired
    private AnalysisReportGroupDefineDao templateGroupDao;

    public String getResourceCategoryId() {
        return "AnalysisReportResourceCategory-f4c9dcedfc8a";
    }

    public Resource getParent(Object resource) {
        try {
            String resourceId;
            if (resource instanceof Resource) {
                resourceId = ((Resource)resource).getId();
            } else if (resource instanceof AnalysisReportResource) {
                resourceId = ((AnalysisReportResource)resource).getId();
            } else {
                throw new RuntimeException("\u67e5\u8be2\u4e0a\u7ea7\u8d44\u6e90\u9519\u8bef");
            }
            AnalysisReportDefine customAnalysisTemplate = this.templateDao.getListByKey(resourceId);
            if (customAnalysisTemplate != null) {
                String parentKey = customAnalysisTemplate.getGroupKey();
                if (!StringUtils.hasLength((String)parentKey)) {
                    return null;
                }
                AnalysisReportGroupDefine groupByKey = this.templateGroupDao.getGroupByKey(parentKey);
                return ResourceGroupItem.createResourceGroupItem((String)groupByKey.getKey(), (String)groupByKey.getTitle(), (boolean)true);
            }
            AnalysisReportGroupDefine analysisReportGroup = this.templateGroupDao.getGroupByKey(resourceId);
            if (analysisReportGroup != null) {
                String parentKey = analysisReportGroup.getParentgroup();
                if (!StringUtils.hasLength((String)parentKey)) {
                    return null;
                }
                if (this.virtualNode.equals(parentKey)) {
                    return null;
                }
                AnalysisReportGroupDefine groupByKey = this.templateGroupDao.getGroupByKey(parentKey);
                return ResourceGroupItem.createResourceGroupItem((String)groupByKey.getKey(), (String)groupByKey.getTitle(), (boolean)true);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        return Collections.emptySet();
    }
}

