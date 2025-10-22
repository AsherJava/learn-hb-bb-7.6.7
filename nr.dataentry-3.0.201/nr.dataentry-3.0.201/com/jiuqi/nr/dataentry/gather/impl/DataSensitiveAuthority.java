/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nvwa.authz.feign.client.NvwaAuthorityClient
 */
package com.jiuqi.nr.dataentry.gather.impl;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nvwa.authz.feign.client.NvwaAuthorityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSensitiveAuthority
implements IRegisterDataentryRefreshParams {
    @Autowired
    private NvwaAuthorityClient nvwaAuthorityClient;
    private static final String CODE = "dataSensitive";

    @Override
    public IRegisterDataentryRefreshParams.RefreshType getRefreshType() {
        return IRegisterDataentryRefreshParams.RefreshType.FORM_REFRESH;
    }

    @Override
    public String getPramaKey(JtableContext context) {
        return CODE;
    }

    @Override
    public Object getPramaValue(JtableContext context) {
        Authority authority = this.nvwaAuthorityClient.query("22222222-5555-5555-5555-222222222222", NpContextHolder.getContext().getIdentityId(), "99999999-0000-0000-0000-999999999999");
        if (authority == Authority.ALLOW) {
            return true;
        }
        return false;
    }
}

