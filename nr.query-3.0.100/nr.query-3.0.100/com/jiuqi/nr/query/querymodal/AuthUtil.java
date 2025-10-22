/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.querymodal;

import com.jiuqi.nr.query.service.IQueryAuthority;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil
implements InitializingBean {
    @Autowired
    public IQueryAuthority authority;
    public static AuthUtil instance = new AuthUtil();

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
    }
}

