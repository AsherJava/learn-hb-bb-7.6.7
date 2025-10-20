/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.auth.AuthDO
 *  com.jiuqi.va.domain.task.AuthRegisterTask
 */
package com.jiuqi.va.organization.task;

import com.jiuqi.va.domain.auth.AuthDO;
import com.jiuqi.va.domain.task.AuthRegisterTask;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaOrgAuthRegisterTask
implements AuthRegisterTask {
    public List<AuthDO> getAuths() {
        ArrayList<AuthDO> list = new ArrayList<AuthDO>();
        list.add(this.initAuth("OrgAuthRule", "\u7ec4\u7ec7\u673a\u6784\u89c4\u5219", "/api/org/anon/va-org-auth.js", "VaOrgAuth", "VaOrgAuthRule", null, 2, 0));
        list.add(this.initAuth("OrgAuthDetail", "\u7ec4\u7ec7\u673a\u6784", "/api/org/anon/va-org-auth.js", "VaOrgAuth", "VaOrgAuthDetail", null, 3, -1));
        list.add(this.initAuth("OrgActionAuthDetail", "\u7ec4\u7ec7\u673a\u6784\u52a8\u4f5c", "/api/org/anon/va-org-auth.js", "VaOrgAuth", "VaOrgActionAuthDetail", null, 2, -1));
        return list;
    }
}

