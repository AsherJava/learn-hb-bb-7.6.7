/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.application;

import com.jiuqi.np.common.exception.NvwaUserNotFoundException;
import com.jiuqi.np.core.context.NpContext;

public interface NpContextForUserProvider {
    public NpContext getTempContext(String var1) throws NvwaUserNotFoundException;
}

