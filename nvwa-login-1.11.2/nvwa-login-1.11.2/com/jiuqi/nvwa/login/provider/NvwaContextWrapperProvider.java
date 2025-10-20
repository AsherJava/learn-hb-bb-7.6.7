/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.login.provider;

import com.jiuqi.nvwa.login.constant.LoginConsts;
import com.jiuqi.nvwa.login.domain.NvwaContext;

public interface NvwaContextWrapperProvider {
    public String getId();

    default public LoginConsts.WrapperType getWrapperType() {
        return LoginConsts.WrapperType.ADD;
    }

    default public Object getUnWrappedObject(NvwaContext nvwaContext) {
        return null;
    }

    default public Object getWrappedObject(NvwaContext nvwaContext) {
        return null;
    }
}

