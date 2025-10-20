/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context;

import java.io.Serializable;

public interface ContextUser
extends Serializable {
    public String getId();

    public String getName();

    public String getFullname();

    public String getDescription();

    public String getOrgCode();

    default public String getSecuritylevel() {
        return null;
    }

    default public int getType() {
        return 0;
    }

    default public boolean isAvatar() {
        return false;
    }
}

