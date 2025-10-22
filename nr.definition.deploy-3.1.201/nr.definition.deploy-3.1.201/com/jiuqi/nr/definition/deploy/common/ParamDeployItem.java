/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.common;

import com.jiuqi.nr.definition.common.ParamResourceType;
import java.util.Collections;
import java.util.List;

public class ParamDeployItem {
    private final ParamResourceType type;
    private final String SchemeKey;
    private final List<String> resourceKeys;

    public ParamDeployItem(ParamResourceType type, String SchemeKey) {
        this.type = type;
        this.SchemeKey = SchemeKey;
        this.resourceKeys = Collections.emptyList();
    }

    public ParamDeployItem(ParamResourceType type, String SchemeKey, List<String> resourceKeys) {
        this.type = type;
        this.SchemeKey = SchemeKey;
        this.resourceKeys = resourceKeys;
    }

    public ParamResourceType getType() {
        return this.type;
    }

    public String getSchemeKey() {
        return this.SchemeKey;
    }

    public List<String> getResourceKeys() {
        return this.resourceKeys;
    }
}

