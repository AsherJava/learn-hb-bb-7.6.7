/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.auth.DataSchemeAuthResourceType
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.util.authUtil;

import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.dataentry.bean.AuthorityOptions;
import com.jiuqi.nr.datascheme.auth.DataSchemeAuthResourceType;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckAuthOfResourceUtil {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private INvwaSystemOptionService systemOptionService;

    public boolean checkResourceAuthOfType(String taskKey, AuthorityOptions authorityOptions) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        String selectDataSumScope = this.systemOptionService.get("nr-data-entry-group", "IS_ENABLE_DATA_AUTH_OPTIONS");
        boolean hasAuth = true;
        if (selectDataSumScope.equals("1")) {
            hasAuth = this.privilegeService.hasAuth(authorityOptions.getId(), identityId, (Object)DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(taskKey));
        }
        return hasAuth;
    }
}

