/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.model;

import com.jiuqi.bi.dataset.model.DSModel;

public abstract class DSModelFactory {
    public abstract DSModel createDataSetModel();

    public abstract String getType();
}

