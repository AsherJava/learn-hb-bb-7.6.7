/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.common;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeployItem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ParamDeployContext {
    private final BiConsumer<ParamResourceType, String> progressConsumer;
    private final List<ParamDeployItem> deployItems;

    public ParamDeployContext() {
        this((type, progress) -> {});
    }

    public ParamDeployContext(BiConsumer<ParamResourceType, String> progressConsumer) {
        this.progressConsumer = progressConsumer;
        this.deployItems = new ArrayList<ParamDeployItem>();
    }

    public BiConsumer<ParamResourceType, String> getProgressConsumer() {
        return this.progressConsumer;
    }

    public List<ParamDeployItem> getDeployItems() {
        return this.deployItems;
    }
}

