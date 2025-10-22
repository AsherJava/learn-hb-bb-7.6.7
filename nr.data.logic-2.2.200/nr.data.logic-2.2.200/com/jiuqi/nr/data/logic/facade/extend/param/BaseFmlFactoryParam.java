/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

import java.io.Serializable;

public abstract class BaseFmlFactoryParam
implements Serializable {
    private static final long serialVersionUID = -3028874238398807372L;
    public static final String DEFAULT_FACTORY_NAME = "DEFAULT";

    public String getProviderFactoryName() {
        return DEFAULT_FACTORY_NAME;
    }
}

