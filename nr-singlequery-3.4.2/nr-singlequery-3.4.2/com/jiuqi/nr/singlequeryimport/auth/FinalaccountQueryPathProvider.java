/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Privilege
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 */
package com.jiuqi.nr.singlequeryimport.auth;

import com.jiuqi.np.authz2.privilege.Privilege;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResourceType;
import com.jiuqi.nr.singlequeryimport.service.impl.QueryModleServiceImpl;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinalaccountQueryPathProvider
implements SuperInheritPathProvider {
    @Autowired
    private QueryModleServiceImpl queryModleService;

    public String getResourceCategoryId() {
        return "finalaccountquery-auth-resource-category";
    }

    private String getResourceId(Object resource) {
        if (resource instanceof Resource) {
            return ((Resource)resource).getId();
        }
        if (resource instanceof String) {
            return String.valueOf(resource);
        }
        throw new RuntimeException(String.format("\u67e5\u8be2\u4e0a\u7ea7\u8d44\u6e90\u51fa\u9519: %s", this.getClass().toString()));
    }

    public Resource getParent(Object resource) {
        String resourceId = this.getResourceId(resource);
        if (resourceId == null) {
            return null;
        }
        FinalaccountQueryAuthResourceType parseFromType = FinalaccountQueryAuthResourceType.parseFrom(resourceId);
        String objectId = parseFromType.toObjectId(resourceId);
        Object parentKey = null;
        switch (parseFromType) {
            case FQ_TASK: {
                String taskGroupKey = parseFromType.toObjectId(resourceId);
                parentKey = null;
                break;
            }
            case FQ_FROM_SCHEME: {
                break;
            }
            case FQ_GROUP: {
                break;
            }
            case FQ_MODEL_NODE: {
                break;
            }
        }
        return null;
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        return null;
    }

    public List<String> getChildren(Privilege privilege, String resourceId) {
        throw new UnsupportedOperationException("\u7efc\u5408\u67e5\u8be2\u8d44\u6e90\u5c1a\u672a\u652f\u6301\u83b7\u53d6\u4e0b\u7ea7\u7ee7\u627f\u65b9\u5f0f\u3002");
    }

    public Set<String> computeIfChildren(String resourceId, Set<String> resourceIds) {
        return Collections.emptySet();
    }
}

