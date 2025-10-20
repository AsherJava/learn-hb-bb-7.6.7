/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.NvwaRestApiProperties
 *  com.jiuqi.va.shiro.config.MyWebSecurityManage
 *  com.jiuqi.va.shiro.config.MyWebSecurityProvider
 *  org.apache.shiro.realm.Realm
 */
package com.jiuqi.nvwa.login.shiro;

import com.jiuqi.np.core.application.NvwaRestApiProperties;
import com.jiuqi.nvwa.login.shiro.MyUserRealm;
import com.jiuqi.va.shiro.config.MyWebSecurityManage;
import com.jiuqi.va.shiro.config.MyWebSecurityProvider;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyWebSecurityServiceProvider
implements MyWebSecurityProvider {
    @Autowired(required=false)
    private NvwaRestApiProperties nvwaRestApiProperties;

    public MyWebSecurityManage getWebSecurityManager() {
        MyWebSecurityManage mwsm = new MyWebSecurityManage();
        boolean enablePermissions = true;
        if (this.nvwaRestApiProperties != null) {
            enablePermissions = this.nvwaRestApiProperties.isPermissions();
        }
        mwsm.setRealm((Realm)new MyUserRealm(enablePermissions));
        return mwsm;
    }
}

