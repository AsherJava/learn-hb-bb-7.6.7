/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context;

import java.io.Serializable;

public interface ContextIdentity
extends Serializable {
    public String getId();

    public String getTitle();

    default public String getOrgCode() {
        return "";
    }
}

