/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.define;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;

public interface IBizDataModelLoader<C, R> {
    public static final String DEFAULT_KEY = "#";

    public IBdePluginType getPluginType();

    public String getBizDataModelCode();

    default public String getComputationModelCode() {
        return DEFAULT_KEY;
    }

    public R loadData(C var1);
}

