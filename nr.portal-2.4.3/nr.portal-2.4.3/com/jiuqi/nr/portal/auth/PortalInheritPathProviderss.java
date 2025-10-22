/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 */
package com.jiuqi.nr.portal.auth;

import com.jiuqi.nr.portal.news2.impl.FileImpl;
import com.jiuqi.nr.portal.news2.impl.NewsImpl;
import com.jiuqi.nr.portal.news2.service.INews2Service;
import com.jiuqi.nr.portal.news2.service.IPortalFileService;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PortalInheritPathProviderss
implements SuperInheritPathProvider {
    @Autowired
    private INews2Service news2Service;
    @Autowired
    private IPortalFileService portalFileService;

    public String getResourceCategoryId() {
        return "PortalResourceCategory-213bc9dce325";
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
        if (resourceId == null) {
            return null;
        }
        String resourceType = resourceId.substring(0, resourceId.indexOf("_") + 1);
        try {
            switch (resourceType) {
                case "model_": {
                    String pageID = resourceId.substring(resourceId.indexOf("_") + 1, resourceId.indexOf("$"));
                    return "page_" + pageID;
                }
                case "page_": {
                    return null;
                }
                case "group_": {
                    String prefix = resourceId.substring(resourceId.indexOf("_") + 1);
                    return "page_" + prefix;
                }
                case "item-news_": {
                    String baseId = resourceId.substring(resourceId.indexOf("_") + 1);
                    NewsImpl baseInfo = this.news2Service.queryNewsById(baseId);
                    if (baseInfo == null) {
                        return null;
                    }
                    return "model_" + baseInfo.getPortalId() + "$" + baseInfo.getMid();
                }
                case "item-file_": {
                    String baseId2 = resourceId.substring(resourceId.indexOf("_") + 1);
                    FileImpl baseInfo2 = this.portalFileService.queryFileByid(baseId2);
                    if (baseInfo2 == null) {
                        return null;
                    }
                    return "model_" + baseInfo2.getPortalId() + "$" + baseInfo2.getMid();
                }
            }
            throw new UnsupportedOperationException("unrecognized task resource id: " + resourceId);
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u4e0a\u7ea7\u8d44\u6e90\u9519\u8bef\u3002", e);
        }
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        return null;
    }
}

