/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.domain.task;

import com.jiuqi.va.domain.auth.AuthDO;
import java.util.List;

public interface AuthRegisterTask {
    public List<AuthDO> getAuths();

    default public AuthDO initAuth(String name, String title, String umdUrl, String umdName, String mainName, String mainParam, Integer ordinal, Integer adaptflag) {
        AuthDO auth = new AuthDO();
        auth.setName(name);
        auth.setTitle(title);
        auth.setUmdUrl(umdUrl);
        auth.setUmdName(umdName);
        auth.setMainName(mainName);
        auth.setMainParam(mainParam);
        auth.setOrdinal(ordinal);
        auth.setAdaptflag(adaptflag);
        return auth;
    }
}

