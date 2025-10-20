/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 */
package com.jiuqi.nvwa.login.shiro;

import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.nvwa.login.domain.NvwaContext;
import com.jiuqi.nvwa.login.domain.NvwaContextIdentity;
import com.jiuqi.nvwa.login.provider.NvwaExtendProvider;
import com.jiuqi.nvwa.login.provider.NvwaPermExtendProvider;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyNvwaExtendProvider
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired(required=false)
    private NvwaExtendProvider nvwaIdentityProvider;
    @Autowired(required=false)
    private NvwaPermExtendProvider nvwaPermExtendProvider;

    public NvwaContextIdentity buildContextIdentity(ContextUser user, Map<String, Object> extInfo) {
        if (null != this.nvwaIdentityProvider) {
            return this.nvwaIdentityProvider.getNvwaContextIdentity(user, extInfo);
        }
        NvwaContextIdentity contextIdentity = new NvwaContextIdentity();
        contextIdentity.setId(user.getId());
        contextIdentity.setTitle(user.getFullname());
        contextIdentity.setOrgCode(user.getOrgCode());
        return contextIdentity;
    }

    public boolean existOrgAuth(String orgCode, ContextUser npUser, ContextIdentity contextIdentity) {
        if (null != this.nvwaIdentityProvider) {
            return this.nvwaIdentityProvider.existOrgAuth(orgCode, npUser, contextIdentity);
        }
        return true;
    }

    public String getOrgTitle(String orgCode) {
        if (null != this.nvwaIdentityProvider) {
            return this.nvwaIdentityProvider.getOrgTitle(orgCode);
        }
        return orgCode;
    }

    public Set<String> getPermissions(NvwaContext nvwaCtx) {
        if (null != this.nvwaPermExtendProvider) {
            return this.nvwaPermExtendProvider.getPermissions(nvwaCtx);
        }
        HashSet<String> perms = new HashSet<String>();
        perms.add("-");
        return perms;
    }
}

